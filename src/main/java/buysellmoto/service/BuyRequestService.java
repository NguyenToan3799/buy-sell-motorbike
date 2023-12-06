package buysellmoto.service;

import buysellmoto.core.enumeration.BuyRequestEnum;
import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.*;
import buysellmoto.model.dto.*;
import buysellmoto.model.filter.BuyRequestFilter;
import buysellmoto.model.mapper.BuyRequestMapper;
import buysellmoto.model.mapper.CustomerMapper;
import buysellmoto.model.vo.BuyRequestVo;
import buysellmoto.model.vo.CustomerVo;
import buysellmoto.model.vo.SellRequestVo;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static buysellmoto.core.enumeration.BuyRequestEnum.*;

@Service
public class BuyRequestService {

    @Autowired
    private BuyRequestDao buyRequestDao;
    @Autowired
    private ShowroomDao showroomDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MotorbikeImageDao motorbikeImageDao;
    @Autowired
    private MotorbikeDao motorbikeDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private BuyRequestMapper buyRequestMapper;

    public BuyRequestVo getById(Long id) {
        BuyRequestVo buyRequestVo = buyRequestMapper.dtoToVo(buyRequestDao.getById(id));

        buyRequestVo.setShowroomDto(showroomDao.getById(buyRequestVo.getShowroomId()));
        buyRequestVo.setCustomerVo(customerMapper.dtoToVo(customerDao.getById(buyRequestVo.getCustomerId())));
        buyRequestVo.getCustomerVo().setPhone(userDao.getById(buyRequestVo.getCustomerVo().getUserId()).getPhone());
        buyRequestVo.setMotorbikeImageDto(motorbikeImageDao.getByMotorbikeId(buyRequestVo.getMotorbikeId()));
        buyRequestVo.setPostDto(postDao.getById(buyRequestVo.getPostId()));
        buyRequestVo.setMotorbikeDto(motorbikeDao.getById(buyRequestVo.getMotorbikeId()));
        return buyRequestVo;
    }

    public List<BuyRequestDto> getAll() {
        return buyRequestDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean createOne(BuyRequestFilter filter) {
        BuyRequestDto preparingDto = filter.getCriteria();
        preparingDto.setStatus(CREATED.getCode());
        preparingDto.setId(null);
        preparingDto.setCreatedDate(LocalDateTime.now());
        buyRequestDao.createOne(preparingDto);
        return true;
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

    @Transactional(rollbackOn = {Exception.class})
    public Boolean cancelBuyRequest(Long id) {
        if (Objects.isNull(buyRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.BUY_REQUEST_ID_REQUIRED);
        }
        this.updateStatus(id, BuyRequestEnum.CANCELLED.getCode());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean confirmBuyRequest(Long id) {
        if (Objects.isNull(buyRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.BUY_REQUEST_ID_REQUIRED);
        }
        this.updateStatus(id, CONFIRMED.getCode());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean depositBuyRequest(Long id) {
        if (Objects.isNull(buyRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.BUY_REQUEST_ID_REQUIRED);
        }
        this.updateStatus(id, DEPOSITED.getCode());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean completeBuyRequest(Long id) {
        if (Objects.isNull(buyRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.BUY_REQUEST_ID_REQUIRED);
        }
        this.updateStatus(id, BuyRequestEnum.COMPLETED.getCode());
        return true;
    }

    public List<BuyRequestVo> getListBuyRequest(Long showroomId, String status) {
        if (BuyRequestEnum.of(status) == BuyRequestEnum.INVALID) {
            throw new BusinessException(ApiMessageCode.INVALID_STATUS);
        }
        List<BuyRequestVo> buyRequestVos = buyRequestDao.getByShowroomIdAndStatus(showroomId, status);
        buyRequestVos = buyRequestVos.stream()
                .sorted(Comparator.comparing(BuyRequestVo::getCreatedDate).reversed())
                .toList();

        // Lấy Customer
        List<Long> customerIds = buyRequestVos.stream().map(BuyRequestVo::getCustomerId).distinct().toList();
        List<CustomerVo> customerVos = customerMapper.dtoToVo(customerDao.getByIds(customerIds));

        // Lấy Phone
        List<Long> userIds = customerVos.stream().map(CustomerVo::getUserId).distinct().toList();
        Map<Long, UserDto> mapUserDtos = userDao.getByIds(userIds).stream()
                .collect(Collectors.toMap(UserDto::getId, Function.identity()));

        customerVos.forEach(customerVo -> customerVo.setPhone(mapUserDtos.get(customerVo.getUserId()).getPhone()));

        Map<Long, CustomerVo> mapCustomerVos = customerVos.stream()
                .collect(Collectors.toMap(CustomerVo::getId, Function.identity()));

        // Lấy Motorbike
        List<Long> motorbikeIds = buyRequestVos.stream().map(BuyRequestVo::getMotorbikeId).distinct().toList();
        Map<Long, MotorbikeDto> mapMotorbikeDto = motorbikeDao.getByIds(motorbikeIds).stream()
                .collect(Collectors.toMap(MotorbikeDto::getId, Function.identity()));

        // Lấy Post
        List<Long> postIds = buyRequestVos.stream().map(BuyRequestVo::getPostId).distinct().toList();
        Map<Long, PostDto> mapPostDto = postDao.getByIds(postIds).stream()
                .collect(Collectors.toMap(PostDto::getId, Function.identity()));

        buyRequestVos.forEach(buyRequestVo -> {
            buyRequestVo.setCustomerVo(mapCustomerVos.get(buyRequestVo.getCustomerId()));
            buyRequestVo.setMotorbikeDto(mapMotorbikeDto.get(buyRequestVo.getMotorbikeId()));
            buyRequestVo.setPostDto(mapPostDto.get(buyRequestVo.getPostId()));
        });

        return buyRequestVos;
    }

    private Boolean updateStatus(Long id, String newStatus) {
        BuyRequestDto loadingDto = buyRequestDao.getById(id);
        if (!validateStatusMoving(BuyRequestEnum.of(loadingDto.getStatus()), BuyRequestEnum.of(newStatus))) {
            throw new BusinessException(ApiMessageCode.INVALID_STATUS_MOVING + " : from=" + loadingDto.getStatus() + " -> to=" + newStatus);
        }
        loadingDto.setStatus(newStatus);
        buyRequestDao.updateOne(loadingDto);
        return true;
    }

    private String generateCode() {
        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        String serial = "SR" + RandomStringUtils.random(13, timestamp.toString());
        return serial;
    }

    private boolean validateStatusMoving(BuyRequestEnum preStatus, BuyRequestEnum newStatus) {
        Map<BuyRequestEnum, List<?>> stateMachine = Map.of(
            CREATED, List.of(CONFIRMED, CANCELLED),
            CONFIRMED, List.of(DEPOSITED, CANCELLED),
            DEPOSITED, List.of(SCHEDULED, CANCELLED),
            SCHEDULED, List.of(COMPLETED, CANCELLED)
        );

        var movableStatuses = stateMachine.getOrDefault(preStatus, List.of());
        return movableStatuses.contains(newStatus);
    }
}
