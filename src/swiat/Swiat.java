package swiat;

import java.util.*;
import java.io.*;

import swiat.organizmy.zwierzeta.*;
import swiat.organizmy.rosliny.*;

public class Swiat {
    private int N, M;
    private Organizm[][] plansza;
    private List<Organizm> organizmy = new ArrayList<>();
    private static final int MAKS_LOGOW = 100;
    private final List<String> logZdarzen = new ArrayList<>();

    public Swiat(int N, int M) {
        this.N = N;
        this.M = M;
        this.plansza = new Organizm[M][N];
    }

    public int getSzerokosc() { return N; }
    public int getWysokosc() { return M; }

    public void dodajOrganizm(Organizm organizm) {
        organizmy.add(organizm);
        ustawNaPlanszy(organizm.getX(), organizm.getY(), organizm);
    }

    public void ustawNaPlanszy(int x, int y, Organizm organizm) {
        if (x >= 0 && x < N && y >= 0 && y < M) {
            plansza[y][x] = organizm;
        }
    }

    public Organizm getOrganizm(int x, int y) {
        if (x >= 0 && x < N && y >= 0 && y < M) {
            return plansza[y][x];
        }
        return null;
    }

    public void usunZPlanszy(Organizm organizm) {
        int x = organizm.getX();
        int y = organizm.getY();
        if (x >= 0 && x < N && y >= 0 && y < M) {
            plansza[y][x] = null;
        }
    }

    public boolean isWPolu(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public boolean isWolnePole(int x, int y) {
        return isWPolu(x, y) && getOrganizm(x, y) == null;
    }

    public void wykonajTure() {
        for (Organizm org : organizmy) {
            if (org.czyZywy()) {
                org.zwiekszWiek();
            }
        }

        organizmy.sort((a, b) -> {
            if (a.getInicjatywa() != b.getInicjatywa())
                return Integer.compare(b.getInicjatywa(), a.getInicjatywa());
            return Integer.compare(b.getWiek(), a.getWiek());
        });

        for (Organizm org : organizmy) {
            if (org.czyZywy()) {
                org.akcja();
            }
        }
    }

    public void dodajZdarzenie(String tekst) {
        if (logZdarzen.size() < MAKS_LOGOW) {
            logZdarzen.add(tekst);
        }
    }

    public void wyczyscLogZdarzen() {
        logZdarzen.clear();
    }

    public String wypiszLogZdarzen() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== AUTOR ===\n");
        sb.append("Kacper Godlewski\nIndeks: 203251\n\n");
        sb.append("=== ZDARZENIA W TEJ TURZE ===\n");
        if (logZdarzen.isEmpty()) {
            sb.append("Brak szczegolnych wydarzen.\n");
        } else {
            for (String zdarzenie : logZdarzen) {
                sb.append("- ").append(zdarzenie).append("\n");
            }
        }
        return sb.toString();
    }

    public void zapiszDoPliku(String nazwaPliku) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nazwaPliku))) {
            writer.println(N + " " + M);
            writer.println(organizmy.size());
            for (Organizm org : organizmy) {
                if (org.czyZywy()) {
                    writer.println(org.zapiszStan());
                }
            }
            dodajZdarzenie("Zapisano stan świata.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wczytajZPliku(String nazwaPliku) {
        try (Scanner scanner = new Scanner(new File(nazwaPliku))) {
            int nowaN = scanner.nextInt();
            int nowaM = scanner.nextInt();
            int liczba = scanner.nextInt();
            scanner.nextLine();

            czyscPlansze();
            inicjujRozmiar(nowaN, nowaM);

            for (int i = 0; i < liczba; i++) {
                String linia = scanner.nextLine();
                String[] tokens = linia.split(" ");

                String gatunek = tokens[0];
                int x = Integer.parseInt(tokens[1]);
                int y = Integer.parseInt(tokens[2]);
                int wiek = Integer.parseInt(tokens[3]);

                Organizm nowy = null;

                switch (gatunek) {
                    case "Wilk" -> nowy = new Wilk(this, x, y);
                    case "Owca" -> nowy = new Owca(this, x, y);
                    case "Lis" -> nowy = new Lis(this, x, y);
                    case "Zolw" -> nowy = new Zolw(this, x, y);
                    case "Antylopa" -> nowy = new Antylopa(this, x, y);
                    case "Czlowiek" -> {
                        int sila = Integer.parseInt(tokens[4]);
                        int aktywne = Integer.parseInt(tokens[5]);
                        int cooldown = Integer.parseInt(tokens[6]);
                        Czlowiek c = new Czlowiek(this, x, y);
                        c.setSila(sila);
                        c.setWiek(wiek);
                        c.wczytajEliksir(aktywne, cooldown);
                        nowy = c;
                    }
                    default -> nowy = switch (gatunek) {
                        case "Trawa" -> new Trawa(this, x, y);
                        case "Mlecz" -> new Mlecz(this, x, y);
                        case "Guarana" -> new Guarana(this, x, y);
                        case "WilczeJagody" -> new WilczeJagody(this, x, y);
                        case "Barszcz" -> new BarszczSosnowskiego(this, x, y);
                        default -> nowy;
                    };
                }

                if (nowy != null) {
                    nowy.setWiek(wiek);
                    dodajOrganizm(nowy);
                }
            }

            dodajZdarzenie("Wczytano stan świata z pliku.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void czyscPlansze() {
        for (Organizm org : organizmy) {
            org.usun();
        }
        organizmy.clear();
        for (int y = 0; y < M; y++) {
            Arrays.fill(plansza[y], null);
        }
    }

    public void inicjujRozmiar(int nowaN, int nowaM) {
        this.N = nowaN;
        this.M = nowaM;
        this.plansza = new Organizm[M][N];
    }

    public Czlowiek getCzlowiek() {
        for (Organizm o : organizmy) {
            if (o instanceof Czlowiek) {
                return (Czlowiek) o;
            }
        }
        return null;
    }
}
