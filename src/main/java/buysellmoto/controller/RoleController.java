package buysellmoto.controller;

import buysellmoto.model.dto.RoleDto;
import buysellmoto.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin

@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Get Role By Id")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

//    @Operation(summary = "Search Coupon By Coupon Name")
//    @GetMapping("/search")
//    public ResponseEntity<?> searchCouponByCouponName(@RequestParam String name) {
//        return controllerWrapper(() -> couponService.searchCouponByCouponName(name));
//    }
//
//    @Operation(summary = "Create New Coupon")
//    @PostMapping()
//    public ResponseEntity<?> createNewCoupon(@RequestBody CouponRequestEntity entity) {
//        return controllerWrapper(() -> couponService.createNewCoupon(entity));
//    }
//
//    @Operation(summary = "Update Existing Coupon")
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateExistingCoupon(@PathVariable String id,
//                                                   @RequestBody CouponRequestEntity entity) {
//        return controllerWrapper(() -> couponService.updateExistingCoupon(id, entity));
//    }
//
//    @Operation(summary = "Delete Existing Coupon")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteCouponByID(@PathVariable String id) {
//        return controllerWrapper(() -> couponService.deleteCouponByID(id));
//    }
}
