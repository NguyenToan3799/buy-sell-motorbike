package buysellmoto.service;

import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.*;
import buysellmoto.model.dto.*;
import buysellmoto.model.filter.MotorbikeFilter;
import buysellmoto.model.filter.RejectRequestFilter;
import buysellmoto.model.filter.SellRequestFilter;
import buysellmoto.model.mapper.*;
import buysellmoto.model.vo.MotorbikeVo;
import buysellmoto.model.vo.SellRequestVo;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private CustomerDao customerDao;
    @Autowired
    private ShowroomDao showroomDao;
    @Autowired
    private CheckedSellRequestDao checkedSellRequestDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MotorbikeMapper motorbikeMapper;
    @Autowired
    private CustomerMapper customerMapper;

    public SellRequestVo getById(Long id) {
        SellRequestVo sellRequestVo = sellRequestDao.getById(id);

        sellRequestVo.setShowroomDto(showroomDao.getById(sellRequestVo.getShowroomId()));
        sellRequestVo.setCustomerVo(customerMapper.dtoToVo(customerDao.getById(sellRequestVo.getCustomerId())));
        sellRequestVo.getCustomerVo().setPhone(userDao.getById(sellRequestVo.getCustomerVo().getUserId()).getPhone());

        sellRequestVo.setMotorbikeImageDto(motorbikeImageDao.getByMotorbikeId(sellRequestVo.getMotorbikeId()));

        if (sellRequestVo.getStatus().equals(SellRequestEnum.REJECTED.getCode())) {
            sellRequestVo.setRejectRequestDto(rejectRequestDao.getBySellRequestId(sellRequestVo.getId()));
        }
        if (sellRequestVo.getStatus().equals(SellRequestEnum.CHECKED.getCode())) {
            sellRequestVo.setCheckedSellRequestDto(checkedSellRequestDao.getBySellRequestId(sellRequestVo.getId()));
        }

        return sellRequestVo;
    }

    public List<SellRequestDto> getAll() {
        return sellRequestDao.getAll();
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
    public SellRequestDto createOne(SellRequestFilter filter) {

        // Create Motorbike
        MotorbikeVo motorbikeVo = filter.getMotorbikeVo();
        motorbikeVo.setId(null);
        MotorbikeDto motorbikeDto = motorbikeDao.createOne(motorbikeMapper.voToDto(motorbikeVo));

        //Create List Image
        if (!ObjectUtils.isEmpty(motorbikeVo.getMotorbikeImageDtos())) {
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

    public List<SellRequestVo> getListSellRequestByShowroomId(Long showroomId, String status) {
        if (SellRequestEnum.of(status) == SellRequestEnum.INVALID) {
            throw new BusinessException(ApiMessageCode.INVALID_STATUS);
        }
        List<SellRequestVo> sellRequestVos = sellRequestDao.getByShowroomIdAndStatus(showroomId, status);
        sellRequestVos = sellRequestVos.stream()
                .sorted(Comparator.comparing(SellRequestVo::getCreatedDate).reversed())
                .toList();

        // Lấy Customer
        List<Long> customerIds = sellRequestVos.stream().map(SellRequestVo::getCustomerId).distinct().toList();
        Map<Long, CustomerDto> mapCustomerDtos = customerDao.getByIds(customerIds).stream()
                .collect(Collectors.toMap(CustomerDto::getId, Function.identity()));

        // Lấy Motorbike
        List<Long> motorbikeIds = sellRequestVos.stream().map(SellRequestVo::getMotorbikeId).distinct().toList();
        Map<Long, MotorbikeDto> mapMotorbikeDto = motorbikeDao.getByIds(motorbikeIds).stream()
                .collect(Collectors.toMap(MotorbikeDto::getId, Function.identity()));

        sellRequestVos.forEach(sellRequestVo -> {
            sellRequestVo.setCustomerDto(mapCustomerDtos.get(sellRequestVo.getCustomerId()));
            sellRequestVo.setMotorbikeDto(mapMotorbikeDto.get(sellRequestVo.getMotorbikeId()));
        });

        return sellRequestVos;
    }

    public List<SellRequestVo> getListSellRequestByCustomerId(Long customerId) {
        List<SellRequestVo> sellRequestVos = sellRequestDao.findAllByCustomerId(customerId);
        sellRequestVos = sellRequestVos.stream()
                .sorted(Comparator.comparing(SellRequestVo::getCreatedDate).reversed())
                .toList();

        // Lấy Customer
        List<Long> customerIds = sellRequestVos.stream().map(SellRequestVo::getCustomerId).distinct().toList();
        Map<Long, CustomerDto> mapCustomerDtos = customerDao.getByIds(customerIds).stream()
                .collect(Collectors.toMap(CustomerDto::getId, Function.identity()));

        // Lấy Motorbike
        List<Long> motorbikeIds = sellRequestVos.stream().map(SellRequestVo::getMotorbikeId).distinct().toList();
        Map<Long, MotorbikeDto> mapMotorbikeDto = motorbikeDao.getByIds(motorbikeIds).stream()
                .collect(Collectors.toMap(MotorbikeDto::getId, Function.identity()));

        sellRequestVos.forEach(sellRequestVo -> {
            sellRequestVo.setCustomerDto(mapCustomerDtos.get(sellRequestVo.getCustomerId()));
            sellRequestVo.setMotorbikeDto(mapMotorbikeDto.get(sellRequestVo.getMotorbikeId()));
        });

        return sellRequestVos;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        sellRequestDao.deleteById(id);
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean cancelSellRequest(Long id) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.CANCELLED.getCode());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean completeSellRequest(Long id) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.COMPLETED.getCode());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean approvedSellRequest(Long id) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.APPROVED.getCode());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean rejectedSellRequest(Long id, SellRequestFilter sellRequestFilter) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.REJECTED.getCode());

        sellRequestFilter.getRejectRequestDto().setRejectedDate(LocalDateTime.now());
        sellRequestFilter.getRejectRequestDto().setSellRequestId(id);
        rejectRequestDao.createOne(sellRequestFilter.getRejectRequestDto());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean checkedSellRequest(Long id, SellRequestFilter sellRequestFilter) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.CHECKED.getCode());

        if (!Objects.isNull(sellRequestFilter.getMotorbikeDto())) {
            motorbikeDao.updateOne(sellRequestFilter.getMotorbikeDto());
        }
        CheckedSellRequestDto checkedSellRequestDto = sellRequestFilter.getCheckedSellRequestDto();
        checkedSellRequestDto.setCheckedDate(LocalDateTime.now());
        checkedSellRequestDto.setSellRequestId(id);
        checkedSellRequestDao.createOne(checkedSellRequestDto);

        return true;
    }

    private Boolean updateStatus(Long id, String newStatus) {
        SellRequestDto loadingDto = sellRequestDao.getById(id);
        if (!validateStatusMoving(SellRequestEnum.of(loadingDto.getStatus()), SellRequestEnum.of(newStatus))) {
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
                if (newStatus == SellRequestEnum.COMPLETED || newStatus == SellRequestEnum.EXPIRED) {
                    return true;
                }
            case EXPIRED:
                if (newStatus == SellRequestEnum.POSTED || newStatus == SellRequestEnum.REJECTED) {
                    return true;
                }
            default:
                return false;
        }
    }

}
