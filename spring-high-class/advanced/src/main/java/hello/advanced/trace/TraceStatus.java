package hello.advanced.trace;

import lombok.Getter;

@Getter
public class TraceStatus {//로그시작할때의 상태정보, 로그 종료시 사용

    private TraceId traceId;//트랜잭션 id + 레벨
    private Long startTimeMs;//로그시작 시간
    private String message;//시작시 사용한 메시지

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }
}
