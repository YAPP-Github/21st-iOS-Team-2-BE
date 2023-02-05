package com.yapp.ios2.fitfty.infrastructure.report;

import com.yapp.ios2.fitfty.domain.report.ReportBoard;
import com.yapp.ios2.fitfty.domain.report.ReportUser;
import com.yapp.ios2.fitfty.domain.report.ReportReader;
import com.yapp.ios2.fitfty.global.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportReaderImpl implements ReportReader {

    private final ReportUserRepository reportUserRepository;
    private final ReportBoardRepository reportBoardRepository;

    @Override
    public List<ReportUser> findAllUserReport() {
        return reportUserRepository.findAll();
    }

    @Override
    public List<ReportBoard> findAllBoardReport() {
        return reportBoardRepository.findAll();
    }

    @Override
    public Optional<ReportUser> findFirstByReportedUserTokenOrderByReportedCount(String reportedUserToken) {
        return reportUserRepository.findFirstByReportedUserTokenOrderByReportedCountDesc(reportedUserToken);
    }

    @Override
    public Optional<ReportBoard> findFirstByReportedBoardTokenOrderByReportedCount(
            String reportedBoardToken) {
        return reportBoardRepository.findFirstByReportedBoardTokenOrderByReportedCountDesc(reportedBoardToken);
    }

    @Override
    public ReportUser findUserReportByReportToken(String reportToken) {
        return reportUserRepository.findByReportToken(reportToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ReportBoard findBoardReportByReportToken(String reportToken) {
        return reportBoardRepository.findByReportToken(reportToken)
                .orElseThrow(EntityNotFoundException::new);
    }
}
