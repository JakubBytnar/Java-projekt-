package model;

import wyjatki.ZleDaneWyjatek;
import java.io.Serializable;
import java.math.BigDecimal; // NOWOŚĆ
import java.util.Objects;

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

    // ZMIANA: Implementacja zniżki przy użyciu BigDecimal
    @Override
    public BigDecimal naliczZnizke(BigDecimal kwotaPoczatkowa) {
        if (czyVip) {
            // Mnożymy przez 0.85 (czyli ucinamy 15%) przy pomocy metody multiply()
            BigDecimal mnoznik = new BigDecimal("0.85");
            return kwotaPoczatkowa.multiply(mnoznik);
        }
        return kwotaPoczatkowa;
    }

    @Override
    public String pobierzInformacje() {
        String status = czyVip ? " (VIP)" : "";
        return "Gość: " + getImie() + " " + getNazwisko() + " (PESEL: " + pesel + ")" + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gosc gosc = (Gosc) o;
        return Objects.equals(pesel, gosc.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }
}