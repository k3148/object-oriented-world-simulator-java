package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Zwierze;
import swiat.Organizm;

import java.util.Random;

public class Antylopa extends Zwierze {
    private static final Random rand = new Random();

    public Antylopa(Swiat swiat, int x, int y) {
        super(swiat, x, y, 4, 4);
    }

    @Override
    public void akcja() {
        int[] dx = {-2, 0, 2, 0};
        int[] dy = {0, -2, 0, 2};
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
        if (inny.czyZwierze()) {
            if (rand.nextBoolean()) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int newX = getX() + i;
                        int newY = getY() + j;
                        if (swiat.isWolnePole(newX, newY)) {
                            swiat.usunZPlanszy(this);
                            setPozycja(newX, newY);
                            swiat.ustawNaPlanszy(newX, newY, this);
                            swiat.dodajZdarzenie(opis() + " uciekła z walki na (" + newX + ", " + newY + ")");
                            return;
                        }
                    }
                }
            }
        }
        super.kolizja(inny);
    }

    @Override
    public char narysuj() {
        return 'A';
    }

    @Override
    public String getGatunek() {
        return "Antylopa";
    }

    @Override
    public String zapiszStan() {
        return "Antylopa " + getX() + " " + getY() + " " + getWiek() + " " + getSila();
    }

    @Override
    public boolean czyZwierze() {
        return true;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Antylopa(swiat, x, y);
    }

    @Override
    public String getColorHex() {
        return "#D2691E";
    }
}
