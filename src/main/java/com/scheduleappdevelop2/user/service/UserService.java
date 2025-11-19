package com.scheduleappdevelop2.user.service;

import com.scheduleappdevelop2.user.dto.login.LoginResponse;
import com.scheduleappdevelop2.user.dto.login.LoginRequest;
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


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 생성
     * - 요청으로 들어온 이름/이메일이 비어있는지 검증한다.
     * - 문제가 없으면 User 엔티티를 생성하고 DB에 저장한다.
     * - 저장된 엔티티를 응답 DTO로 변환해 반환한다.
     */
    @Transactional
    public UserCreateResponse createUser(UserCreateRequest requestData) {

        //이름 공백 확인
        if(requestData.getName() == null || requestData.getName().isBlank()) {
            throw new IllegalArgumentException("이름에 공백이 포함 될수 없습니다.");
        }
        //비밀번호 공백 확인
        if(requestData.getPassword() == null || requestData.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호에 공백이 포함 될수 없습니다.");
        }
        //이메일 공백 체크
        if(requestData.getEmail() == null || requestData.getEmail().isBlank()) {
            throw new IllegalArgumentException("이메일에 공백이 포함 될수 없습니다.");
        }
        //이메일 중복
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
     * - 모든 유저 엔티티를 가져와 DTO 리스트로 변환한다.
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
     * - ID로 유저를 찾고 없으면 예외 발생
     */
    @Transactional(readOnly = true)
    public UserResponse checkOneUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return UserResponse.from(user);
    }

    /**
     * 유저 정보 수정
     * - 전달된 필드 중 null이 아닌 값만 엔터티에 반영
     * - Dirty Checking 으로 트랜잭션 종료 시 자동 업데이트
     */
    @Transactional
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest requestData) {
        if(requestData.getName() == null || requestData.getEmail() == null) {
            throw new IllegalArgumentException("수정할 데이터가 없습니다.");
        }

        // 엔터티에게 수정 명령
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " 의 유저를 찾을 수 없습니다."));
        user.update(requestData.getName(), requestData.getEmail());

        // 수정된 객체를 응답DTO로 변환
        return UpdateUserResponse.from(user);
    }

    /**
     * 유저 삭제
     * - 존재 여부 확인 후 삭제
     */
    @Transactional
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException(id + " 의 유저를 찾을 수 없습니다.");
        }
        userRepository.deleteById(id);
    }

    /**
     * 로그인 기능
     * 1. 클라이언트가 이메일 / 비밀번호를 보낸다.
     * 2. 서버는 해당 이메일로 DB에서 유저를 조회한다.
     * 3. 조회된 유저의 비밀번호와 입력한 비밀번호를 비교한다.
     * 4. 둘 다 맞으면 세션을 생성해 로그인 상태를 유지한다.
     * 5. 이후 필요한 로그인 정보(LoginResponse)를 반환한다.
     */
    @Transactional
    public LoginResponse login(LoginRequest requestData, HttpServletRequest request) {

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
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);

        // 6) 컨트롤러로 반환할 로그인 정보 DTO 생성 후 반환
        return LoginResponse.from(user);
    }


}
