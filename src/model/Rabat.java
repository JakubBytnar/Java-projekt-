package model;

import java.math.BigDecimal; // NOWOŚĆ

// Wymaganie z wykładu: Interfejs
public interface Rabat {
    BigDecimal naliczZnizke(BigDecimal kwotaPoczatkowa); // ZMIANA
}