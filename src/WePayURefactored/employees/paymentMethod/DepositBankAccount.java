package WePayURefactored.employees.paymentMethod;

public class DepositBankAccount implements PaymentMethod {

    @Override
    public String paymentMethod() {
        return "depósito em conta bancária";
    }

    private static DepositBankAccount instance;

    public static synchronized DepositBankAccount getInstance(){
        if(instance == null){
            instance = new DepositBankAccount();
        }
        return instance;
    }

}
