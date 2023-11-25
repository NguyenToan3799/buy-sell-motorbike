package buysellmoto.controller;

import buysellmoto.model.dto.SellAgreementContractDto;
import buysellmoto.model.filter.SellAgreementContractFilter;
import buysellmoto.service.SellAgreementContractService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sell-agreement-contracts")
public class SellAgreementContractController {

    @Autowired
    private SellAgreementContractService sellAgreementContractService;

    @Operation(summary = "Get Sell Agreement Contract By Id")
    @GetMapping("/{id}")
    public ResponseEntity<SellAgreementContractDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sellAgreementContractService.getById(id));
    }

    @Operation(summary = "Get All Sell Agreement Contract")
    @GetMapping()
    public ResponseEntity<List<SellAgreementContractDto>> getAll() {
        return ResponseEntity.ok(sellAgreementContractService.getAll());
    }

    @Operation(summary = "Create New Sell Agreement Contract")
    @PostMapping()
    public ResponseEntity<SellAgreementContractDto> createOne(@RequestBody SellAgreementContractFilter filter) {
        return ResponseEntity.ok(sellAgreementContractService.createOne(filter));
    }

    @Operation(summary = "Update Existing Sell Agreement Contract")
    @PutMapping("/{id}")
    public ResponseEntity<SellAgreementContractDto> updateOne(@RequestBody SellAgreementContractFilter filter) {
        return ResponseEntity.ok(sellAgreementContractService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Sell Agreement Contract")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(sellAgreementContractService.deleteById(id));
    }

}
