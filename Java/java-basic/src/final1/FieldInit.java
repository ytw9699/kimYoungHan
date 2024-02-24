package final1;

public class FieldInit {

    static final int CONST_VALUE = 10;// static final이 붙으면 대문자로한다 이것을 상수라한다 : 공용변수
    final int value = 10;//이경우 value 의 값은 변경이 불가한채로 무조건 10이다.
    //그래서 객체를 생성해도 어차피 값을 바꿀수도 없고 메모리만 낭비된다
    // 이럴때는 static 을 붙여 비효율 문제를 해결하는게 좋다 static final을 붙여 상수로 만들라는 뜻이다.

    /*public FieldInit(int value) {//초기값이 먼저 정해져있으면 생성자로도 변경안됨
        this.value = value;
    }*/
}
