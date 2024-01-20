package buysellmoto.service;

import buysellmoto.core.enumeration.*;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.core.exception.StatusException;
import buysellmoto.core.mail.MailService;
import buysellmoto.dao.*;
import buysellmoto.model.dto.*;
import buysellmoto.model.filter.BuyRequestFilter;
import buysellmoto.model.mapper.BuyRequestMapper;
import buysellmoto.model.mapper.CustomerMapper;
import buysellmoto.model.vo.BuyRequestVo;
import buysellmoto.model.vo.CustomerVo;
import buysellmoto.model.vo.SellRequestVo;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
    private SellRequestDao sellRequestDao;
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
    private CheckingAppointmentDao checkingAppointmentDao;
    @Autowired
    private PurchaseAppointmentDao purchaseAppointmentDao;
    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private BuyRequestMapper buyRequestMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private RequestHistoryDao requestHistoryDao;

    public BuyRequestVo getById(Long id) {
        BuyRequestVo buyRequestVo = buyRequestMapper.dtoToVo(buyRequestDao.getById(id));

        buyRequestVo.setShowroomDto(showroomDao.getById(buyRequestVo.getShowroomId()));
        buyRequestVo.setCustomerVo(customerMapper.dtoToVo(customerDao.getById(buyRequestVo.getCustomerId())));
        buyRequestVo.getCustomerVo().setPhone(userDao.getById(buyRequestVo.getCustomerVo().getUserId()).getPhone());
        buyRequestVo.setMotorbikeImageDto(motorbikeImageDao.getByMotorbikeId(buyRequestVo.getMotorbikeId()));
        buyRequestVo.setPostDto(postDao.getById(buyRequestVo.getPostId()));
        buyRequestVo.setMotorbikeDto(motorbikeDao.getById(buyRequestVo.getMotorbikeId()));
        buyRequestVo.setTransactionDtos(transactionDao.getByBuyRequestId(buyRequestVo.getId()));
        buyRequestVo.setUserDto(userDao.getById(buyRequestVo.getCustomerVo().getUserId()));
        buyRequestVo.setCheckingAppointmentDto(checkingAppointmentDao.getByBuyRequestId(id));
        buyRequestVo.setPurchaseAppointmentDto(purchaseAppointmentDao.getByMotorbikeId(buyRequestVo.getMotorbikeId()));

        buyRequestVo.setRequestHistoryDtos(requestHistoryDao.getByRequestIdAndRequestType(buyRequestVo.getId(), RequestTypeEnum.BUY_REQUEST.getCode()));
        return buyRequestVo;
    }

    public List<BuyRequestDto> getAll() {
        return buyRequestDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean createOne(BuyRequestFilter filter) {
        if (!Objects.isNull(buyRequestDao.findByCustomerIdAndMotorbikeIdAndStatus(filter.getCriteria().getCustomerId(),
                filter.getCriteria().getMotorbikeId(), CREATED.getCode()))) {
            throw new BusinessException("Bạn không thể gửi yêu cầu xe này thêm lần nữa");
        }

        BuyRequestDto preparingDto = filter.getCriteria();
        preparingDto.setStatus(CREATED.getCode());
        preparingDto.setId(null);
        preparingDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        BuyRequestDto createdBuyRequest = buyRequestDao.createOne(preparingDto);

        MotorbikeDto motorbikeDto = motorbikeDao.getById(filter.getCriteria().getMotorbikeId());
        if (Objects.equals(filter.getCriteria().getCustomerId(), motorbikeDto.getCustomerId())) {
            throw new BusinessException(ApiMessageCode.CAN_NOT_BUY_YOUR_CAR);
        }

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        requestHistoryDto.setRequestId(createdBuyRequest.getId());
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Yêu cầu mua xe của bạn đã được gửi đi");
        requestHistoryDao.createOne(requestHistoryDto);

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
    public Boolean cancelBuyRequest(Long id, String reason) {
        BuyRequestDto buyRequestDto = buyRequestDao.getById(id);

        if (Objects.isNull(buyRequestDto)) {
            throw new BusinessException(ApiMessageCode.BUY_REQUEST_ID_REQUIRED);
        }

        if (!buyRequestDto.getStatus().equals(CREATED.getCode())) {
            PostDto postDto = postDao.getById(buyRequestDto.getPostId());
            postDto.setStatus(PostStatusEnum.ACTIVE.getCode());
            postDao.updateOne(postDto);
        }

        this.updateStatus(id, BuyRequestEnum.CANCELLED.getCode());

        BuyRequestVo buyRequestVo = this.getById(id);
        buyRequestVo.setCancelReason(!ObjectUtils.isEmpty(reason) ? reason : "Khách hàng huỷ yêu cầu!!!" );
        buyRequestDao.updateOne(buyRequestVo);

        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(buyRequestVo.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        notificationDto.setRequestId(id);
        notificationDto.setNotificationContent(
                "Yêu cầu mua xe #" + buyRequestVo.getId() + ": Đã bị huỷ!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Yêu cầu mua xe của bạn đã bị huỷ");
        requestHistoryDao.createOne(requestHistoryDto);
        return true;
    }

    @SneakyThrows
    @Transactional(rollbackOn = {Exception.class})
    public Boolean confirmBuyRequest(Long id) {
        if (Objects.isNull(buyRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.BUY_REQUEST_ID_REQUIRED);
        }
        this.updateStatus(id, CONFIRMED.getCode());

        BuyRequestVo buyRequestVo = this.getById(id);
        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(buyRequestVo.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        notificationDto.setRequestId(id);
        notificationDto.setNotificationContent(
                "Yêu cầu mua xe #" + buyRequestVo.getId() + ": Đã được xác nhận!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Yêu cầu mua xe của bạn đã được xác nhận");
        requestHistoryDao.createOne(requestHistoryDto);

        //Send mail
        mailService.approveBuyRequest(buyRequestVo);
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean depositBuyRequest(Long id) {
        if (Objects.isNull(buyRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.BUY_REQUEST_ID_REQUIRED);
        }
        this.updateStatus(id, DEPOSITED.getCode());

        CheckingAppointmentDto checkingAppointmentDto = checkingAppointmentDao.getByBuyRequestId(id);
        checkingAppointmentDto.setStatus(CheckingAppointmentEnum.IN_ACTIVE.getCode());
        checkingAppointmentDao.updateOne(checkingAppointmentDto);

        BuyRequestVo buyRequestVo = this.getById(id);
        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(buyRequestVo.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        notificationDto.setRequestId(id);
        notificationDto.setNotificationContent(
                "Yêu cầu mua xe #" + buyRequestVo.getId() + ": Đã thanh toán tiền cọc!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Bạn đã đặt cọc xe thành công");
        requestHistoryDao.createOne(requestHistoryDto);

        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean completeBuyRequest(Long id) {
        if (Objects.isNull(buyRequestDao.getById(id))) {
            throw new BusinessException(ApiMessageCode.BUY_REQUEST_ID_REQUIRED);
        }
        this.updateStatus(id, BuyRequestEnum.COMPLETED.getCode());

        BuyRequestVo buyRequestVo = this.getById(id);
        //Send noti
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setCustomerId(buyRequestVo.getCustomerId());
        notificationDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        notificationDto.setRequestId(id);
        notificationDto.setNotificationContent(
                "Yêu cầu mua xe #" + buyRequestVo.getId() + ": Đã tất toán!");
        notificationDao.createOne(notificationDto);

        // Tạo Request History
        RequestHistoryDto requestHistoryDto = new RequestHistoryDto();
        requestHistoryDto.setRequestType(RequestTypeEnum.BUY_REQUEST.getCode());
        requestHistoryDto.setRequestId(id);
        requestHistoryDto.setCreatedDate(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        requestHistoryDto.setContent("Bạn đã tất toán xe thành công");
        requestHistoryDao.createOne(requestHistoryDto);

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

    public List<BuyRequestVo> getListBuyRequestByCustomerId(Long customerId) {
        List<BuyRequestVo> buyRequestVos = buyRequestDao.getByCustomerId(customerId);
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

        Map<Long, List<MotorbikeImageDto>> mapMotorbikeImage = motorbikeImageDao.getByMotorbikeIds(motorbikeIds).stream()
                .collect(Collectors.groupingBy(MotorbikeImageDto::getMotorbikeId));

        // Lấy Post
        List<Long> postIds = buyRequestVos.stream().map(BuyRequestVo::getPostId).distinct().toList();
        Map<Long, PostDto> mapPostDto = postDao.getByIds(postIds).stream()
                .collect(Collectors.toMap(PostDto::getId, Function.identity()));

        buyRequestVos.forEach(buyRequestVo -> {
            buyRequestVo.setCustomerVo(mapCustomerVos.get(buyRequestVo.getCustomerId()));
            buyRequestVo.setMotorbikeDto(mapMotorbikeDto.get(buyRequestVo.getMotorbikeId()));
            buyRequestVo.setPostDto(mapPostDto.get(buyRequestVo.getPostId()));
            buyRequestVo.setMotorbikeImageDto(mapMotorbikeImage.get(buyRequestVo.getMotorbikeId()));
        });

        return buyRequestVos;
    }

    public List<BuyRequestVo> getListBuyRequestByPostId(Long postId) {

        List<BuyRequestVo> buyRequestVos = buyRequestDao.findAllByPostIdAndStatus(postId, CREATED.getCode());
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
            throw new StatusException(ApiMessageCode.INVALID_STATUS_MOVING + " : from=" + loadingDto.getStatus() + " -> to=" + newStatus);
        }
        loadingDto.setStatus(newStatus);
        buyRequestDao.updateOne(loadingDto);
        return true;
    }

    private String generateCode() {
        Long timestamp = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime().toInstant(ZoneOffset.UTC).toEpochMilli();
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
