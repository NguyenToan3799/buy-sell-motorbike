package buysellmoto.model.filter;

import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import buysellmoto.model.dto.MotorbikeImageDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MotorbikeFilter {

    //Dto
    private String name;

    private String licensePlate;

    private Double engineSize;

    private String description;

    private String condition;

    private Double odo;

    @JsonSerialize(using = DateToTimestamp.class)
    @JsonDeserialize(using = TimestampToDate.class)
    private LocalDateTime yearOfRegistration;

    private String status;

    private String motoType;

    private Long motoBrandId;

    private Long customerId;

    private Long showroomId;

    // For create
    private List<MotorbikeImageDto> motorbikeImageDtos = new ArrayList<>();

}
