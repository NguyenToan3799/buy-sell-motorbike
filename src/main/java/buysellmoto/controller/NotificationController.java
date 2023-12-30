package buysellmoto.controller;

import buysellmoto.dao.NotificationDao;
import buysellmoto.model.dto.NotificationDto;
import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.filter.RoleFilter;
import buysellmoto.service.NotificationService;
import buysellmoto.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Get By CustomerId")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<NotificationDto>> getByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(notificationService.getByCustomerId(customerId));
    }

    @Operation(summary = "Get Notification Not YetAnnounced By CustomerId ")
    @GetMapping("/customer/{customerId}/not-yet-announced")
    public ResponseEntity<NotificationDto> getNotificationNotYetAnnouncedByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(notificationService.getNotificationNotYetAnnouncedByCustomerId(customerId));
    }

    @Operation(summary = "Update seen notification ")
    @PutMapping("/{id}/is-seen")
    public ResponseEntity<NotificationDto> updateIsSeen(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.updateIsSeen(id));
    }

}
