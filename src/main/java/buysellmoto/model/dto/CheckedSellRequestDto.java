package buysellmoto.model.dto;

import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckedSellRequestDto implements Serializable {

    private Long id;

    @JsonSerialize(using = DateToTimestamp.class)
    @JsonDeserialize(using = TimestampToDate.class)
    private LocalDateTime checkedDate;

    @Column(nullable = false)
    private Double finalPrice;

    private Double sellerReceiveAmount;

    private String technicianNote;

    private String checkedBy;

    @Column(nullable = false, columnDefinition = "BIGINT")
    private Long sellRequestId;

}
