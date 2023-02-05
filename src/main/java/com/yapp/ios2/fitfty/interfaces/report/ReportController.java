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

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse getUserReports() {
        var response = reportService.getUserReports();
        return CommonResponse.success(response);
    }

    @PostMapping("/user/new")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse makeUserReport(@RequestBody ReportDto.MakeUserReport request) {
        var command = reportDtoMapper.of(request);
        reportService.addUserReport(command);
        return CommonResponse.success("OK");
    }

    @PutMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse updateUserReport(@RequestBody ReportDto.UpdateReport request) {
        var command = ReportDto.toUpdateCommand(request, "USER");
        reportService.updateReport(command);
        return CommonResponse.success("OK");
    }

    @GetMapping("/board")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse getBoardReports() {
        var response = reportService.getBoardReports();
        return CommonResponse.success(response);
    }

    @PostMapping("/board/new")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public CommonResponse makeBoardReport(@RequestBody ReportDto.MakeBoardReport request) {
        var command = reportDtoMapper.of(request);
        reportService.addBoardReport(command);
        return CommonResponse.success("OK");
    }

    @PutMapping("/board")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public CommonResponse updateBoardReport(@RequestBody ReportDto.UpdateReport request) {
        var command = ReportDto.toUpdateCommand(request, "BOARD");
        reportService.updateReport(command);
        return CommonResponse.success("OK");
    }
}
