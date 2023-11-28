package buysellmoto.model.vo;

import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface PostProjection {

    Long getId();

    Double getPrice();

    @JsonSerialize(using = DateToTimestamp.class)
    @JsonDeserialize(using = TimestampToDate.class)
    LocalDateTime getCreatedDate();

    String getMotorbikeName();

    String getMotorbikeCondition();

    Double getMotorbikeOdo();

    String getYearOfRegistration();

    String getLocation();

    String getLogoBrand();

    String getBrandName();

    String getMotorbikeThumbnail();

}
