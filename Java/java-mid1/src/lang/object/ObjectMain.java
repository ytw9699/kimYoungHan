package lang.object;

public class ObjectMain {

    public static void main(String[] args) {
        Child child = new Child();
        child.childMethod();
        child.parentMethod();

        // toString()은 Object 클래스의 메서드
        String string = child.toString();
        //toString은 객체에 대한 정보를 반환해줌 lang.object.Child@4e50df2e
        System.out.println(string);
    }
}
