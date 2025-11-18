package com.scheduleappdevelop2.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    public final UserService userService;

    //생성
    @PostMapping
    public UserCreateResponse create(UserCreateRequset requestData) {
        return userService.createUser(requestData);
    }

    //전체조회
    @GetMapping
    public List<UserResponse> checkAll() {
        return userService.checkAllUser();
    }

    //단건조회
    @GetMapping("/{id}")
    public UserResponse checkOne(@PathVariable Long id) {
        return userService.checkOneUser(id);
    }

    //수정
    @PatchMapping("/{id}")
    public UpdateUserResponse update(@PathVariable Long id, @RequestBody UpdateUserRequset requestData) {
        return userService.checkOneUser(id, requestData);
    }

    //삭제
    @DeleteMapping("/{id}")
    public String deldte(@PathVariable Long id) {
        scheduleService.deleteUser(id);
        return "해당 유저가 삭제 되었습니다.";
    }
}
