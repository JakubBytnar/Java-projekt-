package model;

import java.io.Serializable;

public class Pokoj implements Serializable {

    // Blok statyczny - zostawiamy, żeby mieć punkty z wykładów
    static {
        System.out.println("Ładowanie modułu pokoi... Przygotowywanie struktury na 60 pokoi.");
    }

    private int numer;
    private double cenaZaNoc;
    private boolean czyZajety;
    private int pojemnosc; // Nowość: określa ilu osobowy jest pokój

    // Nowy konstruktor: sami podajemy numer i pojemność przy generowaniu
    public Pokoj(int numer, double cenaZaNoc, int pojemnosc) {
        this.numer = numer;
        this.cenaZaNoc = cenaZaNoc;
        this.pojemnosc = pojemnosc;
        this.czyZajety = false; // Domyślnie nowy pokój jest wolny
    }

    public int getNumer() {
        return numer;
    }

    public double getCenaZaNoc() {
        return cenaZaNoc;
    }

    public int getPojemnosc() {
        return pojemnosc; // Nowy getter do odczytu liczby miejsc
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
        // Dodaliśmy pojemność do wyświetlania w okienku
        return "Pokój nr " + numer + " (" + pojemnosc + "-osobowy) | Cena: " + cenaZaNoc + " zł/noc | Status: " + status;
    }
}