package buysellmoto.service;

import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.core.exception.UnauthorizedException;
import buysellmoto.dao.RejectRequestDao;
import buysellmoto.dao.SellRequestDao;
import buysellmoto.model.dto.RejectRequestDto;
import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.filter.RejectRequestFilter;
import buysellmoto.model.filter.SellRequestFilter;
import buysellmoto.model.mapper.RejectRequestMapper;
import buysellmoto.model.mapper.SellRequestMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SellRequestService {

    @Autowired
    private SellRequestDao sellRequestDao;
    @Autowired
    private RejectRequestDao rejectRequestDao;
    @Autowired
    private SellRequestMapper sellRequestMapper;
    @Autowired
    private RejectRequestMapper rejectRequestMapper;

    public SellRequestDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return sellRequestDao.getById(id);
    }
    
    public List<SellRequestDto> getAll() {
        return sellRequestDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellRequestDto createOne (SellRequestFilter filter) {
        SellRequestDto preparingDto = sellRequestMapper.filterToDto(filter);
        return sellRequestDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellRequestDto updateOne(Long id, SellRequestFilter filter) {
        SellRequestDto preparingDto = sellRequestMapper.filterToDto(filter);
        preparingDto.setId(id);
        return sellRequestDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        sellRequestDao.deleteById(id);
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean approvedSellRequest(Long id){
        if(Objects.isNull(sellRequestDao.getById(id))){
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.APPROVED.getCode());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean rejectedSellRequest(Long id, RejectRequestFilter rejectRequestFilter){
        if(Objects.isNull(sellRequestDao.getById(id))){
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.REJECTED.getCode());

        rejectRequestFilter.setRejectedDate(LocalDateTime.now());
        rejectRequestFilter.setSellRequestId(id);
        rejectRequestDao.createOne(rejectRequestMapper.filterToDto(rejectRequestFilter));

        return true;
    }

    private Boolean updateStatus(Long id, String newStatus) {
        SellRequestDto loadingDto = sellRequestDao.getById(id);
        if(!validateStatusMoving(SellRequestEnum.of(loadingDto.getStatus()), SellRequestEnum.of(newStatus))){
            throw new BusinessException(ApiMessageCode.INVALID_STATUS_MOVING);
        }
        loadingDto.setStatus(newStatus);
        sellRequestDao.updateOne(loadingDto);
        return true;
    }

    private boolean validateStatusMoving(SellRequestEnum preStatus, SellRequestEnum newStatus) {
        switch (preStatus) {
            case DRAFT:
                if (newStatus == SellRequestEnum.CONFIRMED || newStatus == SellRequestEnum.CANCELLED) {
                    return true;
                }
            case CONFIRMED:
                if (newStatus == SellRequestEnum.APPROVED || newStatus == SellRequestEnum.REJECTED) {
                    return true;
                }
            case APPROVED:
                if (newStatus == SellRequestEnum.COMPLETED) {
                    return true;
                }
            default:
                return false;
        }
    }

}
