package com.awg.gpe.web.utils;

import java.math.BigDecimal;

/**
 * Clase estática que proporciona métodos útiles para las clases numéricas
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class NumericUtils {

    /**
     * Método que devuelve si un {@link Long} está comprendido entre dos valores
     * 
     * @param number
     *            El número que se quiere comparar
     * @param from Inicio del rango (inclusive)
     * @param to Fin del rango (inclusive)
     * @return Si el número pasado por parámetros se encuentra dentro del rango
     * @version 1.0
     * @since 1.0
     */
    public static Boolean between(Long number, Long from, Long to) {
        return number.compareTo(from) >= 0 && number.compareTo(to) <= 0;
    }

    /**
     * Método que devuelve si un {@link Integer} está comprendido entre dos valores
     * 
     * @param number
     *            El número que se quiere comparar
     * @param from Inicio del rango, inclusive
     * @param to Fin del rango, inclusive
     * @return Si el número pasado por parámetros se encuentra dentro del rango
     * @version 1.0
     * @since 1.0
     */
    public static Boolean between(Integer number, Integer from, Integer to) {
        return number.compareTo(from) >= 0 && number.compareTo(to) <= 0;
    }

    /**
     * Método que devuelve si un {@link BigDecimal} está comprendido entre dos valores
     * @param number Número que se quiere comparar
     * @param from Inicio del rango, inclusive
     * @param to Fin del rango, inclusive
     * @return Si el número está dentro del rango
     * @version 1.0
     * @since 1.0
     */
    public static Boolean between(BigDecimal number, BigDecimal from, BigDecimal to) {
        return number.compareTo(from) >= 0 && number.compareTo(to) <= 0;
    }
    
    /**
     * Método que devuelve si un {@link Float} está comprendido entre dos valores
     * @param number Número que se quiere comparar
     * @param from Inicio del rango, inclusive
     * @param to Fin del rango, inclusive
     * @return Si el número está dentro del rango
     * @version 1.0
     * @since 1.0
     */
    public static Boolean between(Float number, Float from, Float to) {
        return number.compareTo(from) >= 0 && number.compareTo(to) <= 0;
    }
}
