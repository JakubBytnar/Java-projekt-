import logika.SystemHotelowy;
import model.Gosc;
import model.Pokoj;
import model.Rezerwacja;
import narzedzia.NarzedziaHotelowe;
import wyjatki.ZleDaneWyjatek;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

public class Main extends JFrame {
    private SystemHotelowy system;
    private DefaultTableModel modelTabeli;
    private JTable tabelaRezerwacji;

    public Main() {
        // Wczytywanie bazy i generowanie 60 pokoi
        SystemHotelowy wczytany = NarzedziaHotelowe.wczytajSystem();
        if (wczytany != null) {
            SystemHotelowy.setInstancja(wczytany);
        }
        system = SystemHotelowy.getInstancja();
        system.generujPokojeStartowe();

        // Ustawienia okna
        setTitle("System Zarządzania Hotelem - Recepcja PRO");
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Tworzenie tabeli
        String[] kolumny = {"Moment Rejestracji", "Gość", "Nr Pokoju", "Pojemność", "Data Wyjazdu"};
        modelTabeli = new DefaultTableModel(kolumny, 0);
        tabelaRezerwacji = new JTable(modelTabeli);
        JScrollPane scrollPane = new JScrollPane(tabelaRezerwacji);
        odswiezTabele(); // Wypełnia tabelę danymi

        // Przyciski
        JPanel panelPrzyciskow = new JPanel();
        JButton btnZamelduj = new JButton("Zamelduj Nowego Gościa");
        JButton btnWyjdz = new JButton("Zapisz i Wyjdź");
        panelPrzyciskow.add(btnZamelduj);
        panelPrzyciskow.add(btnWyjdz);

        // Akcja meldunku
        btnZamelduj.addActionListener(e -> zameldujGoscia());

        // Akcja wyjścia
        btnWyjdz.addActionListener(e -> wyjdzZProgramu());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { wyjdzZProgramu(); }
        });

        // Układanie elementów w oknie
        add(new JLabel("  Aktywne meldunki:"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelPrzyciskow, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void zameldujGoscia() {
        try {
            String imie = JOptionPane.showInputDialog(this, "Podaj imię:");
            if (imie == null) return;

            String nazwisko = JOptionPane.showInputDialog(this, "Podaj nazwisko:");
            if (nazwisko == null) return;

            String pesel = JOptionPane.showInputDialog(this, "Podaj PESEL (11 cyfr):");
            if (pesel == null) return;

            // Wybór pojemności pokoju
            String[] opcje = {"1-osobowy", "2-osobowy", "3-osobowy"};
            int wybor = JOptionPane.showOptionDialog(this, "Wybierz rodzaj pokoju:", "Rezerwacja",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcje, opcje[1]);

            if (wybor == -1) return;
            int wymaganaPojemnosc = wybor + 1; // Bo index to 0, 1, 2

            // Pytanie o liczbę nocy
            String noceStr = JOptionPane.showInputDialog(this, "Na ile nocy zostaje gość?");
            if (noceStr == null) return;
            int liczbaNocy = Integer.parseInt(noceStr);

            // Szukanie pokoju i walidacja danych (ZleDaneWyjatek)
            Pokoj przypisanyPokoj = system.znajdzWolnyPokoj(wymaganaPojemnosc);

            if (przypisanyPokoj != null) {
                Gosc gosc = new Gosc(imie, nazwisko, pesel);
                system.dodajGoscia(gosc);

                Rezerwacja nowaRezerwacja = new Rezerwacja(gosc, przypisanyPokoj, LocalDate.now(), liczbaNocy);
                system.dodajRezerwacje(nowaRezerwacja);

                odswiezTabele(); // Aktualizujemy widok!
                JOptionPane.showMessageDialog(this, "Sukces! Zameldowano w: " + przypisanyPokoj.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Brak wolnych pokoi " + wymaganaPojemnosc + "-osobowych!", "Brak Miejsc", JOptionPane.WARNING_MESSAGE);
            }

        } catch (ZleDaneWyjatek ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Błąd walidacji!", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Błąd: Liczba nocy musi być cyfrą!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void odswiezTabele() {
        modelTabeli.setRowCount(0);
        for (Rezerwacja r : system.getRezerwacje()) {
            Object[] wiersz = {
                    r.getSformatowanyCzas(),
                    r.getGosc().getImie() + " " + r.getGosc().getNazwisko(),
                    "Nr " + r.getPokoj().getNumer(),
                    r.getPokoj().getPojemnosc() + "-osobowy",
                    r.getDataWyjazdu().toString() // Automatycznie wyliczona data wymeldowania!
            };
            modelTabeli.addRow(wiersz);
        }
    }

    private void wyjdzZProgramu() {
        NarzedziaHotelowe.zapiszSystem(system);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
