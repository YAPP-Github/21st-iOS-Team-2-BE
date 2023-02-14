package com.yapp.ios2.fitfty.domain.report;

import com.yapp.ios2.fitfty.domain.report.ReportInfo.BoardReportMain;
import com.yapp.ios2.fitfty.domain.report.ReportInfo.UserReportMain;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReportMapper {

    UserReportMain of(ReportUser reportUser);

    BoardReportMain of(ReportBoard reportBoard);
}
