package buysellmoto.model.filter;

import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseAppointmentFilter {

    //Dto
    private String status;

    @JsonSerialize(using = DateToTimestamp.class)
    @JsonDeserialize(using = TimestampToDate.class)
    private LocalDateTime appointmentDate;

    private Long customerId;

    private Long motorbikeId;

    private Long showroomId;

}
