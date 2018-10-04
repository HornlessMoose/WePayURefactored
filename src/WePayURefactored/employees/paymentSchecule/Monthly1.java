package WePayURefactored.employees.paymentSchecule;

public class Monthly1 implements PaymentSchedule {
    @Override
    public String paymentSchedule() {
        return "mensal 1";
    }

    private static Monthly1 instance;

    public static synchronized Monthly1 getInstance(){
        if(instance == null){
            instance = new Monthly1();
        }
        return instance;
    }
}
