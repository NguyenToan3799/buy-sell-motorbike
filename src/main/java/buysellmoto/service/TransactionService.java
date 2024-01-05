package buysellmoto.service;


import buysellmoto.core.enumeration.TransactionTypeEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.BuyRequestDao;
import buysellmoto.dao.SellRequestDao;
import buysellmoto.dao.TransactionDao;
import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.dto.TransactionDto;
import buysellmoto.model.filter.TransactionFilter;
import buysellmoto.model.mapper.TransactionMapper;
import buysellmoto.model.vo.SellRequestVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.BreakIterator;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private BuyRequestService buyRequestService;
    @Autowired
    private SellRequestService sellRequestService;
    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionDto getById(Long id) {
        if (Objects.isNull(id)) {
        }
        return transactionDao.getById(id);
    }

    public List<TransactionDto> getAll() {
        return transactionDao.getAll().stream()
                .sorted(Comparator.comparing(TransactionDto::getRecordedDate).reversed())
                .toList();
    }

    public List<TransactionDto> getByBuyRequestId(Long buyRequestId) {
        return transactionDao.getByBuyRequestId(buyRequestId).stream()
                .sorted(Comparator.comparing(TransactionDto::getRecordedDate).reversed())
                .toList();
    }

    public List<TransactionDto> getBySellRequestId(Long sellRequestId) {
        return transactionDao.getBySellRequestId(sellRequestId).stream()
                .sorted(Comparator.comparing(TransactionDto::getRecordedDate).reversed())
                .toList();
    }

    public List<TransactionDto> getByShowroomId(Long showroomId) {
        return transactionDao.getByShowroomId(showroomId).stream()
                .sorted(Comparator.comparing(TransactionDto::getRecordedDate).reversed())
                .toList();
    }

    public List<TransactionDto> getByShowroomIdAndType(Long showroomId, String type) {
        return transactionDao.getByShowroomIdAndType(showroomId, type).stream()
                .sorted(Comparator.comparing(TransactionDto::getRecordedDate).reversed())
                .toList();
    }

    @Transactional(rollbackOn = {Exception.class})
    public TransactionDto createOne(TransactionFilter filter) {
        TransactionDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        preparingDto.setRecordedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());

        switch (TransactionTypeEnum.of(filter.getCriteria().getType())) {
            case DEPOSIT -> buyRequestService.depositBuyRequest(filter.getCriteria().getBuyRequestId());
            case FINALISE -> buyRequestService.completeBuyRequest(filter.getCriteria().getBuyRequestId());
            case SELLER_PAY -> sellRequestService.completeSellRequest(filter.getCriteria().getSellRequestId());
            case POSTING_FEE -> sellRequestService.renewSellRequest(filter.getCriteria().getSellRequestId());
        }
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