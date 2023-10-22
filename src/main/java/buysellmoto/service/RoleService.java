package buysellmoto.service;

import buysellmoto.dao.RoleDao;
import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.filter.RoleFilter;
import buysellmoto.model.mapper.RoleMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleMapper roleMapper;

    public RoleDto getById(Long id) {
        if(Objects.isNull(id)){
        }
        return roleDao.getById(id);
    }

    public List<RoleDto> getByIds(RoleFilter filter) {
        return roleDao.getByIds(filter.getIds());
    }

    public List<RoleDto> getAll() {
        return roleDao.getAll();
    }

    @Transactional(rollbackOn = {Exception.class})
    public RoleDto createOne (RoleFilter filter) {
        RoleDto preparingDto = roleMapper.filterToDto(filter);
        return roleDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public RoleDto updateOne(Long id, RoleFilter filter) {
        RoleDto preparingDto = roleMapper.filterToDto(filter);
        preparingDto.setId(id);
        return roleDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        roleDao.deleteById(id);
        return true;
    }

}
