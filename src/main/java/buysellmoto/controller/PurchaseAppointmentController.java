package buysellmoto.controller;

import buysellmoto.model.dto.PurchaseAppointmentDto;
import buysellmoto.model.filter.PurchaseAppointmentFilter;
import buysellmoto.service.PurchaseAppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/purchase-appointments")
public class PurchaseAppointmentController {

    @Autowired
    private PurchaseAppointmentService purchaseAppointmentService;

    @Operation(summary = "Get Purchase Appointment By Id")
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseAppointmentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseAppointmentService.getById(id));
    }

    @Operation(summary = "Get All Purchase Appointment")
    @GetMapping()
    public ResponseEntity<List<PurchaseAppointmentDto>> getAll() {
        return ResponseEntity.ok(purchaseAppointmentService.getAll());
    }

    @Operation(summary = "Create New Purchase Appointment")
    @PostMapping()
    public ResponseEntity<PurchaseAppointmentDto> createOne(@RequestBody PurchaseAppointmentFilter filter) {
        return ResponseEntity.ok(purchaseAppointmentService.createOne(filter));
    }

    @Operation(summary = "Update Existing Purchase Appointment")
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseAppointmentDto> updateOne(@PathVariable Long id,
                                                                 @RequestBody PurchaseAppointmentFilter filter) {
        return ResponseEntity.ok(purchaseAppointmentService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Purchase Appointment")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseAppointmentService.deleteById(id));
    }

}
