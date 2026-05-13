package logika;

import model.Gosc;
import model.Pokoj;
import model.Rezerwacja;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemHotelowy implements Serializable {
    // Statyczne pole przechowujące jedyną instancję (wzorzec Singleton)
    private static SystemHotelowy instancja;

    // Nasze główne kolekcje danych
    private Map<Integer, Pokoj> pokoje;
    private Set<Gosc> goscie;
    private List<Rezerwacja> rezerwacje;

    // Prywatny konstruktor - nikt z zewnątrz nie zrobi "new SystemHotelowy()"
    private SystemHotelowy() {
        pokoje = new HashMap<>();
        goscie = new HashSet<>();
        rezerwacje = new ArrayList<>();
    }

    // Metoda dostępowa Singletonu
    public static SystemHotelowy getInstancja() {
        if (instancja == null) {
            instancja = new SystemHotelowy();
        }
        return instancja;
    }

    // Metoda przydatna po wczytaniu danych z pliku (deserializacji)
    public static void setInstancja(SystemHotelowy wczytanaInstancja) {
        instancja = wczytanaInstancja;
    }

    public void dodajPokoj(Pokoj p) {
        pokoje.put(p.getNumer(), p); // Numer pokoju to nasz "Klucz" w HashMapie
    }

    public void dodajGoscia(Gosc g) {
        goscie.add(g); // HashSet sam zadba o brak powtórzeń (o ile byśmy nadpisali equals/hashCode, ale na razie nam to wystarczy)
    }

    public void dodajRezerwacje(Rezerwacja r) {
        rezerwacje.add(r);
    }

    public Map<Integer, Pokoj> getPokoje() { return pokoje; }
    public Set<Gosc> getGoscie() { return goscie; }
    public List<Rezerwacja> getRezerwacje() { return rezerwacje; }
}