package buysellmoto.model.vo;

import buysellmoto.model.dto.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseAppointmentVo extends PurchaseAppointmentDto {

    private CustomerDto buyerDto;

    private CustomerDto sellerDto;

    private ShowroomDto showroomDto;

    private MotorbikeDto motorbikeDto;

    private BuyRequestDto buyRequestDto;

    private SellRequestDto sellRequestDto;

}
