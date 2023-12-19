package buysellmoto.dao;

import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.mapper.SellRequestMapper;
import buysellmoto.model.vo.SellRequestVo;
import buysellmoto.repository.SellRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellRequestDao {

    @Autowired
    private SellRequestRepository sellRequestRepository;

    @Autowired
    private SellRequestMapper mapper;

    public SellRequestVo getById(Long id) {
        return mapper.entityToVo(sellRequestRepository.findById(id).orElseThrow());
    }

    public List<SellRequestDto> getAll() {
        return mapper.toDto(sellRequestRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellRequestDto createOne(SellRequestDto dto) {
        return mapper.toDto(sellRequestRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellRequestDto updateOne(SellRequestDto dto) {
        return mapper.toDto(sellRequestRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean updateAll(List<SellRequestVo> vos) {
        sellRequestRepository.saveAll(mapper.voToEntity(vos));
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        sellRequestRepository.delete(sellRequestRepository.findById(id).orElseThrow());
        return true;
    }

    public List<SellRequestVo> getByShowroomIdAndStatus(Long showroomId, String status){
        return mapper.entityToVo(sellRequestRepository.findAllByShowroomIdAndStatus(showroomId, status));
    }

    public List<SellRequestVo> getByStatus(String status){
        return mapper.entityToVo(sellRequestRepository.findAllByStatus(status));
    }

    public List<SellRequestVo> getByIds(List<Long> ids) {
        return mapper.entityToVo(sellRequestRepository.findAllByIdIn(ids));
    }

    public List<SellRequestVo> findAllByCustomerId(Long customerId){
        return mapper.entityToVo(sellRequestRepository.findAllByCustomerId(customerId));
    }

}
