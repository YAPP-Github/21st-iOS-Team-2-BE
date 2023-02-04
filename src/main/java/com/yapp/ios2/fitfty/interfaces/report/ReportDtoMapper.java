package com.yapp.ios2.fitfty.interfaces.report;

import com.yapp.ios2.fitfty.domain.report.ReportCommand;
import com.yapp.ios2.fitfty.interfaces.report.ReportDto.NewReport;
import com.yapp.ios2.fitfty.interfaces.report.ReportDto.UpdateReport;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReportDtoMapper {

    ReportCommand.NewCommand of(NewReport request);
    ReportCommand.UpdateCommand of(UpdateReport request);
}
