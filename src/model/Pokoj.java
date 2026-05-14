package model;

import java.io.Serializable;
import java.math.BigDecimal; // NOWOŚĆ: Profesjonalny typ do pieniędzy

public class Pokoj implements Serializable {

    static {
        System.out.println("Ładowanie modułu pokoi... Przygotowywanie struktury na 60 pokoi.");
    }

    private int numer;
    private BigDecimal cenaZaNoc; // ZMIANA
    private boolean czyZajety;
    private int pojemnosc;

    public Pokoj(int numer, BigDecimal cenaZaNoc, int pojemnosc) {
        this.numer = numer;
        this.cenaZaNoc = cenaZaNoc;
        this.pojemnosc = pojemnosc;
        this.czyZajety = false;
    }

    public int getNumer() { return numer; }
    public BigDecimal getCenaZaNoc() { return cenaZaNoc; }
    public int getPojemnosc() { return pojemnosc; }
    public boolean isCzyZajety() { return czyZajety; }
    public void setCzyZajety(boolean czyZajety) { this.czyZajety = czyZajety; }

    @Override
    public String toString() {
        String status = czyZajety ? "Zajęty" : "Wolny";
        return "Pokój nr " + numer + " (" + pojemnosc + "-osobowy) | Cena: " + cenaZaNoc + " zł/noc | Status: " + status;
    }
}