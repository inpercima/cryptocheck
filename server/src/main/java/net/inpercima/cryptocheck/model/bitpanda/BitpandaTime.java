package net.inpercima.cryptocheck.model.bitpanda;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitpandaTime {

    /**
     * date of transaction
     * <p>
     * api response: {@code date}
     * <p>
     * example: 2019-02-21T16:36:01+01:00
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @JsonProperty("date_iso8601")
    private LocalDateTime dateIso8601;
}
