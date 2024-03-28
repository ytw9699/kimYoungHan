package lang.object.tostring;

public class ToStringMain1 {

    public static void main(String[] args) {
        Object object = new Object();
        String string = object.toString();

        //toString() 반환값 출력
        System.out.println(string);//클래스에 대한정보와@참조값
        //object 직접 출력
        System.out.println(object);//결과가 같음. 내부에서 tostring 호출하기때문
    }

}
