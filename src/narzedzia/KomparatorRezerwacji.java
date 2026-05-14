package narzedzia;

import model.Rezerwacja;
import java.util.Comparator;

public class KomparatorRezerwacji implements Comparator<Rezerwacja> {
    @Override
    public int compare(Rezerwacja r1, Rezerwacja r2) {
        // Zmieniliśmy "getDataOd()" na nową nazwę: "getDataPrzyjazdu()"
        return r1.getDataPrzyjazdu().compareTo(r2.getDataPrzyjazdu());
    }
}