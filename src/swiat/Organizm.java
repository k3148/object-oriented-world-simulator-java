package swiat;

public abstract class Organizm {
    protected int sila, inicjatywa;
    protected int x, y, staryX, staryY;
    protected int wiek = 0;
    protected boolean zywy = true;
    protected Swiat swiat;

    public Organizm(Swiat swiat, int x, int y, int sila, int inicjatywa) {
        this.swiat = swiat;
        this.x = x;
        this.y = y;
        this.staryX = x;
        this.staryY = y;
        this.sila = sila;
        this.inicjatywa = inicjatywa;
    }

    public abstract void akcja();
    public abstract void kolizja(Organizm inny);
    public void efektPoZjedzeniu(Organizm inny) {
    }
    public abstract char narysuj();
    public abstract String getGatunek();
    public abstract String zapiszStan();
    public abstract boolean czyZwierze();
    public abstract Organizm stworzKopie(int x, int y);

    public int getX() { return x; }
    public int getY() { return y; }
    public int getPoprzedniX() { return staryX; }
    public int getPoprzedniY() { return staryY; }

    public int getSila() { return sila; }
    public int getInicjatywa() { return inicjatywa; }
    public boolean czyZywy() { return zywy; }
    public void zabij() { zywy = false; }

    public void setPozycja(int newX, int newY) {
        this.staryX = this.x;
        this.staryY = this.y;
        this.x = newX;
        this.y = newY;
    }

    public void zwiekszSile(int value) {
        this.sila += value;
    }

    public int getWiek() { return wiek; }
    public void zwiekszWiek() { wiek++; }

    public void setSila(int s) { this.sila = s; }
    public void setWiek(int w) { this.wiek = w; }

    public String opis() {
        return getGatunek() + "(s=" + sila + ", i=" + inicjatywa + ")";
    }

    public void usun() {
        swiat.usunZPlanszy(this);
        this.zabij();
    }

    public String getColorHex() {
        return "#000000";
    }
}
