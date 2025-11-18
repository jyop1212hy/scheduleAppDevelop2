package com.scheduleappdevelop2.schedule.service;

import com.scheduleappdevelop2.schedule.dto.UpdateSchedule.UpdateScheduleRequest;
import com.scheduleappdevelop2.schedule.dto.UpdateSchedule.UpdateScheduleResponse;
import com.scheduleappdevelop2.schedule.dto.createSchedule.CreateScheduleRequest;
import com.scheduleappdevelop2.schedule.dto.createSchedule.CreateScheduleResponse;
import com.scheduleappdevelop2.schedule.dto.checkSchedule.ScheduleResponse;
import com.scheduleappdevelop2.schedule.entity.Schedule;
import com.scheduleappdevelop2.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 생성
     * - 입력값이 비어있지 않은지 먼저 확인한다.
     * - 문제가 없으면 엔티티 객체를 만든 뒤 DB에 저장한다.
     * - 저장된 엔티티에서 필요한 정보만 꺼내 DTO로 변환하여 반환한다.
     */
    @Transactional
    public CreateScheduleResponse createSchedule(CreateScheduleRequest requestData) {

        // 1) 요청 데이터 검증 (비어있는 값이 있으면 바로 예외 발생)
        if (requestData.getTitle() == null || requestData.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (requestData.getContent() == null || requestData.getContent().isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
        if (requestData.getWriter() == null || requestData.getWriter().isBlank()) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }

        // 2) 엔티티에 데이터 넣기 (of() 사용)
        Schedule schedule = Schedule.of(
                requestData.getTitle(),
                requestData.getContent(),
                requestData.getWriter()
        );

        // 3) 엔터티에 넣은 내용 DB에 저장하기
        Schedule saved = scheduleRepository.save(schedule);

        // 4) 저장된 엔티티를 DTO로 변환해서 응답
        return CreateScheduleResponse.from(saved);
    }

    /**
     * 전체 일정 조회
     * - DB에서 모든 일정 엔티티를 꺼낸다.
     * - stream()을 이용해 엔티티 하나씩 DTO로 바꾼다.
     * - DTO로 바뀐 모든 결과를 리스트 형태로 반환한다.
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
     * - id값을 이용해 DB에서 해당 엔티티를 찾는다.
     * - 엔티티가 존재하지 않으면 예외를 던진다.
     * - 찾은 엔티티를 DTO로 변환하여 반환한다.
     */
    @Transactional(readOnly = true)
    public ScheduleResponse checkOneSchedule(Long id) {

        // 1) 데이터베이스 PK와 비교 후 맞으면 엔터티 형태로 가져옴
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("입력한 " + id + "의 일정이 없습니다."));

        // 2) DTO로 전달
        return ScheduleResponse.from(schedule);
    }

    /**
     * 일정 수정
     * - 먼저 id로 엔티티를 조회한다.
     * - 존재하지 않으면 예외를 발생시킨다.
     * - 요청으로 들어온 데이터가 null이 아닌 값만 골라 변경한다.
     *   (실제 변경 작업은 엔티티의 update() 메서드가 담당)
     * - 트랜잭션 끝나는 시점에 JPA의 Dirty Checking으로 자동 업데이트된다.
     */
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long id, UpdateScheduleRequest requestData) {
        if(requestData.getTitle() == null || requestData.getContent() == null || requestData.getWriter() == null) {
            // 데이터베이스 PK와 비교 후 맞으면 엔터티 형태로 가져옴
            throw new IllegalArgumentException("입력한 " + id + "의 일정이 없습니다.");
        }

        // 엔티티에게 직접 업데이트 일을 시킨다 (도메인 주도 설계 느낌)
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " 의 유저를 찾을 수 없습니다."));
        schedule.update(requestData.getTitle(), requestData.getContent());


        // 변경된 엔티티를 DTO로 만들어 반환
        return UpdateScheduleResponse.from(schedule);
    }

    /**
     * 일정 삭제
     * - 삭제하려는 일정이 실제 DB에 존재하는지 먼저 확인한다.
     * - 존재한다면 deleteById()를 통해 DB에서 삭제한다.
     */
    @Transactional
    public void deleteSchedule(Long id) {

        //데이터베이스 PK와 비교 후 맞으면 엔터티 형태로 가져옴
        if(!scheduleRepository.existsById(id)) {
                throw new IllegalArgumentException("입력한 " + id + "의 일정이 없습니다.");
        }

        //데이터베이스에서 삭제 하기
        scheduleRepository.deleteById(id);
    }

}
