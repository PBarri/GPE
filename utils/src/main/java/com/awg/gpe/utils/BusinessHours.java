package com.awg.gpe.utils;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Clase que implementa la interfaz {@link TemporalAmount}, y que encapsula la lógica de sumar y restar
 * días laborables a una fecha cualquiera.
 * <p>
 * Soporta la suma de años, meses, días, horas y minutos 
 * </p>
 * @author Pablo
 * @version 1.0
 * @since 1.0
 *
 */
public class BusinessHours implements TemporalAmount {
	
	/**
     * A constant for a period of zero.
     */
    public static final BusinessHours ZERO = new BusinessHours(0, 0, 0, 0, 0, null);
	
	/**
     * The set of supported units.
     */
    private static final List<TemporalUnit> SUPPORTED_UNITS =
            Collections.unmodifiableList(Arrays.<TemporalUnit>asList(YEARS, MONTHS, DAYS, HOURS, MINUTES));

    /**
     * The number of years.
     */
    private final int years;
    /**
     * The number of months.
     */
    private final int months;
    /**
     * The number of days.
     */
    private final int days;
    
    /**
     * The number of hours
     */
    private final int hours;
    
    /**
     * The number of minutes
     */
    private final int minutes;
    
    /**
     * A collection of bank holidays
     * @version 1.0
     * @since 1.0
     */
    private final List<LocalDate> holidays;
    
    
    /**
     * Creates an instance.
     *
     * @param years  the amount
     * @param months  the amount
     * @param days  the amount
     * @param minutes the amount
     */
    private static BusinessHours create(int years, int months, int days, int hours, int minutes) {
        return BusinessHours.create(years, months, days, hours, minutes, null);
    }
    
    private static BusinessHours create(int years, int months, int days, int hours, int minutes, List<LocalDate> holidays) {
        if ((years | months | days | hours | minutes) == 0) {
            return BusinessHours.ZERO;
        }
        return new BusinessHours(years, months, days, hours, minutes, holidays);
    }
    
    /**
     * Constructor.
     *
     * @param years  the amount
     * @param months  the amount
     * @param days  the amount
     */
    private BusinessHours(int years, int months, int days, int hours, int minutes, List<LocalDate> holidays) {
        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.holidays = holidays;
    }
    
    /**
     * Obtains a {@code Period} representing a number of years.
     * <p>
     * The resulting period will have the specified years.
     * The months and days units will be zero.
     *
     * @param years  the number of years, positive or negative
     * @return the period of years, not null
     */
    public static BusinessHours ofYears(int years) {
        return BusinessHours.create(years, 0, 0, 0, 0);
    }

    /**
     * Obtains a {@code Period} representing a number of months.
     * <p>
     * The resulting period will have the specified months.
     * The years and days units will be zero.
     *
     * @param months  the number of months, positive or negative
     * @return the period of months, not null
     */
    public static BusinessHours ofMonths(int months) {
        return BusinessHours.create(0, months, 0, 0, 0);
    }

    /**
     * Obtains a {@code Period} representing a number of weeks.
     * <p>
     * The resulting period will be day-based, with the amount of days
     * equal to the number of weeks multiplied by 7.
     * The years and months units will be zero.
     *
     * @param weeks  the number of weeks, positive or negative
     * @return the period, with the input weeks converted to days, not null
     */
    public static BusinessHours ofWeeks(int weeks) {
        return BusinessHours.create(0, 0, Math.multiplyExact(weeks, 7), 0, 0);
    }

    /**
     * Obtains a {@code Period} representing a number of days.
     * <p>
     * The resulting period will have the specified days.
     * The years and months units will be zero.
     *
     * @param days  the number of days, positive or negative
     * @return the period of days, not null
     */
    public static BusinessHours ofDays(int days) {
        return BusinessHours.create(0, 0, days, 0, 0);
    }
    
    public static BusinessHours ofHours(int hours) {
    	return BusinessHours.create(0, 0, 0, hours, 0);
    }
    
    public static BusinessHours ofMinutes(int minutes) {
    	return BusinessHours.create(0, 0, 0, 0, minutes);
    } 
    
    public static BusinessHours ofHoursAndMinutes(int hours, int minutes) {
    	return BusinessHours.create(0, 0, 0, hours, minutes);
    }
    
    public static BusinessHours ofDays(int days, List<LocalDate> holidays) {
        return BusinessHours.create(0, 0, days, 0, 0);
    }
    
    public static BusinessHours ofHours(int hours, List<LocalDate> holidays) {
    	return BusinessHours.create(0, 0, 0, hours, 0);
    }
    
    public static BusinessHours ofMinutes(int minutes, List<LocalDate> holidays) {
    	return BusinessHours.create(0, 0, 0, 0, minutes);
    } 
    
    public static BusinessHours ofHoursAndMinutes(int hours, int minutes, List<LocalDate> holidays) {
    	return BusinessHours.create(0, 0, 0, hours, minutes);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Period} representing a number of years, months and days.
     * <p>
     * This creates an instance based on years, months and days.
     *
     * @param years  the amount of years, may be negative
     * @param months  the amount of months, may be negative
     * @param days  the amount of days, may be negative
     * @return the period of years, months and days, not null
     */
    public static BusinessHours of(int years, int months, int days, int hours, int minutes) {
        return BusinessHours.create(years, months, days, hours, minutes);
    }
    
    

	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalAmount#get(java.time.temporal.TemporalUnit)
	 */
	@Override
	public long get(TemporalUnit unit) {
		if (unit == YEARS) {
            return getYears();
        } else if (unit == MONTHS) {
            return getMonths();
        } else if (unit == DAYS) {
            return getDays();
        } else if (unit == HOURS) {
        	return getHours();
        } else if(unit == MINUTES) {
        	return getMinutes();
        } else {
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
	}

	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalAmount#getUnits()
	 */
	@Override
	public List<TemporalUnit> getUnits() {
		return BusinessHours.SUPPORTED_UNITS;
	}

	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalAmount#addTo(java.time.temporal.Temporal)
	 */
	@Override
	public Temporal addTo(Temporal temporal) {
		validateChrono(temporal);
		if (this.months == 0) {
            if (this.years != 0) {
                temporal = temporal.plus(this.years, YEARS);
            }
        } else {
            long totalMonths = toTotalMonths();
            if (totalMonths != 0) {
                temporal = temporal.plus(totalMonths, MONTHS);
            }
        }
		if (this.days != 0) {
            temporal = temporal.plus(this.days, DAYS);
        }
		
		if (this.hours != 0) {
			BigDecimal[] totalTime = new BigDecimal(this.hours).divideAndRemainder(new BigDecimal(8), MathContext.DECIMAL32);
			Integer totalHours = totalTime[0].multiply(BigDecimal.valueOf(8)).intValue();
			Integer totalMinutes = totalTime[1].multiply(BigDecimal.valueOf(60)).intValue();
			LocalDateTime endTime = LocalDate.from(temporal).atTime(16, 0);
			Integer hoursToEnd = Long.valueOf(temporal.until(endTime, HOURS)).intValue();
			
			while (totalHours >= hoursToEnd) {
				hoursToEnd = 8;
				temporal = temporal.plus(hoursToEnd + 16, HOURS);
				temporal = checkFreeDays(temporal);
				totalHours -= hoursToEnd;
			}
			
			temporal = temporal.plus(totalHours, HOURS);
			
			// Añadimos los minutos
			if (this.minutes != 0) {
				totalMinutes += this.minutes;
			}
			endTime = LocalDate.from(temporal).atTime(16, 0);
			Integer minutesToEnd = Long.valueOf(temporal.until(endTime, MINUTES)).intValue();
			if (totalMinutes > minutesToEnd) {
				temporal = temporal.plus(minutesToEnd, MINUTES);
				totalMinutes -= minutesToEnd;
				temporal = temporal.plus(16, HOURS);
				temporal = checkFreeDays(temporal);
				temporal = temporal.plus(totalMinutes, MINUTES);
			} else {
				temporal = temporal.plus(totalMinutes, MINUTES);
			}
			
		} else if (this.minutes != 0) {
			Integer totalMinutes = this.minutes;
			LocalDateTime endTime = LocalDate.from(temporal).atTime(16, 0);
			Integer minutesToEnd = Long.valueOf(temporal.until(endTime, MINUTES)).intValue();
			if (totalMinutes > minutesToEnd) {
				temporal = temporal.plus(minutesToEnd, MINUTES);
				totalMinutes -= minutesToEnd;
				temporal = temporal.plus(16, HOURS);
				temporal = checkFreeDays(temporal);
				temporal = temporal.plus(totalMinutes, MINUTES);
			} else {
				temporal = temporal.plus(totalMinutes, MINUTES);
			}
		}
		
        return temporal;
	}

	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalAmount#subtractFrom(java.time.temporal.Temporal)
	 */
	@Override
	public Temporal subtractFrom(Temporal temporal) {
		validateChrono(temporal);
		return null;
	}
	
	/**
     * Validates that the temporal has the correct chronology.
     */
    private void validateChrono(TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        Chronology temporalChrono = temporal.query(TemporalQueries.chronology());
        if (temporalChrono != null && IsoChronology.INSTANCE.equals(temporalChrono) == false) {
            throw new DateTimeException("Chronology mismatch, expected: ISO, actual: " + temporalChrono.getId());
        }
    }
    
    private Temporal checkFreeDays(Temporal temporal) {
    	if (DayOfWeek.from(temporal) == DayOfWeek.SATURDAY) {
			temporal = temporal.plus(2, DAYS);
		} else if (this.holidays != null && !this.holidays.isEmpty() && this.holidays.contains(LocalDate.from(temporal))) {
			temporal = temporal.plus(1, DAYS);
		}
    	return temporal;
    }
    
    /**
     * Checks if this period is equal to another period.
     * <p>
     * The comparison is based on the type {@code Period} and each of the three amounts.
     * To be equal, the years, months and days units must be individually equal.
     * Note that this means that a period of "15 Months" is not equal to a period
     * of "1 Year and 3 Months".
     *
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other period
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BusinessHours) {
        	BusinessHours other = (BusinessHours) obj;
            return this.years == other.years &&
                    this.months == other.months &&
                    this.days == other.days &&
                    this.hours == other.hours &&
                    this.minutes == other.minutes;
        } else if (obj instanceof Period) {
        	Period other = (Period) obj;
        	return this.years == other.getYears() &&
                    this.months == other.getMonths() &&
                    this.days == other.getDays() &&
                    this.hours == 0 &&
                    this.minutes == 0;
        }
        return false;
    }
    
    /**
     * Gets the total number of months in this period.
     * <p>
     * This returns the total number of months in the period by multiplying the
     * number of years by 12 and adding the number of months.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return the total number of months in the period, may be negative
     */
    public long toTotalMonths() {
        return this.years * 12L + this.months;  // no overflow
    }

	public int getYears() {
		return this.years;
	}

	public int getMonths() {
		return this.months;
	}

	public int getDays() {
		return this.days;
	}

	public int getHours() {
		return this.hours;
	}

	public int getMinutes() {
		return this.minutes;
	}

}
