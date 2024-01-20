package buysellmoto.service;

import buysellmoto.core.enumeration.PostStatusEnum;
import buysellmoto.core.enumeration.RequestTypeEnum;
import buysellmoto.core.enumeration.SellRequestEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.core.exception.StatusException;
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
import lombok.Synchronized;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
    private PurchaseAppointmentDao purchaseAppointmentDao;
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private MotorbikeMapper motorbikeMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private RequestHistoryDao requestHistoryDao;

    public SellRequestVo getById(Long id) {
        SellRequestVo sellRequestVo = sellRequestDao.getById(id);

        sellRequestVo.setShowroomDto(showroomDao.getById(sellRequestVo.getShowroomId()));
        sellRequestVo.setCustomerVo(customerMapper.dtoToVo(customerDao.getById(sellRequestVo.getCustomerId())));
        sellRequestVo.getCustomerVo().setPhone(userDao.getById(sellRequestVo.getCustomerVo().getUserId()).getPhone());
        sellRequestVo.setMotorbikeDto(motorbikeDao.getById(sellRequestVo.getMotorbikeId()));
        sellRequestVo.setMotorbikeImageDto(motorbikeImageDao.getByMotorbikeId(sellRequestVo.getMotorbikeId()));
        sellRequestVo.setUserDto(userDao.getById(sellRequestVo.getCustomerVo().getUserId()));

        sellRequestVo.setRejectRequestDto(rejectRequestDao.getBySellRequestId(sellRequestVo.getId()));


        sellRequestVo.setCheckedSellRequestDto(checkedSellRequestDao.getBySellRequestId(sellRequestVo.getId()));

        sellRequestVo.setPostDto(postDao.getBySellRequestId(sellRequestVo.getId()));
        sellRequestVo.setPurchaseAppointmentDto(purchaseAppointmentDao.getByMotorbikeId(sellRequestVo.getMotorbikeId()));

        sellRequestVo.setTransactionDtos(transactionDao.getBySellRequestId(sellRequestVo.getId()));

        sellRequestVo.setRequestHistoryDtos(requestHistoryDao.getByRequestIdAndRequestType(sellRequestVo.getId(), RequestTypeEnum.SELL_REQUEST.getCode()));
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
        sellRequestDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        sellRequestDto.setMotorbikeId(motorbikeDto.getId());

        sellRequestDto = sellRequestDao.createOne(sellRequestDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        requestHistoryDto.setRequestId(sellRequestDto.getId());
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Yêu cầu bán xe của bạn đã được gửi đi");
        requestHistoryDao.createOne(requestHistoryDto);

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

        SellRequestDto sellRequestDto = sellRequestDao.getById(id);

        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(sellRequestDto.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        notificationDto.setRequestId(sellRequestDto.getId());
        notificationDto.setNotificationContent(
                "Yêu cầu bán xe #" + sellRequestDto.getId() + " của bạn đã huỷ!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Bạn đã huỷ yêu cầu bán xe #" + id);
        requestHistoryDao.createOne(requestHistoryDto);

        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean completeSellRequest(Long id) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.COMPLETED.getCode());

        SellRequestDto sellRequestDto = sellRequestDao.getById(id);

        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(sellRequestDto.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        notificationDto.setRequestId(sellRequestDto.getId());
        notificationDto.setNotificationContent(
                "Yêu cầu bán xe #" + sellRequestDto.getId() + " của bạn đã thành công!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Yêu cầu bán xe đã hoàn tất");
        requestHistoryDao.createOne(requestHistoryDto);

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

        SellRequestVo sellRequestVo = this.getById(id);

        //Send mail
        mailService.approveSellRequest(sellRequestVo);

        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(sellRequestVo.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        notificationDto.setRequestId(id);
        notificationDto.setNotificationContent(
                "Yêu cầu bán xe #" + sellRequestVo.getId() + ": Đã được chấp nhận!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Yêu cầu bán xe đã được chấp nhận");
        requestHistoryDao.createOne(requestHistoryDto);

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
        postDto.setExpiredDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime().plusDays(30));

        postDao.updateOne(postDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Yêu cầu bán xe đã được gia hạn");
        requestHistoryDao.createOne(requestHistoryDto);

    }

    @SneakyThrows
    @Transactional(rollbackOn = {Exception.class})
    public Boolean rejectedSellRequest(Long id, SellRequestFilter sellRequestFilter) {
        if (Objects.isNull(sellRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.SELL_REQUEST_NOT_EXIST);
        }
        this.updateStatus(id, SellRequestEnum.REJECTED.getCode());

        sellRequestFilter.getRejectRequestDto().setRejectedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        sellRequestFilter.getRejectRequestDto().setSellRequestId(id);
        rejectRequestDao.createOne(sellRequestFilter.getRejectRequestDto());

        SellRequestVo sellRequestVo = this.getById(id);

        //Send mail
        mailService.rejectSellRequest(sellRequestVo);

        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(sellRequestVo.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        notificationDto.setRequestId(id);
        notificationDto.setNotificationContent(
                "Yêu cầu bán xe #" + sellRequestVo.getId() + ": Đã bị từ chối!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Yêu cầu bán xe của bạn đã bị từ chối vì: "
                + sellRequestFilter.getRejectRequestDto().getRejectedReason());
        requestHistoryDao.createOne(requestHistoryDto);

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
        }

        CheckedSellRequestDto checkedSellRequestDto = sellRequestFilter.getCheckedSellRequestDto();
        checkedSellRequestDto.setCheckedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        checkedSellRequestDto.setSellRequestId(id);
        checkedSellRequestDao.createOne(checkedSellRequestDto);

        SellRequestVo sellRequestVo = this.getById(id);

        //Send mail
        mailService.checkedSellRequest(sellRequestVo);

        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(sellRequestVo.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        notificationDto.setRequestId(id);
        notificationDto.setNotificationContent(
                "Yêu cầu bán xe #" + sellRequestVo.getId() + ": Đã được nhận xe!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.SELL_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Tiếp nhận xe thành công");
        requestHistoryDao.createOne(requestHistoryDto);

        return true;
    }


    private Boolean updateStatus(Long id, String newStatus) {
        SellRequestDto loadingDto = sellRequestDao.getById(id);
        if (!validateStatusMoving(SellRequestEnum.of(loadingDto.getStatus()), SellRequestEnum.of(newStatus))) {
            throw new StatusException(ApiMessageCode.INVALID_STATUS_MOVING);
        }
        loadingDto.setStatus(newStatus);

        if (Objects.equals(newStatus, SellRequestEnum.APPROVED.getCode())) {
            loadingDto.setApprovedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        }
        sellRequestDao.updateOne(loadingDto);
        return true;
    }

    private String generateCode() {
        Long timestamp = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime().toInstant(ZoneOffset.UTC).toEpochMilli();
        String serial = "SR" + RandomStringUtils.random(13, timestamp.toString());
        return serial;
    }

    private boolean validateStatusMoving(SellRequestEnum preStatus, SellRequestEnum newStatus) {
        return switch (preStatus) {
            case CREATED -> newStatus == SellRequestEnum.APPROVED || newStatus == SellRequestEnum.REJECTED || newStatus == SellRequestEnum.CANCELLED;
            case APPROVED -> newStatus == SellRequestEnum.CHECKED || newStatus == SellRequestEnum.REJECTED;
            case CHECKED -> newStatus == SellRequestEnum.POSTED;
            case POSTED -> newStatus == SellRequestEnum.COMPLETED || newStatus == SellRequestEnum.EXPIRED;
            case EXPIRED -> newStatus == SellRequestEnum.POSTED || newStatus == SellRequestEnum.REJECTED;
            default -> false;
        };
    }

}
