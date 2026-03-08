package swiat.organizmy.rosliny;

import swiat.organizmy.Roslina;
import swiat.Organizm;
import swiat.Swiat;

public class Trawa extends Roslina {

    public Trawa(Swiat swiat, int x, int y) {
        super(swiat, x, y, 0);
    }

    @Override
    public void efektPoZjedzeniu(Organizm inny) {
        swiat.dodajZdarzenie(inny.opis() + " zjadl trawe " + opis() + ". Trawa rosla przez: " + getWiek() + " lat.");
    }

    @Override
    public char narysuj() {
        return 'T';
    }

    @Override
    public String getGatunek() {
        return "Trawa";
    }

    @Override
    public boolean czyZwierze() {
        return false;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Trawa(swiat, x, y);
    }

    @Override
    public String zapiszStan() {
        return "Trawa " + getX() + " " + getY() + " " + getWiek();
    }

    @Override
    public String getColorHex() {
        return "#7CFC00";
    }
}
