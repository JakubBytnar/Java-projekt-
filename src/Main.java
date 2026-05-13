import logika.SystemHotelowy;
import model.Gosc;
import model.Pokoj;
import narzedzia.NarzedziaHotelowe;
import wyjatki.ZleDaneWyjatek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    private SystemHotelowy system;

    public Main() {
        // 1. WCZYTYWANIE DANYCH PRZY STARCIE (Deserializacja)
        SystemHotelowy wczytany = NarzedziaHotelowe.wczytajSystem();
        if (wczytany != null) {
            SystemHotelowy.setInstancja(wczytany);
        }
        system = SystemHotelowy.getInstancja();

        // 2. USTAWIENIA OKNA (GUI)
        setTitle("System Zarządzania Hotelem - Projekt Java");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Sami obsłużymy zamykanie
        setLayout(new GridLayout(4, 1, 10, 10)); // Siatka: 4 rzędy, 1 kolumna

        // 3. TWORZENIE PRZYCISKÓW
        JButton btnDodajPokoj = new JButton("Dodaj nowy pokój");
        JButton btnDodajGoscia = new JButton("Dodaj Gościa (Walidacja RegEx)");
        JButton btnStan = new JButton("Pokaż stan hotelu");
        JButton btnWyjdz = new JButton("Zapisz i Wyjdź");

        // 4. LOGIKA PRZYCISKÓW (Event Listenery)

        // Dodawanie pokoju
        btnDodajPokoj.addActionListener(e -> {
            String cenaStr = JOptionPane.showInputDialog("Podaj cenę pokoju za noc (np. 150.50):");
            if (cenaStr != null && !cenaStr.isEmpty()) {
                try {
                    double cena = Double.parseDouble(cenaStr);
                    Pokoj p = new Pokoj(cena);
                    system.dodajPokoj(p);
                    JOptionPane.showMessageDialog(this, "Pomyślnie dodano:\n" + p.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Błąd: Wpisano niepoprawną kwotę!", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Dodawanie gościa (z naszym własnym wyjątkiem!)
        btnDodajGoscia.addActionListener(e -> {
            String imie = JOptionPane.showInputDialog("Podaj imię:");
            String nazwisko = JOptionPane.showInputDialog("Podaj nazwisko:");
            String pesel = JOptionPane.showInputDialog("Podaj PESEL (dokładnie 11 cyfr):");

            if (imie != null && nazwisko != null && pesel != null) {
                try {
                    // Tu może polecieć nasz wyjątek, jeśli RegEx się nie zgadza
                    Gosc g = new Gosc(imie, nazwisko, pesel);
                    system.dodajGoscia(g);
                    JOptionPane.showMessageDialog(this, "Sukces! Dodano:\n" + g.pobierzInformacje());
                } catch (ZleDaneWyjatek ex) {
                    // Łapiemy nasz własny wyjątek i pokazujemy czerwoną ikonkę błędu
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd Walidacji Danych", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Wyświetlanie stanu hotelu
        btnStan.addActionListener(e -> {
            int liczbaPokoi = system.getPokoje().size();
            int liczbaGosci = system.getGoscie().size();
            String ostatniGosc = "";

            // Używamy naszej generycznej metody!
            if (liczbaGosci > 0) {
                Gosc ostatni = NarzedziaHotelowe.pobierzOstatni(new java.util.ArrayList<>(system.getGoscie()));
                ostatniGosc = "\nOstatnio dodany gość: " + ostatni.getImie() + " " + ostatni.getNazwisko();
            }

            JOptionPane.showMessageDialog(this, "Zarejestrowanych pokoi: " + liczbaPokoi +
                    "\nZarejestrowanych gości: " + liczbaGosci + ostatniGosc, "Stan Systemu", JOptionPane.INFORMATION_MESSAGE);
        });

        // Zapisz i Wyjdź
        btnWyjdz.addActionListener(e -> wyjdzZProgramu());

        // Obsługa fizycznego "krzyżyka" w rogu okna
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                wyjdzZProgramu();
            }
        });

        // 5. DODANIE PRZYCISKÓW DO OKNA
        add(btnDodajPokoj);
        add(btnDodajGoscia);
        add(btnStan);
        add(btnWyjdz);

        // Wyśrodkowanie okna na ekranie
        setLocationRelativeTo(null);
    }

    // Metoda zapisująca stan Singletonu do pliku przed wyłączeniem Javy
    private void wyjdzZProgramu() {
        NarzedziaHotelowe.zapiszSystem(system);
        System.exit(0);
    }

    // Punkt startowy całego programu
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
