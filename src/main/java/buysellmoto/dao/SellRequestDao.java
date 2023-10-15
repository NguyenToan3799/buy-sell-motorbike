package buysellmoto.dao;

import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.mapper.SellRequestMapper;
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

    public SellRequestDto getById(Long id) {
        return mapper.toDto(sellRequestRepository.findById(id).orElseThrow());
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
    public boolean deleteById(Long id) {
        sellRequestRepository.delete(sellRequestRepository.findById(id).orElseThrow());
        return true;
    }

}
