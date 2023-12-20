package buysellmoto.service;

import buysellmoto.core.enumeration.BuyRequestEnum;
import buysellmoto.core.enumeration.CheckingAppointmentEnum;
import buysellmoto.core.enumeration.PostStatusEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.*;
import buysellmoto.model.dto.*;
import buysellmoto.model.filter.CheckingAppointmentFilter;
import buysellmoto.model.mapper.CheckingAppointmentMapper;
import buysellmoto.model.vo.BuyRequestVo;
import buysellmoto.model.vo.CheckingAppointmentVo;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CheckingAppointmentService {

    @Autowired
    private CheckingAppointmentDao checkingAppointmentDao;
    @Autowired
    private SellRequestDao sellRequestDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BuyRequestDao buyRequestDao;
    @Autowired
    private BuyRequestService buyRequestService;
    @Autowired
    private CheckingAppointmentMapper checkingAppointmentMapper;

    public CheckingAppointmentVo getById(Long id) {
        CheckingAppointmentVo checkingAppointmentVo = checkingAppointmentDao.getById(id);
        checkingAppointmentVo.setBuyRequestVo(buyRequestService.getById(checkingAppointmentVo.getBuyRequestId()));
        return checkingAppointmentVo;
    }

    public List<CheckingAppointmentVo> getAllActive() {
        List<CheckingAppointmentVo> checkingAppointmentVos = checkingAppointmentDao.getAllActive();
        checkingAppointmentVos.forEach(checkingAppointmentVo -> {
            checkingAppointmentVo.setBuyRequestVo(buyRequestService.getById(checkingAppointmentVo.getBuyRequestId()));
        });
        return checkingAppointmentVos;
    }

    public List<CheckingAppointmentVo> getByShowroomId(Long showroomId) {
        List<CheckingAppointmentVo> checkingAppointmentVos = checkingAppointmentDao.getByShowroomId(showroomId);

        List<Long> customerIds = checkingAppointmentVos.stream().map(CheckingAppointmentVo::getCustomerId).distinct().toList();
        Map<Long, CustomerDto> mapCustomerDtos = customerDao.getByIds(customerIds).stream()
                .collect(Collectors.toMap(CustomerDto::getId, Function.identity()));

        List<Long> buyRequestIds = checkingAppointmentVos.stream().map(CheckingAppointmentVo::getBuyRequestId).distinct().toList();
        Map<Long, BuyRequestDto> mapBuyRequestDtos = buyRequestDao.getByIds(customerIds).stream()
                .collect(Collectors.toMap(BuyRequestDto::getId, Function.identity()));

        checkingAppointmentVos.forEach(checkingAppointmentVo -> {
            checkingAppointmentVo.setBuyRequestDto(mapBuyRequestDtos.get(checkingAppointmentVo.getBuyRequestId()));
            checkingAppointmentVo.setCustomerDto(mapCustomerDtos.get(checkingAppointmentVo.getCustomerId()));
        });

        return checkingAppointmentVos;
    }

    public List<CheckingAppointmentDto> getAll() {
        return checkingAppointmentDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public CheckingAppointmentDto createOne (CheckingAppointmentFilter filter) {
        CheckingAppointmentDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        preparingDto.setStatus(CheckingAppointmentEnum.ACTIVE.getCode());

        BuyRequestDto buyRequestDto = buyRequestDao.getById(filter.getCriteria().getBuyRequestId());

        PostDto postDto = postDao.getById(buyRequestDto.getPostId());
        postDto.setStatus(PostStatusEnum.SCHEDULED.getCode());
        postDao.updateOne(postDto);
        preparingDto = checkingAppointmentDao.createOne(preparingDto);
        buyRequestService.confirmBuyRequest(filter.getCriteria().getBuyRequestId());

        List<BuyRequestVo> buyRequestVos = buyRequestService.getListBuyRequestByPostId(postDto.getId());
        if (!ObjectUtils.isEmpty(buyRequestVos)) {
            buyRequestVos.forEach(buyRequestVo -> {
                buyRequestVo.setStatus(BuyRequestEnum.CANCELLED.getCode());
                buyRequestVo.setCancelReason("Bài đăng đã có người hẹn xem xe!");
            });
            buyRequestDao.updateAllVos(buyRequestVos);

            //Todo: Gửi mail xin lỗi khách hàng

        }
        return preparingDto;
    }

    @Transactional(rollbackOn = {Exception.class})
    public CheckingAppointmentDto updateOne(CheckingAppointmentFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        CheckingAppointmentDto preparingDto = filter.getCriteria();
        return checkingAppointmentDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        checkingAppointmentDao.deleteById(id);
        return true;
    }

}
