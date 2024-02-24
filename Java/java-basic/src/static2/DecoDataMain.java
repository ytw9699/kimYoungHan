package static2;

import static static2.DecoData.staticCall;
//import static static2.DecoData.*;

public class DecoDataMain {

    public static void main(String[] args) {
        System.out.println("1. 정적 호출");
        DecoData.staticCall();
        staticCall();

        System.out.println("2. 인스턴스 호출1");
        DecoData data1 = new DecoData();
        data1.instanceCall();

        System.out.println("3. 인스턴스 호출2");
        DecoData data2 = new DecoData();
        data2.instanceCall();

        //추가
        //인스턴스를 통한 접근
        DecoData data3 = new DecoData();
        data3.staticCall();//이렇게 하면 가독성 떨어진다 마치 인스턴스 메소드에 접근하는거 같이보이자나

        //클래스를 통한 접근
        DecoData.staticCall();

        //참조값을 넘겨서 접근
        DecoData.staticCall(data1);
    }
}
