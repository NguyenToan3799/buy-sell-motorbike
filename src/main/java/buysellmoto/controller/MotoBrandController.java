package buysellmoto.controller;

import buysellmoto.model.dto.MotoBrandDto;
import buysellmoto.model.filter.MotoBrandFilter;
import buysellmoto.service.MotoBrandService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/moto-brands")
public class MotoBrandController {

    @Autowired
    private MotoBrandService motoBrandService;

    @Operation(summary = "Get Moto Brand By Id")
    @GetMapping("/{id}")
    public ResponseEntity<MotoBrandDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(motoBrandService.getById(id));
    }

    @Operation(summary = "Get All Moto Brand")
    @GetMapping()
    public ResponseEntity<List<MotoBrandDto>> getAll() {
        return ResponseEntity.ok(motoBrandService.getAll());
    }

    @Operation(summary = "Create New Moto Brand")
    @PostMapping()
    public ResponseEntity<MotoBrandDto> createOne(@RequestBody MotoBrandFilter filter) {
        return ResponseEntity.ok(motoBrandService.createOne(filter));
    }

    @Operation(summary = "Update Existing Moto Brand")
    @PutMapping("/{id}")
    public ResponseEntity<MotoBrandDto> updateOne(@PathVariable Long id,
                                                        @RequestBody MotoBrandFilter filter) {
        return ResponseEntity.ok(motoBrandService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Moto Brand")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(motoBrandService.deleteById(id));
    }

}
