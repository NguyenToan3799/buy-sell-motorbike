package buysellmoto.dao;

import buysellmoto.model.dto.RejectRequestDto;
import buysellmoto.model.mapper.RejectRequestMapper;
import buysellmoto.repository.RejectRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RejectRequestDao {

    @Autowired
    private RejectRequestRepository rejectRequestRepository;

    @Autowired
    private RejectRequestMapper mapper;

    public RejectRequestDto getById(Long id) {
        return mapper.toDto(rejectRequestRepository.findById(id).orElseThrow());
    }

    public RejectRequestDto getBySellRequestId(Long sellRequestId) {
        return mapper.toDto(rejectRequestRepository.findBySellRequestId(sellRequestId).orElse(null));
    }

    public List<RejectRequestDto> getAll() {
        return mapper.toDto(rejectRequestRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public RejectRequestDto createOne(RejectRequestDto dto) {
        return mapper.toDto(rejectRequestRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public RejectRequestDto updateOne(RejectRequestDto dto) {
        return mapper.toDto(rejectRequestRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        rejectRequestRepository.delete(rejectRequestRepository.findById(id).orElseThrow());
        return true;
    }

}
