import java.io.Serializable;

public class Gosc extends Osoba implements Serializable {
    private String pesel;

    // Musimy dodać "throws ZleDaneWyjatek", bo nasza metoda w środku może rzucić ten błąd
    public Gosc(String imie, String nazwisko, String pesel) throws ZleDaneWyjatek {
        super(imie, nazwisko); // Wywołanie konstruktora klasy bazowej (Osoba)
        ustawPesel(pesel);     // Używamy metody z walidacją zamiast zwykłego przypisania
    }

    public String getPesel() {
        return pesel;
    }

    // Walidacja danych z wykorzystaniem RegEx
    public void ustawPesel(String pesel) throws ZleDaneWyjatek {
        // [0-9]{11} oznacza dokładnie 11 cyfr w przedziale od 0 do 9
        if (!pesel.matches("[0-9]{11}")) {
            // Jeśli PESEL nie pasuje do wzorca, rzucamy nasz własny wyjątek
            throw new ZleDaneWyjatek("Niepoprawny numer PESEL! Musi składać się dokładnie z 11 cyfr.");
        }
        this.pesel = pesel;
    }

    // Nadpisujemy metodę abstrakcyjną z klasy bazowej
    @Override
    public String pobierzInformacje() {
        return "Gość: " + getImie() + " " + getNazwisko() + " (PESEL: " + pesel + ")";
    }
}