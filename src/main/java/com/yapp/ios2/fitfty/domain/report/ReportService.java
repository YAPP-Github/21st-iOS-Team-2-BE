package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.report.ReportCommand.MakeUserCommand;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.BoardReportMain;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.UserReportMain;
import java.util.List;


public interface ReportService {

    List<UserReportMain> getUserReports();
    List<BoardReportMain> getBoardReports();

    void addUserReport(MakeUserCommand command);
    void addBoardReport(ReportCommand.MakeBoardCommand command);

    void updateReport(ReportCommand.UpdateCommand command);

}
