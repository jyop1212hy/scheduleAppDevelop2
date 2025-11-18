package com.scheduleappdevelop2.controller;

import com.scheduleappdevelop2.dto.user.UserCreateRequest;
import com.scheduleappdevelop2.dto.user.UserCreateResponse;
import com.scheduleappdevelop2.dto.user.UserResponse;
import com.scheduleappdevelop2.dto.user.UpdateUserRequest;
import com.scheduleappdevelop2.dto.user.UpdateUserResponse;
import com.scheduleappdevelop2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController
 * - /users 로 들어오는 모든 HTTP 요청을 처리하는 컨트롤러.
 * - 클라이언트 요청을 받고, 비즈니스 로직은 Service에게 넘긴다.
 * - 컨트롤러는 "데이터를 받고 → 전달하고 → 돌려주는" 역할만 담당.
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
     * - 요청 바디로 들어온 JSON 데이터를 UserCreateRequest 로 받고,
     *   Service에 전달하여 생성 작업을 수행한다.
     */
    @PostMapping
    public UserCreateResponse create(@RequestBody UserCreateRequest requestData) {
        return userService.createUser(requestData);
    }

    /**
     * 모든 유저 조회
     * - GET /users
     * - DB에 저장된 모든 유저 목록을 반환한다.
     */
    @GetMapping
    public List<UserResponse> findAllUsers() {
        return userService.checkAllUser();
    }

    /**
     * 특정 유저 조회
     * - GET /users/{id}
     * - URL의 {id} 값을 이용해 해당 유저 정보를 가져온다.
     */
    @GetMapping("/{id}")
    public UserResponse findUser(@PathVariable Long id) {
        return userService.checkOneUser(id);
    }

    /**
     * 특정 유저 수정
     * - PATCH /users/{id}
     * - 수정하고 싶은 필드만 JSON 으로 전달하면 됨.
     * - Service에서 엔터티를 수정하고 결과를 반환한다.
     */
    @PatchMapping("/{id}")
    public UpdateUserResponse update(@PathVariable Long id,
                                     @RequestBody UpdateUserRequest requestData) {
        return userService.updateUser(id, requestData);
    }

    /**
     * 특정 유저 삭제
     * - DELETE /users/{id}
     * - 삭제 완료 후 간단한 메시지를 반환한다.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "해당 유저가 삭제 되었습니다.";
    }
}
