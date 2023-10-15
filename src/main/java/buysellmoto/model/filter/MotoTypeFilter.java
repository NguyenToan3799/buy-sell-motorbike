package buysellmoto.model.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MotoTypeFilter {

    //Dto
    private String name;

    private Double engineSize;

    private Long motoBrandId;

}
