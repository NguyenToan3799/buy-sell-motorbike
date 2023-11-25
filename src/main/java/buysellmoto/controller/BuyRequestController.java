package buysellmoto.controller;

import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.filter.BuyRequestFilter;
import buysellmoto.service.BuyRequestService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/buy-requests")
public class BuyRequestController {

    @Autowired
    private BuyRequestService buyRequestService;

    @Operation(summary = "Get Buy Request By Id")
    @GetMapping("/{id}")
    public ResponseEntity<BuyRequestDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(buyRequestService.getById(id));
    }

    @Operation(summary = "Get All Buy Request")
    @GetMapping()
    public ResponseEntity<List<BuyRequestDto>> getAll() {
        return ResponseEntity.ok(buyRequestService.getAll());
    }

    @Operation(summary = "Create New Buy Request")
    @PostMapping()
    public ResponseEntity<BuyRequestDto> createOne(@RequestBody BuyRequestFilter filter) {
        return ResponseEntity.ok(buyRequestService.createOne(filter));
    }

    @Operation(summary = "Update Existing Buy Request")
    @PutMapping("/{id}")
    public ResponseEntity<BuyRequestDto> updateOne(@RequestBody BuyRequestFilter filter) {
        return ResponseEntity.ok(buyRequestService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Buy Request")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(buyRequestService.deleteById(id));
    }

}
