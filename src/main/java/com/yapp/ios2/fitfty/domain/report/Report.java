package com.yapp.ios2.fitfty.domain.report;

import com.querydsl.core.util.StringUtils;
import com.yapp.ios2.fitfty.domain.user.User;
import com.yapp.ios2.fitfty.global.exception.InvalidParamException;
import com.yapp.ios2.fitfty.global.util.BooleanToYNConverter;
import com.yapp.ios2.fitfty.global.util.TokenGenerator;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "`report`")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private static final String REPORT_PREFIX = "report_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportToken;
//    @ManyToOne
//    @JoinColumn(name = "report_user")
    private String reportUserToken;
//    @ManyToOne
//    @JoinColumn(name = "reported_user")
    private String reportedUserToken;
    private Integer reportedCount;
    private String content;

    @Enumerated(EnumType.STRING)
    private ReportType type;
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isConfirmed;

    @Getter
    @RequiredArgsConstructor
    public enum ReportType {
        COMMERCIAL("영리목적/홍보성"),
        COPYRIGHT("저작권침해"),
        OBSCENE("음란성/선정성"),
        INSULT("욕설/인신공격"),
        PRIVACY("개인정보 노출"),
        REPEAT("같은내용 반복게시");

        private final String description;
    }

    @Builder
    public Report(String reportUserToken, String reportedUserToken, Integer count, ReportType type, String content) {
        if (StringUtils.isNullOrEmpty(reportUserToken) || StringUtils.isNullOrEmpty(reportedUserToken)) {
            throw new InvalidParamException("Report.user");
        }
        if (count == null) {
            throw new InvalidParamException("Report.count");
        }
        if (type == null) {
            throw new InvalidParamException("Report.type");
        }

        this.reportToken = TokenGenerator.randomCharacterWithPrefix(REPORT_PREFIX);
        this.reportUserToken = reportUserToken;
        this.reportedUserToken = reportedUserToken;
        this.reportedCount = count;
        this.type = type;
        this.content = null;
        this.isConfirmed = false;
    }

    public void confirmReport() {
        this.isConfirmed = true;
    }
}
