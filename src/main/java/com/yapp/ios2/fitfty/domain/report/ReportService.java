package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.report.ReportCommand.MakeUserCommand;
import java.util.List;


public interface ReportService {

    List<ReportInfo.UserMain> getUserReports();
    List<ReportInfo.BoardMain> getBoardReports();

    void addUserReport(MakeUserCommand command);
    void addBoardReport(ReportCommand.MakeBoardCommand command);

    void updateReport(ReportCommand.UpdateCommand command);

}
