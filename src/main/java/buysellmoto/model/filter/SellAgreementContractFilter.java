package buysellmoto.model.filter;

import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellAgreementContractFilter {

    //Dto
    private String code;

    @JsonSerialize(using = DateToTimestamp.class)
    @JsonDeserialize(using = TimestampToDate.class)
    private LocalDateTime contractSignDate;

    private Double contractDuration;

    private Double serviceFee;

    private Double commissionFee;

    private Double agreementPrice;

    private Boolean customerSignature;

    private Boolean showroomSignature;

    private String status;

    private Long customerId;

    private Long showroomId;

    private Long motorbikeId;

}
