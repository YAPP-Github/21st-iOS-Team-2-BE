package com.yapp.ios2.fitfty.domain.report;

import java.util.List;
import java.util.Optional;

public interface ReportReader {

    List<ReportUser> findAllUserReport();

    List<ReportBoard> findAllBoardReport();

    Optional<ReportUser> findFirstByReportedUserTokenOrderByReportedCount(String reportedUserToken);

    Optional<ReportBoard> findFirstByReportedBoardTokenOrderByReportedCount(
            String reportedBoardToken);

    ReportUser findUserReportByReportToken(String reportToken);

    ReportBoard findBoardReportByReportToken(String reportToken);

    boolean findFirstByReportUserTokenAndReportedBoardToken(String userToken, String boardToken);

    boolean findFirstByReportUserTokenAndReportedUserToken(String currentUserToken,
                                                           String reportedUserToken);
}
