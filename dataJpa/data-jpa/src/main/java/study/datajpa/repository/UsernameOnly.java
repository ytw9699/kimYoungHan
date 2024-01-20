package study.datajpa.repository;

public interface UsernameOnly {

    //    @Value("#{target.username + ' ' + target.age}") 원하는 데이터 다가져와서 조합해서 반환 : 오픈 프로젝션
    String getUsername();//클로즈 프로젝션
}