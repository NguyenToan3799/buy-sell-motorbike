package buysellmoto.controller;

import buysellmoto.model.dto.ShowroomImageDto;
import buysellmoto.model.filter.ShowroomImageFilter;
import buysellmoto.service.ShowroomImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/showroom-images")
public class ShowroomImageController {

    @Autowired
    private ShowroomImageService showroomImageService;

    @Operation(summary = "Get Showroom Image By Id")
    @GetMapping("/{id}")
    public ResponseEntity<ShowroomImageDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(showroomImageService.getById(id));
    }

    @Operation(summary = "Get All Showroom Image")
    @GetMapping()
    public ResponseEntity<List<ShowroomImageDto>> getAll() {
        return ResponseEntity.ok(showroomImageService.getAll());
    }

    @Operation(summary = "Create New Showroom Image")
    @PostMapping()
    public ResponseEntity<ShowroomImageDto> createOne(@RequestBody ShowroomImageFilter filter) {
        return ResponseEntity.ok(showroomImageService.createOne(filter));
    }

    @Operation(summary = "Update Existing Showroom Image")
    @PutMapping("/{id}")
    public ResponseEntity<ShowroomImageDto> updateOne(@RequestBody ShowroomImageFilter filter) {
        return ResponseEntity.ok(showroomImageService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Showroom Image")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(showroomImageService.deleteById(id));
    }

}
