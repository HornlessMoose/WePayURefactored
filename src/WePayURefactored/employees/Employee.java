package WePayURefactored.employees;


import WePayURefactored.employees.paymentMethod.CheckInHands;
import WePayURefactored.employees.paymentMethod.CheckPostOffice;
import WePayURefactored.employees.paymentMethod.DepositBankAccount;
import WePayURefactored.utility.GetInfo;

import java.text.ParseException;


public class Employee {
    private String name, address, paymentMethod, paymentSchedule;
    private int ID;


    public Employee(EmployeeBuilder employeeBuilder){
        this.name = employeeBuilder.name;
        this.address = employeeBuilder.address;
        this.ID = employeeBuilder.ID;
        this.paymentMethod = employeeBuilder.paymentMethod;
        this.paymentSchedule = employeeBuilder.paymentSchedule;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getID() {
        return ID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentSchedule(String paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public String getPaymentSchedule() {
        return paymentSchedule;
    }

    @Override
    public String toString() {
        return  "\nID: " + getID() + "\nNome: " + getName() + "\nEndereço: "+ getAddress()
                + "\nMetodo de pagamento: " + getPaymentMethod();
    }

    public float calculateSalary() throws ParseException {
        return 0;
    }

    public static class EmployeeBuilder{
        private String name, address, paymentMethod, paymentSchedule;
        private int ID, option;
        GetInfo getInfo = new GetInfo();

        public EmployeeBuilder askEmployeeInfo(int ID, String paymentSchedule){

            System.out.println("Nome: ");
            setName(getInfo.getString());

            System.out.println("Endereço: ");
            setAddress(getInfo.getString());

            System.out.println("Metodo de pagamento(1: cheque pelos correios, 2: cheque em mãos, 3: depósito em conta bancária) ");

            option = getInfo.getInt();

            switch (option){
                case 1:
                    setPaymentMethod(new CheckPostOffice().paymentMethod());
                    break;
                case 2:
                    setPaymentMethod(new CheckInHands().paymentMethod());
                    break;
                case 3:
                    setPaymentMethod(DepositBankAccount.getInstance().paymentMethod());
                    break;
            }

            setID(ID);
            setPaymentSchedule(paymentSchedule);



            return this;

        }

        public void setName(String name){
            this.name = name;

        }

        public void setAddress(String address){
            this.address = address;

        }

        public void setID(int ID){
            this.ID = ID;

        }

        public void setPaymentMethod(String paymentMethod){
            this.paymentMethod = paymentMethod;

        }

        public void setPaymentSchedule(String paymentSchedule){
            this.paymentSchedule = paymentSchedule;
        }

        public Employee build(){
            return new Employee(this);
        }

    }
}
