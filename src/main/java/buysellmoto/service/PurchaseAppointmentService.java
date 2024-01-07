package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.*;
import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.dto.PurchaseAppointmentDto;
import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.filter.PurchaseAppointmentFilter;
import buysellmoto.model.mapper.MotorbikeMapper;
import buysellmoto.model.mapper.PurchaseAppointmentMapper;
import buysellmoto.model.vo.PurchaseAppointmentVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PurchaseAppointmentService {

    @Autowired
    private PurchaseAppointmentDao purchaseAppointmentDao;
    @Autowired
    private PurchaseAppointmentMapper purchaseAppointmentMapper;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private MotorbikeDao motorbikeDao;
    @Autowired
    private ShowroomDao showroomDao;
    @Autowired
    private BuyRequestDao buyRequestDao;
    @Autowired
    private SellRequestDao sellRequestDao;

    public PurchaseAppointmentVo getById(Long id) {
        PurchaseAppointmentVo purchaseAppointmentVo = purchaseAppointmentDao.getById(id);
        purchaseAppointmentVo.setBuyerDto(customerDao.getById(purchaseAppointmentVo.getBuyerId()));
        purchaseAppointmentVo.setSellerDto(customerDao.getById(purchaseAppointmentVo.getSellerId()));

        if (!Objects.isNull(purchaseAppointmentVo.getMotorbikeId()))
            purchaseAppointmentVo.setMotorbikeDto(motorbikeDao.getById(purchaseAppointmentVo.getMotorbikeId()));
        purchaseAppointmentVo.setShowroomDto(showroomDao.getById(purchaseAppointmentVo.getShowroomId()));

        if (!Objects.isNull(purchaseAppointmentVo.getSellRequestId())) {
            purchaseAppointmentVo.setSellRequestDto(sellRequestDao.getById(purchaseAppointmentVo.getSellRequestId()));
        }
        if (!Objects.isNull(purchaseAppointmentVo.getBuyRequestId())) {
            purchaseAppointmentVo.setBuyRequestDto(buyRequestDao.getById(purchaseAppointmentVo.getBuyRequestId()));
        }
        return purchaseAppointmentVo;
    }

    public List<PurchaseAppointmentDto> getAll() {
        return purchaseAppointmentDao.getAll();
    }

    public List<PurchaseAppointmentVo> getByShowroomId(Long showroomId) {
        List<PurchaseAppointmentVo> purchaseAppointmentVos = purchaseAppointmentDao.getByShowroomId(showroomId);
        purchaseAppointmentVos.forEach(purchaseAppointmentVo -> {
            purchaseAppointmentVo.setBuyerDto(customerDao.getById(purchaseAppointmentVo.getBuyerId()));
            purchaseAppointmentVo.setSellerDto(customerDao.getById(purchaseAppointmentVo.getSellerId()));
            purchaseAppointmentVo.setMotorbikeDto(motorbikeDao.getById(purchaseAppointmentVo.getMotorbikeId()));
            purchaseAppointmentVo.setShowroomDto(showroomDao.getById(purchaseAppointmentVo.getShowroomId()));

            if (!Objects.isNull(purchaseAppointmentVo.getSellRequestId())) {
                purchaseAppointmentVo.setSellRequestDto(sellRequestDao.getById(purchaseAppointmentVo.getSellRequestId()));
            }
            if (!Objects.isNull(purchaseAppointmentVo.getBuyRequestId())) {
                purchaseAppointmentVo.setBuyRequestDto(buyRequestDao.getById(purchaseAppointmentVo.getBuyRequestId()));
            }
        });
        return purchaseAppointmentVos;
    }

    @Transactional(rollbackOn = {Exception.class})
    public PurchaseAppointmentDto createOne(PurchaseAppointmentFilter filter) {
        PurchaseAppointmentDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return purchaseAppointmentDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public PurchaseAppointmentDto updateOne(PurchaseAppointmentFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        PurchaseAppointmentDto preparingDto = filter.getCriteria();
        return purchaseAppointmentDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        purchaseAppointmentDao.deleteById(id);
        return true;
    }

}
