package swiat.organizmy.zwierzeta;

import swiat.organizmy.Zwierze;
import swiat.Organizm;
import swiat.Swiat;

public class Wilk extends Zwierze {

    public Wilk(Swiat swiat, int x, int y) {
        super(swiat, x, y, 9, 5);
    }

    @Override
    public char narysuj() {
        return 'W';
    }

    @Override
    public String getGatunek() {
        return "Wilk";
    }

    @Override
    public boolean czyZwierze() {
        return true;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Wilk(swiat, x, y);
    }

    @Override
    public String zapiszStan() {
        return "Wilk " + getX() + " " + getY() + " " + getWiek() + " " + getSila();
    }

    @Override
    public String getColorHex() {
        return "#808080";
    }
}
