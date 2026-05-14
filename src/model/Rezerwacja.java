package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rezerwacja implements Serializable {
    private Gosc gosc;
    private Pokoj pokoj;
    private LocalDate dataPrzyjazdu;
    private LocalDate dataWyjazdu;
    private LocalDateTime momentZapisu; // Zapisuje dokładną datę i godzinę kliknięcia

    // Nowy konstruktor przyjmujący ilość nocy
    public Rezerwacja(Gosc gosc, Pokoj pokoj, LocalDate przyjazd, int liczbaNocy) {
        this.gosc = gosc;
        this.pokoj = pokoj;
        this.dataPrzyjazdu = przyjazd;
        this.dataWyjazdu = przyjazd.plusDays(liczbaNocy); // Automatycznie wylicza datę wyjazdu!
        this.momentZapisu = LocalDateTime.now(); // Pobiera aktualny czas systemowy
        this.pokoj.setCzyZajety(true);
    }

    public Gosc getGosc() { return gosc; }
    public Pokoj getPokoj() { return pokoj; }
    public LocalDate getDataPrzyjazdu() { return dataPrzyjazdu; }
    public LocalDate getDataWyjazdu() { return dataWyjazdu; }

    // Ta metoda ładnie sformatuje nam czas do wyświetlenia w tabeli
    public String getSformatowanyCzas() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dtf.format(momentZapisu);
    }
}