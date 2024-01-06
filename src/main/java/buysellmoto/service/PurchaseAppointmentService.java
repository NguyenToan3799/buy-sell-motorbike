package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.CustomerDao;
import buysellmoto.dao.MotorbikeDao;
import buysellmoto.dao.PurchaseAppointmentDao;
import buysellmoto.dao.ShowroomDao;
import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.dto.PurchaseAppointmentDto;
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

    public PurchaseAppointmentDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return purchaseAppointmentDao.getById(id);
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
        });
        return purchaseAppointmentVos;
    }

    @Transactional(rollbackOn = {Exception.class})
    public PurchaseAppointmentDto createOne (PurchaseAppointmentFilter filter) {
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
