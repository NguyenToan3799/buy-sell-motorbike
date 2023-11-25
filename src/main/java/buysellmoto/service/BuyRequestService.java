package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.BuyRequestDao;
import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.filter.BuyRequestFilter;
import buysellmoto.model.mapper.BuyRequestMapper;
import jakarta.transaction.Transactional;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.AbstractPastInstantBasedValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BuyRequestService {

    @Autowired
    private BuyRequestDao buyRequestDao;
    @Autowired
    private BuyRequestMapper buyRequestMapper;

    public BuyRequestDto getById(Long id) {
        if (Objects.isNull(id)) {
        }
        return buyRequestDao.getById(id);
    }

    public List<BuyRequestDto> getAll() {
        return buyRequestDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public BuyRequestDto createOne(BuyRequestFilter filter) {
        BuyRequestDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return buyRequestDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public BuyRequestDto updateOne(BuyRequestFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        BuyRequestDto preparingDto = filter.getCriteria();
        return buyRequestDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        buyRequestDao.deleteById(id);
        return true;
    }

}
