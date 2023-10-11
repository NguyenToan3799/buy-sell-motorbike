package buysellmoto.service;


import buysellmoto.dao.CustomerDao;
import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.filter.CustomerFilter;
import buysellmoto.model.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerMapper customerMapper;

    public CustomerDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return customerDao.getById(id);
    }
    
    public List<CustomerDto> getAll() {
        return customerDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerDto createOne (CustomerFilter filter) {
        CustomerDto preparingDto = customerMapper.filterToDto(filter);
        return customerDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerDto updateOne(Long id, CustomerFilter filter) {
        CustomerDto preparingDto = customerMapper.filterToDto(filter);
        preparingDto.setId(id);
        return customerDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        customerDao.deleteById(id);
        return true;
    }

}
