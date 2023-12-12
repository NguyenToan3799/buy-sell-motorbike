package buysellmoto.controller;

import buysellmoto.model.dto.TransactionDto;
import buysellmoto.model.filter.TransactionFilter;
import buysellmoto.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get Transaction By Id")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @Operation(summary = "Get Transaction By Sell Request Id")
    @GetMapping("/sell-request/{sellRequestId}")
    public ResponseEntity<List<TransactionDto>> getBySellRequestId(@PathVariable Long sellRequestId) {
        return ResponseEntity.ok(transactionService.getBySellRequestId(sellRequestId));
    }

    @Operation(summary = "Get Transaction By Buy Request Id")
    @GetMapping("/buy-request/{buyRequestId}")
    public ResponseEntity<List<TransactionDto>> getByBuyRequestId(@PathVariable Long buyRequestId) {
        return ResponseEntity.ok(transactionService.getByBuyRequestId(buyRequestId));
    }

    @Operation(summary = "Get All Transaction")
    @GetMapping()
    public ResponseEntity<List<TransactionDto>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @Operation(summary = "Get Transaction By ShowroomId")
    @GetMapping("/showroom/{showroomId}")
    public ResponseEntity<List<TransactionDto>> getByShowroomId(@PathVariable Long showroomId) {
        return ResponseEntity.ok(transactionService.getByShowroomId(showroomId));
    }

    @Operation(summary = "Get Transaction By ShowroomID and Type")
    @GetMapping("/showroom-and-type/{showroomId}")
    public ResponseEntity<List<TransactionDto>> getByShowroomIdAndType(@PathVariable Long showroomId,
                                                                @RequestParam String type) {
        return ResponseEntity.ok(transactionService.getByShowroomIdAndType(showroomId, type));
    }

    @Operation(summary = "Create New Transaction")
    @PostMapping()
    public ResponseEntity<TransactionDto> createOne(@RequestBody TransactionFilter filter) {
        return ResponseEntity.ok(transactionService.createOne(filter));
    }

    @Operation(summary = "Update Existing Transaction")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateOne(@RequestBody TransactionFilter filter) {
        return ResponseEntity.ok(transactionService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Transaction")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.deleteById(id));
    }

}
