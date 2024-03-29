package buysellmoto.controller;

import buysellmoto.dao.CheckingAppointmentDao;
import buysellmoto.model.dto.CheckingAppointmentDto;
import buysellmoto.model.filter.CheckingAppointmentFilter;
import buysellmoto.model.vo.CheckingAppointmentVo;
import buysellmoto.service.CheckingAppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/checking-appointments")
public class CheckingAppointmentController {

    @Autowired
    private CheckingAppointmentService checkingAppointmentService;

    @Operation(summary = "Get Checking Appointment By Id")
    @GetMapping("/{id}")
    public ResponseEntity<CheckingAppointmentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(checkingAppointmentService.getById(id));
    }

    @Operation(summary = "Get All Checking Appointment")
    @GetMapping()
    public ResponseEntity<List<CheckingAppointmentDto>> getAll() {
        return ResponseEntity.ok(checkingAppointmentService.getAll());
    }

    @Operation(summary = "Get All Checking Appointment By ShowroomId")
    @GetMapping("/showroom/{showroomId}")
    public ResponseEntity<List<CheckingAppointmentVo>> getByShowroomId(@PathVariable Long showroomId) {
        return ResponseEntity.ok(checkingAppointmentService.getByShowroomId(showroomId));
    }

    @Operation(summary = "Create New Checking Appointment")
    @PostMapping()
    public synchronized ResponseEntity<CheckingAppointmentDto> createOne(@RequestBody CheckingAppointmentFilter filter) {
        return ResponseEntity.ok(checkingAppointmentService.createOne(filter));
    }

    @Operation(summary = "Update Existing Checking Appointment")
    @PutMapping("/{id}")
    public ResponseEntity<CheckingAppointmentDto> updateOne(@RequestBody CheckingAppointmentFilter filter) {
        return ResponseEntity.ok(checkingAppointmentService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Checking Appointment")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(checkingAppointmentService.deleteById(id));
    }

}
