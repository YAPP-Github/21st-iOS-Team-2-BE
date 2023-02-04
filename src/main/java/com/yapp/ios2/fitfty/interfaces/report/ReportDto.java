package com.yapp.ios2.fitfty.interfaces.report;

import com.yapp.ios2.fitfty.domain.report.Report.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ReportDto {
    @Getter
    @Builder
    @ToString
    public static class NewReport {
        private String reportUser;
        private String reportedUser;
        private String content;
        private ReportType type;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReport {
        private Boolean isConfirmed;
    }
}
