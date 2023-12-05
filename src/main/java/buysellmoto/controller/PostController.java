package buysellmoto.controller;

import buysellmoto.model.dto.PostDto;
import buysellmoto.model.filter.PostFilter;
import buysellmoto.model.vo.PostProjection;
import buysellmoto.model.vo.PostVo;
import buysellmoto.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Get Post By Id")
    @GetMapping("/{id}")
    public ResponseEntity<PostVo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getById(id));
    }

    @Operation(summary = "Get All Post")
    @GetMapping()
    public ResponseEntity<List<PostDto>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @Operation(summary = "Create New Post")
    @PostMapping()
    public ResponseEntity<PostDto> createOne(@RequestBody PostFilter filter) {
        return ResponseEntity.ok(postService.createOne(filter));
    }

    @Operation(summary = "Update Existing Post")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updateOne(@RequestBody PostFilter filter) {
        return ResponseEntity.ok(postService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing Post")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.deleteById(id));
    }

    @Operation(summary = "Paging Post")
    @PostMapping("/paging")
    public ResponseEntity<Page<PostProjection>> getPaging(@RequestBody PostFilter filter) {
        return ResponseEntity.ok(postService.getPaging(filter));
    }

    @Operation(summary = "Get Post by Showroom Id")
    @GetMapping("/projection/{showroomId}")
    public ResponseEntity<List<PostProjection>> getPostByShowroomId(@PathVariable Long showroomId) {
        return ResponseEntity.ok(postService.getPostByShowroomId(showroomId));
    }

}
