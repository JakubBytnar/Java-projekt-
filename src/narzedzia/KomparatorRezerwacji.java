package narzedzia;

import model.Rezerwacja;
import java.util.Comparator;

public class KomparatorRezerwacji implements Comparator<Rezerwacja> {
    @Override
    public int compare(Rezerwacja r1, Rezerwacja r2) {
        // Porównujemy daty przyjazdu (dataOd)
        // Jeśli r1 jest wcześniej niż r2, zwróci wartość ujemną (r1 będzie wyżej na liście)
        return r1.getDataOd().compareTo(r2.getDataOd());
    }
}