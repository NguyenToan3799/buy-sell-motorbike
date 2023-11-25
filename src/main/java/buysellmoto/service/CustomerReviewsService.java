package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.CustomerReviewsDao;
import buysellmoto.model.dto.CustomerReviewsDto;
import buysellmoto.model.filter.CustomerReviewsFilter;
import buysellmoto.model.mapper.CustomerReviewsMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerReviewsService {

    @Autowired
    private CustomerReviewsDao customerReviewsDao;
    @Autowired
    private CustomerReviewsMapper customerReviewsMapper;

    public CustomerReviewsDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return customerReviewsDao.getById(id);
    }
    
    public List<CustomerReviewsDto> getAll() {
        return customerReviewsDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerReviewsDto createOne (CustomerReviewsFilter filter) {
        CustomerReviewsDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return customerReviewsDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerReviewsDto updateOne(CustomerReviewsFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        CustomerReviewsDto preparingDto = filter.getCriteria();
        return customerReviewsDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        customerReviewsDao.deleteById(id);
        return true;
    }

}
