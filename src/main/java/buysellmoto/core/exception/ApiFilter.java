package buysellmoto.core.exception;

import buysellmoto.core.constants.Constants;
import buysellmoto.core.enumeration.NamingConventionEnum;
import buysellmoto.core.ultilities.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApiFilter<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T criteria;

    private String search;

    private List<String> sortByMultiple = new ArrayList<>();
    private String sortBy;
    private String sortDirection;
    private NamingConventionEnum sortByConvention;

    private Integer page;
    private Integer size;

    @JsonIgnore
    private List<String> sortDirectionSupport = Arrays.asList(Sort.Direction.ASC.name(), Sort.Direction.DESC.name());

    public void initialize() {
        initialize("id");
    }

    public void initialize(String sortByDefault) {
        /* Set default PageNumber */
        if (Objects.isNull(page) || page < 0) {
            page = Constants.DEFAULT_PAGE;
        }
        /* Set default PageSize */
        if (Objects.isNull(size) || size < 0 || size > Constants.DEFAULT_PAGE_SIZE_MAX) {
            size = Constants.DEFAULT_PAGE_SIZE;
        }
        /* Set default Sort Direction */
        if (StringUtils.isBlank(sortDirection) || !sortDirectionSupport.contains(sortDirection.toUpperCase())) {
            sortDirection = Sort.Direction.ASC.name();
        }
        /* Set default SortBy*/
        if (StringUtils.isBlank(sortBy)) {
            sortBy = sortByDefault;
        }
        /* Set default Sort Type to Camel, based on the definition of FE. */
        if (Objects.isNull(sortByConvention)) {
            sortByConvention = NamingConventionEnum.CAMEL;
        } else {
            /* Filter transformation (use Camel) */
            sortBy = Constants.CREATED_DATE.equals(sortBy) ? Constants.CREATED_DATE_TIME
                    : Constants.UPDATED_DATE.equals(sortBy) ? Constants.UPDATED_DATE_TIME
                    : this.sortBy;
        }
    }

    /* Remember that input must be camel by default */
    public Sort getSort() {
        initialize();
        if (CollectionUtils.isNotEmpty(sortByMultiple)) {
            String[] stockArr = new String[sortByMultiple.size()];
            for (int i = 0; i < sortByMultiple.size(); i++) {
                stockArr[i] = transform(sortByMultiple.get(i));
            }
            return Sort.by(Sort.Direction.ASC.name().equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC, stockArr);
        }
        return Sort.Direction.ASC.name().equalsIgnoreCase(sortDirection)
                ? Sort.by(transform(sortBy)).ascending()
                : Sort.by(transform(sortBy)).descending();
    }

    public Pageable getPageable() {
        return getPageable(NamingConventionEnum.CAMEL);
    }

    public Pageable getPageable(NamingConventionEnum sortByConvention) {
        this.sortByConvention = sortByConvention;
        return PageRequest.of((page == null || page <= 0) ? Constants.DEFAULT_PAGE : page, (size == null || size <= 0) ? Constants.DEFAULT_PAGE_SIZE : size, getSort());
    }

    public String getSearch() {
        if (StringUtils.isBlank(search)) {
            return StringUtils.EMPTY;
        }

        /* Check sensitive character */
        for (String sc : Arrays.asList("%", "_")) {
            search = search.replaceAll(sc, "\\\\" + sc);
        }

        return search;
    }

    public String transform(String val) {
        switch (sortByConvention) {
            case SNAKE:
                return StringUtil.camelToSnake(val);
            case PASCAL:
                return StringUtil.snakeToPascal(StringUtil.camelToSnake(val));
        }
        return val;
    }

}
