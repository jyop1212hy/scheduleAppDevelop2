package com.scheduleappdevelop2.user.service;

import com.scheduleappdevelop2.global.exception.UnauthorizedUserAccessException;
import com.scheduleappdevelop2.global.exception.UserNotFoundException;
import com.scheduleappdevelop2.user.dto.login.LoginResponse;
import com.scheduleappdevelop2.user.dto.login.LoginRequest;
import com.scheduleappdevelop2.user.dto.sessionUser.SessionUser;
import com.scheduleappdevelop2.user.dto.updateUser.UpdateUserRequest;
import com.scheduleappdevelop2.user.dto.updateUser.UpdateUserResponse;
import com.scheduleappdevelop2.user.dto.userResponse.UserResponse;
import com.scheduleappdevelop2.user.dto.userCreate.UserCreateRequest;
import com.scheduleappdevelop2.user.dto.userCreate.UserCreateResponse;
import com.scheduleappdevelop2.user.entity.User;
import com.scheduleappdevelop2.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * UserService
 * - 유저 관련 모든 비즈니스 로직을 담당하는 계층.
 * - Controller는 단순 요청 처리만 하고, 핵심 로직은 모두 Service에서 수행한다.
 * - 유효성 검사, 중복 체크, DB 조회/수정/삭제 등의 실제 처리 흐름이 들어있다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /** 의존성 주입 */
    private final UserRepository userRepository;

    /**
     * 유저 생성
     * - 이름/이메일/비밀번호의 유효성을 검사하고 중복 여부도 확인한다.
     * - User.of()를 통해 엔티티를 생성하여 DB에 저장한다.
     * - 저장한 엔티티를 UserCreateResponse DTO로 변환해 반환한다.
     */
    @Transactional
    public UserCreateResponse createUser(UserCreateRequest requestData) {

        // 이름 공백 확인
        if(requestData.getName() == null || requestData.getName().isBlank()) {
            throw new IllegalArgumentException("이름에 공백이 포함 될수 없습니다.");
        }
        // 비밀번호 공백 확인
        if(requestData.getPassword() == null || requestData.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호에 공백이 포함 될수 없습니다.");
        }
        // 이메일 공백 체크
        if(requestData.getEmail() == null || requestData.getEmail().isBlank()) {
            throw new IllegalArgumentException("이메일에 공백이 포함 될수 없습니다.");
        }
        // 이메일 중복 검사
        if(userRepository.existsByEmail(requestData.getEmail())) {
            throw new IllegalArgumentException("중복된 이메일 입니다.");
        }

        // DTO → 엔터티 변환 (정적 팩토리 메서드 사용)
        User user = User.of(
                requestData.getName(),
                requestData.getEmail(),
                requestData.getPassword()
        );

        // 엔터티 저장
        User saved = userRepository.save(user);

        // 저장된 엔터티를 응답 DTO로 변환
        return UserCreateResponse.from(saved);
    }

    /**
     * 전체 유저 조회
     * - 모든 User 엔티티를 조회하고 UserResponse DTO 리스트로 변환하여 반환한다.
     * - 조회 전용이므로 readOnly = true 로 성능 최적화.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> checkAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    /**
     * 단일 유저 조회
     * - ID로 유저를 조회하고 존재하지 않으면 예외를 던진다.
     * - 조회된 엔티티를 DTO로 변환해 반환한다.
     */
    @Transactional(readOnly = true)
    public UserResponse checkOneUser(Long id, SessionUser sessionUser) {

        // 조회할 유저 조회
        User oneUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!oneUser.getId().equals(sessionUser.getId())) {
            throw new UnauthorizedUserAccessException(id);
        }

        return UserResponse.from(oneUser);
    }

    /**
     * 유저 정보 수정
     * - 수정 요청에서 전달된 값들 중 null이 아닌 값만 엔티티에 반영한다.
     * - 엔티티 수정 후 UpdateUserResponse DTO로 변환하여 반환한다.
     */
    @Transactional
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest requestData, SessionUser sessionUser) {

        // 엔티티 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        //로그인 세션 조회
        if(!user.getId().equals(sessionUser.getId())) {
            throw new UnauthorizedUserAccessException(sessionUser.getId());
        }

        // 요청 데이터 확인
        if(requestData.getName() == null || requestData.getEmail() == null) {
            throw new IllegalArgumentException("수정할 데이터가 없습니다.");
        }

        // 엔티티에게 값 변경 명령
        user.update(requestData.getName(), requestData.getEmail());

        // DTO 변환 후 반환
        return UpdateUserResponse.from(user);
    }

    /**
     * 유저 삭제
     * - 존재 여부 확인 후 삭제를 수행한다.
     * - 삭제 실패 시 예외를 발생시킨다.
     */
    @Transactional
    public void deleteUser(Long id, SessionUser sessionUser) {

        // 엔티티 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        //로그인 세션 조회
        if(!user.getId().equals(sessionUser.getId())) {
            throw new UnauthorizedUserAccessException(sessionUser.getId());
        }

        // 삭제
        userRepository.deleteById(sessionUser.getId());
    }

    /**
     * 로그인 기능
     * - 이메일/비밀번호 검증 후, DB 값과 비교해 로그인 여부를 결정한다.
     * - 로그인 성공 시 세션을 생성하고 로그인 정보를 저장한다.
     * - 이후 로그인한 사용자의 기본 정보를 LoginResponse로 반환한다.
     */
    @Transactional
    public LoginResponse login(LoginRequest requestData, HttpServletRequest httpRequest) {

        // 1) 이메일 입력값 검증
        // - 이메일이 null이거나 비어 있으면 아예 로그인 시도 자체가 불가능하므로 즉시 예외 처리.
        if(requestData.getEmail() == null || requestData.getEmail().isBlank()) {
            throw new IllegalArgumentException("해당 이메일이 존재하지 않습니다.");
        }

        // 2) 비밀번호 입력값 검증
        // - 비밀번호도 null/빈값이면 검증할 의미가 없으므로 바로 예외 처리.
        if(requestData.getPassword() == null || requestData.getPassword().isBlank()) {
            throw new IllegalArgumentException("해당 비밀번호가 존재하지 않습니다.");
        }

        // 3) 이메일을 기준으로 데이터베이스에서 유저 조회
        // - 일치하는 이메일이 없으면 Optional이 비어 있으므로 orElseThrow로 예외 처리.
        User user = userRepository.findByEmail(requestData.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 존재하지 않습니다."));

        // 4) 비밀번호 비교 (passwordEncoder를 아직 배우지 않았으므로 평문 비교)
        // - user.getPassword() : DB에 저장된 비밀번호
        // - requestData.getPassword() : 사용자가 입력한 비밀번호
        // - 두 값이 다르면 예외 발생 (로그인 실패)
        if(!user.getPassword().equals(requestData.getPassword())) {
                throw new IllegalArgumentException("해당 이메일이 존재하지 않습니다.");
        }

        // 5) 이메일과 비밀번호가 모두 일치했으므로 로그인 성공
        // - 세션 객체를 가져오고(true: 세션 없으면 새로 생성)
        // - 로그인한 사용자 정보를 세션에 저장 (로그인 유지 목적)
        HttpSession session = httpRequest.getSession(true);
        SessionUser sessionUser = new SessionUser(user.getId(), user.getEmail());
        session.setAttribute("loginUser", sessionUser);

        // 6) 컨트롤러로 반환할 로그인 정보 DTO 생성 후 반환
        return LoginResponse.from(user);
    }

}
