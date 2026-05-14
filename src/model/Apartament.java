package model;

public class Apartament extends Pokoj {
    private String nazwaWlasna;
    private boolean maZaciemnienie;

    // Musimy przekazać numer i pojemność do klasy bazowej Pokoj
    public Apartament(int numer, double cenaZaNoc, int pojemnosc, String nazwaWlasna, boolean maZaciemnienie) {
        super(numer, cenaZaNoc, pojemnosc);
        this.nazwaWlasna = nazwaWlasna;
        this.maZaciemnienie = maZaciemnienie;
    }

    public String getNazwaWlasna() {
        return nazwaWlasna;
    }

    @Override
    public String toString() {
        String rolety = maZaciemnienie ? " (Posiada rolety zaciemniające)" : "";
        return "⭐ Apartament '" + nazwaWlasna + "' -> " + super.toString() + rolety;
    }
}