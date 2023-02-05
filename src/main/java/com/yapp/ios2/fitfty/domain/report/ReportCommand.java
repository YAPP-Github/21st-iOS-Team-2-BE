package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.global.enums.ReportType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
public class ReportCommand {

    @Getter
    @Builder
    @ToString
    public static class MakeUserCommand {
        private String reportUserToken;
        private String reportedUserToken;
        private ReportType type;
    }

    @Getter
    @Builder
    @ToString
    public static class MakeBoardCommand {
        private String reportUserToken;
        private String reportedBoardToken;
        private ReportType type;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateCommand {
        private Boolean isConfirmed;
        private String type;
    }
}
