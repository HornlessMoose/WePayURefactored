package WePayURefactored.employees.paymentSchecule;

public class Monthly7 implements PaymentSchedule {
    @Override
    public String paymentSchedule() {
        return "mensal 7";
    }

    private static Monthly7 instance;

    public static synchronized Monthly7 getInstance(){
        if(instance == null){
            instance = new Monthly7();
        }
        return instance;
    }
}
