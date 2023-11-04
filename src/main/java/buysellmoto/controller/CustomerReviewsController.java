package buysellmoto.controller;

import buysellmoto.model.dto.CustomerReviewsDto;
import buysellmoto.model.filter.CustomerReviewsFilter;
import buysellmoto.service.CustomerReviewsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customer-reviews")
public class CustomerReviewsController {

    @Autowired
    private CustomerReviewsService customerReviewsService;

    @Operation(summary = "Get Customer Reviews By Id")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerReviewsDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerReviewsService.getById(id));
    }

    @Operation(summary = "Get All Customer Reviews")
    @GetMapping()
    public ResponseEntity<List<CustomerReviewsDto>> getAll() {
        return ResponseEntity.ok(customerReviewsService.getAll());
    }

    @Operation(summary = "Create New Customer Reviews")
    @PostMapping()
    public ResponseEntity<CustomerReviewsDto> createOne(@RequestBody CustomerReviewsFilter filter) {
        return ResponseEntity.ok(customerReviewsService.createOne(filter));
    }

    @Operation(summary = "Update Existing Customer Reviews")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerReviewsDto> updateOne(@PathVariable Long id,
                                                        @RequestBody CustomerReviewsFilter filter) {
        return ResponseEntity.ok(customerReviewsService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Customer Reviews")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(customerReviewsService.deleteById(id));
    }

}
