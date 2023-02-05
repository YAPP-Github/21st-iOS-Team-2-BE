package com.yapp.ios2.fitfty.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
    OBSCENE("음란성/선정성"),
    WEATHER("날씨 태그가 사진과 어울리지 않음"),
    COPYRIGHT("지적재산권 침해"),
    INSULT("혐오 / 욕설 / 인신공격"),
    REPEAT("같은내용 반복게시"),
    MISC("기타");

    private final String description;
}

