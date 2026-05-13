package model;

import java.io.Serializable;

public class Pokoj implements Serializable {
    // Statyczne pole (wspólne dla wszystkich obiektów) - posłuży do auto-numeracji
    private static int nastepnyNumer = 100;

    // Blok statyczny - uruchomi się tylko raz przy ładowaniu klasy (wymaganie z wykładu)
    static {
        System.out.println("Ładowanie modułu pokoi... Numeracja startuje od: " + nastepnyNumer);
    }

    private int numer;
    private double cenaZaNoc;
    private boolean czyZajety;

    public Pokoj(double cenaZaNoc) {
        this.numer = nastepnyNumer++; // Przypisuje numer, a potem zwiększa licznik o 1
        this.cenaZaNoc = cenaZaNoc;
        this.czyZajety = false; // Domyślnie nowy pokój jest wolny
    }

    public int getNumer() {
        return numer;
    }

    public double getCenaZaNoc() {
        return cenaZaNoc;
    }

    public boolean isCzyZajety() {
        return czyZajety;
    }

    public void setCzyZajety(boolean czyZajety) {
        this.czyZajety = czyZajety;
    }

    @Override
    public String toString() {
        String status = czyZajety ? "Zajęty" : "Wolny";
        return "Pokój nr " + numer + " | Cena: " + cenaZaNoc + " zł/noc | Status: " + status;
    }
}