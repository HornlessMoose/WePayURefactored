package WePayURefactored.employees.paymentMethod;

public class CheckPostOffice implements PaymentMethod{

    @Override
    public String paymentMethod() {
        return "cheque pelos correios";
    }

    private static CheckPostOffice instance;

    public static synchronized CheckPostOffice getInstance(){
        if(instance == null){
            instance = new CheckPostOffice();
        }
        return instance;
    }
}
