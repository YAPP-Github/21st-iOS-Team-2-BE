package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.report.ReportCommand.NewCommand;
import com.yapp.ios2.fitfty.domain.report.ReportCommand.UpdateCommand;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.Main;
import java.util.List;


public interface ReportService {

    List<Main> getAllReports();

    void addReport(NewCommand command);

    ReportInfo.Main updateReport(UpdateCommand command);
}
