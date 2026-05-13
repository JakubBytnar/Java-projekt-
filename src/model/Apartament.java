package model;

// Apartament dziedziczy po Pokoju
public class Apartament extends Pokoj {
    private String nazwaWlasna; // np. "Apartament Prezydencki"
    private boolean maZaciemnienie;

    public Apartament(double cenaZaNoc, String nazwaWlasna, boolean maZaciemnienie) {
        super(cenaZaNoc); // Wywołanie konstruktora klasy bazowej (Pokoj)
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