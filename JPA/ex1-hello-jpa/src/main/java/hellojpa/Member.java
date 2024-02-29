package hellojpa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity//jpa가 로딩될때 jpa가 관리하는 객체로 인식한다 테이블과 매핑함
@Table(name = "Member")//맵핑
public class Member {

   /* @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/

    @Id//pk가 뭔지 알려준다
    private Long id;
    @Column(name="name", unique = true, length = 20)//맵핑, unique = true 잘 안씀 이름이 
    private String name;

    @Column(name="phoneNum", columnDefinition = "varchar(100) default 'EMPTY'")//column직접 정의
    private String phoneNum;

    //private Integer age;
    @Column(updatable = false)//이 필드 변경했을때 디비에 업데이트 반영안되게
    private int age;
    //private BigDecimal age;//아주큰 숫자나 소수점 쓸때 사용
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    //디비에는 enum타입이 없어서 varchar로 들어가게 하자. ORDINAL는 순서를 저장, STRING은 enum이름을 저장
    @Temporal(TemporalType.TIMESTAMP)//TIMESTAMP는 날짜시간둘다 적용하는것. 왜냐면 디비는 날짜,시간,날짜시간을 구분함
    private Date createDate;//과거버전이면 date 사용, 최신이면 LocalDateTime
    @Temporal(TemporalType.TIMESTAMP)//Temporal날짜 타입 매핑
    private Date updateDate;
    private LocalDate testLocalDate;//디비에 date타입 :년월만 있음
    private LocalDateTime testLocalDateTime;//디비에 TIMESTAMP타입 : 년월일 모두 있음.보통 하이버네이트 최신이면 이걸로 씀

    @Lob//디비에 varchar를 넘어서는 큰 컨텐츠 문자면 CLOB, 문자가 아닌 나머지는 BLOB
    private String description;
    @Transient//디비랑 맵핑안하고싶을때
    private int temp;//메모리에서만 임시로 어떤값을 보관하고싶을때, 캐싱필요할때

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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
