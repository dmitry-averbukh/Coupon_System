package data.ex;

public class CouponAlreadyPurchaseException extends Exception {
        public CouponAlreadyPurchaseException(String message){
            super(message);
        }
}