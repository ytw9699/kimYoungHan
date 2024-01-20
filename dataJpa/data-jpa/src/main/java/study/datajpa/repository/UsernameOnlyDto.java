package study.datajpa.repository;

public class UsernameOnlyDto {

    private final String username;

    public UsernameOnlyDto(String username) {//username이 파라미터명을 보고 분석함 달라지면안됨
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}