package buysellmoto.dao;

import buysellmoto.model.dto.EmployeeShowroomDto;
import buysellmoto.model.mapper.EmployeeShowroomMapper;
import buysellmoto.model.vo.EmployeeShowroomVo;
import buysellmoto.repository.EmployeeShowroomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeShowroomDao {

    @Autowired
    private EmployeeShowroomRepository employeeShowroomRepository;

    @Autowired
    private EmployeeShowroomMapper mapper;

    public EmployeeShowroomVo getById(Long id) {
        return mapper.entityToVo(employeeShowroomRepository.findById(id).orElseThrow());
    }

    public EmployeeShowroomDto getByUserId(Long userId) {
        return mapper.toDto(employeeShowroomRepository.getByUserId(userId));
    }

    public List<EmployeeShowroomVo> getByShowroomId(Long showroomId) {
        return mapper.entityToVo(employeeShowroomRepository.findAllByShowroomId(showroomId));
    }

    public List<EmployeeShowroomDto> getAll() {
        return mapper.toDto(employeeShowroomRepository.findAll());
    }

    @Transactional(rollbackOn = {Exception.class})
    public EmployeeShowroomDto createOne(EmployeeShowroomDto dto) {
        return mapper.toDto(employeeShowroomRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public EmployeeShowroomDto updateOne(EmployeeShowroomDto dto) {
        return mapper.toDto(employeeShowroomRepository.save(mapper.toEntity(dto)));
    }

    @Transactional(rollbackOn = {Exception.class})
    public boolean deleteById(Long id) {
        employeeShowroomRepository.delete(employeeShowroomRepository.findById(id).orElseThrow());
        return true;
    }

}
