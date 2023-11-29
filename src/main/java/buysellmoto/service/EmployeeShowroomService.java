package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.EmployeeShowroomDao;
import buysellmoto.dao.RoleDao;
import buysellmoto.dao.ShowroomDao;
import buysellmoto.dao.UserDao;
import buysellmoto.model.dto.EmployeeShowroomDto;
import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.dto.ShowroomDto;
import buysellmoto.model.dto.UserDto;
import buysellmoto.model.filter.EmployeeShowroomFilter;
import buysellmoto.model.mapper.EmployeeShowroomMapper;
import buysellmoto.model.vo.CustomerVo;
import buysellmoto.model.vo.EmployeeShowroomVo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeShowroomService {

    @Autowired
    private EmployeeShowroomDao employeeShowroomDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ShowroomDao showroomDao;
    @Autowired
    private EmployeeShowroomMapper employeeShowroomMapper;

    public EmployeeShowroomVo getById(Long id) {
        EmployeeShowroomVo employeeShowroomVo = employeeShowroomDao.getById(id);
        employeeShowroomVo.setUserDto(userDao.getById(employeeShowroomVo.getUserId()));
        employeeShowroomVo.setRoleDto(roleDao.getById(employeeShowroomVo.getUserDto().getRoleId()));
        return employeeShowroomDao.getById(id);
    }
    
    public List<EmployeeShowroomDto> getAll() {
        return employeeShowroomDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public EmployeeShowroomDto createOne (EmployeeShowroomFilter filter) {
        EmployeeShowroomDto preparingDto =filter.getCriteria();
        preparingDto.setId(null);
        return employeeShowroomDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public EmployeeShowroomDto updateOne(EmployeeShowroomFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        EmployeeShowroomDto preparingDto = filter.getCriteria();
        return employeeShowroomDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        employeeShowroomDao.deleteById(id);
        return true;
    }

    public List<EmployeeShowroomVo> getByShowroomId(Long showroomId) {
        List<EmployeeShowroomVo> employeeShowroomVos = employeeShowroomDao.getByShowroomId(showroomId);

        // Lấy User
        List<Long> userIds = employeeShowroomVos.stream().map(EmployeeShowroomVo::getUserId).toList();
        Map<Long, UserDto> mapUserDtos = userDao.getByIds(userIds).stream()
                .collect(Collectors.toMap(UserDto::getId, Function.identity()));

        //Lấy role
        List<Long> roleIds = userDao.getByIds(userIds).stream().map(UserDto::getRoleId).distinct().toList();
        Map<Long, RoleDto> mapRoleDtos = roleDao.getByIds(roleIds).stream()
                .collect(Collectors.toMap(RoleDto::getId, Function.identity()));

        employeeShowroomVos.forEach(employeeShowroomVo -> {
            employeeShowroomVo.setUserDto(mapUserDtos.get(employeeShowroomVo.getUserId()));
            employeeShowroomVo.setRoleDto(mapRoleDtos.get(employeeShowroomVo.getUserDto().getRoleId()));
        });
        return employeeShowroomVos;
    }

}
