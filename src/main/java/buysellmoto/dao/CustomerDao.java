package buysellmoto.dao;

import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.mapper.CustomerMapper;
import buysellmoto.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDao {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper mapper;

    public CustomerDto getById(Long id) {
        return mapper.toDto(customerRepository.findById(id).orElseThrow());
    }

    public CustomerDto getByUserId(Long userId) {
        return mapper.toDto(customerRepository.getByUserId(userId));
    }

    public List<CustomerDto> getAll() {
        return mapper.toDto(customerRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerDto createOne(CustomerDto dto) {
        return mapper.toDto(customerRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerDto updateOne(CustomerDto dto) {
        return mapper.toDto(customerRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        customerRepository.delete(customerRepository.findById(id).orElseThrow());
        return true;
    }

}
