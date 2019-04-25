package tech.lmru.yandex.courier.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Ilya on 22.04.2019.
 */
@Data
@Builder
public class CourierDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String number;

    @JsonProperty
    private String phone;

    @JsonProperty("sms_enabled")
    private Boolean smsEnabled;
}
