package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.report.Report.ReportType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
public class ReportCommand {

    @Getter
    @Builder
    @ToString
    public static class NewCommand {
        private String reportUser;
        private String reportedUser;
        private String content;
        private ReportType type;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateCommand {
        private Boolean isConfirmed;
    }
}
