package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.CheckingAppointmentDao;
import buysellmoto.model.dto.CheckingAppointmentDto;
import buysellmoto.model.filter.CheckingAppointmentFilter;
import buysellmoto.model.mapper.CheckingAppointmentMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CheckingAppointmentService {

    @Autowired
    private CheckingAppointmentDao checkingAppointmentDao;
    @Autowired
    private CheckingAppointmentMapper checkingAppointmentMapper;

    public CheckingAppointmentDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return checkingAppointmentDao.getById(id);
    }
    
    public List<CheckingAppointmentDto> getAll() {
        return checkingAppointmentDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public CheckingAppointmentDto createOne (CheckingAppointmentFilter filter) {
        CheckingAppointmentDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return checkingAppointmentDao.createOne(preparingDto);
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
