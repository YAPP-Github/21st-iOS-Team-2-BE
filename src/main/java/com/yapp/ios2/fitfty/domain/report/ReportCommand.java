package com.yapp.ios2.fitfty.domain.report;

import java.util.List;
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
        private String reportedUserToken;
        private List<String> type;
    }

    @Getter
    @Builder
    @ToString
    public static class MakeBoardCommand {
        private String reportedBoardToken;
        private List<String> type;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateCommand {
        private String reportToken;
        private Boolean isConfirmed;
        private String reportDomain;
    }
}
