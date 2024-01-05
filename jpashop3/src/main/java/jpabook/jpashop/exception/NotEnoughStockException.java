package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
    //오버라이드 하는 이유가 메시지랑 예외가 발생한 근원 이세셥은 넣어서 트레이스를 찍기 위함

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

}
