package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit; // Dodany import do liczenia różnicy dat

public class Rezerwacja implements Serializable {
    private Gosc gosc;
    private Pokoj pokoj;
    private LocalDate dataPrzyjazdu;
    private LocalDate dataWyjazdu;
    private LocalDateTime momentZapisu;

    public Rezerwacja(Gosc gosc, Pokoj pokoj, LocalDate przyjazd, int liczbaNocy) {
        this.gosc = gosc;
        this.pokoj = pokoj;
        this.dataPrzyjazdu = przyjazd;
        this.dataWyjazdu = przyjazd.plusDays(liczbaNocy);
        this.momentZapisu = LocalDateTime.now();
        this.pokoj.setCzyZajety(true);
    }

    public Gosc getGosc() { return gosc; }
    public Pokoj getPokoj() { return pokoj; }
    public LocalDate getDataPrzyjazdu() { return dataPrzyjazdu; }
    public LocalDate getDataWyjazdu() { return dataWyjazdu; }

    // NOWOŚĆ: Metoda obliczająca łączną kwotę za pobyt
    public double getLacznaKwota() {
        // Liczy ilość dni między datą przyjazdu a wyjazdu
        long liczbaNocy = ChronoUnit.DAYS.between(dataPrzyjazdu, dataWyjazdu);
        return liczbaNocy * pokoj.getCenaZaNoc();
    }

    public String getSformatowanyCzas() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dtf.format(momentZapisu);
    }
}