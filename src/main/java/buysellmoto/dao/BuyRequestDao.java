package buysellmoto.dao;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.mapper.BuyRequestMapper;
import buysellmoto.model.vo.BuyRequestVo;
import buysellmoto.repository.BuyRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyRequestDao {

    @Autowired
    private BuyRequestRepository buyRequestRepository;

    @Autowired
    private BuyRequestMapper mapper;

    public BuyRequestDto getById(Long id) {
        return mapper.toDto(buyRequestRepository.findById(id).orElseThrow());
    }

    public List<BuyRequestDto> getByIds(List<Long> ids) {
        return mapper.toDto(buyRequestRepository.findAllByIdIn(ids));
    }

    public List<BuyRequestDto> getAll() {
        return mapper.toDto(buyRequestRepository.findAll());
    }

    public List<BuyRequestVo> getByShowroomIdAndStatus(Long showroomId, String status) {
        return mapper.entityToVo(buyRequestRepository.findAllByShowroomIdAndStatus(showroomId, status));
    }

    public List<BuyRequestVo> getByCustomerId(Long customerId) {
        return mapper.entityToVo(buyRequestRepository.findAllByCustomerId(customerId));
    }

    @Transactional(rollbackOn = {Exception.class})
    public BuyRequestDto createOne(BuyRequestDto dto) {
        return mapper.toDto(buyRequestRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public BuyRequestDto updateOne(BuyRequestDto dto) {
        return mapper.toDto(buyRequestRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public List<BuyRequestDto> updateAllVos(List<BuyRequestVo> vos) {
        return mapper.toDto(buyRequestRepository.saveAll(mapper.voToEntity(vos)));
    }


    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        buyRequestRepository.delete(buyRequestRepository.findById(id).orElseThrow());
        return true;
    }

    public List<BuyRequestVo> findAllByPostIdAndStatus(Long postId, String status) {
        return mapper.entityToVo(buyRequestRepository.findAllByPostIdAndStatus(postId, status));
    }

    public BuyRequestDto findByCustomerIdAndMotorbikeIdAndStatus(Long customerId, Long motorbikeId, String status) {
        return mapper.toDto(buyRequestRepository
                .findByCustomerIdAndMotorbikeIdAndStatus(customerId, motorbikeId, status)
                .orElseThrow(() -> new BusinessException("Bạn không thể gửi yêu cầu xe này thêm lần nữa")));
    }

}
