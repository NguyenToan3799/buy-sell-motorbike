package buysellmoto.model.vo;

import buysellmoto.dao.PurchaseAppointmentDao;
import buysellmoto.dao.RejectRequestDao;
import buysellmoto.dao.ShowroomDao;
import buysellmoto.model.dto.*;
import buysellmoto.model.mapper.RejectRequestMapper;
import buysellmoto.repository.PurchaseAppointmentRepository;
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
public class SellRequestVo extends SellRequestDto {

    private CustomerDto customerDto;

    private CustomerVo customerVo;

    private MotorbikeDto motorbikeDto;

    private List<MotorbikeImageDto> motorbikeImageDto = new ArrayList<>();

    private ShowroomDto showroomDto;

    private RejectRequestDto rejectRequestDto;

    private CheckedSellRequestDto checkedSellRequestDto;

    private UserDto userDto;

    private PostDto postDto;

    private List<TransactionDto> transactionDtos;

    private PurchaseAppointmentDto purchaseAppointmentDto;

    private List<RequestHistoryDto> requestHistoryDtos;

}
