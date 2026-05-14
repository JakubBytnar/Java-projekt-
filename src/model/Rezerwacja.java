package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

    public double getLacznaKwota() {
        long liczbaNocy = ChronoUnit.DAYS.between(dataPrzyjazdu, dataWyjazdu);
        double kwotaPodstawowa = liczbaNocy * pokoj.getCenaZaNoc();

        // Zastosowanie polimorfizmu i interfejsu!
        return gosc.naliczZnizke(kwotaPodstawowa);
    }

    public String getSformatowanyCzas() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dtf.format(momentZapisu);
    }
}