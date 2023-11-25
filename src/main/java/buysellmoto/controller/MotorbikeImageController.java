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
@RequestMapping("/motorbike-images")
public class MotorbikeImageController {

    @Autowired
    private MotorbikeImageService motorbikeImageService;

    @Operation(summary = "Get Motorbike Image Image Image By Id")
    @GetMapping("/{id}")
    public ResponseEntity<MotorbikeImageDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(motorbikeImageService.getById(id));
    }

    @Operation(summary = "Get All Motorbike Image")
    @GetMapping()
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
    public ResponseEntity<MotorbikeImageDto> updateOne(@RequestBody MotorbikeImageFilter filter) {
        return ResponseEntity.ok(motorbikeImageService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Motorbike Image")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(motorbikeImageService.deleteById(id));
    }

}
