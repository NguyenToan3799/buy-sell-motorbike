package buysellmoto.dao;

import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.mapper.BuyRequestMapper;
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

    public List<BuyRequestDto> getAll() {
        return mapper.toDto(buyRequestRepository.findAll());
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
    public boolean deleteById(Long id) {
        buyRequestRepository.delete(buyRequestRepository.findById(id).orElseThrow());
        return true;
    }

}