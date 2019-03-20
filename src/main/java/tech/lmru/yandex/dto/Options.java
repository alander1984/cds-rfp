package tech.lmru.yandex.dto;

import java.math.BigDecimal;

public class Options {
    private BigDecimal time_zone = new BigDecimal(3);
    private BigDecimal default_speed_km_h;
    /**
     * Starting date for all routes (YYYY-MM-DD format), defaults to current date.
     */
    private String date;
    private Minimize minimize;
    
    
}
