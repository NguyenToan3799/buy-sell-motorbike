package buysellmoto.service;


import buysellmoto.core.exception.ApiMessageCode;
import buysellmoto.core.exception.BusinessException;
import buysellmoto.dao.notificationDao;
import buysellmoto.dao.NotificationDao;
import buysellmoto.model.dto.NotificationDto;
import buysellmoto.model.filter.MotoTypeFilter;
import buysellmoto.model.mapper.MotoTypeMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public List<NotificationDto> getByCustomerId(Long customerId) {
        return notificationDao.getByCustomerId(customerId);
    }

    @Transactional(rollbackOn = {Exception.class})
    public NotificationDto updateIsSeen(Long id) {
        NotificationDto loadingDto = notificationDao.

        return notificationDao.updateOne()
    }

}
