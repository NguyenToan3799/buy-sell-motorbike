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

    @Operation(summary = "Get All Transaction")
    @GetMapping()
    public ResponseEntity<List<TransactionDto>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
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
