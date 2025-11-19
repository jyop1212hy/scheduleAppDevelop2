package com.scheduleappdevelop2.user.controller;

import com.scheduleappdevelop2.user.dto.login.LoginRequest;
import com.scheduleappdevelop2.user.dto.login.LoginResponse;
import com.scheduleappdevelop2.user.dto.sessionUser.SessionUser;
import com.scheduleappdevelop2.user.dto.updateUser.UpdateUserRequest;
import com.scheduleappdevelop2.user.dto.updateUser.UpdateUserResponse;
import com.scheduleappdevelop2.user.dto.userCreate.UserCreateRequest;
import com.scheduleappdevelop2.user.dto.userCreate.UserCreateResponse;
import com.scheduleappdevelop2.user.dto.userResponse.UserResponse;
import com.scheduleappdevelop2.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController
 * - /users 로 들어오는 모든 HTTP 요청을 처리하는 컨트롤러.
 * - 요청 데이터를 받아 Service에 전달하고, 처리된 결과를 클라이언트에게 반환한다.
 * - 컨트롤러는 비즈니스 로직 없이 "입력 받고 → 전달하고 → 응답 주는" 역할만 담당한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    /**
     * UserService 의존성 주입
     * - @RequiredArgsConstructor 덕분에 자동으로 생성자 주입됨.
     * - final 필드여야만 주입 가능 → 안정성 확보.
     */
    private final UserService userService;

    /**
     * 유저 생성
     * - POST /users
     * - 요청 바디의 JSON을 UserCreateRequest DTO로 받아 Service로 전달한다.
     * - 생성된 유저 정보를 DTO 형태로 반환한다.
     */
    @PostMapping
    public UserCreateResponse create(@RequestBody UserCreateRequest requestData) {
        return userService.createUser(requestData);
    }

    /**
     * 모든 유저 조회
     * - GET /users
     * - DB에 저장된 전체 유저 리스트를 조회해 반환한다.
     */
    @GetMapping
    public List<UserResponse> findAllUsers() {
        return userService.checkAllUser();
    }

    /**
     * 특정 유저 단건 조회
     * - GET /users/{id}
     * - 경로 변수 id로 유저를 조회하여 DTO로 반환한다.
     */
    @GetMapping("/{id}")
    public UserResponse findUser(@PathVariable Long id) {
        return userService.checkOneUser(id);
    }

    /**
     * 유저 정보 수정
     * - PATCH /users/{id}
     * - 수정하고 싶은 필드만 담은 DTO를 전달하면 Service에서 엔티티를 갱신한다.
     */
    @PatchMapping("/{id}")
    public UpdateUserResponse update(@PathVariable Long id,
                                     @RequestBody UpdateUserRequest requestData) {
        return userService.updateUser(id, requestData);
    }

    /**
     * 유저 삭제
     * - DELETE /users/{id}
     * - 해당 ID의 유저를 삭제하고 간단한 메세지를 반환한다.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "해당 유저가 삭제 되었습니다.";
    }

    /**
     * 로그인
     * - POST /users/login
     * - 이메일/비밀번호를 DTO로 받고, 로그인 성공 시 세션 정보를 생성한다.
     * - 로그인한 유저의 기본 정보(LoginResponse)를 반환한다.
     */
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest requestData, HttpServletRequest httpRequest) {
        return userService.login(requestData, httpRequest);
    }

    /**
     * 로그아웃
     * - POST /users/logout
     * - 세션에 저장된 loginUser 값을 확인하고, 존재하면 세션을 무효화한다.
     */
     @PostMapping("/logout")
    public ResponseEntity<Void> logout(
             @SessionAttribute(name = "loginUser", required = false)
             SessionUser sessionUser, HttpSession session) {

         if(sessionUser == null) {
            return ResponseEntity.badRequest().build();
         }
             session.invalidate();
             return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
     }
}
