package WePayURefactored.employees.paymentSchecule;

public class Weekly1Friday implements PaymentSchedule{
    @Override
    public String paymentSchedule() {
        return "semanal 1 sexta";
    }

    private static Weekly1Friday instance;

    public static synchronized Weekly1Friday getInstance(){
        if(instance == null){
            instance = new Weekly1Friday();
        }
        return instance;
    }
}
