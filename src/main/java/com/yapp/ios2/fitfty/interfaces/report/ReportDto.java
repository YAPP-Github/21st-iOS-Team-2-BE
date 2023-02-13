package com.yapp.ios2.fitfty.interfaces.report;

import com.yapp.ios2.fitfty.domain.report.ReportCommand.UpdateCommand;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ReportDto {
    @Getter
    @Builder
    @ToString
    public static class MakeUserReport {
        private String reportedUserToken;
        private List<String> type;
    }

    @Getter
    @Builder
    @ToString
    public static class MakeBoardReport {
        private String reportedBoardToken;
        private List<String> type;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReport {
        private String reportToken;
        private Boolean isConfirmed;
        private String reportDomain;
    }

    public static UpdateCommand toUpdateCommand(UpdateReport updateReport, String reportDomain) {
        return UpdateCommand.builder()
                .reportToken(updateReport.reportToken)
                .isConfirmed(updateReport.isConfirmed)
                .reportDomain(reportDomain)
                .build();
    }
}
