package tech.lmru.yandex.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class VisitedLocation {
    /**
     * Reference to a location id, specified in locations.
     */
    private int id=0;
    /**
     * Unique identifier of a shift.
     */
    @JsonInclude(Include.NON_NULL)
    private String shift_id;
    /**
     * Time of arrival to already visited location, in [D.]HH[:MM[:SS]] format or ISO 8601 2018-09-06T10:15:00+03:00, 2018-09-06T10:15:00Z.
     */
    @JsonInclude(Include.NON_NULL)
    private String time;
}
