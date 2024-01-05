package buysellmoto.dao;

import buysellmoto.model.dto.CheckedSellRequestDto;
import buysellmoto.model.mapper.CheckedSellRequestMapper;
import buysellmoto.repository.CheckedSellRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckedSellRequestDao {

    @Autowired
    private CheckedSellRequestRepository checkedSellRequestRepository;

    @Autowired
    private CheckedSellRequestMapper mapper;

    public CheckedSellRequestDto getById(Long id) {
        return mapper.toDto(checkedSellRequestRepository.findById(id).orElseThrow());
    }

    public CheckedSellRequestDto getBySellRequestId(Long sellRequestId) {
        return mapper.toDto(checkedSellRequestRepository.findBySellRequestId(sellRequestId).orElse(null));
    }


    public List<CheckedSellRequestDto> getAll() {
        return mapper.toDto(checkedSellRequestRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public CheckedSellRequestDto createOne(CheckedSellRequestDto dto) {
        return mapper.toDto(checkedSellRequestRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public CheckedSellRequestDto updateOne(CheckedSellRequestDto dto) {
        return mapper.toDto(checkedSellRequestRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        checkedSellRequestRepository.delete(checkedSellRequestRepository.findById(id).orElseThrow());
        return true;
    }

}
