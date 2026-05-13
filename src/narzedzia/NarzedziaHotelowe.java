package narzedzia;

import logika.SystemHotelowy;

import java.io.*;
import java.util.List;

public class NarzedziaHotelowe {
    private static final String PLIK_ZAPISU = "hotel_baza.dat";

    // Zapis do pliku (Serializacja)
    public static void zapiszSystem(SystemHotelowy system) {
        // Używamy try-with-resources, które automatycznie zamknie strumienie
        try (ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream(PLIK_ZAPISU))) {
            wy.writeObject(system);
            System.out.println("Dane systemu zostały pomyślnie zapisane.");
        } catch (IOException e) {
            System.out.println("Błąd zapisu pliku: " + e.getMessage());
        }
    }

    // Odczyt z pliku (Deserializacja)
    public static SystemHotelowy wczytajSystem() {
        try (ObjectInputStream we = new ObjectInputStream(new FileInputStream(PLIK_ZAPISU))) {
            return (SystemHotelowy) we.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Plik nie istnieje. System uruchomi się z pustą bazą.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Błąd odczytu pliku: " + e.getMessage());
        }
        return null; // Zwraca null, jeśli odczyt się nie powiedzie
    }

    // Metoda generyczna (Wymaganie z wykładu)
    // Pozwala wyciągnąć ostatni element z dowolnej listy (List<String>, List<Gosc> itp.)
    public static <T> T pobierzOstatni(List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        return lista.get(lista.size() - 1);
    }
}