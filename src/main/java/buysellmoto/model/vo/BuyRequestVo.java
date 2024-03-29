package buysellmoto.model.vo;

import buysellmoto.model.dto.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuyRequestVo extends BuyRequestDto {

    private CustomerVo customerVo;

    private MotorbikeDto motorbikeDto;

    private UserDto userDto;

    private List<MotorbikeImageDto> motorbikeImageDto;

    private ShowroomDto showroomDto;

    private PostDto postDto;

    private List<TransactionDto> transactionDtos;

    private CheckingAppointmentDto checkingAppointmentDto;

    private PurchaseAppointmentDto purchaseAppointmentDto;

    private List<RequestHistoryDto> requestHistoryDtos;

}
