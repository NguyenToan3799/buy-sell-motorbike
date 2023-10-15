package buysellmoto.controller;

import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.filter.SellRequestFilter;
import buysellmoto.service.SellRequestService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sell-request")
public class SellRequestController {

    @Autowired
    private SellRequestService sellRequestService;

    @Operation(summary = "Get Sell Request By Id")
    @GetMapping("/{id}")
    public ResponseEntity<SellRequestDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sellRequestService.getById(id));
    }

    @Operation(summary = "Get All Sell Request")
    @GetMapping("/all")
    public ResponseEntity<List<SellRequestDto>> getAll() {
        return ResponseEntity.ok(sellRequestService.getAll());
    }

    @Operation(summary = "Create New Sell Request")
    @PostMapping()
    public ResponseEntity<SellRequestDto> createOne(@RequestBody SellRequestFilter filter) {
        return ResponseEntity.ok(sellRequestService.createOne(filter));
    }

    @Operation(summary = "Update Existing Sell Request")
    @PutMapping("/{id}")
    public ResponseEntity<SellRequestDto> updateExistingCoupon(@PathVariable Long id,
                                                        @RequestBody SellRequestFilter filter) {
        return ResponseEntity.ok(sellRequestService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Sell Request")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCouponByID(@PathVariable Long id) {
        return ResponseEntity.ok(sellRequestService.deleteById(id));
    }

}
