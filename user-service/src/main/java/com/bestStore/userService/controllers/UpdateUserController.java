package com.bestStore.userService.controllers;

import com.bestStore.userService.services.update.UpdatableUserInfoService;
import com.common.lib.userModule.userDto.request.UserUpdateRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-update")
@RequiredArgsConstructor
@Validated
public class UpdateUserController {

    private final UpdatableUserInfoService updatableUserInfoService;

    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto,
                                                 @PathVariable @Positive Long id) {

        updatableUserInfoService.updateUser(userUpdateRequestDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete-by-id/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable Long id) {
        updatableUserInfoService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/delete-by-email/{email}")
    public ResponseEntity<HttpStatus> deleteUserByEmail(@PathVariable String email) {
        updatableUserInfoService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
