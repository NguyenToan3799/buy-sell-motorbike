package buysellmoto.model.filter;

import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.vo.MotorbikeVo;
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
public class SellRequestFilter {

    //Dto
    private String code;

    @JsonSerialize(using = DateToTimestamp.class)
    @JsonDeserialize(using = TimestampToDate.class)
    private LocalDateTime createdDate;

    private String status;

    private Double askingPrice;

    private Long motorbikeId;

    private Long customerId;

    private Long showroomId;

    private MotorbikeVo motorbikeVo;

}
