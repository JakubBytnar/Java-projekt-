package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rezerwacja implements Serializable {
    private Gosc gosc;
    private Pokoj pokoj;
    private LocalDate dataOd;
    private LocalDate dataDo;
    private List<String> uslugiDodatkowe;

    // Konstruktor 1 (przeciążony) - podajemy dokładne daty
    public Rezerwacja(Gosc gosc, Pokoj pokoj, LocalDate dataOd, LocalDate dataDo) {
        this.gosc = gosc;
        this.pokoj = pokoj;
        this.dataOd = dataOd;
        this.dataDo = dataDo;
        this.uslugiDodatkowe = new ArrayList<>();
        this.pokoj.setCzyZajety(true); // Oznaczamy przypisany pokój jako zajęty
    }

    // Konstruktor 2 (przeciążony) - domyślnie rezerwacja na 1 noc od dzisiaj
    public Rezerwacja(Gosc gosc, Pokoj pokoj) {
        this(gosc, pokoj, LocalDate.now(), LocalDate.now().plusDays(1));
    }

    // Metoda wykorzystująca zmienną liczbę argumentów (varargs) z wykładów
    public void dodajUslugi(String... uslugi) {
        for (String usluga : uslugi) {
            uslugiDodatkowe.add(usluga);
        }
    }

    public Pokoj getPokoj() {
        return pokoj;
    }

    public LocalDate getDataOd() {
        return dataOd;
    }

    @Override
    public String toString() {
        return "Rezerwacja: " + gosc.getImie() + " " + gosc.getNazwisko() +
                " | Pokój nr " + pokoj.getNumer() + " | Od: " + dataOd + " Do: " + dataDo +
                " | Dodatki: " + uslugiDodatkowe;
    }
}