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
    private static SystemHotelowy instancja;

    private Map<Integer, Pokoj> pokoje;
    private Set<Gosc> goscie;
    private List<Rezerwacja> rezerwacje;

    private SystemHotelowy() {
        pokoje = new HashMap<>();
        goscie = new HashSet<>();
        rezerwacje = new ArrayList<>();
    }

    public static SystemHotelowy getInstancja() {
        if (instancja == null) {
            instancja = new SystemHotelowy();
        }
        return instancja;
    }

    public static void setInstancja(SystemHotelowy wczytanaInstancja) {
        instancja = wczytanaInstancja;
    }

    // NOWOŚĆ 1: Metoda generująca 60 pokoi przy pierwszym uruchomieniu
    public void generujPokojeStartowe() {
        if (pokoje.isEmpty()) {
            for (int i = 1; i <= 60; i++) {
                int pojemnosc;
                double cena;
                if (i <= 20) { pojemnosc = 1; cena = 120.0; }
                else if (i <= 40) { pojemnosc = 2; cena = 200.0; }
                else { pojemnosc = 3; cena = 270.0; }

                pokoje.put(i, new Pokoj(i, cena, pojemnosc));
            }
        }
    }

    // NOWOŚĆ 2: Szuka pierwszego wolnego pokoju o wybranej pojemności
    public Pokoj znajdzWolnyPokoj(int wymaganaPojemnosc) {
        for (Pokoj p : pokoje.values()) {
            if (!p.isCzyZajety() && p.getPojemnosc() == wymaganaPojemnosc) {
                return p;
            }
        }
        return null; // Zwraca null, jeśli hotel jest pełny
    }

    public void dodajGoscia(Gosc g) { goscie.add(g); }
    public void dodajRezerwacje(Rezerwacja r) { rezerwacje.add(r); }

    public Map<Integer, Pokoj> getPokoje() { return pokoje; }
    public Set<Gosc> getGoscie() { return goscie; }
    public List<Rezerwacja> getRezerwacje() { return rezerwacje; }
}