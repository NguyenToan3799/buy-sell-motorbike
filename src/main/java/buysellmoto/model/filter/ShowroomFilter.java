package buysellmoto.model.filter;

import buysellmoto.core.exception.ApiFilter;
import buysellmoto.core.ultilities.StringUtil;
import buysellmoto.model.dto.ShowroomDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ShowroomFilter extends ApiFilter<ShowroomDto> {

    private String searchValue;

    private String province;

    @JsonIgnore
    public void beautify() {
        this.searchValue = StringUtil.trim(this.searchValue);
        this.province = StringUtil.trim(this.province);

    }

}
