package access.a;

public class PublicClass {
    public static void main(String[] args) {
        PublicClass publicClass = new PublicClass();
        DefaultClass1 class1 = new DefaultClass1();
        DefaultClass2 class2 = new DefaultClass2();
    }
}

class DefaultClass1 {
//하나의 자바 파일에 default 접근 제어자를 사용하는 클래스 무한정 만들수 있음
}

class DefaultClass2 {
}
