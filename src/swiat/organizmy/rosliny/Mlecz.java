package swiat.organizmy.rosliny;

import swiat.Swiat;
import swiat.organizmy.Roslina;
import swiat.Organizm;

public class Mlecz extends Roslina {

    public Mlecz(Swiat swiat, int x, int y) {
        super(swiat, x, y, 0);
    }

    @Override
    public void akcja() {
        for (int i = 0; i < 3; i++) {
            super.akcja();
        }
    }

    @Override
    public void efektPoZjedzeniu(Organizm inny) {
        swiat.dodajZdarzenie(inny.opis() + " pozbył się " + opis() +
                " ostatniego w tym sezonie. Mlecz rósł przez: " + getWiek() + " lat.");
    }

    @Override
    public char narysuj() {
        return 'M';
    }

    @Override
    public String getGatunek() {
        return "Mlecz";
    }

    @Override
    public boolean czyZwierze() {
        return false;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Mlecz(swiat, x, y);
    }

    @Override
    public String zapiszStan() {
        return "Mlecz " + getX() + " " + getY() + " " + getWiek();
    }

    @Override
    public String getColorHex() {
        return "#FFFF00";
    }
}
