package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity//jpa가 로딩될때 jpa를 사용하는 애로인식하고 관리한다.
@Table(name = "Member")//맵핑
public class Member {
    @Id//pk가 뭔지 알려준다
    private Long id;
    @Column(name="name", unique = true, length = 20)//맵핑
    private String name;

    @Column(name="phoneNum", columnDefinition = "varchar(100) default 'EMPTY'")
    private String phoneNum;

    //private Integer age;
    @Column(updatable = false)//업데이트 반영안되게
    private int age;
    @Enumerated(EnumType.STRING)//디비에는 enum타입이 없어서 varchar로 들어가게 하자. ORDINAL는 숫자로들어감
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP)//TIMESTAMP는 날짜시간둘다 적용하는것. 왜냐면 디비는 날짜,시간,날짜시간을 구분함
    private Date createDate;//과거버전이면 date 사용, 최신이면 LocalDateTime
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private LocalDate testLocalDate;//날짜 date타입
    private LocalDateTime testLocalDateTime;//TIMESTAMP타입 날짜 시간 둘다 적용 보통 하이버네이트 최신이면 이걸로 씀

    @Lob//디비에 varchar를 넘어서는 큰 컨텐츠 CLOB, 문자가 아닌 나머지는 BLOB
    private String description;
    @Transient//디비랑 맵핑안하고싶을때
    private int temp;//메모리에만 있음 캐싱필요할때

    public Member(){} //jpa는 내부적으로 리플렉션 같은것을 쓰기에 동적으로 객체생성하려면 기본생성자 있어야함

    public Member(Long id, String name){//생성자
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
