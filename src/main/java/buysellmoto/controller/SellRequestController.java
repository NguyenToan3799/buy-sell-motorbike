package buysellmoto.controller;

import buysellmoto.model.dto.SellRequestDto;
import buysellmoto.model.filter.MotorbikeFilter;
import buysellmoto.model.filter.RejectRequestFilter;
import buysellmoto.model.filter.SellRequestFilter;
import buysellmoto.model.vo.SellRequestVo;
import buysellmoto.service.SellRequestService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sell-requests")
public class SellRequestController {

    @Autowired
    private SellRequestService sellRequestService;

    @Operation(summary = "Get Sell Request By Id")
    @GetMapping("/{id}")
    public ResponseEntity<SellRequestDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sellRequestService.getById(id));
    }

    @Operation(summary = "Get All Sell Request")
    @GetMapping()
    public ResponseEntity<List<SellRequestDto>> getAll() {
        return ResponseEntity.ok(sellRequestService.getAll());
    }

    @Operation(summary = "Get List Sell Request")
    @GetMapping("/list/{showroomId}")
    public ResponseEntity<List<SellRequestVo>> getListSellRequest(@PathVariable Long showroomId,
                                                                  @RequestParam String status) {
        return ResponseEntity.ok(sellRequestService.getListSellRequestByShowroomId(showroomId, status));
    }

    @Operation(summary = "Get List Sell Request By Showroom")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SellRequestVo>> getListSellRequest(@PathVariable Long customerId) {
        return ResponseEntity.ok(sellRequestService.getListSellRequestByCustomerId(customerId));
    }

    @Operation(summary = "Approve Sell Request")
    @GetMapping("/{id}/approve")
    public ResponseEntity<Boolean> approvedSellRequest(@PathVariable Long id) {
        return ResponseEntity.ok(sellRequestService.approvedSellRequest(id));
    }

    @Operation(summary = "Reject Sell Request")
    @PostMapping("/{id}/reject")
    public ResponseEntity<Boolean> rejectedSellRequest(@PathVariable Long id,
                                                       @RequestBody SellRequestFilter sellRequestFilter) {
        return ResponseEntity.ok(sellRequestService.rejectedSellRequest(id, sellRequestFilter));
    }

    @Operation(summary = "Checked Sell Request")
    @PostMapping("/{id}/checked")
    public ResponseEntity<Boolean> checkedSellRequest(@PathVariable Long id,
                                                      @RequestBody SellRequestFilter sellRequestFilter) {
        return ResponseEntity.ok(sellRequestService.checkedSellRequest(id, sellRequestFilter));
    }

    @Operation(summary = "Cancel Sell Request")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Boolean> cancelSellRequest(@PathVariable Long id) {
        return ResponseEntity.ok(sellRequestService.cancelSellRequest(id));
    }

    @Operation(summary = "Create New Sell Request")
    @PostMapping()
    public ResponseEntity<SellRequestDto> createOne(@RequestBody SellRequestFilter filter) throws MessagingException {
        return ResponseEntity.ok(sellRequestService.createOne(filter));
    }

    @Operation(summary = "Update Existing Sell Request")
    @PutMapping("/{id}")
    public ResponseEntity<SellRequestDto> updateOne(@RequestBody SellRequestFilter filter) {
        return ResponseEntity.ok(sellRequestService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Sell Request")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(sellRequestService.deleteById(id));
    }

}
