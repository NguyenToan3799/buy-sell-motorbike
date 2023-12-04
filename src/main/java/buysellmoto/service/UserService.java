package buysellmoto.service;

import buysellmoto.core.enumeration.RoleEnum;
import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.core.exception.NotFoundException;
import buysellmoto.core.exception.UnauthorizedException;
import buysellmoto.core.mail.MailService;
import buysellmoto.dao.CustomerDao;
import buysellmoto.dao.EmployeeShowroomDao;
import buysellmoto.dao.RoleDao;
import buysellmoto.dao.UserDao;
import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.dto.EmployeeShowroomDto;
import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.dto.UserDto;
import buysellmoto.model.filter.UserFilter;
import buysellmoto.model.filter.other.LoginFilter;
import buysellmoto.model.mapper.UserMapper;
import buysellmoto.model.vo.UserVo;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private EmployeeShowroomDao employeeShowroomDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailService mailService;

    public UserVo getById(Long id) {
        UserVo userVo = userMapper.dtoToVo(userDao.getById(id));
        RoleDto roleDto = roleDao.getById(userVo.getRoleId());
        userVo.setRoleName(roleDto.getName());

        if (roleDto.getName().equals(RoleEnum.CUSTOMER.getCode())) {
            userVo.setCustomerDto(customerDao.getByUserId(userVo.getId()));
        }
        if (roleDto.getName().equals(RoleEnum.MANAGER.getCode()) || roleDto.getName().equals(RoleEnum.STAFF.getCode())) {
            userVo.setEmployeeShowroomDto(employeeShowroomDao.getByUserId(userVo.getId()));
        }
        return userVo;
    }

    @SneakyThrows
    public Boolean resetPassword(String email) {
        UserDto userDto = userDao.getByEmail(email);
        if (!userDto.getStatus()) {
            throw new BusinessException(ApiMessageCode.DEACTIVATED_USER);
        }
        UserVo userVo = this.getById(userDto.getId());

        String newPassword = this.randomPassword();
        userDto.setPassword(newPassword);
        userDao.updateOne(userDto);

        mailService.resetPassword(userVo, newPassword);
        return true;
    }

    public List<UserDto> getAll() {
        return userDao.getAll();
    }

    public UserDto checkLogin(LoginFilter loginFilter) {
        UserVo userVo = userMapper.dtoToVo(userDao.checkLogin(loginFilter.getLoginIdentity(), loginFilter.getPassword()));
        if (Objects.isNull(userVo)) {
            throw new NotFoundException(ApiMessageCode.INVALID_LOGIN_IDENTITIES_OR_PASSWORD);
        }
        if (!userVo.getStatus()) {
            throw new NotFoundException(ApiMessageCode.DEACTIVATED_USER);
        }
        RoleDto roleDto = roleDao.getById(userVo.getRoleId());
        userVo.setRoleName(roleDto.getName());

        if (roleDto.getName().equals(RoleEnum.CUSTOMER.getCode())) {
            userVo.setCustomerDto(customerDao.getByUserId(userVo.getId()));
        }
        if (roleDto.getName().equals(RoleEnum.MANAGER.getCode()) || roleDto.getName().equals(RoleEnum.STAFF.getCode())) {
            userVo.setEmployeeShowroomDto(employeeShowroomDao.getByUserId(userVo.getId()));
        }
        return userVo;
    }

    @Transactional(rollbackOn = {Exception.class})
    public UserDto createOne(UserFilter filter) {
        UserDto preparingDto = filter.getCriteria();
        return userDao.createOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public UserVo createCustomer(UserFilter filter) {
        UserDto preparingDto = filter.getCriteria();
        if (userDao.existsByUserName(preparingDto.getUserName())) {
            throw new BusinessException(ApiMessageCode.USER_NAME_EXIST);
        }
        if (userDao.existByPhone(preparingDto.getPhone())) {
            throw new BusinessException(ApiMessageCode.PHONE_EXIST);
        }
        if (userDao.existsByEmail(preparingDto.getEmail())) {
            throw new BusinessException(ApiMessageCode.EMAIL_EXIST);
        }
        preparingDto.setId(null);
        preparingDto.setStatus(true);
        preparingDto.setRoleId(roleDao.getByName(RoleEnum.CUSTOMER.getCode()).getId());

        UserVo userVo = userMapper.dtoToVo(userDao.createOne(preparingDto));

        CustomerDto preparingCustomer = filter.getCustomerDto();
        preparingCustomer.setUserId(userVo.getId());
        preparingCustomer.setId(null);
        userVo.setCustomerDto(customerDao.createOne(preparingCustomer));

        return userVo;
    }

    @Transactional(rollbackOn = {Exception.class})
    public UserVo createStaff(UserFilter filter) {
        UserDto preparingDto = filter.getCriteria();
        preparingDto.setId(null);
        preparingDto.setStatus(true);
        UserVo userVo = userMapper.dtoToVo(userDao.createOne(preparingDto));

        EmployeeShowroomDto employeeShowroomDto = filter.getEmployeeShowroomDto();
        employeeShowroomDto.setUserId(userVo.getId());
        employeeShowroomDto.setId(null);
        userVo.setEmployeeShowroomDto(employeeShowroomDao.createOne(employeeShowroomDto));

        return userVo;
    }

    @Transactional(rollbackOn = {Exception.class})
    public UserDto updateOne(UserFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        UserDto preparingDto = filter.getCriteria();
        return userDao.updateOne(preparingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean updateCustomerInfor(UserFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        userDao.updateOne(filter.getCriteria());
        customerDao.updateOne(filter.getCustomerDto());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean updatePassword(UserFilter filter) {
        if (Objects.isNull(filter.getCriteria().getId())) {
            throw new BusinessException(ApiMessageCode.REQUIRED_ID);
        }
        UserDto userDto = userDao.getById(filter.getCriteria().getId());
        if(Objects.equals(filter.getOldPassword(), userDto.getPassword())){
            throw new BusinessException(ApiMessageCode.WRONG_OLD_PASSWORD);
        }
        if(Objects.equals(filter.getNewPassword(), filter.getConfirmNewPassword())){
            throw new BusinessException(ApiMessageCode.NEW_PASSWORD_NOT_MATCH);
        }
        userDto.setPassword(filter.getNewPassword());
        userDao.updateOne(filter.getCriteria());
        return true;
    }

    @Transactional(rollbackOn = {Exception.class})
    public Boolean deleteById(Long id) {
        userDao.deleteById(id);
        return true;
    }

    private String randomPassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
