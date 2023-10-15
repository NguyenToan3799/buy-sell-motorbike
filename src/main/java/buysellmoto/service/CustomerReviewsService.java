package buysellmoto.service;


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
        CustomerReviewsDto preparingDto = customerReviewsMapper.filterToDto(filter);
        return customerReviewsDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerReviewsDto updateOne(Long id, CustomerReviewsFilter filter) {
        CustomerReviewsDto preparingDto = customerReviewsMapper.filterToDto(filter);
        preparingDto.setId(id);
        return customerReviewsDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        customerReviewsDao.deleteById(id);
        return true;
    }

}
