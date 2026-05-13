import java.io.Serializable;

// Dodajemy implements Serializable, żeby później móc zapisywać obiekty do pliku
public abstract class Osoba implements Serializable {
    private String imie;
    private String nazwisko;

    public Osoba(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    // Metoda abstrakcyjna - każda klasa dziedzicząca musi mieć własną implementację tej metody
    public abstract String pobierzInformacje();
}