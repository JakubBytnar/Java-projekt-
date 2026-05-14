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

    private static final int MAX_LICZBA_POKOI = 60;

    private static final int LIMIT_POKOI_1_OS = 20;
    private static final int LIMIT_POKOI_2_OS = 40;

    private static final double CENA_1_OS = 100.0;
    private static final double CENA_2_OS = 120.0;
    private static final double CENA_3_OS = 135.0;

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

    // Generator używa teraz profesjonalnych, opisowych stałych
    public void generujPokojeStartowe() {
        if (pokoje.isEmpty()) {
            for (int i = 1; i <= MAX_LICZBA_POKOI; i++) {
                int pojemnosc;
                double cena;

                if (i <= LIMIT_POKOI_1_OS) {
                    pojemnosc = 1;
                    cena = CENA_1_OS;
                }
                else if (i <= LIMIT_POKOI_2_OS) {
                    pojemnosc = 2;
                    cena = CENA_2_OS;
                }
                else {
                    pojemnosc = 3;
                    cena = CENA_3_OS;
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