package model;

import wyjatki.ZleDaneWyjatek;
import java.io.Serializable;

// Wymaganie: Implementacja interfejsu (implements Rabatowalny)
public class Gosc extends Osoba implements Serializable, Rabat {
    private String pesel;
    private boolean czyVip; // Nowe pole

    public Gosc(String imie, String nazwisko, String pesel, boolean czyVip) throws ZleDaneWyjatek {
        super(imie, nazwisko);
        ustawPesel(pesel);
        this.czyVip = czyVip;
    }

    public String getPesel() { return pesel; }
    public boolean isCzyVip() { return czyVip; }

    public void ustawPesel(String pesel) throws ZleDaneWyjatek {
        if (!pesel.matches("[0-9]{11}")) {
            throw new ZleDaneWyjatek("Niepoprawny numer PESEL! Musi składać się dokładnie z 11 cyfr.");
        }
        this.pesel = pesel;
    }

    // Wymaganie: Zaimplementowana metoda z interfejsu
    @Override
    public double naliczZnizke(double kwotaPoczatkowa) {
        if (czyVip) {
            return kwotaPoczatkowa * 0.85; // 15% zniżki dla VIP
        }
        return kwotaPoczatkowa; // Brak zniżki
    }

    @Override
    public String pobierzInformacje() {
        String status = czyVip ? " (VIP)" : "";
        return "Gość: " + getImie() + " " + getNazwisko() + " (PESEL: " + pesel + ")" + status;
    }
}