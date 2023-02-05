package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.report.ReportCommand.MakeBoardCommand;
import com.yapp.ios2.fitfty.domain.report.ReportCommand.MakeUserCommand;
import com.yapp.ios2.fitfty.domain.report.ReportCommand.UpdateCommand;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.BoardMain;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.UserMain;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportReader reportReader;
    private final ReportMapper reportMapper;

    @Override
    public List<UserMain> getUserReports() {
        return null;
    }

    @Override
    public List<BoardMain> getBoardReports() {
        return null;
    }

    @Override
    public void addUserReport(MakeUserCommand command) {

    }

    @Override
    public void addBoardReport(MakeBoardCommand command) {

    }

    @Override
    public void updateReport(UpdateCommand command) {

    }
}
