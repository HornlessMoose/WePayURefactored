package WePayURefactored.employees.paymentMethod;

public class CheckInHands implements PaymentMethod {

    @Override
    public String paymentMethod() {
        return "cheque em mãos";
    }

    private static CheckInHands instance;

    public static synchronized CheckInHands getInstance(){
        if(instance == null){
            instance = new CheckInHands();
        }
        return instance;
    }
}
