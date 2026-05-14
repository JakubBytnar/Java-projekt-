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

    public void generujPokojeStartowe() {
        if (pokoje.isEmpty()) {
            for (int i = 1; i <= 60; i++) {
                int pojemnosc;
                double cena;

                if (i <= 20) {
                    pojemnosc = 1;
                    cena = 100.0; // 1-osobowy za 100 zł
                }
                else if (i <= 40) {
                    pojemnosc = 2;
                    cena = 120.0; // 2-osobowy za 120 zł
                }
                else {
                    pojemnosc = 3;
                    cena = 135.0; // 3-osobowy za 135 zł
                }

                pokoje.put(i, new Pokoj(i, cena, pojemnosc));
            }
        }
    }

    public Pokoj znajdzWolnyPokoj(int wymaganaPojemnosc) {
        for (Pokoj p : pokoje.values()) {
            if (!p.isCzyZajety() && p.getPojemnosc() == wymaganaPojemnosc) {
                return p;
            }
        }
        return null;
    }

    public void dodajGoscia(Gosc g) { goscie.add(g); }
    public void dodajRezerwacje(Rezerwacja r) { rezerwacje.add(r); }

    public Map<Integer, Pokoj> getPokoje() { return pokoje; }
    public Set<Gosc> getGoscie() { return goscie; }
    public List<Rezerwacja> getRezerwacje() { return rezerwacje; }
}