package buysellmoto.controller;

import buysellmoto.model.dto.MotoTypeDto;
import buysellmoto.model.filter.MotoTypeFilter;
import buysellmoto.service.MotoTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/moto-types")
public class MotoTypeController {

    @Autowired
    private MotoTypeService motoTypeService;

    @Operation(summary = "Get Moto Type By Id")
    @GetMapping("/{id}")
    public ResponseEntity<MotoTypeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(motoTypeService.getById(id));
    }

    @Operation(summary = "Get All Moto Type")
    @GetMapping()
    public ResponseEntity<List<MotoTypeDto>> getAll() {
        return ResponseEntity.ok(motoTypeService.getAll());
    }

    @Operation(summary = "Create New Moto Type")
    @PostMapping()
    public ResponseEntity<MotoTypeDto> createOne(@RequestBody MotoTypeFilter filter) {
        return ResponseEntity.ok(motoTypeService.createOne(filter));
    }

    @Operation(summary = "Update Existing Moto Type")
    @PutMapping("/{id}")
    public ResponseEntity<MotoTypeDto> updateOne(@RequestBody MotoTypeFilter filter) {
        return ResponseEntity.ok(motoTypeService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Moto Type")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(motoTypeService.deleteById(id));
    }

}
