package model;

import java.math.BigDecimal; // NOWOŚĆ

public class Apartament extends Pokoj {
    private String nazwaWlasna;
    private boolean maZaciemnienie;

    // ZMIANA: Konstruktor przyjmuje BigDecimal
    public Apartament(int numer, BigDecimal cenaZaNoc, int pojemnosc, String nazwaWlasna, boolean maZaciemnienie) {
        super(numer, cenaZaNoc, pojemnosc);
        this.nazwaWlasna = nazwaWlasna;
        this.maZaciemnienie = maZaciemnienie;
    }

    public String getNazwaWlasna() { return nazwaWlasna; }

    @Override
    public String toString() {
        String rolety = maZaciemnienie ? " (Posiada rolety zaciemniające)" : "";
        return "⭐ Apartament '" + nazwaWlasna + "' -> " + super.toString() + rolety;
    }
}