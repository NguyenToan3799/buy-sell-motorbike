package buysellmoto.controller;

import buysellmoto.model.dto.BuyRequestDto;
import buysellmoto.model.dto.CheckingAppointmentDto;
import buysellmoto.model.filter.BuyRequestFilter;
import buysellmoto.model.vo.BuyRequestVo;
import buysellmoto.model.vo.SellRequestVo;
import buysellmoto.service.BuyRequestService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.kafka.common.protocol.types.Field;
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
    public ResponseEntity<BuyRequestVo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(buyRequestService.getById(id));
    }

    @Operation(summary = "Get All Buy Request")
    @GetMapping()
    public ResponseEntity<List<BuyRequestDto>> getAll() {
        return ResponseEntity.ok(buyRequestService.getAll());
    }

    @Operation(summary = "Get List Buy Request")
    @GetMapping("/list/{showroomId}")
    public ResponseEntity<List<BuyRequestVo>> getListBuyRequest(@PathVariable Long showroomId,
                                                                @RequestParam String status) {
        return ResponseEntity.ok(buyRequestService.getListBuyRequest(showroomId, status));
    }

    @Operation(summary = "Get List Buy Request By CustomerId")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BuyRequestVo>> getListBuyRequestByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(buyRequestService.getListBuyRequestByCustomerId(customerId));
    }

    @Operation(summary = "Create New Buy Request")
    @PostMapping()
    public ResponseEntity<Boolean> createOne(@RequestBody BuyRequestFilter filter) {
        return ResponseEntity.ok(buyRequestService.createOne(filter));
    }

    @Operation(summary = "Cancel Buy Request")
    @PostMapping("/{id}/cancel")
    public synchronized ResponseEntity<Boolean> cancelBuyRequest(@PathVariable Long id,
                                                    @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(buyRequestService.cancelBuyRequest(id, reason));
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
