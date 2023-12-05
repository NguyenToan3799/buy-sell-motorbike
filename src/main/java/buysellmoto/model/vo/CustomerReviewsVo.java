package buysellmoto.model.vo;

import buysellmoto.model.dto.CommentReviewsDto;
import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.dto.CustomerReviewsDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerReviewsVo extends CustomerReviewsDto {

    private CustomerDto customerDto;

    private List<CommentReviewsDto> commentReviewsDtos = new ArrayList<>();

}
