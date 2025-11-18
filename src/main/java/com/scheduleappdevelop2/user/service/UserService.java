package com.scheduleappdevelop2.user.service;

import com.scheduleappdevelop2.user.dto.updateUser.UpdateUserRequest;
import com.scheduleappdevelop2.user.dto.updateUser.UpdateUserResponse;
import com.scheduleappdevelop2.user.dto.userResponse.UserResponse;
import com.scheduleappdevelop2.user.dto.userCreate.UserCreateRequest;
import com.scheduleappdevelop2.user.dto.userCreate.UserCreateResponse;
import com.scheduleappdevelop2.user.entity.User;
import com.scheduleappdevelop2.user.repository.UserRepository;
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

        //이름/이메일 유무 확인
        if(requestData.getName() == null || requestData.getName().isBlank()) {
            throw new IllegalArgumentException("이름 작성은 필수입니다.");
        }
        if(requestData.getEmail() == null || requestData.getEmail().isBlank()) {
            throw new IllegalArgumentException("이메일 작성은 필수입니다.");
        }

        // DTO → 엔터티 변환 (정적 팩토리 메서드 사용)
        User user = User.of(
                requestData.getName(),
                requestData.getEmail()
        );

        // 엔터티 저장
        User saved= userRepository.save(user);

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
        if(requestData.getName() == null && requestData.getEmail() == null) {
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

}
