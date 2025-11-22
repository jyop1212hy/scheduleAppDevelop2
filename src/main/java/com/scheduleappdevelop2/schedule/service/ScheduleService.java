package com.scheduleappdevelop2.schedule.service;

import com.scheduleappdevelop2.schedule.dto.UpdateSchedule.UpdateScheduleRequest;
import com.scheduleappdevelop2.schedule.dto.UpdateSchedule.UpdateScheduleResponse;
import com.scheduleappdevelop2.schedule.dto.createSchedule.CreateScheduleRequest;
import com.scheduleappdevelop2.schedule.dto.createSchedule.CreateScheduleResponse;
import com.scheduleappdevelop2.schedule.dto.checkSchedule.ScheduleResponse;
import com.scheduleappdevelop2.schedule.entity.Schedule;
import com.scheduleappdevelop2.schedule.repository.ScheduleRepository;
import com.scheduleappdevelop2.user.dto.sessionUser.SessionUser;
import com.scheduleappdevelop2.user.entity.User;
import com.scheduleappdevelop2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.scheduleappdevelop2.global.exception.ErrorMessage.*;

/**
 * ScheduleService
 * - 일정 생성/조회/수정/삭제에 대한 핵심 비즈니스 로직을 처리한다.
 * - 일정은 반드시 '작성자(User)'와 연결되며, 수정/삭제 시 작성자 본인인지 검증한다.
 * - 컨트롤러와 리포지토리 사이에서 도메인 로직을 담당하는 계층이다.
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정 생성
     * - 입력 데이터(title, content)를 검증한 뒤 DB에 새 일정(Schedule)을 저장한다.
     * - sessionUser.id로 실제 User 엔티티를 찾아 일정 작성자로 연결한다.
     * - 저장된 엔티티를 CreateScheduleResponse DTO로 변환해 반환한다.
     */
    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest requestData, SessionUser sessionUser) {

        // 요청 데이터 검증 (비어있는 값이 있으면 바로 예외 발생)
        if (requestData.getTitle() == null || requestData.getTitle().isBlank()) {
            throw new CustomException(INVALID_INPUT);
        }
        if (requestData.getContent() == null || requestData.getContent().isBlank()) {
            throw new CustomException(INVALID_INPUT);
        }

        // 로그인한 유저 엔티티 조회
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(()-> new CustomException(DATA_NOT_FOUND));

        // 엔티티에 데이터 넣기 (of() 사용)
        Schedule schedule = Schedule.of(
                requestData.getTitle(),
                requestData.getContent(),
                user
        );

        // 엔터티에 넣은 내용 DB에 저장하기
        Schedule saved = scheduleRepository.save(schedule);

        // 저장된 엔티티를 DTO로 변환해서 응답
        return CreateScheduleResponse.from(saved);
    }

    /**
     * 전체 일정 조회
     * - 모든 Schedule 엔티티를 가져와 ScheduleResponse DTO 리스트로 변환하여 반환한다.
     * - 단순 조회 작업이므로 readOnly 속성을 사용해 성능 최적화한다.
     */
    @Transactional(readOnly = true)
    public List<ScheduleResponse> checkAllSchedules() {

        // 1) 데이터에 있는 자료 찾기
        return scheduleRepository.findAll()
                .stream() // 2) 하나씩 꺼낸다.
                .map(ScheduleResponse::from) // 3) 다른형태로 변환 한다.
                .toList(); // 4) 스트림을 다시 리스트로 변환
    }

    /**
     * 단일 일정 조회
     * - 일정 id로 특정 엔티티를 조회한다.
     * - 일정이 존재하지 않으면 예외를 던진다.
     * - ScheduleResponse DTO로 변환하여 반환한다.
     */
    @Transactional(readOnly = true)
    public ScheduleResponse checkOneSchedule(Long id,  SessionUser sessionUser) {

        // 조회할 일정 조회
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SCHEDULE));

        // 로그인 유저 조회
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 권한 체크 (본인것만 조회 가능)
        if(!schedule.getUser().getId().equals(user.getId())) {
            throw new CustomException(NOT_VALID_OWNER);
        }

        // DTO로 전달
        return ScheduleResponse.from(schedule);
    }

    /**
     * 일정 수정
     * - 수정 대상 일정(id)을 조회한다.
     * - sessionUser.id로 로그인한 유저를 조회한다.
     * - 일정 작성자와 로그인 유저가 같은지 검증한다(본인만 수정 가능).
     * - 엔터티 내부 update() 호출하여 필요한 필드만 변경한다.
     * - 수정된 엔티티를 DTO(UpdateScheduleResponse)로 반환한다.
     */
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long id, UpdateScheduleRequest requestData,SessionUser sessionUser) {

        // 수정할 일정 조회
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SCHEDULE));

        // 로그인 유저 조회
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 권한 체크 (본인만 수정 가능)
        if(!schedule.getUser().getId().equals(user.getId())) {
            throw new CustomException(NOT_VALID_OWNER);
        } else {
            schedule.update(requestData.getTitle(), requestData.getContent());
        }

        // 변경된 엔티티를 DTO로 만들어 반환
        return UpdateScheduleResponse.from(schedule);
    }

    /**
     * 일정 삭제
     * - 삭제 대상 엔티티(id)를 조회한다.
     * - sessionUser.id로 로그인 유저를 조회한다.
     * - 본인 일정인지 검증하여 권한을 확인한다.
     * - 검증 통과 시 DB에서 해당 일정 엔티티를 삭제한다.
     */
    @Transactional
    public void deleteSchedule(Long id, SessionUser sessionUser) {

        //데이터베이스 PK와 비교 후 맞으면 스케줄 일정 가저옴
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SCHEDULE));

        // 로그인한 유저 조회
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 권한 체크 (본인만 삭제 가능)
        if(!schedule.getUser().getId().equals(user.getId())) {
            throw new CustomException(NOT_VALID_OWNER);
        }

        //데이터베이스에서 삭제 하기
        scheduleRepository.delete(schedule);
    }

}