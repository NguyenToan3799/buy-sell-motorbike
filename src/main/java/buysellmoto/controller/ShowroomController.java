package buysellmoto.controller;

import buysellmoto.model.dto.ShowroomDto;
import buysellmoto.model.filter.ShowroomFilter;
import buysellmoto.service.ShowroomService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/showrooms")
public class ShowroomController {

    @Autowired
    private ShowroomService showroomService;

    @Operation(summary = "Get Showroom By Id")
    @GetMapping("/{id}")
    public ResponseEntity<ShowroomDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(showroomService.getById(id));
    }

    @Operation(summary = "Get All Showroom")
    @GetMapping("/all")
    public ResponseEntity<List<ShowroomDto>> getAll() {
        return ResponseEntity.ok(showroomService.getAll());
    }

    @Operation(summary = "Create New Showroom")
    @PostMapping()
    public ResponseEntity<ShowroomDto> createOne(@RequestBody ShowroomFilter filter) {
        return ResponseEntity.ok(showroomService.createOne(filter));
    }

    @Operation(summary = "Update Existing Showroom")
    @PutMapping("/{id}")
    public ResponseEntity<ShowroomDto> updateExistingCoupon(@PathVariable Long id,
                                                        @RequestBody ShowroomFilter filter) {
        return ResponseEntity.ok(showroomService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Showroom")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCouponByID(@PathVariable Long id) {
        return ResponseEntity.ok(showroomService.deleteById(id));
    }

}
