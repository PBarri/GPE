package com.awg.gpe.data.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @author a602274
 *
 */
public enum EnumSprintDuration {
	
	ONE_WEEK(1L, "sprint.duration.week", 7),
	TWO_WEEKS(2L, "sprint.duration.two.week", 14),
	THREE_WEEKS(3L, "sprint.duration.three.week", 21),
	ONE_MONTH(4L, "sprint.duration.month", 30);
	
	EnumSprintDuration(Long id, String code, Integer days) {
        this.id = id;
        this.code = code;
        this.days = days;
    }

    private final Long id;

    private final String code;

    private final Integer days;
   
    public static List<EnumSprintDuration> getValues() {
    	return Arrays.asList(EnumSprintDuration.values());
    }

    // Getters ----------------------------------------------------------------
    
	public Long getId() {
		return this.id;
	}

	public String getCode() {
		return this.code;
	}

	public Integer getDays() {
		return this.days;
	}

}
