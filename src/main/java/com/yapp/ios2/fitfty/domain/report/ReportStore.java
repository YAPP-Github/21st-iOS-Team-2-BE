package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.user.User;

public interface ReportStore {

    void storeUserReport(ReportUser reportUser);

    void storeBoardReport(ReportBoard reportBoard);

}
