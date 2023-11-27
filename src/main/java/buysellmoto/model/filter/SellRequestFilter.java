package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import buysellmoto.model.dto.CheckedSellRequestDto;
import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.dto.RejectRequestDto;
import buysellmoto.model.dto.SellRequestDto;
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
public class SellRequestFilter extends ApiFilter<SellRequestDto> {

    private MotorbikeVo motorbikeVo;

    private RejectRequestDto rejectRequestDto;

    private MotorbikeDto motorbikeDto;

    private CheckedSellRequestDto checkedSellRequestDto;

}
