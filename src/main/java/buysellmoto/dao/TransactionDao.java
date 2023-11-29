package buysellmoto.dao;

import buysellmoto.model.dto.TransactionDto;
import buysellmoto.model.mapper.TransactionMapper;
import buysellmoto.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDao {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionDto getById(Long id) {
        return transactionMapper.toDto(transactionRepository.findById(id).orElseThrow());
    }

    public List<TransactionDto> getAll() {
        return transactionMapper.toDto(transactionRepository.findAll());
    }

    public List<TransactionDto> getByShowroomId(Long showroomId) {
        return transactionMapper.toDto(transactionRepository.findAllByShowroomId(showroomId));
    }

    public List<TransactionDto> getByShowroomIdAndType(Long showroomId, String type) {
        return transactionMapper.toDto(transactionRepository.findAllByShowroomIdAndType(showroomId, type));
    }

    @Transactional(rollbackOn = {Exception.class})
    public TransactionDto createOne(TransactionDto dto) {
        return transactionMapper.toDto(transactionRepository.save(transactionMapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public TransactionDto updateOne(TransactionDto dto) {
        return transactionMapper.toDto(transactionRepository.save(transactionMapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        transactionRepository.delete(transactionRepository.findById(id).orElseThrow());
        return true;
    }

}