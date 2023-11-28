package variable;

public class Var6 {

    static int b;//클래스 변수 초기화가 0로 자동됨

    public static void main(String[] args) {
        int a;//지역 변수 초기화를 안한것이다.
        //System.out.println(a);
        //위는 컴파일 오류가 나는데 원인은 이상한 값이 출력될수 있기때문
        //메모리는 여러 시스템이 함께 사용하는 공간이고 어떠한 값들이 계속 저장된다.
        //변수를 선언하면 메모리상의 어떤 공간을 차지하고 사용한다.
        System.out.println(b);
    }
}
