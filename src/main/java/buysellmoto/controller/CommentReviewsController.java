package buysellmoto.controller;

import buysellmoto.model.dto.CommentReviewsDto;
import buysellmoto.model.filter.CommentReviewsFilter;
import buysellmoto.service.CommentReviewsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comment-reviews")
public class CommentReviewsController {

    @Autowired
    private CommentReviewsService commentReviewsService;

    @Operation(summary = "Get Comment Reviews By Id")
    @GetMapping("/{id}")
    public ResponseEntity<CommentReviewsDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commentReviewsService.getById(id));
    }

    @Operation(summary = "Get All Comment Reviews")
    @GetMapping()
    public ResponseEntity<List<CommentReviewsDto>> getAll() {
        return ResponseEntity.ok(commentReviewsService.getAll());
    }

    @Operation(summary = "Create New Comment Reviews")
    @PostMapping()
    public ResponseEntity<CommentReviewsDto> createOne(@RequestBody CommentReviewsFilter filter) {
        return ResponseEntity.ok(commentReviewsService.createOne(filter));
    }

    @Operation(summary = "Update Existing Comment Reviews")
    @PutMapping("/{id}")
    public ResponseEntity<CommentReviewsDto> updateOne(@RequestBody CommentReviewsFilter filter) {
        return ResponseEntity.ok(commentReviewsService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Comment Reviews")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(commentReviewsService.deleteById(id));
    }

}
