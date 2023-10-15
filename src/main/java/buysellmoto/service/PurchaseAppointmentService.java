package buysellmoto.service;

import buysellmoto.dao.PurchaseAppointmentDao;
import buysellmoto.model.dto.PurchaseAppointmentDto;
import buysellmoto.model.filter.PurchaseAppointmentFilter;
import buysellmoto.model.mapper.PurchaseAppointmentMapper;
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

    public PurchaseAppointmentDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return purchaseAppointmentDao.getById(id);
    }
    
    public List<PurchaseAppointmentDto> getAll() {
        return purchaseAppointmentDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public PurchaseAppointmentDto createOne (PurchaseAppointmentFilter filter) {
        PurchaseAppointmentDto preparingDto = purchaseAppointmentMapper.filterToDto(filter);
        return purchaseAppointmentDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public PurchaseAppointmentDto updateOne(Long id, PurchaseAppointmentFilter filter) {
        PurchaseAppointmentDto preparingDto = purchaseAppointmentMapper.filterToDto(filter);
        preparingDto.setId(id);
        return purchaseAppointmentDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        purchaseAppointmentDao.deleteById(id);
        return true;
    }

}
