package buysellmoto.service;

import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.MotorbikeDao;
import buysellmoto.dao.MotorbikeImageDao;
import buysellmoto.dao.RejectRequestDao;
import buysellmoto.dao.SellRequestDao;
import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.dto.MotorbikeImageDto;
import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.filter.SellRequestFilter;
import buysellmoto.model.mapper.MotorbikeImageMapper;
import buysellmoto.model.mapper.MotorbikeMapper;
import buysellmoto.model.mapper.RejectRequestMapper;
import buysellmoto.model.mapper.SellRequestMapper;
import buysellmoto.model.vo.MotorbikeVo;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Service
public class SellRequestService {

    @Autowired
    private SellRequestDao sellRequestDao;
    @Autowired
    private RejectRequestDao rejectRequestDao;
    @Autowired
    private MotorbikeDao motorbikeDao;
    @Autowired
    private MotorbikeImageDao motorbikeImageDao;
    @Autowired
    private SellRequestMapper sellRequestMapper;
    @Autowired
    private RejectRequestMapper rejectRequestMapper;
    @Autowired
    private MotorbikeMapper motorbikeMapper;
    @Autowired
    private MotorbikeImageMapper motorbikeImageMapper;

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

        // Create Motorbike
        MotorbikeVo motorbikeVo = filter.getMotorbikeVo();
        motorbikeVo.setId(null);
        motorbikeVo.setStatus(MotorbikeEnum.IN_SELL_REQUEST.getCode());
        MotorbikeDto motorbikeDto = motorbikeDao.createOne(motorbikeMapper.voToDto(motorbikeVo));

        //Crate List Image
        if(ObjectUtils.isEmpty(motorbikeVo.getMotorbikeImageDtos())){
            List<MotorbikeImageDto> motorbikeImageDtos = motorbikeVo.getMotorbikeImageDtos();
            motorbikeImageDtos.forEach(motorbikeImageDto -> {
                motorbikeImageDto.setId(null);
                motorbikeImageDto.setMotorbikeId(motorbikeDto.getId());
            });
            motorbikeImageDao.createAll(motorbikeImageDtos);
        }

        // Create
        SellRequestDto sellRequestDto = filter.getCriteria();
        sellRequestDto.setId(null);
        sellRequestDto.setCode(generateCode());
        sellRequestDto.setStatus(SellRequestEnum.CREATED.getCode());
        sellRequestDto.setCreatedDate(LocalDateTime.now());
        sellRequestDto.setMotorbikeId(motorbikeDto.getId());

        return sellRequestDao.createOne(sellRequestDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public SellRequestDto updateOne(SellRequestFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        SellRequestDto preparingDto = filter.getCriteria();
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

//    @Transactional(rollbackOn = {Exception.class})
//    public Boolean rejectedSellRequest(Long id, RejectRequestFilter rejectRequestFilter){
//        if(Objects.isNull(sellRequestDao.getById(id))){
//            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
//        }
//        this.updateStatus(id, SellRequestEnum.REJECTED.getCode());
//
//        rejectRequestFilter.setRejectedDate(LocalDateTime.now());
//        rejectRequestFilter.setSellRequestId(id);
//        rejectRequestDao.createOne(rejectRequestMapper.filterToDto(rejectRequestFilter));
//
//        return true;
//    }

    private Boolean updateStatus(Long id, String newStatus) {
        SellRequestDto loadingDto = sellRequestDao.getById(id);
        if(!validateStatusMoving(SellRequestEnum.of(loadingDto.getStatus()), SellRequestEnum.of(newStatus))){
            throw new BusinessException(ApiMessageCode.INVALID_STATUS_MOVING);
        }
        loadingDto.setStatus(newStatus);
        sellRequestDao.updateOne(loadingDto);
        return true;
    }

    private String generateCode() {
        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        String serial = "SR" + RandomStringUtils.random(13, timestamp.toString());
        return serial;
    }

    private boolean validateStatusMoving(SellRequestEnum preStatus, SellRequestEnum newStatus) {
        switch (preStatus) {
            case CREATED:
                if (newStatus == SellRequestEnum.APPROVED || newStatus == SellRequestEnum.REJECTED) {
                    return true;
                }
            case APPROVED:
                if (newStatus == SellRequestEnum.CHECKED || newStatus == SellRequestEnum.REJECTED) {
                    return true;
                }

            case CHECKED:
                if (newStatus == SellRequestEnum.POSTED) {
                    return true;
                }
            case POSTED:
                if (newStatus == SellRequestEnum.COMPLETED) {
                    return true;
                }
            default:
                return false;
        }
    }

}
