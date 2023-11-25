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
