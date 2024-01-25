package cond;

public class Switch4 {

    public static void main(String[] args) {

        int grade = 2;

        int coupon = switch (grade) {//자바 14이상 새로운 스위치문
            case 1 -> 1000;
            case 2 -> 2000;
            case 3 -> 3000;
            default ->  500;
        };
        System.out.println("발급받은 쿠폰 " + coupon);
    }
}
