package narzedzia;

import logika.SystemHotelowy;
import model.Rezerwacja;

import java.io.*;

public class NarzedziaHotelowe {

    private static final String PLIK_ZAPISU = "hotel_baza.dat";

    public static void zapiszSystem(SystemHotelowy system) {
        try (ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream(PLIK_ZAPISU))) {
            wy.writeObject(system);
            System.out.println("System został pomyślnie zapisany do pliku.");
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu systemu: " + e.getMessage());
        }
    }

    public static SystemHotelowy wczytajSystem() {
        try (ObjectInputStream we = new ObjectInputStream(new FileInputStream(PLIK_ZAPISU))) {
            SystemHotelowy wczytany = (SystemHotelowy) we.readObject();
            System.out.println("Dane systemu zostały pomyślnie wczytane z pliku.");
            return wczytany;
        } catch (FileNotFoundException e) {
            System.out.println("Plik bazy nie istnieje. Inicjalizacja nowego systemu.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd podczas wczytywania systemu: " + e.getMessage());
        }
        return SystemHotelowy.getInstancja();
    }

    // ZMIANA: Nowa logika nazywania plików paragonów
    public static void generujParagon(Rezerwacja r) {
        // Dodajemy PESEL do nazwy, aby uniknąć nadpisywania plików osób o tym samym nazwisku
        String nazwaPliku = "Paragon_" + r.getGosc().getNazwisko() + "_" + r.getGosc().getPesel() + ".txt";

        try (BufferedWriter plik = new BufferedWriter(new FileWriter(nazwaPliku))) {
            plik.write("========================================"); plik.newLine();
            plik.write("           PARAGON HOTELOWY             "); plik.newLine();
            plik.write("========================================"); plik.newLine();
            plik.write("Data wystawienia: " + r.getSformatowanyCzas()); plik.newLine();
            plik.write("Gość: " + r.getGosc().getImie() + " " + r.getGosc().getNazwisko()); plik.newLine();
            plik.write("PESEL: " + r.getGosc().getPesel()); plik.newLine();
            plik.write("Pokój: nr " + r.getPokoj().getNumer()); plik.newLine();
            plik.write("Pobyt: " + r.getDataPrzyjazdu() + " do " + r.getDataWyjazdu()); plik.newLine();
            plik.write("----------------------------------------"); plik.newLine();
            plik.write("SUMA DO ZAPŁATY: " + r.getLacznaKwota() + " zł"); plik.newLine();
            plik.write("========================================"); plik.newLine();
            plik.write("Dziękujemy za skorzystanie z naszych usług!");

            System.out.println("Wygenerowano paragon: " + nazwaPliku);
        } catch (IOException e) {
            System.err.println("Błąd podczas generowania paragonu: " + e.getMessage());
        }
    }

    // Metoda generyczna z wykładu W12
    public static <T> T pobierzOstatni(java.util.List<T> lista) {
        if (lista == null || lista.isEmpty()) return null;
        return lista.get(lista.size() - 1);
    }
}