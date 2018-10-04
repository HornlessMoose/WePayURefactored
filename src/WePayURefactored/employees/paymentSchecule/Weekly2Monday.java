package WePayURefactored.employees.paymentSchecule;

public class Weekly2Monday implements PaymentSchedule {
    @Override
    public String paymentSchedule() {
        return "semanal 2 segunda";
    }

    private static Weekly2Monday instance;

    public static synchronized Weekly2Monday getInstance(){
        if(instance == null){
            instance = new Weekly2Monday();
        }
        return instance;
    }
}
