package WePayURefactored.employees.paymentSchecule;

public class Monthly$ implements PaymentSchedule {
    @Override
    public String paymentSchedule() {
        return "mensal $";
    }

    private static Monthly$ instance;

    public static synchronized Monthly$ getInstance(){
        if(instance == null){
            instance = new Monthly$();
        }
        return instance;
    }
}
