package model;

import wyjatki.ZleDaneWyjatek;
import java.io.Serializable;
import java.util.Objects; // NOWOŚĆ: Potrzebne do bezpiecznego porównywania

public class Gosc extends Osoba implements Serializable, Rabat {
    private String pesel;
    private boolean czyVip;

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

    @Override
    public double naliczZnizke(double kwotaPoczatkowa) {
        if (czyVip) {
            return kwotaPoczatkowa * 0.85;
        }
        return kwotaPoczatkowa;
    }

    @Override
    public String pobierzInformacje() {
        String status = czyVip ? " (VIP)" : "";
        return "Gość: " + getImie() + " " + getNazwisko() + " (PESEL: " + pesel + ")" + status;
    }

    // =================================================================
    // NOWOŚĆ: Naprawa błędu z duplikatami (Kontrakt equals i hashCode)
    // =================================================================

    @Override
    public boolean equals(Object o) {
        // 1. Jeśli to ten sam obiekt w pamięci, to na pewno są identyczni
        if (this == o) return true;
        // 2. Jeśli porównywany obiekt jest nullem lub z innej klasy, to nie są tacy sami
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Rzutujemy na Gościa i porównujemy ich PESEL-e
        Gosc gosc = (Gosc) o;
        return Objects.equals(pesel, gosc.pesel);
    }

    @Override
    public int hashCode() {
        // Generuje unikalny kod (tzw. hash) na podstawie numeru PESEL.
        // Dzięki temu Set (zbiór) w Systemie Hotelowym wie, gdzie szukać duplikatów.
        return Objects.hash(pesel);
    }
}