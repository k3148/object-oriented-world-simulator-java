package swiat.organizmy.rosliny;

import swiat.Swiat;
import swiat.organizmy.Roslina;
import swiat.Organizm;

public class Guarana extends Roslina {

    public Guarana(Swiat swiat, int x, int y) {
        super(swiat, x, y, 0);
    }

    @Override
    public void efektPoZjedzeniu(Organizm inny) {
        inny.zwiekszSile(3);
        swiat.dodajZdarzenie(inny.opis() + " zjadł Guaranę i zyskał +3 siły. Guarana rosła przez: " + getWiek() + " lat.");
    }

    @Override
    public char narysuj() {
        return 'G';
    }

    @Override
    public String getGatunek() {
        return "Guarana";
    }

    @Override
    public boolean czyZwierze() {
        return false;
    }

    @Override
    public String zapiszStan() {
        return "Guarana " + getX() + " " + getY() + " " + getWiek();
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Guarana(swiat, x, y);
    }

    @Override
    public String getColorHex() {
        return "#FF0000";
    }
}
