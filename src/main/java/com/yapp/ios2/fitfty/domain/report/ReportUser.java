package com.yapp.ios2.fitfty.domain.report;

import com.querydsl.core.util.StringUtils;
import com.yapp.ios2.fitfty.global.enums.ReportType;
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
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`report_user`")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportUser {
    private static final String REPORT_PREFIX = "rpt_u_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reportToken;
    private String reportUserToken;
    private String reportUserEmail;
    private String reportedUserToken;
    private String reportedUserEmail;
    private Integer reportedCount;
    @Enumerated(EnumType.STRING)
    private ReportType type;
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean isConfirmed;

    @Builder
    public ReportUser(String reportUserToken, String reportedUserToken, String reportUserEmail, String reportedUserEmail, Integer count, ReportType type) {
        if (StringUtils.isNullOrEmpty(reportUserToken) || StringUtils.isNullOrEmpty(reportedUserToken)) {
            throw new InvalidParamException("Report.user");
        }
        if (StringUtils.isNullOrEmpty(reportUserEmail) || StringUtils.isNullOrEmpty(reportedUserEmail)) {
            throw new InvalidParamException("Report.userEmail");
        }
        if (count == null) {
            throw new InvalidParamException("Report.count");
        }
        if (type == null) {
            throw new InvalidParamException("Report.type");
        }
        this.reportToken = TokenGenerator.randomCharacterWithPrefix(REPORT_PREFIX);
        this.reportUserToken = reportUserToken;
        this.reportUserEmail = reportUserEmail;
        this.reportedUserToken = reportedUserToken;
        this.reportedUserEmail = reportedUserEmail;
        this.reportedCount = count;
        this.type = type;
        this.isConfirmed = false;
    }

    public void changeConfirmStatus(boolean status) {
        this.isConfirmed = status;
    }
}
