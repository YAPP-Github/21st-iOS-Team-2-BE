package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.board.BoardReader;
import com.yapp.ios2.fitfty.domain.report.ReportCommand.MakeBoardCommand;
import com.yapp.ios2.fitfty.domain.report.ReportCommand.MakeUserCommand;
import com.yapp.ios2.fitfty.domain.report.ReportCommand.UpdateCommand;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.BoardMain;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.UserMain;
import com.yapp.ios2.fitfty.domain.user.UserReader;
import com.yapp.ios2.fitfty.global.exception.IllegalStatusException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportReader reportReader;
    private final UserReader userReader;
    private final BoardReader boardReader;
    private final ReportStore reportStore;
    private final ReportMapper reportMapper;

    @Override
    @Transactional
    public List<UserMain> getUserReports() {
        var reports = reportReader.findAllUserReport();
        return reports.stream()
                .map(reportMapper::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<BoardMain> getBoardReports() {
        var reports = reportReader.findAllBoardReport();
        return reports.stream()
                .map(reportMapper::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addUserReport(MakeUserCommand command) {
        var reportUser = userReader.findFirstByUserToken(command.getReportUserToken());
        var reportedUser = userReader.findFirstByUserToken(command.getReportedUserToken());
        var count = 0;
        var recentReport = reportReader.findFirstByReportedUserTokenOrderByReportedCount(
                command.getReportedUserToken());
        if (recentReport.isPresent()) {
            count = recentReport.get()
                    .getReportedCount() + 1;
        }

        var initUserReport = new ReportUser(command.getReportUserToken(),
                                            command.getReportedUserToken(), reportUser.getEmail(),
                                            reportedUser.getEmail(), count, command.getType());

        reportStore.storeUserReport(initUserReport);
    }

    @Override
    @Transactional
    public void addBoardReport(MakeBoardCommand command) {
        var reportUser = userReader.findFirstByUserToken(command.getReportUserToken());
        var reportedBoard = boardReader.getBoard(command.getReportedBoardToken());
        var count = 0;
        var recentReport = reportReader.findFirstByReportedBoardTokenOrderByReportedCount(
                command.getReportedBoardToken());
        if (recentReport.isPresent()) {
            count = recentReport.get()
                    .getReportedCount() + 1;
        }

        var initBoardReport = new ReportBoard(command.getReportUserToken(), reportUser.getEmail(),
                                              command.getReportedBoardToken(),
                                              reportedBoard.getPicture().getFilePath(), count, command.getType());
        reportStore.storeBoardReport(initBoardReport);
    }

    @Override
    @Transactional
    public void updateReport(UpdateCommand command) {
        if (command.getType() == "USER") {
            var report = reportReader.findUserReportByReportToken(command.getReportToken());
            report.changeConfirmStatus(command.getIsConfirmed());
        } else if (command.getType() == "BOARD") {
            var report = reportReader.findBoardReportByReportToken(command.getReportToken());
            report.changeConfirmStatus(command.getIsConfirmed());
        } else {
            throw new IllegalStatusException("Report 상태값 이상");
        }
    }
}
