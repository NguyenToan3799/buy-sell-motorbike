package buysellmoto.controller;

import buysellmoto.model.dto.RoleDto;
import buysellmoto.model.filter.RoleFilter;
import buysellmoto.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Get Role By Id")
    @GetMapping("/{id}")
    @CrossOrigin("http://localhost:8080")
    public ResponseEntity<RoleDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @Operation(summary = "Get All Role")
    @GetMapping("/all")
    public ResponseEntity<List<RoleDto>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @Operation(summary = "Get Roles By Ids")
    @PostMapping("/ids")
    public ResponseEntity<List<RoleDto>> getByIds(@RequestBody RoleFilter filter) {
        return ResponseEntity.ok(roleService.getByIds(filter));
    }

    @Operation(summary = "Create New Role")
    @PostMapping()
    public ResponseEntity<RoleDto> createOne(@RequestBody RoleFilter filter) {
        return ResponseEntity.ok(roleService.createOne(filter));
    }

    @Operation(summary = "Update Existing Role")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateExistingCoupon(@PathVariable Long id,
                                                        @RequestBody RoleFilter filter) {
        return ResponseEntity.ok(roleService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing Role")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCouponByID(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.deleteById(id));
    }

}
