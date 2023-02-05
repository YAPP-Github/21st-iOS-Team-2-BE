package com.yapp.ios2.fitfty.infrastructure.report;

import com.yapp.ios2.fitfty.domain.report.ReportBoard;
import com.yapp.ios2.fitfty.domain.report.ReportStore;
import com.yapp.ios2.fitfty.domain.report.ReportUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportStoreImpl implements ReportStore {
    private final ReportUserRepository reportUserRepository;
    private final ReportBoardRepository reportBoardRepository;

    @Override
    public void storeUserReport(ReportUser reportUser) {
        reportUserRepository.save(reportUser);
    }

    @Override
    public void storeBoardReport(ReportBoard reportBoard) {
        reportBoardRepository.save(reportBoard);
    }
}
