package buysellmoto.controller;

import buysellmoto.model.dto.UserDto;
import buysellmoto.model.filter.UserFilter;
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
    @GetMapping("/login")
    public ResponseEntity<UserDto> checkLogin(@RequestParam String account,
                                              @RequestParam String password) {
        return ResponseEntity.ok(userService.checkLogin(account, password));
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

    @Operation(summary = "Update Existing User")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateOne(@PathVariable Long id,
                                             @RequestBody UserFilter filter) {
        return ResponseEntity.ok(userService.updateOne(id, filter));
    }

    @Operation(summary = "Delete Existing User")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

}
