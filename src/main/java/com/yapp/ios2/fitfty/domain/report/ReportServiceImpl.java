package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.report.ReportCommand.NewCommand;
import com.yapp.ios2.fitfty.domain.report.ReportCommand.UpdateCommand;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.Main;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Override
    public List<Main> getAllReports() {
        return null;
    }

    @Override
    public void addReport(NewCommand command) {

    }

    @Override
    public Main updateReport(UpdateCommand command) {
        return null;
    }
}
