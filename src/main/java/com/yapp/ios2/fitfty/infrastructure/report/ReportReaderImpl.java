package com.yapp.ios2.fitfty.infrastructure.report;

import com.yapp.ios2.fitfty.domain.report.ReportUser;
import com.yapp.ios2.fitfty.domain.report.ReportReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportReaderImpl implements ReportReader {

    private final ReportRepository reportRepository;

    @Override
    public List<ReportUser> findAll() {
        return reportRepository.findAll();
    }
}
