package com.yapp.ios2.fitfty.domain.report;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ReportInfo {

    @Getter
    @Builder
    @ToString
    public static class UserReportMain {
        private final String reportToken;
        private final String reportUserToken;
        private final String reportUserEmail;
        private final String reportedUserToken;
        private final String reportedUserEmail;
        private final Integer reportedCount;
        private final List<String> type;
        private final Boolean isConfirmed;
    }

    @Getter
    @Builder
    @ToString
    public static class BoardReportMain {
        private final String reportToken;
        private final String reportUserToken;
        private final String reportUserEmail;
        private final String reportedBoardToken;
        private final String reportedBoardFilePath;
        private final Integer reportedCount;
        private final List<String> type;
        private final Boolean isConfirmed;
    }
}
