package model;

import java.io.Serializable;
import java.math.BigDecimal; // NOWOŚĆ
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

    // Konstruktor główny
    public Rezerwacja(Gosc gosc, Pokoj pokoj, LocalDate przyjazd, int liczbaNocy) {
        this.gosc = gosc;
        this.pokoj = pokoj;
        this.dataPrzyjazdu = przyjazd;
        this.dataWyjazdu = przyjazd.plusDays(liczbaNocy);
        this.momentZapisu = LocalDateTime.now();
        this.pokoj.setCzyZajety(true);
    }

    // Przeciążony konstruktor (domyślnie 1 noc)
    public Rezerwacja(Gosc gosc, Pokoj pokoj, LocalDate przyjazd) {
        this(gosc, pokoj, przyjazd, 1);
    }

    public Gosc getGosc() { return gosc; }
    public Pokoj getPokoj() { return pokoj; }
    public LocalDate getDataPrzyjazdu() { return dataPrzyjazdu; }
    public LocalDate getDataWyjazdu() { return dataWyjazdu; }

    // ZMIANA: Metoda zwraca BigDecimal i używa metody multiply() do mnożenia
    public BigDecimal getLacznaKwota() {
        long liczbaNocy = ChronoUnit.DAYS.between(dataPrzyjazdu, dataWyjazdu);

        // Mnożymy: Cena za noc * liczba nocy (jako BigDecimal)
        BigDecimal kwotaPodstawowa = pokoj.getCenaZaNoc().multiply(BigDecimal.valueOf(liczbaNocy));

        // Zastosowanie polimorfizmu i interfejsu (zwraca po uwzględnieniu zniżki)
        return gosc.naliczZnizke(kwotaPodstawowa);
    }

    public String getSformatowanyCzas() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dtf.format(momentZapisu);
    }
}