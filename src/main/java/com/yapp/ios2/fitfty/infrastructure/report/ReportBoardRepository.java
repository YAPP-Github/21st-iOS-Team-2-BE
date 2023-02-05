package com.yapp.ios2.fitfty.infrastructure.report;

import com.yapp.ios2.fitfty.domain.report.ReportBoard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportBoardRepository extends JpaRepository<ReportBoard, Long> {

    Optional<ReportBoard> findByReportToken(String reportToken);

    Optional<ReportBoard> findFirstByReportedBoardTokenOrderByReportedCountDesc(String reportedBoard);
}
