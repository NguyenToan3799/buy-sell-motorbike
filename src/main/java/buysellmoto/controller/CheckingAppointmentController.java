package buysellmoto.controller;

import buysellmoto.model.dto.CheckingAppointmentDto;
import buysellmoto.model.filter.CheckingAppointmentFilter;
import buysellmoto.service.CheckingAppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/checking-appointment")
public class CheckingAppointmentController {

    @Autowired
    private CheckingAppointmentService checkingAppointmentService;

    @Operation(summary = "Get Checking Appointment By Id")
    @GetMapping("/{id}")
    public ResponseEntity<CheckingAppointmentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(checkingAppointmentService.getById(id));
    }

    @Operation(summary = "Get All Checking Appointment")
    @GetMapping("/all")
    public ResponseEntity<List<CheckingAppointmentDto>> getAll() {
        return ResponseEntity.ok(checkingAppointmentService.getAll());
    }

    @Operation(summary = "Create New Checking Appointment")
    @PostMapping()
    public ResponseEntity<CheckingAppointmentDto> createOne(@RequestBody CheckingAppointmentFilter filter) {
        return ResponseEntity.ok(checkingAppointmentService.createOne(filter));
    }

    @Operation(summary = "Update Existing Checking Appointment")
    @PutMapping("/{id}")
    public ResponseEntity<CheckingAppointmentDto> updateExistingCoupon(@PathVariable Long id,
                                                                 @RequestBody CheckingAppointmentFilter filter) {
        return ResponseEntity.ok(checkingAppointmentService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Checking Appointment")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCouponByID(@PathVariable Long id) {
        return ResponseEntity.ok(checkingAppointmentService.deleteById(id));
    }

}
