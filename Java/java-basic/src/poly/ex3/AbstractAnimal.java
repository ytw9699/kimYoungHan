package poly.ex3;

public abstract class AbstractAnimal {

    public abstract void sound();//오버라이드 다시해야하는 목적으로 만듬

    public void move() {//기능 상속받아서 사용하는 목적
        System.out.println("동물이 움직입니다.");
    }
}
