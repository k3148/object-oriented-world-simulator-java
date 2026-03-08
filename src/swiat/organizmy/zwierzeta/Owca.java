package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Zwierze;
import swiat.Organizm;

public class Owca extends Zwierze {

    public Owca(Swiat swiat, int x, int y) {
        super(swiat, x, y, 4, 4);
    }

    @Override
    public char narysuj() {
        return 'O';
    }

    @Override
    public String getGatunek() {
        return "Owca";
    }

    @Override
    public boolean czyZwierze() {
        return true;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Owca(swiat, x, y);
    }

    @Override
    public String zapiszStan() {
        return "Owca " + getX() + " " + getY() + " " + getWiek() + " " + getSila();
    }

    @Override
    public String getColorHex() {
        return "#FFFFFF";
    }
}
