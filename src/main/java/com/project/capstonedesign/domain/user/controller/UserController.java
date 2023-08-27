package com.project.capstonedesign.domain.user.controller;

import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.dto.UserUpdatdDto;
import com.project.capstonedesign.domain.user.service.UserService;
import com.project.capstonedesign.domain.user.dto.UserSignUpDto;

import com.project.capstonedesign.domain.util.ApiResult;
import lombok.Lombok;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 자체 회원가입
    @PostMapping("/join")
    public String joinUser(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.join(userSignUpDto);
        return "회원가입 성공";
    }

    // 특정 유저 조회
    @GetMapping("/{userId}")
    public ApiResult<User> findUser(@PathVariable Long userId) {
        try {
            return ApiResult.success(userService.findById(userId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 모든 유저 조회
    @GetMapping()
    public ApiResult<List<User>> findAllUser() {
        try {
            return ApiResult.success(userService.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 유저 정보 변경
    @PutMapping("/{userId}")
    public ApiResult<Long> updateUser(@PathVariable Long userId, @RequestBody UserUpdatdDto userUpdatdDto) {
        try {
            return ApiResult.success(userService.updateUser(userId, userUpdatdDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    // jwt test
    @GetMapping("/test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
