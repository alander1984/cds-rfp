package tech.lmru.yandex.dto;

public class VisitedLocation {
    /**
     * Reference to a location id, specified in locations.
     */
    private int id;
    /**
     * Unique identifier of a shift.
     */
    private String shift_id;
    /**
     * Time of arrival to already visited location, in [D.]HH[:MM[:SS]] format or ISO 8601 2018-09-06T10:15:00+03:00, 2018-09-06T10:15:00Z.
     */
    private String time;
}
