package tech.lmru.yandex.courier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Ilya on 13.04.2019.
 */
@Data
@Builder
public class DepotDto {

    @JsonProperty
    private String address;

    @JsonProperty
    private String description;

    @JsonProperty
    private Double lat;

    @JsonProperty
    private Double lon;

    @JsonProperty
    private String name;

    @JsonProperty
    private String number;

    @JsonProperty("order_service_duration_s")
    private Integer orderServiceDurationS;

    @JsonProperty("service_duration_s")
    private Integer serviceDurationS;

    @JsonProperty("time_interval")
    private String timeInterval;

    @JsonProperty("time_zone")
    private String timeZone;
}
