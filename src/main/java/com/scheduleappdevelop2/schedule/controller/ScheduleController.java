package com.scheduleappdevelop2.schedule.controller;

import com.scheduleappdevelop2.global.exception.CustomException;
import com.scheduleappdevelop2.global.exception.ErrorMessage;
import com.scheduleappdevelop2.schedule.dto.UpdateSchedule.UpdateScheduleRequest;
import com.scheduleappdevelop2.schedule.dto.UpdateSchedule.UpdateScheduleResponse;
import com.scheduleappdevelop2.schedule.dto.createSchedule.CreateScheduleResponse;
import com.scheduleappdevelop2.schedule.dto.createSchedule.CreateScheduleRequest;
import com.scheduleappdevelop2.schedule.dto.checkSchedule.ScheduleResponse;
import com.scheduleappdevelop2.schedule.service.ScheduleService;
import com.scheduleappdevelop2.user.dto.sessionUser.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.scheduleappdevelop2.global.exception.ErrorMessage.NOT_AUTHENTICATED;

/**
 * ScheduleController
 * - /schedules 관련 요청을 처리하는 REST 컨트롤러.
 * - 일정 생성, 조회, 수정, 삭제에 대한 엔드포인트를 제공한다.
 * - 세션에서 로그인 유저(SessionUser)를 가져와 권한 기반 요청 처리가 가능하도록 구성한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 생성
     * - 요청 JSON을 DTO(CreateScheduleRequest)로 받아 처리한다.
     * - 세션에서 로그인 유저의 정보를 조회하여 일정 작성자로 연결한다.
     * - 생성된 일정 정보를 DTO(CreateScheduleResponse)로 반환한다.
     */
    @PostMapping
    public CreateScheduleResponse create(@RequestBody CreateScheduleRequest requestData, HttpServletRequest sessionRequest){

        // 세션에서 로그인 유저 정보 획득
        HttpSession session = sessionRequest.getSession(false);
        if (session == null) throw new CustomException(NOT_AUTHENTICATED);

        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) throw new CustomException(ErrorMessage.NOT_AUTHENTICATED);

        // 서비스에 일정 생성 요청
        return scheduleService.createSchedule(requestData, sessionUser);
    }

    /**
     * 전체 일정 조회
     * - DB에 존재하는 모든 일정을 조회하여 리스트로 반환한다.
     * - 조회는 로그인 여부와 상관 없이 가능하도록 설정되어 있다.
     */
    @GetMapping
    public List<ScheduleResponse> checkAll() {

        // 서비스에 일정 전체 조회 요청
        return scheduleService.checkAllSchedules();
    }

    /**
     * 단일 일정 조회
     * - URL 경로의 id를 사용하여 특정 일정을 조회한다.
     * - 단일 조회는 id를 사용함으로 로그인 상태에서만 조회가능 하도록 했다.
     * - 조회된 일정 정보를 DTO로 변환하여 반환한다.
     */
    @GetMapping("/{id}")
    public ScheduleResponse checkOne(@PathVariable Long id, HttpServletRequest sessionRequest) {

        // 세션에서 로그인 유저 정보 획득
        HttpSession session = sessionRequest.getSession(false);
        if (session == null) throw new CustomException();

        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) throw new CustomException();

        // 서비스에 일정 조회 요청
        return scheduleService.checkOneSchedule(id, sessionUser);
    }

    /**
     * 일정 수정
     * - 수정할 내용(JSON)을 DTO(UpdateScheduleRequest)로 받아 처리한다.
     * - 세션에서 로그인 유저 정보를 조회하여 해당 사용자가 작성자인지 검증한다.
     * - 검증 통과 후 일정 수정 결과를 DTO로 반환한다.
     */
    @PatchMapping("/{id}")
    public UpdateScheduleResponse update(@PathVariable Long id, HttpServletRequest sessionRequest,
                                         @RequestBody UpdateScheduleRequest requestData) {

        // 세션에서 로그인 유저 정보 획득
        HttpSession session = sessionRequest.getSession(false);
        if (session == null) throw new CustomException(NOT_AUTHENTICATED);

        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) throw new CustomException(NOT_AUTHENTICATED);

        // 서비스에 일정 조회 요청
        return scheduleService.updateSchedule(id, requestData, sessionUser);
    }

    /**
     * 일정 삭제
     * - URL의 id로 삭제 대상 일정을 조회한 후,
     *   세션의 로그인 유저와 작성자를 비교하여 권한을 체크한다.
     *
     * - 삭제 성공 시 간단한 응답 메시지를 반환한다.
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest sessionRequest) {

        // 세션에서 로그인 유저 정보 획득
        HttpSession session = sessionRequest.getSession(false);
        if (session == null) throw new CustomException(NOT_AUTHENTICATED);

        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if (sessionUser == null) throw new CustomException(NOT_AUTHENTICATED);

        scheduleService.deleteSchedule(id, sessionUser);

        // 서비스에 일정 조회 요청
        return "해당 일정이 삭제 되었습니다.";
    }
}
