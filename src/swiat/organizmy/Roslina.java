package swiat.organizmy;

import swiat.Swiat;
import swiat.Organizm;
import java.util.Random;

public abstract class Roslina extends Organizm {
    private static final Random rand = new Random();

    public Roslina(Swiat swiat, int x, int y, int sila) {
        super(swiat, x, y, sila, 0);
    }

    @Override
    public void akcja() {
        if (rand.nextInt(100) >= 5) return;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int kierunek = rand.nextInt(4);
            int newX = getX() + dx[kierunek];
            int newY = getY() + dy[kierunek];

            if (swiat.isWolnePole(newX, newY)) {
                Organizm nowaRoslina = stworzKopie(newX, newY);
                swiat.dodajOrganizm(nowaRoslina);
                swiat.dodajZdarzenie(opis() + " rozrosła się na (" + newX + ", " + newY + ")");
                break;
            }
        }
    }

    @Override
    public void kolizja(Organizm inny) {
    }

    @Override
    public void efektPoZjedzeniu(Organizm inny) {
    }
}
