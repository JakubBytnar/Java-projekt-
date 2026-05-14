package gui;

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
import java.util.Iterator;

public class OknoRecepcji extends JFrame {
    private SystemHotelowy system;
    private DefaultTableModel modelTabeli;
    private JTable tabelaRezerwacji;

    // ZMIANA: Konstruktor okna przyjmuje wczytany wcześniej system
    public OknoRecepcji(SystemHotelowy system) {
        this.system = system;

        setTitle("System Zarządzania Hotelem - Recepcja PRO");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        Font czcionkaTabeli = new Font("Segoe UI", Font.PLAIN, 14);
        Font czcionkaNaglowkow = new Font("Segoe UI", Font.BOLD, 15);

        String[] kolumny = {"Moment Rejestracji", "Gość", "Pokój", "Data Przyjazdu", "Data Wyjazdu", "Łączna Kwota"};
        modelTabeli = new DefaultTableModel(kolumny, 0);
        tabelaRezerwacji = new JTable(modelTabeli);
        tabelaRezerwacji.setFont(czcionkaTabeli);
        tabelaRezerwacji.setRowHeight(30);
        tabelaRezerwacji.getTableHeader().setFont(czcionkaNaglowkow);
        tabelaRezerwacji.getTableHeader().setBackground(new Color(70, 130, 180));
        tabelaRezerwacji.getTableHeader().setForeground(Color.WHITE);
        tabelaRezerwacji.getTableHeader().setReorderingAllowed(false); // Blokada przesuwania kolumn

        JScrollPane scrollPane = new JScrollPane(tabelaRezerwacji);
        odswiezTabele();

        // Przyciski
        JPanel panelPrzyciskow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelPrzyciskow.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        JButton btnZamelduj = new JButton("Zamelduj Nowego Gościa");
        JButton btnWymelduj = new JButton("Wymelduj (Zaznacz w tabeli)");
        JButton btnWyjdz = new JButton("Zapisz i Wyjdź");

        btnZamelduj.setFont(czcionkaNaglowkow); btnZamelduj.setBackground(new Color(46, 139, 87)); btnZamelduj.setForeground(Color.WHITE); btnZamelduj.setFocusPainted(false);
        btnWymelduj.setFont(czcionkaNaglowkow); btnWymelduj.setBackground(new Color(255, 140, 0)); btnWymelduj.setForeground(Color.WHITE); btnWymelduj.setFocusPainted(false);
        btnWyjdz.setFont(czcionkaNaglowkow); btnWyjdz.setBackground(new Color(178, 34, 34)); btnWyjdz.setForeground(Color.WHITE); btnWyjdz.setFocusPainted(false);

        panelPrzyciskow.add(btnZamelduj);
        panelPrzyciskow.add(btnWymelduj);
        panelPrzyciskow.add(btnWyjdz);

        btnZamelduj.addActionListener(e -> zameldujGoscia());
        btnWymelduj.addActionListener(e -> wymeldujGoscia());
        btnWyjdz.addActionListener(e -> wyjdzZProgramu());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { wyjdzZProgramu(); }
        });

        JLabel tytul = new JLabel("   Aktywne rezerwacje i meldunki w systemie:");
        tytul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tytul.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        add(tytul, BorderLayout.NORTH);
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

            int czyVipWybor = JOptionPane.showConfirmDialog(this, "Czy gość posiada kartę VIP (-15%)?", "Status VIP", JOptionPane.YES_NO_OPTION);
            boolean isVip = (czyVipWybor == JOptionPane.YES_OPTION);

            String[] opcje = {"1-osobowy", "2-osobowy", "3-osobowy"};
            int wybor = JOptionPane.showOptionDialog(this, "Wybierz rodzaj pokoju:", "Rezerwacja", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcje, opcje[1]);
            if (wybor == -1) return;
            int wymaganaPojemnosc = wybor + 1;

            String noceStr = JOptionPane.showInputDialog(this, "Na ile nocy zostaje gość?");
            if (noceStr == null) return;
            int liczbaNocy = Integer.parseInt(noceStr);

            Pokoj przypisanyPokoj = system.znajdzWolnyPokoj(wymaganaPojemnosc);

            if (przypisanyPokoj != null) {
                Gosc gosc = new Gosc(imie, nazwisko, pesel, isVip);
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

    private void wymeldujGoscia() {
        int zaznaczonyWiersz = tabelaRezerwacji.getSelectedRow();
        if (zaznaczonyWiersz == -1) {
            JOptionPane.showMessageDialog(this, "Najpierw zaznacz gościa w tabeli!", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Rezerwacja rDoUsuniecia = system.getRezerwacje().get(zaznaczonyWiersz);

        Iterator<Rezerwacja> iterator = system.getRezerwacje().iterator();
        while (iterator.hasNext()) {
            Rezerwacja r = iterator.next();
            if (r.equals(rDoUsuniecia)) {
                NarzedziaHotelowe.generujParagon(r);
                r.getPokoj().setCzyZajety(false);
                iterator.remove();
                break;
            }
        }

        odswiezTabele();
        JOptionPane.showMessageDialog(this, "Gość wymeldowany! Pokój jest znowu wolny.\nWygenerowano paragon w folderze projektu.", "Wymeldowanie", JOptionPane.INFORMATION_MESSAGE);
    }

    private void odswiezTabele() {
        modelTabeli.setRowCount(0);

        // Komparator układa rezerwacje wg daty
        system.getRezerwacje().sort(new narzedzia.KomparatorRezerwacji());

        for (Rezerwacja r : system.getRezerwacje()) {

            String daneGoscia = r.getGosc().getImie() + " " + r.getGosc().getNazwisko();
            if (r.getGosc().isCzyVip()) {
                daneGoscia = "<html><nobr><font color='#DAA520'><b>👑 VIP</b></font> " + daneGoscia + "</nobr></html>";
            }

            Object[] wiersz = {
                    r.getSformatowanyCzas(),
                    daneGoscia,
                    "Nr " + r.getPokoj().getNumer() + " (" + r.getPokoj().getPojemnosc() + "-os)",
                    r.getDataPrzyjazdu().toString(),
                    r.getDataWyjazdu().toString(),
                    r.getLacznaKwota() + " zł"
            };
            modelTabeli.addRow(wiersz);
        }
    }

    private void wyjdzZProgramu() {
        NarzedziaHotelowe.zapiszSystem(system);
        System.exit(0);
    }
}