package swiat.organizmy.rosliny;

import swiat.Swiat;
import swiat.organizmy.Roslina;
import swiat.Organizm;

public class BarszczSosnowskiego extends Roslina {

    public BarszczSosnowskiego(Swiat swiat, int x, int y) {
        super(swiat, x, y, 10);
    }

    @Override
    public void akcja() {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int nx = getX() + dx[i];
            int ny = getY() + dy[i];

            if (swiat.isWPolu(nx, ny)) {
                Organizm cel = swiat.getOrganizm(nx, ny);
                if (cel != null && cel.czyZwierze()) {
                    swiat.dodajZdarzenie(cel.opis() + " zatruł się Barszczem Sosnowskiego! Żył: " + cel.getWiek() + " lat.");
                    cel.zabij();
                    swiat.usunZPlanszy(cel);
                }
            }
        }
    }

    @Override
    public void kolizja(Organizm inny) {
    }

    @Override
    public void efektPoZjedzeniu(Organizm inny) {
        inny.zabij();
        swiat.usunZPlanszy(inny);
        swiat.dodajZdarzenie(inny.opis() + " zjadł Barszcz Sosnowskiego i zginął w męczarniach. Żył: " + inny.getWiek() + " lat.");
    }

    @Override
    public char narysuj() {
        return 'B';
    }

    @Override
    public String getGatunek() {
        return "BarszczSosnowskiego";
    }

    @Override
    public String zapiszStan() {
        return "Barszcz " + getX() + " " + getY() + " " + getWiek();
    }

    @Override
    public boolean czyZwierze() {
        return false;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new BarszczSosnowskiego(swiat, x, y);
    }

    @Override
    public String getColorHex() {
        return "#ADFF2F";
    }

}
