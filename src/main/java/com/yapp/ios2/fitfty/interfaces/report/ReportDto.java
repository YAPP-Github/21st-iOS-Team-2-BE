package com.yapp.ios2.fitfty.interfaces.report;

import com.yapp.ios2.fitfty.domain.report.ReportCommand.UpdateCommand;
import com.yapp.ios2.fitfty.global.enums.ReportType;
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
        private String reportUserToken;
        private String reportedUserToken;
        private ReportType type;
    }

    @Getter
    @Builder
    @ToString
    public static class MakeBoardReport {
        private String reportUserToken;
        private String reportedBoardToken;
        private ReportType type;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateReport {
        private Boolean isConfirmed;
    }

    public static UpdateCommand toUpdateCommand(UpdateReport updateReport, String type) {
        return UpdateCommand.builder()
                .isConfirmed(updateReport.isConfirmed)
                .type(type)
                .build();
    }
}
