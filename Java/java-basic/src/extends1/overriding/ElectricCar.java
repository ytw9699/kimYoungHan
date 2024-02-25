package extends1.overriding;

public class ElectricCar extends Car {

    @Override//Override어노테이션은 실수로 move 메서드를 다른이름으로 바꾸는것을 방지한다, 컴파일 오류
    public void move() {
        System.out.println("전기차를 빠르게 이동합니다.");
    }

    public void charge() {
        System.out.println("충전합니다.");
    }
}
