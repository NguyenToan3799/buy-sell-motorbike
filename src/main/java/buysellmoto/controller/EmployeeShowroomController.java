package buysellmoto.controller;

import buysellmoto.model.dto.EmployeeShowroomDto;
import buysellmoto.model.filter.EmployeeShowroomFilter;
import buysellmoto.service.EmployeeShowroomService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/employee-showrooms")
public class EmployeeShowroomController {

    @Autowired
    private EmployeeShowroomService employeeShowroomService;

    @Operation(summary = "Get EmployeeShowroom By Id")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeShowroomDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeShowroomService.getById(id));
    }

    @Operation(summary = "Get All EmployeeShowroom")
    @GetMapping()
    public ResponseEntity<List<EmployeeShowroomDto>> getAll() {
        return ResponseEntity.ok(employeeShowroomService.getAll());
    }

    @Operation(summary = "Create New EmployeeShowroom")
    @PostMapping()
    public ResponseEntity<EmployeeShowroomDto> createOne(@RequestBody EmployeeShowroomFilter filter) {
        return ResponseEntity.ok(employeeShowroomService.createOne(filter));
    }

    @Operation(summary = "Update Existing EmployeeShowroom")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeShowroomDto> updateOne(@PathVariable Long id,
                                                        @RequestBody EmployeeShowroomFilter filter) {
        return ResponseEntity.ok(employeeShowroomService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing EmployeeShowroom")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeShowroomService.deleteById(id));
    }

}
