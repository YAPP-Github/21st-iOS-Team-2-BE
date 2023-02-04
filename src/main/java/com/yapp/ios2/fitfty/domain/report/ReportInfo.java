package com.yapp.ios2.fitfty.domain.report;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ReportInfo {
    @Getter
    @Builder
    @ToString
    public static class Main {

        private final String reportToken;
        private final String reportUser;
        private final String reportedUser;
        private final Integer reportedCount;
        private final String content;
        private final Report.ReportType type;
        private final boolean isConfirmed;
    }
}
