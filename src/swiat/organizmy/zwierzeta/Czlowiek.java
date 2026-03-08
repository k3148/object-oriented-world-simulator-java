package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Zwierze;
import swiat.Organizm;

public class Czlowiek extends Zwierze {

    private int cooldown;
    private int aktywneTury;
    private int kierunekX;
    private int kierunekY;
    private int silaPrzedEliksirem;

    public Czlowiek(Swiat swiat, int x, int y) {
        super(swiat, x, y, 5, 4);
        this.cooldown = 0;
        this.aktywneTury = 0;
        this.kierunekX = 0;
        this.kierunekY = 0;
        this.silaPrzedEliksirem = 5;
    }

    @Override
    public void akcja() {
        if (!czyZywy()) return;
        if (kierunekX == 0 && kierunekY == 0) return;

        if (aktywneTury > 0) {
            aktywneTury--;
            if (getSila() > silaPrzedEliksirem) {
                setSila(getSila() - 1);
            }
            if (aktywneTury == 0) {
                setSila(silaPrzedEliksirem);
                cooldown = 5;
            }
        } else if (cooldown > 0) {
            cooldown--;
        }

        int newX = getX() + kierunekX;
        int newY = getY() + kierunekY;

        if (swiat.isWPolu(newX, newY)) {
            Organizm cel = swiat.getOrganizm(newX, newY);
            if (cel == null) {
                swiat.usunZPlanszy(this);
                setPozycja(newX, newY);
                swiat.ustawNaPlanszy(newX, newY, this);
            } else {
                kolizja(cel);
            }
        }

        kierunekX = 0;
        kierunekY = 0;
    }

    @Override
    public void kolizja(Organizm inny) {
        if (getSila() >= inny.getSila()) {
            if (inny.czyZwierze()) {
                swiat.dodajZdarzenie(opis() + " pokonał " + inny.opis() + ". Żył: " + inny.getWiek() + " lat.");
            } else {
                inny.efektPoZjedzeniu(this);
            }
            inny.zabij();
            swiat.usunZPlanszy(inny);

            swiat.usunZPlanszy(this);
            setPozycja(inny.getX(), inny.getY());
            swiat.ustawNaPlanszy(getX(), getY(), this);
        } else {
            if (inny.czyZwierze()) {
                swiat.dodajZdarzenie(opis() + " został zabity przez " + inny.opis() + ". Żył: " + getWiek() + " lat.");
            } else {
                swiat.dodajZdarzenie(opis() + " zatruł się " + inny.opis() + ". Żył: " + getWiek() + " lat.");
            }
            zabij();
            swiat.usunZPlanszy(this);
        }
    }

    public void aktywujEliksir() {
        if (!czyZywy()) return;

        if (cooldown == 0 && aktywneTury == 0) {
            silaPrzedEliksirem = getSila();
            setSila(10);
            aktywneTury = 5;
            swiat.dodajZdarzenie("Człowiek wypił Eliksir! Siła: 10");
        } else {
            swiat.dodajZdarzenie("Człowiek nie może jeszcze użyć eliksiru.");
        }
    }

    public void ustawKierunek(int dx, int dy) {
        this.kierunekX = dx;
        this.kierunekY = dy;
    }

    public void wczytajEliksir(int aktywne, int cd) {
        this.aktywneTury = aktywne;
        this.cooldown = cd;
    }

    @Override
    public char narysuj() {
        return 'C';
    }

    @Override
    public String getGatunek() {
        return "Czlowiek";
    }

    @Override
    public String zapiszStan() {
        return "Czlowiek " + getX() + " " + getY() + " " +
                getWiek() + " " + getSila() + " " + aktywneTury + " " + cooldown;
    }

    @Override
    public boolean czyZwierze() {
        return true;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Czlowiek(swiat, x, y);
    }

    @Override
    public String getColorHex() {
        return "#F0DCAD";
    }
}
