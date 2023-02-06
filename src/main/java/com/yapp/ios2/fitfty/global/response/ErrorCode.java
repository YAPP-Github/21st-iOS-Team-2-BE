package com.yapp.ios2.fitfty.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),
    MEMBER_NOT_FOUND("등록되지 않은 회원이거나, 해당 정보로 로그인에 실패하였습니다."),
    MEMBER_ALREADY_EXIST("이미 등록된 회원입니다."),
    DUPLICATE_ENTITY("중복된 개체가 이미 존재합니다."),
    CURRENT_CONTEXT_ERROR("Security Context에 회원 정보가 없습니다."),
    MEMBER_NOT_ACTIVATED("비활성화 상태의 회원입니다."),
    FORBIDDEN("자원에 접근할 권한이 없습니다."),
    UNAUTHORIZED("로그인이 필요한 요청입니다."),
    KAKAO_OAUTH_NO_RESPONES("카카오 OAuth 서비스로부터 응답이 잘못되었습니다.");
    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
