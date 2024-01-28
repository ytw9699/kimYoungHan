package loop;

public class Break2 {

    public static void main(String[] args) {
        int sum = 0;
        int i = 1;

        for (;;) {//for문에서 조건들을 생략해도 ;; 은 유지해야한다 그래서 while 문처럼 무한반복함
            sum = sum + i;
            if (sum > 10) {
                System.out.println("합이 10보다 크면 종료: i=" + i + " sum=" + sum);
                break;
            }
            i++;
        }
    }
}
