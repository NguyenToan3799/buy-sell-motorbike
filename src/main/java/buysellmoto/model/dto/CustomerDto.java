package buysellmoto.model.dto;

import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {

    private Long id;

    private String fullName;

    @JsonSerialize(using = DateToTimestamp.class)
    @JsonDeserialize(using = TimestampToDate.class)
    private LocalDateTime dob;

    private String address;

    private String avatarUrl;

    private Long userId;

}
