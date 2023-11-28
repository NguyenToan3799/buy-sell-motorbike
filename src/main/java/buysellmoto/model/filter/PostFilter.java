package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.StringUtil;
import buysellmoto.core.ultilities.TimestampToDate;
import buysellmoto.model.dto.PostDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostFilter extends ApiFilter<PostDto> {

    private String searchValue;

    private String brandName;

    @JsonIgnore
    public void beautify() {
        this.searchValue = StringUtil.trim(this.searchValue);
        this.brandName = StringUtil.trim(this.brandName);
    }
}
