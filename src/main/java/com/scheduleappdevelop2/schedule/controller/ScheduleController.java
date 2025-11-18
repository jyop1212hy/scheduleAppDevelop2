package com.scheduleappdevelop2.schedule.controller;

import com.scheduleappdevelop2.schedule.dto.UpdateSchedule.UpdateScheduleRequest;
import com.scheduleappdevelop2.schedule.dto.UpdateSchedule.UpdateScheduleResponse;
import com.scheduleappdevelop2.schedule.dto.createSchedule.CreateScheduleResponse;
import com.scheduleappdevelop2.schedule.dto.createSchedule.CreateScheduleRequest;
import com.scheduleappdevelop2.schedule.dto.checkSchedule.ScheduleResponse;
import com.scheduleappdevelop2.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    //@RequiredArgsConstructor로 자동생성자를 통해 초기값 설정 가능
    private final ScheduleService scheduleService;

    /**
     * 일정 생성 요청 처리
     * - 클라이언트가 보낸 JSON 데이터를 DTO로 받는다.
     * - Service에 전달해 생성 작업을 수행한다.
     * - 생성된 일정 정보를 그대로 응답한다.
     */
    @PostMapping
    public CreateScheduleResponse create(@RequestBody CreateScheduleRequest requestData){
        return scheduleService.createSchedule(requestData);
    }

    /**
     * 전체 일정 조회 요청 처리
     * - 모든 일정 정보를 조회하여 리스트 형태로 반환한다.
     */
    @GetMapping
    public List<ScheduleResponse> checkAll() {
        return scheduleService.checkAllSchedules();
    }

    /**
     * 단건 일정 조회 요청 처리
     * - URL 경로에서 받은 id를 기반으로 특정 일정 정보를 반환한다.
     */
    @GetMapping("/{id}")
    public ScheduleResponse checkOne(@PathVariable Long id) {
        return scheduleService.checkOneSchedule(id);
    }

    /**
     * 일정 수정 요청 처리
     * - 수정하고 싶은 필드만 받아서 전달한다.
     * - Service에서 엔티티 수정 후 변경된 결과를 반환한다.
     */
    @PatchMapping("/{id}")
    public UpdateScheduleResponse update(@PathVariable Long id, @RequestBody UpdateScheduleRequest requestData) {
        return scheduleService.updateSchedule(id, requestData);
    }

    /**
     * 일정 삭제 요청 처리
     * - 삭제 성공 시 간단한 메세지를 반환한다.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return "해당 일정이 삭제 되었습니다.";
    }
}
