package buysellmoto.service;

import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
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

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;

    public RoleDto getById(Long id) {
        if(Objects.isNull(id)){
        }
//        kafkaTemplate.send("test-topic", "Hello Kafka!");
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
        RoleDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        return roleDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public RoleDto updateOne(RoleFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        RoleDto preparingDto = filter.getCriteria();
        return roleDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        roleDao.deleteById(id);
        return true;
    }

}
