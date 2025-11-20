package com.scheduleappdevelop2.comment.entity;

import com.scheduleappdevelop2.global.baseTimeEntity.BaseTimeEntity;
import com.scheduleappdevelop2.schedule.entity.Schedule;
import com.scheduleappdevelop2.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    private Comment(String content, Schedule schedule, User user) {
        this.content = content;
        this.schedule = schedule;
        this.user = user;
    }

    public static Comment of(String content, Schedule schedule, User user) {
        return Comment.builder()
                .content(content)
                .schedule(schedule)
                .user(user)
                .build();
    }

    public void update(String content) {
        if (content != null) this.content = content;
    }

    public Long getId() { return id; }
    public String getContent() { return content; }
    public Schedule getSchedule() { return schedule; }
    public User getUser() { return user; }
}
