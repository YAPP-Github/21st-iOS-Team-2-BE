package com.yapp.ios2.fitfty.infrastructure.report;

import com.yapp.ios2.fitfty.domain.report.ReportBoard;
import com.yapp.ios2.fitfty.domain.report.ReportUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportUserRepository extends JpaRepository<ReportUser, Long> {

    Optional<ReportUser> findByReportToken(String reportToken);

    Optional<ReportUser> findFirstByReportUserTokenAndReportedUserToken(String currentUserToken,
                                                                         String reportedUserToken);

    Optional<ReportUser> findFirstByReportedUserTokenOrderByReportedCountDesc(
            String reportedUserToken);
}
