package buysellmoto.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long id;

    private String name;

    @JsonIgnore
    public void beautify() {
        this.name = StringUtils.trim(this.name);

    }

}
