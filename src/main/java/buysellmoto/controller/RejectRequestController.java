package buysellmoto.controller;

import buysellmoto.model.dto.RejectRequestDto;
import buysellmoto.model.filter.RejectRequestFilter;
import buysellmoto.service.RejectRequestService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reject-requests")
public class RejectRequestController {

    @Autowired
    private RejectRequestService rejectRequestService;

    @Operation(summary = "Get Reject Request By Id")
    @GetMapping("/{id}")
    public ResponseEntity<RejectRequestDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(rejectRequestService.getById(id));
    }

    @Operation(summary = "Get All Reject Request")
    @GetMapping()
    public ResponseEntity<List<RejectRequestDto>> getAll() {
        return ResponseEntity.ok(rejectRequestService.getAll());
    }

    @Operation(summary = "Create New Reject Request")
    @PostMapping()
    public ResponseEntity<RejectRequestDto> createOne(@RequestBody RejectRequestFilter filter) {
        return ResponseEntity.ok(rejectRequestService.createOne(filter));
    }

    @Operation(summary = "Update Existing Reject Request")
    @PutMapping("/{id}")
    public ResponseEntity<RejectRequestDto> updateOne(@PathVariable Long id,
                                                                 @RequestBody RejectRequestFilter filter) {
        return ResponseEntity.ok(rejectRequestService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Reject Request")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(rejectRequestService.deleteById(id));
    }

}
