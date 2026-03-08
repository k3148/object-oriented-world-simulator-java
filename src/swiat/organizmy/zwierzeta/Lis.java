package swiat.organizmy.zwierzeta;

import swiat.Swiat;
import swiat.organizmy.Zwierze;
import swiat.Organizm;

import java.util.Random;

public class Lis extends Zwierze {

    private static final Random random = new Random();

    public Lis(Swiat swiat, int x, int y) {
        super(swiat, x, y, 3, 7);
    }

    @Override
    public void akcja() {
        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };

        for (int i = 0; i < 10; i++) {
            int kierunek = random.nextInt(4);
            int newX = getX() + dx[kierunek];
            int newY = getY() + dy[kierunek];

            if (!swiat.isWPolu(newX, newY)) continue;

            Organizm cel = swiat.getOrganizm(newX, newY);

            if (cel == null || cel.getSila() <= this.getSila()) {
                swiat.usunZPlanszy(this);
                setPozycja(newX, newY);

                if (cel == null) {
                    swiat.ustawNaPlanszy(newX, newY, this);
                } else {
                    kolizja(cel);
                }

                return;
            } else {
                swiat.dodajZdarzenie(opis() + " wyczuł zagrożenie " + cel.opis());
            }
        }
    }

    @Override
    public char narysuj() {
        return 'L';
    }

    @Override
    public String getGatunek() {
        return "Lis";
    }

    @Override
    public boolean czyZwierze() {
        return true;
    }

    @Override
    public Organizm stworzKopie(int x, int y) {
        return new Lis(swiat, x, y);
    }

    @Override
    public String zapiszStan() {
        return "Lis " + getX() + " " + getY() + " " + getWiek() + " " + getSila();
    }

    @Override
    public String getColorHex() {
        return "#FFA500";
    }
}
