import gui.OknoRecepcji;
import logika.SystemHotelowy;
import narzedzia.NarzedziaHotelowe;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // 1. Warstwa Danych (Backend): Wczytywanie bazy lub tworzenie nowej
        SystemHotelowy system = NarzedziaHotelowe.wczytajSystem();
        SystemHotelowy.setInstancja(system);
        system.generujPokojeStartowe();

        // 2. Warstwa Widoku (Frontend): Uruchomienie interfejsu graficznego
        SwingUtilities.invokeLater(() -> {
            OknoRecepcji okno = new OknoRecepcji(system);
            okno.setVisible(true);
        });
    }
}