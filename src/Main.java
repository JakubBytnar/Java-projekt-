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
        SystemHotelowy wczytany = NarzedziaHotelowe.wczytajSystem();
        if (wczytany != null) {
            SystemHotelowy.setInstancja(wczytany);
        }
        system = SystemHotelowy.getInstancja();
        system.generujPokojeStartowe();

        // Ustawienia okna
        setTitle("System Zarządzania Hotelem - Recepcja PRO");
        setSize(1000, 500); // Jeszcze szersze okno, żeby zmieścić nowe kolumny
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(15, 15)); // Większe marginesy w oknie

        // Nowe, ładne czcionki (wygląd komercyjnej aplikacji)
        Font czcionkaTabeli = new Font("Segoe UI", Font.PLAIN, 14);
        Font czcionkaNaglowkow = new Font("Segoe UI", Font.BOLD, 15);

        // Zaktualizowane kolumny tabeli
        String[] kolumny = {"Moment Rejestracji", "Gość", "Pokój", "Data Przyjazdu", "Data Wyjazdu", "Łączna Kwota"};
        modelTabeli = new DefaultTableModel(kolumny, 0);
        tabelaRezerwacji = new JTable(modelTabeli);

        // Stylowanie tabeli
        tabelaRezerwacji.setFont(czcionkaTabeli);
        tabelaRezerwacji.setRowHeight(30); // Wyższe, bardziej czytelne wiersze
        tabelaRezerwacji.getTableHeader().setFont(czcionkaNaglowkow);
        tabelaRezerwacji.getTableHeader().setBackground(new Color(70, 130, 180)); // Niebieski kolor nagłówka
        tabelaRezerwacji.getTableHeader().setForeground(Color.WHITE); // Biały tekst nagłówka

        JScrollPane scrollPane = new JScrollPane(tabelaRezerwacji);
        odswiezTabele();

        // Tworzenie dolnego panelu z przyciskami (z fajnymi marginesami)
        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelPrzyciskow.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        JButton btnZamelduj = new JButton("Zamelduj Nowego Gościa");
        JButton btnWyjdz = new JButton("Zapisz i Wyjdź");

        // Stylowanie przycisków
        btnZamelduj.setFont(czcionkaNaglowkow);
        btnZamelduj.setBackground(new Color(46, 139, 87)); // Zielony (Sea Green)
        btnZamelduj.setForeground(Color.WHITE);
        btnZamelduj.setFocusPainted(false); // Usuwa brzydką ramkę po kliknięciu

        btnWyjdz.setFont(czcionkaNaglowkow);
        btnWyjdz.setBackground(new Color(178, 34, 34)); // Czerwony (Firebrick)
        btnWyjdz.setForeground(Color.WHITE);
        btnWyjdz.setFocusPainted(false);

        panelPrzyciskow.add(btnZamelduj);
        panelPrzyciskow.add(btnWyjdz);

        btnZamelduj.addActionListener(e -> zameldujGoscia());
        btnWyjdz.addActionListener(e -> wyjdzZProgramu());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { wyjdzZProgramu(); }
        });

        // Tytuł nad tabelą
        JLabel tytul = new JLabel("   Aktywne rezerwacje i meldunki w systemie:");
        tytul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tytul.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        add(tytul, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelPrzyciskow, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Wyśrodkowanie okna na ekranie
    }

    private void zameldujGoscia() {
        try {
            String imie = JOptionPane.showInputDialog(this, "Podaj imię:");
            if (imie == null) return;

            String nazwisko = JOptionPane.showInputDialog(this, "Podaj nazwisko:");
            if (nazwisko == null) return;

            String pesel = JOptionPane.showInputDialog(this, "Podaj PESEL (11 cyfr):");
            if (pesel == null) return;

            String[] opcje = {"1-osobowy", "2-osobowy", "3-osobowy"};
            int wybor = JOptionPane.showOptionDialog(this, "Wybierz rodzaj pokoju:", "Rezerwacja",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcje, opcje[1]);

            if (wybor == -1) return;
            int wymaganaPojemnosc = wybor + 1;

            String noceStr = JOptionPane.showInputDialog(this, "Na ile nocy zostaje gość?");
            if (noceStr == null) return;
            int liczbaNocy = Integer.parseInt(noceStr);

            Pokoj przypisanyPokoj = system.znajdzWolnyPokoj(wymaganaPojemnosc);

            if (przypisanyPokoj != null) {
                Gosc gosc = new Gosc(imie, nazwisko, pesel);
                system.dodajGoscia(gosc);

                Rezerwacja nowaRezerwacja = new Rezerwacja(gosc, przypisanyPokoj, LocalDate.now(), liczbaNocy);
                system.dodajRezerwacje(nowaRezerwacja);

                odswiezTabele();
                JOptionPane.showMessageDialog(this, "Sukces! Zarezerwowano pokój nr " + przypisanyPokoj.getNumer() +
                                "\nKwota do zapłaty: " + nowaRezerwacja.getLacznaKwota() + " zł",
                        "Zameldowano", JOptionPane.INFORMATION_MESSAGE);
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
                    "Nr " + r.getPokoj().getNumer() + " (" + r.getPokoj().getPojemnosc() + "-os)",
                    r.getDataPrzyjazdu().toString(), // NOWOŚĆ: Data przyjazdu
                    r.getDataWyjazdu().toString(),
                    r.getLacznaKwota() + " zł"       // NOWOŚĆ: Obliczona łączna kwota (dni * 100 zł)
            };
            modelTabeli.addRow(wiersz);
        }
    }

    private void wyjdzZProgramu() {
        NarzedziaHotelowe.zapiszSystem(system);
        System.exit(0);
    }

    public static void main(String[] args) {
        // Uruchamiamy nowy, piękny interfejs!
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}