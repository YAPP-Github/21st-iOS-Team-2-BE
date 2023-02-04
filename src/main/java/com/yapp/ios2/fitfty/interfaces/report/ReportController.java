package com.yapp.ios2.fitfty.interfaces.report;

import static com.yapp.ios2.fitfty.global.util.Constants.API_PREFIX;

import com.yapp.ios2.fitfty.domain.report.ReportService;
import com.yapp.ios2.fitfty.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(API_PREFIX + "/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ReportDtoMapper reportDtoMapper;

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse getReports() {
        var response = reportService.getAllReports();
        return CommonResponse.success(response);
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse makeReport(@RequestBody ReportDto.NewReport request) {
        var command = reportDtoMapper.of(request);
        reportService.addReport(command);
        return CommonResponse.success("OK");
    }

    @PutMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse updateReport(@RequestBody ReportDto.UpdateReport request) {
        var command = reportDtoMapper.of(request);
        var response = reportService.updateReport(command);
        return CommonResponse.success(response);
    }
}
