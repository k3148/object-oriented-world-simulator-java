package swiat.organizmy.zwierzeta;

import swiat.Organizm;
import swiat.Swiat;
import swiat.organizmy.Zwierze;

import java.util.Random;

public class Zolw extends Zwierze {

    private static final Random rand = new Random();

    public Zolw(Swiat swiat, int x, int y) {
        super(swiat, x, y, 2, 1);
    }

    @Override
    public void akcja() {
        if (rand.nextInt(4) == 0) {
            super.akcja();
        }
    }

    @Override
    public void kolizja(Organizm inny) {
        if (inny.getSila() < 5 && inny.czyZwierze()) {
            swiat.dodajZdarzenie(opis() + " odparl atak " + inny.opis());
            swiat.ustawNaPlanszy(inny.getPoprzedniX(), inny.getPoprzedniY(), inny);
        } else {
            super.kolizja(inny);
        }
    }

    @Override
    public char narysuj() {
        return 'Z';
    }

    @Override
    public String getGatunek() {
        return "Zolw";
    }

    @Override
    public boolean czyZwierze() {
        return true;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Zolw(swiat, x, y);
    }

    @Override
    public String zapiszStan() {
        return "Zolw " + getX() + " " + getY() + " " + getWiek() + " " + getSila();
    }

    @Override
    public String getColorHex() {
        return "#228B22";
    }
}
