package buysellmoto.dao;

import buysellmoto.model.dto.RequestHistoryDto;
import buysellmoto.model.entity.TransactionEntity;
import buysellmoto.model.mapper.RequestHistoryMapper;
import buysellmoto.repository.RequestHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RequestHistoryDao {

    @Autowired
    private RequestHistoryRepository requestHistoryRepository;

    @Autowired
    private RequestHistoryMapper requestHistoryMapper;

    public List<RequestHistoryDto> getByRequestIdAndRequestType(Long requestId, String requestType) {
        return requestHistoryMapper.toDto(requestHistoryRepository.findAllByRequestIdAndRequestType(requestId, requestType))
                .stream()
                .sorted(Comparator.comparing(RequestHistoryDto::getCreatedDate).reversed())
                .toList();
    }

    @Transactional(rollbackOn = {Exception.class})
    public RequestHistoryDto createOne(RequestHistoryDto dto) {
        return requestHistoryMapper.toDto(requestHistoryRepository.save(requestHistoryMapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public RequestHistoryDto updateOne(RequestHistoryDto dto) {
        return requestHistoryMapper.toDto(requestHistoryRepository.save(requestHistoryMapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        requestHistoryRepository.delete(requestHistoryRepository.findById(id).orElseThrow());
        return true;
    }

}
