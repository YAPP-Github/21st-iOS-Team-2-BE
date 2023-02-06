package com.yapp.ios2.fitfty.domain.report;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReportMapper {

    ReportInfo.UserMain of(ReportUser reportUser);

    ReportInfo.BoardMain of(ReportBoard reportBoard);
}
