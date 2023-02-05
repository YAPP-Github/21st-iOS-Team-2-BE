package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.global.enums.ReportType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ReportInfo {

    @Getter
    @Builder
    @ToString
    public static class UserMain {
        private final String reportToken;
        private final String reportUserToken;
        private final String reportUserEmail;
        private final String reportedUserToken;
        private final String reportedUserEmail;
        private final Integer reportedCount;
        private final ReportType type;
        private final Boolean isConfirmed;
    }

    @Getter
    @Builder
    @ToString
    public static class BoardMain {
        private final String reportToken;
        private final String reportUserToken;
        private final String reportUserEmail;
        private final String reportedBoardToken;
        private final String reportedBoardFilePath;
        private final Integer reportedCount;
        private final ReportType type;
        private final Boolean isConfirmed;
    }
}
