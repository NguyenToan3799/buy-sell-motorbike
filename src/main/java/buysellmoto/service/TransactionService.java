package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.TransactionDao;
import buysellmoto.model.dto.TransactionDto;
import buysellmoto.model.filter.TransactionFilter;
import buysellmoto.model.mapper.TransactionMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return transactionDao.getById(id);
    }
    
    public List<TransactionDto> getAll() {
        return transactionDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public TransactionDto createOne (TransactionFilter filter) {
        TransactionDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return transactionDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public TransactionDto updateOne(TransactionFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        TransactionDto preparingDto = filter.getCriteria();
        return transactionDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        transactionDao.deleteById(id);
        return true;
    }

}