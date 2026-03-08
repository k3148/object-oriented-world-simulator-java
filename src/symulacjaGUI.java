import swiat.Organizm;
import swiat.Swiat;
import swiat.organizmy.rosliny.*;
import swiat.organizmy.zwierzeta.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class symulacjaGUI extends JFrame {
    private final Swiat swiat;
    private JPanel planszaPanel;
    private final JTextArea logTextArea;
    private Czlowiek czlowiek;

    public symulacjaGUI(int szerokosc, int wysokosc) {
        setTitle("Symulacja Świata");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        swiat = new Swiat(szerokosc, wysokosc);
        czlowiek = new Czlowiek(swiat, szerokosc / 2, wysokosc / 2);
        swiat.dodajOrganizm(czlowiek);

        generujLosoweOrganizmy(szerokosc, wysokosc);

        planszaPanel = new JPanel(new GridLayout(wysokosc, szerokosc));
        add(planszaPanel, BorderLayout.CENTER);

        logTextArea = new JTextArea(8, 40);
        logTextArea.setEditable(false);
        add(new JScrollPane(logTextArea), BorderLayout.SOUTH);

        JPanel controlPanel = new JPanel();
        JButton turaButton = new JButton("Następna tura");
        JButton zapiszButton = new JButton("Zapisz");
        JButton wczytajButton = new JButton("Wczytaj");

        controlPanel.add(turaButton);
        controlPanel.add(zapiszButton);
        controlPanel.add(wczytajButton);
        add(controlPanel, BorderLayout.NORTH);

        turaButton.addActionListener(e -> wykonajTure());
        zapiszButton.addActionListener(e -> zapiszDoPliku());
        wczytajButton.addActionListener(e -> wczytajZPliku());

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (Character.toLowerCase(e.getKeyChar())) {
                    case 'w' -> czlowiek.ustawKierunek(0, -1);
                    case 's' -> czlowiek.ustawKierunek(0, 1);
                    case 'a' -> czlowiek.ustawKierunek(-1, 0);
                    case 'd' -> czlowiek.ustawKierunek(1, 0);
                    case 'e' -> czlowiek.aktywujEliksir();
                    case ' ' -> wykonajTure();
                }
            }
        });

        setFocusable(true);
        rysujPlansze();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generujLosoweOrganizmy(int szerokosc, int wysokosc) {
        Random random = new Random();
        int liczbaOrganizmow = (szerokosc + wysokosc) / 2;

        for (int i = 0; i < liczbaOrganizmow; ++i) {
            int x = random.nextInt(szerokosc);
            int y = random.nextInt(wysokosc);

            if (swiat.getOrganizm(x, y) != null || (x == szerokosc / 2 && y == wysokosc / 2)) {
                --i;
                continue;
            }

            Organizm nowy = switch (random.nextInt(10)) {
                case 0 -> new Wilk(swiat, x, y);
                case 1 -> new Owca(swiat, x, y);
                case 2 -> new Lis(swiat, x, y);
                case 3 -> new Zolw(swiat, x, y);
                case 4 -> new Antylopa(swiat, x, y);
                case 5 -> new Trawa(swiat, x, y);
                case 6 -> new Mlecz(swiat, x, y);
                case 7 -> new Guarana(swiat, x, y);
                case 8 -> new WilczeJagody(swiat, x, y);
                case 9 -> new BarszczSosnowskiego(swiat, x, y);
                default -> null;
            };

            swiat.dodajOrganizm(nowy);
        }
    }

    private void wykonajTure() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                swiat.wykonajTure();
                return null;
            }

            @Override
            protected void done() {
                rysujPlansze();
                String log = swiat.wypiszLogZdarzen();
                swiat.wyczyscLogZdarzen();
                logTextArea.setText(log);
            }
        }.execute();
    }

    private void rysujPlansze() {
        planszaPanel.removeAll();
        planszaPanel.setLayout(new GridLayout(swiat.getWysokosc(), swiat.getSzerokosc()));

        for (int y = 0; y < swiat.getWysokosc(); y++) {
            for (int x = 0; x < swiat.getSzerokosc(); x++) {
                Organizm org = swiat.getOrganizm(x, y);
                JButton pole = new JButton();

                if (org != null) {
                    pole.setBackground(Color.decode(org.getColorHex()));
                    pole.setText(org.narysuj() + "");
                } else {
                    pole.setBackground(Color.LIGHT_GRAY);
                    pole.setText(" ");
                }
                pole.setOpaque(true);
                pole.setBorderPainted(false);

                pole.setEnabled(true);

                final int fx = x;
                final int fy = y;

                pole.addActionListener(e -> {
                    if (swiat.getOrganizm(fx, fy) != null) {
                        int odpowiedz = JOptionPane.showConfirmDialog(
                                this,
                                "Pole jest zajęte. Czy chcesz usunąć organizm?",
                                "Usuń organizm",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (odpowiedz == JOptionPane.YES_OPTION) {
                            Organizm organizm = swiat.getOrganizm(fx, fy);
                            swiat.usunZPlanszy(organizm);
                            rysujPlansze();
                        }
                        return;
                    }

                    String[] organizmy = {
                            "Wilk", "Owca", "Lis", "Zolw", "Antylopa",
                            "Trawa", "Mlecz", "Guarana", "Wilcze Jagody", "Barszcz Sosnowskiego"
                    };

                    String wybor = (String) JOptionPane.showInputDialog(
                            this,
                            "Wybierz organizm do dodania:",
                            "Dodaj organizm",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            organizmy,
                            organizmy[0]
                    );

                    if (wybor != null) {
                        Organizm nowy = switch (wybor) {
                            case "Wilk" -> new Wilk(swiat, fx, fy);
                            case "Owca" -> new Owca(swiat, fx, fy);
                            case "Lis" -> new Lis(swiat, fx, fy);
                            case "Zolw" -> new Zolw(swiat, fx, fy);
                            case "Antylopa" -> new Antylopa(swiat, fx, fy);
                            case "Trawa" -> new Trawa(swiat, fx, fy);
                            case "Mlecz" -> new Mlecz(swiat, fx, fy);
                            case "Guarana" -> new Guarana(swiat, fx, fy);
                            case "Wilcze Jagody" -> new WilczeJagody(swiat, fx, fy);
                            case "Barszcz Sosnowskiego" -> new BarszczSosnowskiego(swiat, fx, fy);
                            default -> null;
                        };

                        if (nowy != null) {
                            swiat.dodajOrganizm(nowy);
                            rysujPlansze();
                        }
                    }
                });

                planszaPanel.add(pole);
            }
        }

        planszaPanel.revalidate();
        planszaPanel.repaint();
    }


    private void zapiszDoPliku() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            swiat.zapiszDoPliku(file.getAbsolutePath());
            logTextArea.setText("Zapisano świat do: " + file.getName());
        }
    }

    private void wczytajZPliku() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            swiat.wczytajZPliku(file.getAbsolutePath());
            czlowiek = swiat.getCzlowiek();

            remove(planszaPanel);
            planszaPanel = new JPanel(new GridLayout(swiat.getWysokosc(), swiat.getSzerokosc()));
            add(planszaPanel, BorderLayout.CENTER);

            rysujPlansze();
            logTextArea.setText("Wczytano świat z: " + file.getName());

            revalidate();
            pack();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int szer = Integer.parseInt(JOptionPane.showInputDialog("Podaj szerokość planszy:"));
            int wys = Integer.parseInt(JOptionPane.showInputDialog("Podaj wysokość planszy:"));
            new symulacjaGUI(szer, wys);
        });
    }
}
