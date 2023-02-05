package com.yapp.ios2.fitfty.infrastructure.report;

import com.yapp.ios2.fitfty.domain.report.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportUser, Long> {

}
