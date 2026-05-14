package narzedzia;

import logika.SystemHotelowy;
import model.Rezerwacja;

import java.io.*;
import java.util.List;

public class NarzedziaHotelowe {
    private static final String PLIK_ZAPISU = "hotel_baza.dat";

    // --- Serializacja ---
    public static void zapiszSystem(SystemHotelowy system) {
        try (ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream(PLIK_ZAPISU))) {
            wy.writeObject(system);
        } catch (IOException e) {
            System.out.println("Błąd zapisu pliku: " + e.getMessage());
        }
    }

    // ZMIANA: Metoda nigdy nie zwróci null (Rada kuzyna - bezpieczeństwo)
    public static SystemHotelowy wczytajSystem() {
        try (ObjectInputStream we = new ObjectInputStream(new FileInputStream(PLIK_ZAPISU))) {
            SystemHotelowy wczytany = (SystemHotelowy) we.readObject();
            System.out.println("Dane systemu zostały pomyślnie wczytane z pliku.");
            return wczytany;
        } catch (FileNotFoundException e) {
            System.out.println("Plik bazy nie istnieje. Inicjalizacja nowego systemu.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Wykryto błąd lub niekompatybilność pliku bazy. Tworzenie nowej bazy danych.");
        }

        // Zamiast zwracać null, zwracamy czysty, działający obiekt Singletona
        return SystemHotelowy.getInstancja();
    }

    // --- Wymaganie: Operacje plikowe (BufferedWriter) ---
    public static void generujParagon(Rezerwacja r) {
        String nazwaPliku = "Paragon_" + r.getGosc().getNazwisko() + ".txt";

        try (BufferedWriter pisacz = new BufferedWriter(new FileWriter(nazwaPliku))) {
            pisacz.write("================================="); pisacz.newLine();
            pisacz.write("         PARAGON HOTELOWY        "); pisacz.newLine();
            pisacz.write("================================="); pisacz.newLine();
            pisacz.write("Gość: " + r.getGosc().getImie() + " " + r.getGosc().getNazwisko()); pisacz.newLine();
            pisacz.write("Pokój nr: " + r.getPokoj().getNumer()); pisacz.newLine();
            pisacz.write("Okres: " + r.getDataPrzyjazdu() + " do " + r.getDataWyjazdu()); pisacz.newLine();

            if (r.getGosc().isCzyVip()) {
                pisacz.write("---------------------------------"); pisacz.newLine();
                pisacz.write("*** NALICZONO ZNIŻKĘ VIP 15% ***"); pisacz.newLine();
            }

            pisacz.write("---------------------------------"); pisacz.newLine();
            pisacz.write("DO ZAPŁATY: " + r.getLacznaKwota() + " zł"); pisacz.newLine();
            pisacz.write("================================="); pisacz.newLine();
        } catch (IOException e) {
            System.out.println("Błąd podczas generowania paragonu: " + e.getMessage());
        }
    }

    // Metoda generyczna
    public static <T> T pobierzOstatni(List<T> lista) {
        if (lista == null || lista.isEmpty()) return null;
        return lista.get(lista.size() - 1);
    }
}