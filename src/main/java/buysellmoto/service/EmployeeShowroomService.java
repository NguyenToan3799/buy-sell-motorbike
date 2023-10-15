package buysellmoto.service;


import buysellmoto.dao.EmployeeShowroomDao;
import buysellmoto.model.dto.EmployeeShowroomDto;
import buysellmoto.model.filter.EmployeeShowroomFilter;
import buysellmoto.model.mapper.EmployeeShowroomMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeShowroomService {

    @Autowired
    private EmployeeShowroomDao employeeShowroomDao;
    @Autowired
    private EmployeeShowroomMapper employeeShowroomMapper;

    public EmployeeShowroomDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return employeeShowroomDao.getById(id);
    }
    
    public List<EmployeeShowroomDto> getAll() {
        return employeeShowroomDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public EmployeeShowroomDto createOne (EmployeeShowroomFilter filter) {
        EmployeeShowroomDto preparingDto = employeeShowroomMapper.filterToDto(filter);
        return employeeShowroomDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public EmployeeShowroomDto updateOne(Long id, EmployeeShowroomFilter filter) {
        EmployeeShowroomDto preparingDto = employeeShowroomMapper.filterToDto(filter);
        preparingDto.setId(id);
        return employeeShowroomDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        employeeShowroomDao.deleteById(id);
        return true;
    }

}
