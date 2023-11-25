package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import buysellmoto.model.dto.CustomerDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.AbstractPastInstantBasedValidator;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CustomerFilter extends ApiFilter<CustomerDto> {

}
