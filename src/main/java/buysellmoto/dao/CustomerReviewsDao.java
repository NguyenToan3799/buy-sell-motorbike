package buysellmoto.dao;

import buysellmoto.model.dto.CustomerReviewsDto;
import buysellmoto.model.mapper.CustomerReviewsMapper;
import buysellmoto.model.vo.CustomerReviewsVo;
import buysellmoto.repository.CustomerReviewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerReviewsDao {

    @Autowired
    private CustomerReviewsRepository customerReviewsRepository;

    @Autowired
    private CustomerReviewsMapper mapper;

    public CustomerReviewsDto getById(Long id) {
        return mapper.toDto(customerReviewsRepository.findById(id).orElseThrow());
    }

    public List<CustomerReviewsVo> getByShowroomId(Long showroomId) {
        return mapper.entityToVo(customerReviewsRepository.findAllByShowroomId(showroomId));
    }

    public List<CustomerReviewsDto> getAll() {
        return mapper.toDto(customerReviewsRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerReviewsDto createOne(CustomerReviewsDto dto) {
        return mapper.toDto(customerReviewsRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public CustomerReviewsDto updateOne(CustomerReviewsDto dto) {
        return mapper.toDto(customerReviewsRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        customerReviewsRepository.delete(customerReviewsRepository.findById(id).orElseThrow());
        return true;
    }

}
