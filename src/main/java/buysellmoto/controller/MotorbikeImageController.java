package buysellmoto.controller;

import buysellmoto.model.dto.MotorbikeImageDto;
import buysellmoto.model.filter.MotorbikeImageFilter;
import buysellmoto.service.MotorbikeImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/motorbike-image")
public class MotorbikeImageController {

    @Autowired
    private MotorbikeImageService motorbikeImageService;

    @Operation(summary = "Get Motorbike Image Image Image By Id")
    @GetMapping("/{id}")
    public ResponseEntity<MotorbikeImageDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(motorbikeImageService.getById(id));
    }

    @Operation(summary = "Get All Motorbike Image")
    @GetMapping("/all")
    public ResponseEntity<List<MotorbikeImageDto>> getAll() {
        return ResponseEntity.ok(motorbikeImageService.getAll());
    }

    @Operation(summary = "Create New Motorbike Image")
    @PostMapping()
    public ResponseEntity<MotorbikeImageDto> createOne(@RequestBody MotorbikeImageFilter filter) {
        return ResponseEntity.ok(motorbikeImageService.createOne(filter));
    }

    @Operation(summary = "Update Existing Motorbike Image")
    @PutMapping("/{id}")
    public ResponseEntity<MotorbikeImageDto> updateExistingCoupon(@PathVariable Long id,
                                                                  @RequestBody MotorbikeImageFilter filter) {
        return ResponseEntity.ok(motorbikeImageService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Motorbike Image")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCouponByID(@PathVariable Long id) {
        return ResponseEntity.ok(motorbikeImageService.deleteById(id));
    }

}
