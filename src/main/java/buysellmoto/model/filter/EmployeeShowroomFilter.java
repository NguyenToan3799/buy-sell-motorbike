package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import buysellmoto.model.dto.EmployeeShowroomDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Log4j2
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class EmployeeShowroomFilter extends ApiFilter<EmployeeShowroomDto> {

}
