package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import buysellmoto.model.dto.CheckingAppointmentDto;
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
public class CheckingAppointmentFilter extends ApiFilter<CheckingAppointmentDto> {

}
