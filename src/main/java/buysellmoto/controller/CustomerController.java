package buysellmoto.controller;

import buysellmoto.model.dto.CustomerDto;
import buysellmoto.model.filter.CustomerFilter;
import buysellmoto.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Get Customer By Id")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @Operation(summary = "Get All Customer")
    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @Operation(summary = "Create New Customer")
    @PostMapping()
    public ResponseEntity<CustomerDto> createOne(@RequestBody CustomerFilter filter) {
        return ResponseEntity.ok(customerService.createOne(filter));
    }

    @Operation(summary = "Update Existing Customer")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateOne(@PathVariable Long id,
                                                        @RequestBody CustomerFilter filter) {
        return ResponseEntity.ok(customerService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Customer")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.deleteById(id));
    }

}
