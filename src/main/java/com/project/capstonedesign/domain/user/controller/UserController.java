package com.project.capstonedesign.domain.user.controller;

import com.project.capstonedesign.common.jwt.service.JwtService;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.dto.UserLogoutReq;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * 자체 회원가입
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
     * @param userId
     * @param userUpdatdDto
     * @return
     */
    @PutMapping("/edit/{userId}")
    public ApiResult<Long> updateUser(@PathVariable Long userId, @RequestBody UserUpdatdDto userUpdatdDto) {
        try {
            return ApiResult.success(userService.updateUser(userId, userUpdatdDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * 회원 탈퇴
     * @param userId
     */
    @DeleteMapping("/withdraw/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

//    @PostMapping("/autologin")
//    public ApiResult<User> autoLogin(@RequestBody UserLogoutReq userLogoutReq) {
//
//        try {
//            int userId = userLogoutReq.getUserId();
//            int jwtId = jwtService.extractUserId();
//        }
//    }
//
//    @PostMapping("/logout")
//    public ApiResult<String> logOut(@RequestBody UserLogoutReq userLogoutReq) {
//
//        try {
//            int userId = userLogoutReq.getUserId();
//            int jwtId = jwtService.extractUserId();
//            if (userId!=jwtId) {
//
//            }
//        }
//    }

    /**
     * jwt test
     * @return
     */
    @GetMapping("/test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
