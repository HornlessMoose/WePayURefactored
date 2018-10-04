package WePayURefactored.employees;

import WePayURefactored.saleResult.SaleResult;
import WePayURefactored.utility.GetInfo;

import java.util.ArrayList;

public class Commissioned extends Salaried{
    private float commission;
    private ArrayList<SaleResult>  saleResults = new ArrayList<>();


    public Commissioned(CommissionedBuilder commissionedBuilder) {
        super(commissionedBuilder);
        this.commission = commissionedBuilder.commission;

    }

    public float getCommission() {
        return commission;
    }

    @Override
    public String toString() {
        return super.toString() + "\ncomissão: " + commission;
    }

    public ArrayList<SaleResult> getSaleResults() {
        return saleResults;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    @Override
    public float calculateSalary() {
        float salary = 0;

        for (SaleResult saleResult:saleResults) {
            salary +=  saleResult.getValue();
        }

        return getSalary()/2 + salary * commission;

    }

    public static class CommissionedBuilder extends SalariedBuilder{
        private float commission;
        GetInfo getInfo = new GetInfo();

        @Override
        public CommissionedBuilder askEmployeeInfo(int ID, String paymentSchedule) {
            super.askEmployeeInfo(ID, paymentSchedule);
            System.out.println("comissão: ");
            setCommission(getInfo.getFloat());

            return this;
        }

        public CommissionedBuilder changeToCommissioned(Employee employee){
            setName(employee.getName());
            setAddress(employee.getAddress());

            System.out.println("salario:");
            setSalary(getInfo.getFloat());

            System.out.println("comissão:");
            setCommission(getInfo.getFloat());

            setID(employee.getID());
            setPaymentMethod(employee.getPaymentMethod());
            setPaymentSchedule(employee.getPaymentSchedule());

            return this;
        }

        public CommissionedBuilder setCommission(float commission){
            this.commission = commission;

            return this;
        }

        public Commissioned build(){
            return new Commissioned(this);
        }
    }
}
