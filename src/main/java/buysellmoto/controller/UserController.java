package buysellmoto.controller;

import buysellmoto.model.dto.UserDto;
import buysellmoto.model.filter.UserFilter;
import buysellmoto.model.filter.other.LoginFilter;
import buysellmoto.model.vo.UserVo;
import buysellmoto.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get User By Id")
    @GetMapping("/{id}")
    public ResponseEntity<UserVo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "Get All User")
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<UserDto> checkLogin(@RequestBody LoginFilter loginFilter) {
        return ResponseEntity.ok(userService.checkLogin(loginFilter));
    }

    @Operation(summary = "Reset password")
    @PutMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(@RequestParam String email) {
        return ResponseEntity.ok(userService.resetPassword(email));
    }

    @Operation(summary = "Create New User")
    @PostMapping()
    public ResponseEntity<UserDto> createOne(@RequestBody UserFilter filter) {
        return ResponseEntity.ok(userService.createOne(filter));
    }

    @Operation(summary = "Create New Customer")
    @PostMapping("/customer")
    public ResponseEntity<UserVo> createCustomer(@RequestBody UserFilter filter) {
        return ResponseEntity.ok(userService.createCustomer(filter));
    }

    @Operation(summary = "Create New Staff")
    @PostMapping("/staff")
    public ResponseEntity<UserVo> createStaff(@RequestBody UserFilter filter) {
        return ResponseEntity.ok(userService.createStaff(filter));
    }

    @Operation(summary = "Update Existing User")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateOne(@RequestBody UserFilter filter) {
        return ResponseEntity.ok(userService.updateOne(filter));
    }

    @Operation(summary = "Delete Existing User")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

}
