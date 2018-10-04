package WePayURefactored.employees.paymentSchecule;

public class Weekly1Monday implements PaymentSchedule {
    @Override
    public String paymentSchedule() {
        return "semanal 1 segunda";
    }

    private static Weekly1Monday instance;

    public static synchronized Weekly1Monday getInstance(){
        if(instance == null){
            instance = new Weekly1Monday();
        }
        return instance;
    }
}
