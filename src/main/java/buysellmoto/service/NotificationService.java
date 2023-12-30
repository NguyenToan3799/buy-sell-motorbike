package buysellmoto.service;


import buysellmoto.dao.NotificationDao;
import buysellmoto.model.dto.NotificationDto;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public List<NotificationDto> getByCustomerId(Long customerId) {
        return notificationDao.getByCustomerId(customerId).stream()
                .sorted(Comparator.comparing(NotificationDto::getNotificationDate))
                .toList();
    }

    @Transactional(rollbackOn = {Exception.class})
    public NotificationDto updateIsSeen(Long id) {
        NotificationDto loadingDto = notificationDao.getById(id);
        loadingDto.setIsSeen(true);
        return notificationDao.updateOne(loadingDto);
    }

    @Transactional(rollbackOn = {Exception.class})
    public NotificationDto getNotificationNotYetAnnouncedByCustomerId(Long customerId) {
        List<NotificationDto> notificationDtos = notificationDao
                .getNotificationNotYetAnnouncedByCustomerId(customerId, false);

        if (ObjectUtils.isEmpty(notificationDtos)) {
            return new NotificationDto();
        }

        // Sort xem noti nào có trước thông báo trước. Tuần tự
        List<NotificationDto> sortedList = notificationDtos.stream()
                .sorted(Comparator.comparing(NotificationDto::getNotificationDate).reversed())
                .toList();
        
        NotificationDto notificationPicked = sortedList.get(0);
        notificationPicked.setIsNotified(true);
        notificationDao.updateOne(notificationPicked);

        return notificationPicked;
    }

}
