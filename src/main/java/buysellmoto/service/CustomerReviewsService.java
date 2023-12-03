package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.CustomerDao;
import buysellmoto.dao.CustomerReviewsDao;
import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.dto.CustomerReviewsDto;
import buysellmoto.model.filter.CustomerReviewsFilter;
import buysellmoto.model.mapper.CustomerReviewsMapper;
import buysellmoto.model.vo.CustomerReviewsVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerReviewsService {

    @Autowired
    private CustomerReviewsDao customerReviewsDao;
    @Autowired
    private CustomerDao customerDao;
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

    public List<CustomerReviewsVo> getByShowroomId(Long showroomId) {
        List<CustomerReviewsVo> customerReviewsVos = customerReviewsDao.getByShowroomId(showroomId);

        List<Long> customerIds = customerReviewsVos.stream().map(CustomerReviewsVo::getCustomerId).toList();
        Map<Long, CustomerDto> customerDtoMap = customerDao.getByIds(customerIds).stream()
                .collect(Collectors.toMap(CustomerDto::getId, Function.identity()));

        customerReviewsVos.forEach(customerReviewsVo
                -> customerReviewsVo.setCustomerDto(customerDtoMap.get(customerReviewsVo.getCustomerId())));
        return customerReviewsVos;
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
