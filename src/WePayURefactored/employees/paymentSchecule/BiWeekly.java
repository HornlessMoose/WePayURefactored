package WePayURefactored.employees.paymentSchecule;

public class BiWeekly implements PaymentSchedule {
    @Override
    public String paymentSchedule() {
        return "bi-weekly";
    }

    private static BiWeekly instance;

    public static synchronized BiWeekly getInstance(){
        if(instance == null){
            instance = new BiWeekly();
        }
        return instance;
    }
}
