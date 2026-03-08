package swiat.organizmy;

import swiat.Swiat;
import swiat.Organizm;

import java.util.Random;

public abstract class Zwierze extends Organizm {
    private static final Random rand = new Random();

    public Zwierze(Swiat swiat, int x, int y, int sila, int inicjatywa) {
        super(swiat, x, y, sila, inicjatywa);
    }

    @Override
    public void akcja() {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        int dir = rand.nextInt(4);
        int newX = getX() + dx[dir];
        int newY = getY() + dy[dir];

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
    }

    @Override
    public void kolizja(Organizm inny) {
        if (this.getGatunek().equals(inny.getGatunek())) {
            if (this.getWiek() >= 5 && inny.getWiek() >= 5) {
                int[] dx = {-1, 1, 0, 0};
                int[] dy = {0, 0, -1, 1};

                for (int i = 0; i < 4; i++) {
                    int newX = getX() + dx[i];
                    int newY = getY() + dy[i];

                    if (swiat.isWolnePole(newX, newY)) {
                        Organizm dziecko = this.stworzKopie(newX, newY);
                        swiat.dodajOrganizm(dziecko);
                        swiat.dodajZdarzenie(opis() + " rozmnożył się na (" + newX + ", " + newY + ")");
                        return;
                    }
                }
            } else {
                swiat.dodajZdarzenie(opis() + " i " + inny.opis() + " są zbyt młode, żeby się rozmnażać.");
                swiat.usunZPlanszy(this);
                swiat.ustawNaPlanszy(getPoprzedniX(), getPoprzedniY(), this);
            }
        } else {
            if (this.getSila() >= inny.getSila()) {
                if (inny.czyZwierze()) {
                    swiat.dodajZdarzenie(opis() + " zabił " + inny.opis() + ". Żył: " + inny.getWiek() + " lat.");
                }

                inny.efektPoZjedzeniu(this);
                inny.zabij();
                swiat.usunZPlanszy(inny);
                swiat.usunZPlanszy(this);
                setPozycja(inny.getX(), inny.getY());
                swiat.ustawNaPlanszy(getX(), getY(), this);

            } else {
                this.zabij();
                swiat.usunZPlanszy(this);
            }
        }
    }
}
