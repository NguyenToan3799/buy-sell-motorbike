package buysellmoto.service;

import buysellmoto.core.enumeration.PostStatusEnum;
import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.core.mail.MailService;
import buysellmoto.dao.*;
import buysellmoto.model.dto.*;
import buysellmoto.model.filter.SellRequestFilter;
import buysellmoto.model.mapper.*;
import buysellmoto.model.vo.MotorbikeVo;
import buysellmoto.model.vo.SellRequestVo;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
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
    private PostDao postDao;
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private MotorbikeMapper motorbikeMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private MailService mailService;

    public SellRequestVo getById(Long id) {
        SellRequestVo sellRequestVo = sellRequestDao.getById(id);

        sellRequestVo.setShowroomDto(showroomDao.getById(sellRequestVo.getShowroomId()));
        sellRequestVo.setCustomerVo(customerMapper.dtoToVo(customerDao.getById(sellRequestVo.getCustomerId())));
        sellRequestVo.getCustomerVo().setPhone(userDao.getById(sellRequestVo.getCustomerVo().getUserId()).getPhone());
        sellRequestVo.setMotorbikeDto(motorbikeDao.getById(sellRequestVo.getMotorbikeId()));
        sellRequestVo.setMotorbikeImageDto(motorbikeImageDao.getByMotorbikeId(sellRequestVo.getMotorbikeId()));
        sellRequestVo.setUserDto(userDao.getById(sellRequestVo.getCustomerVo().getUserId()));

        if (sellRequestVo.getStatus().equals(SellRequestEnum.REJECTED.getCode())) {
            sellRequestVo.setRejectRequestDto(rejectRequestDao.getBySellRequestId(sellRequestVo.getId()));
        }
        if (sellRequestVo.getStatus().equals(SellRequestEnum.CHECKED.getCode())) {
            sellRequestVo.setCheckedSellRequestDto(checkedSellRequestDao.getBySellRequestId(sellRequestVo.getId()));
        }
        if (sellRequestVo.getStatus().equals(SellRequestEnum.POSTED.getCode())) {
            sellRequestVo.setPostDto(postDao.getBySellRequestId(sellRequestVo.getId()));
        }
        sellRequestVo.setTransactionDtos(transactionDao.getBySellRequestId(sellRequestVo.getId()));
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
    public SellRequestDto createOne(SellRequestFilter filter) throws MessagingException {

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

        sellRequestDto = sellRequestDao.createOne(sellRequestDto);
        return sellRequestDto;
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

        // Lay motorbike images
        Map<Long, List<MotorbikeImageDto>> mapMotorbikeImage = motorbikeImageDao.getByMotorbikeIds(motorbikeIds).stream()
                .collect(Collectors.groupingBy(MotorbikeImageDto::getMotorbikeId));


        sellRequestVos.forEach(sellRequestVo -> {
            sellRequestVo.setCustomerDto(mapCustomerDtos.get(sellRequestVo.getCustomerId()));
            sellRequestVo.setMotorbikeDto(mapMotorbikeDto.get(sellRequestVo.getMotorbikeId()));
            sellRequestVo.setMotorbikeImageDto(mapMotorbikeImage.get(sellRequestVo.getMotorbikeId()));
        });

        return sellRequestVos;
    }

    public List<SellRequestVo> getListSellRequestByStatus(String status) {
        List<SellRequestVo> sellRequestVos = sellRequestDao.getByStatus(status);
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

    public List<SellRequestVo> getListSellRequestByIds(List<Long> ids) {
        List<SellRequestVo> sellRequestVos = sellRequestDao.getByIds(ids);
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

    @SneakyThrows
    @Transactional(rollbackOn = {Exception.class})
    public Boolean approvedSellRequest(Long id) {
        SellRequestDto sellRequestDto = sellRequestDao.getById(id);
        if (Objects.isNull(sellRequestDto)) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.APPROVED.getCode());
        mailService.approveSellRequest(this.getById(sellRequestDto.getId()));
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public void renewSellRequest(Long id) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.POSTED.getCode());

        PostDto postDto = postDao.getBySellRequestId(id);
        postDto.setStatus(PostStatusEnum.ACTIVE.getCode());
        postDto.setExpiredDate(LocalDateTime.now().plusDays(30));

        postDao.updateOne(postDto);
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

    @SneakyThrows
    @Transactional(rollbackOn = {Exception.class})
    public Boolean checkedSellRequest(Long id, SellRequestFilter sellRequestFilter) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.CHECKED.getCode());

        if (!Objects.isNull(sellRequestFilter.getMotorbikeVo())) {
            motorbikeDao.updateVo(sellRequestFilter.getMotorbikeVo());
            motorbikeImageDao.deleteByMotorbikeId(sellRequestFilter.getMotorbikeVo().getId());
            if (!Objects.isNull(sellRequestFilter.getMotorbikeVo().getMotorbikeImageDtos())) {
                sellRequestFilter.getMotorbikeVo().getMotorbikeImageDtos().forEach(motorbikeImageDto -> {
                    motorbikeImageDto.setMotorbikeId(sellRequestFilter.getMotorbikeVo().getId());
                    motorbikeImageDto.setId(null);
                });
                motorbikeImageDao.createAll(sellRequestFilter.getMotorbikeVo().getMotorbikeImageDtos());
            }
        }

        CheckedSellRequestDto checkedSellRequestDto = sellRequestFilter.getCheckedSellRequestDto();
        checkedSellRequestDto.setCheckedDate(LocalDateTime.now());
        checkedSellRequestDto.setSellRequestId(id);
        checkedSellRequestDao.createOne(checkedSellRequestDto);

        mailService.checkedSellRequest(this.getById(id));

        return true;
    }

    private Boolean updateStatus(Long id, String newStatus) {
        SellRequestDto loadingDto = sellRequestDao.getById(id);
        if (!validateStatusMoving(SellRequestEnum.of(loadingDto.getStatus()), SellRequestEnum.of(newStatus))) {
            throw new BusinessException(ApiMessageCode.INVALID_STATUS_MOVING);
        }
        loadingDto.setStatus(newStatus);

        if (Objects.equals(newStatus, SellRequestEnum.APPROVED.getCode())) {
            loadingDto.setApprovedDate(LocalDateTime.now());
        }
        sellRequestDao.updateOne(loadingDto);
        return true;
    }

    private String generateCode() {
        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        String serial = "SR" + RandomStringUtils.random(13, timestamp.toString());
        return serial;
    }

    private boolean validateStatusMoving(SellRequestEnum preStatus, SellRequestEnum newStatus) {
        return switch (preStatus) {
            case CREATED -> newStatus == SellRequestEnum.APPROVED || newStatus == SellRequestEnum.REJECTED;
            case APPROVED -> newStatus == SellRequestEnum.CHECKED || newStatus == SellRequestEnum.REJECTED;
            case CHECKED -> newStatus == SellRequestEnum.POSTED;
            case POSTED -> newStatus == SellRequestEnum.COMPLETED || newStatus == SellRequestEnum.EXPIRED;
            case EXPIRED -> newStatus == SellRequestEnum.POSTED || newStatus == SellRequestEnum.REJECTED;
            default -> false;
        };
    }

}
