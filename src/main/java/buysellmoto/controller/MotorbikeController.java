package buysellmoto.controller;

import buysellmoto.model.dto.MotorbikeDto;
import buysellmoto.model.filter.MotorbikeFilter;
import buysellmoto.service.MotorbikeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/motorbike")
public class MotorbikeController {

    @Autowired
    private MotorbikeService motorbikeService;

    @Operation(summary = "Get Motorbike By Id")
    @GetMapping("/{id}")
    public ResponseEntity<MotorbikeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(motorbikeService.getById(id));
    }

    @Operation(summary = "Get All Motorbike")
    @GetMapping("/all")
    public ResponseEntity<List<MotorbikeDto>> getAll() {
        return ResponseEntity.ok(motorbikeService.getAll());
    }

    @Operation(summary = "Create New Motorbike")
    @PostMapping()
    public ResponseEntity<MotorbikeDto> createOne(@RequestBody MotorbikeFilter filter) {
        return ResponseEntity.ok(motorbikeService.createOne(filter));
    }

    @Operation(summary = "Update Existing Motorbike")
    @PutMapping("/{id}")
    public ResponseEntity<MotorbikeDto> updateExistingCoupon(@PathVariable Long id,
                                                        @RequestBody MotorbikeFilter filter) {
        return ResponseEntity.ok(motorbikeService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Motorbike")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCouponByID(@PathVariable Long id) {
        return ResponseEntity.ok(motorbikeService.deleteById(id));
    }

}
