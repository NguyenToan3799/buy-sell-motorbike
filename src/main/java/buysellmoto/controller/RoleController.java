package hoangdung.springboot.projecthighlands.controller;

import hoangdung.springboot.projecthighlands.model.request.CouponRequestEntity;
import hoangdung.springboot.projecthighlands.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static hoangdung.springboot.projecthighlands.common.CommonUtils.controllerWrapper;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;


    @Operation(summary = "Get Coupon By Coupon Code")
    @GetMapping("/{code}")
    public ResponseEntity<?> getCouponByCouponCode(@PathVariable String code) {
        return controllerWrapper(() -> couponService.getCouponByCouponCode(code));
    }

    @Operation(summary = "Search Coupon By Coupon Name")
    @GetMapping("/search")
    public ResponseEntity<?> searchCouponByCouponName(@RequestParam String name) {
        return controllerWrapper(() -> couponService.searchCouponByCouponName(name));
    }

    @Operation(summary = "Create New Coupon")
    @PostMapping()
    public ResponseEntity<?> createNewCoupon(@RequestBody CouponRequestEntity entity) {
        return controllerWrapper(() -> couponService.createNewCoupon(entity));
    }

    @Operation(summary = "Update Existing Coupon")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExistingCoupon(@PathVariable String id,
                                                   @RequestBody CouponRequestEntity entity) {
        return controllerWrapper(() -> couponService.updateExistingCoupon(id, entity));
    }

    @Operation(summary = "Delete Existing Coupon")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCouponByID(@PathVariable String id) {
        return controllerWrapper(() -> couponService.deleteCouponByID(id));
    }
}
