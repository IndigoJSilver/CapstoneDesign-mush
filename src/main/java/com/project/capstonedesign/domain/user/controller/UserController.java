package com.project.capstonedesign.domain.user.controller;

import com.project.capstonedesign.common.jwt.service.JwtService;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.dto.UserSignUpDto;
import com.project.capstonedesign.domain.user.dto.UserUpdatedDto;
import com.project.capstonedesign.domain.user.service.UserService;
import com.project.capstonedesign.domain.util.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 자체 회원가입
     *
     * @param userSignUpDto
     * @return
     * @throws Exception
     */
    @PostMapping("/signup")
    public String joinUser(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.join(userSignUpDto);
        return "회원가입 성공";
    }

    /**
     * 특정 유저 조회
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ApiResult<User> findUser(@PathVariable Long userId) {
        try {
            return ApiResult.success(userService.findById(userId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * 모든 유저 조회
     *
     * @return
     */
    @GetMapping()
    public ApiResult<List<User>> findAllUser() {
        try {
            return ApiResult.success(userService.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * 유저 정보 변경
     *
     * @param
     * @param userUpdatedDto
     * @return
     */
    @PutMapping("/edit")
    public ApiResult<Long> updateUser(@AuthenticationPrincipal User user, @RequestBody UserUpdatedDto userUpdatedDto) {
        try {
            return ApiResult.success(userService.updateUser(user.getUserId(), userUpdatedDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * 회원 탈퇴
     *
     * @param
     */
    @DeleteMapping("/withdraw")
    public void deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getUserId());
    }

    /**
     * 로그아웃
     * @param user
     * @return
     */
    @PostMapping("/logout")
    public ApiResult<String> logOut(@AuthenticationPrincipal User user) {
        jwtService.extractUserId(user.getUserId());
        return ApiResult.success("로그아웃");
    }

    /**
     * jwt test
     *
     * @return
     */
    @GetMapping("/test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
