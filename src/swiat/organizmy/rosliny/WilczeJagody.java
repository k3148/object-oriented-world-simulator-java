package swiat.organizmy.rosliny;

import swiat.organizmy.Roslina;
import swiat.Organizm;
import swiat.Swiat;

public class WilczeJagody extends Roslina {

    public WilczeJagody(Swiat swiat, int x, int y) {
        super(swiat, x, y, 99);
    }

    @Override
    public void efektPoZjedzeniu(Organizm inny) {
        inny.zabij();
        swiat.usunZPlanszy(inny);
        swiat.dodajZdarzenie(inny.opis() + " zjadl Wilcze Jagody i zginal! Zyl: " + inny.getWiek() + " lat.");
    }

    @Override
    public char narysuj() {
        return 'J';
    }

    @Override
    public String getGatunek() {
        return "WilczeJagody";
    }

    @Override
    public boolean czyZwierze() {
        return false;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new WilczeJagody(swiat, x, y);
    }

    @Override
    public String zapiszStan() {
        return "WilczeJagody " + getX() + " " + getY() + " " + getWiek();
    }

    @Override
    public String getColorHex() {
        return "#4B0082";
    }
}
