package tech.lmru.yandex.courier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Ilya on 22.04.2019.
 */
@Data
@Builder
public class RouteDto {

    @JsonProperty("car_id")
    private Long carId;

    @JsonProperty("courier_number")
    private String courierNumber;

    @JsonProperty
    private String date;

    @JsonProperty("depot_number")
    private String depotNumber;

    @JsonProperty
    private String number;

    @JsonProperty
    private Long imei;
}
