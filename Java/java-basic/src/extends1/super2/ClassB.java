package extends1.super2;

public class ClassB extends ClassA {

    public ClassB(int a) {
        this(a, 0); //내 거를 호출하더라도 호출한곳 안에서 한번은 super 를 호출해야함
        System.out.println("ClassB 생성자 a=" + a);
    }

    public ClassB(int a, int b) {
        super(); //생성자에서 첫줄에 부모생성자를 적어줘야하는데 기본 생성자는 생략 가능
        System.out.println("ClassB 생성자 a=" + a + " b=" + b);
    }
}
