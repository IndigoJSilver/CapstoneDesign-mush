package com.project.capstonedesign.domain.report;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private Long reportedId; // 신고 당한 유저

    private Long reporterId; // 신고자

    private Type type;

    private String code;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Report(final Long reportedId, final Long reporterId, final Type type, final String code) {
        this.reportedId = reportedId;
        this.reporterId = reporterId;
        this.type = type;
        this.code = code;
    }
}
