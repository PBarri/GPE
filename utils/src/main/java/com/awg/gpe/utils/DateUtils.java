package com.awg.gpe.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Colección de métodos de utilidad para fechas laborables
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class DateUtils {

	private DateUtils() {
	}

	/**
	 * Método que devuelve una lista con los días laborables entre dos fechas
	 * @param startDate Fecha de inicio
	 * @param endDate Fecha de fin
	 * @return Una lista de {@link LocalDate}
	 * @version 1.0
	 * @since 1.0
	 */
	public static List<LocalDate> getWorkingDays(LocalDate startDate, LocalDate endDate) {
		return Stream.iterate(startDate, d -> d.plusDays(1)).limit(startDate.until(endDate, ChronoUnit.DAYS) + 1)
				.filter(d -> !DateUtils.isWeekendDay(d)).collect(Collectors.toList());
	}

	/**
	 * Método que devuelve una lista con los días laborables entre dos fechas contando con los días festivos
	 * @param startDate Fecha de inicio
	 * @param endDate Fecha de fin
	 * @param bankHolidays Lista de días festivos
	 * @return Una lista de {@link LocalDate}
	 * @version 1.0
	 * @since 1.0
	 */
	public static List<LocalDate> getWorkingDays(LocalDate startDate, LocalDate endDate, List<LocalDate> bankHolidays) {
		return Stream.iterate(startDate, d -> d.plusDays(1)).limit(startDate.until(endDate, ChronoUnit.DAYS) + 1)
				.filter(d -> !DateUtils.isWeekendDay(d) && !DateUtils.isBankHoliday(bankHolidays, d)).collect(Collectors.toList());
	}

	/**
	 * Método que devuelve el número de días laborables entre dos fechas
	 * @param startDate Fecha de inicio
	 * @param endDate Fecha de fin
	 * @return El número de días
	 * @version 1.0
	 * @since 1.0
	 */
	public static Integer businessDaysBetween(LocalDate startDate, LocalDate endDate) {
		Long nDays = Stream.iterate(startDate, d -> d.plusDays(1)).limit(startDate.until(endDate, ChronoUnit.DAYS) + 1)
				.filter(d -> !DateUtils.isWeekendDay(d)).count();
		return nDays.intValue();
	}

	/**
	 * Método que devuelve el número de días laborables entre dos fechas, contando con los días festivos
	 * @param startDate Fecha de inicio
	 * @param endDate Fecha de fin
	 * @param bankHolidays Colección de días festivos
	 * @return El número de días
	 * @version 1.0
	 * @since 1.0
	 */
	public static Integer businessDaysBetween(LocalDate startDate, LocalDate endDate, List<LocalDate> bankHolidays) {
		Long nDays = Stream.iterate(startDate, d -> d.plusDays(1)).limit(startDate.until(endDate, ChronoUnit.DAYS) + 1)
				.filter(d -> !DateUtils.isWeekendDay(d) && !DateUtils.isBankHoliday(bankHolidays, d)).count();
		return nDays.intValue();
	}

	/**
	 * Método que comprueba si un día se encuentra en fin de semana
	 * @param date Día a comprobar
	 * @return Si es sábado o domingo
	 * @version 1.0
	 * @since 1.0
	 */
	public static Boolean isWeekendDay(LocalDate date) {
		return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}

	/**
	 * Método que comprueba si un día es festivo
	 * @param bankHolidays Colección de días festivos
	 * @param date Día a comprobar
	 * @return Si es un día festivo
	 * @version 1.0
	 * @since 1.0
	 */
	public static boolean isBankHoliday(List<LocalDate> bankHolidays, LocalDate date) {
		return (bankHolidays != null) && bankHolidays.contains(date);
	}

	/**
	 * Método que comprueba si una fecha se encuentra en el periodo comprendido por dos fechas
	 * @param date Fecha a comprobar
	 * @param before Fecha de inicio del periodo
	 * @param after Fecha de fin del periodo
	 * @return Si la fecha a comprobar se encuentra en el periodo seleccionado
	 * @version 1.0
	 * @since 1.0
	 */
	public static Boolean isBetween(LocalDate date, LocalDate before, LocalDate after) {
		return date.isAfter(before) && date.isBefore(after);
	}

	/**
	 * Método que comprueba si un timestamp se encuentra en el periodo comprendido por dos timestamps
	 * @param date Timestamp a comprobar
	 * @param before Timestap de inicio del periodo
	 * @param after Timestamp de fin del periodo
	 * @return Si el timestamp a comprobar se encuentra en el periodo seleccionado
	 * @version 1.0
	 * @since 1.0
	 */
	public static Boolean isBetween(LocalDateTime date, LocalDateTime before, LocalDateTime after) {
		return date.isAfter(before) && date.isBefore(after);
	}

	/**
	 * Método que comprueba si un timestamp se encuentra en el periodo comprendido por dos fechas
	 * @param date Timestamp a comprobar
	 * @param before Fecha de inicio de periodo
	 * @param after Fecha de fin de periodo
	 * @return Si el timestamp se encuentra dentro del periodo
	 * @version 1.0
	 * @since 1.0
	 */
	public static Boolean isBetween(LocalDateTime date, LocalDate before, LocalDate after) {
		return date.isAfter(before.atStartOfDay()) && date.isBefore(after.atTime(LocalTime.MAX));
	}

	/**
	 * Método que comprueba si una fecha se encuentra en el periodo commprendido por dos timestamps
	 * @param date Fecha a comprobar
	 * @param before Timestamp de inicio del periodo
	 * @param after Timestamp de fin del periodo
	 * @return Si la fecha se encuentra dentro del periodo
	 * @version 1.0
	 * @since 1.0
	 */
	public static Boolean isBetween(LocalDate date, LocalDateTime before, LocalDateTime after) {
		return date.isAfter(before.toLocalDate()) && date.isBefore(after.toLocalDate());
	}

	/**
	 * Método que comprueba si la fecha actual se encuentra dentro de un periodo
	 * @param before Fecha de inicio del periodo
	 * @param after Fecha de fin del periodo
	 * @return Si ese periodo incluye la fecha actual
	 * @version 1.0
	 * @since 1.0
	 */
	public static Boolean isActual(LocalDate before, LocalDate after) {
		LocalDate date = LocalDate.now();
		return date.isAfter(before) && date.isBefore(after);
	}

	/**
	 * Método que comprueba si el momento actual se encuentra dentro de un periodo
	 * @param before Timestamp de inicio del periodo
	 * @param after Timestamp de fin del periodo
	 * @return Si ese periodo incluye el momento actual
	 * @version 1.0
	 * @since 1.0
	 */
	public static Boolean isActual(LocalDateTime before, LocalDateTime after) {
		LocalDateTime date = LocalDateTime.now();
		return date.isAfter(before) && date.isBefore(after);
	}

}
