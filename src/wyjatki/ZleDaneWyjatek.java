package wyjatki;

public class ZleDaneWyjatek extends Exception {
    // Konstruktor przyjmujący wiadomość o błędzie
    public ZleDaneWyjatek(String wiadomosc) {
        super(wiadomosc); // Wywołanie konstruktora klasy bazowej (Exception)
    }
}