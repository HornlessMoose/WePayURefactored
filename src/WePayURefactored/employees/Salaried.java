package WePayURefactored.employees;

import WePayURefactored.utility.GetInfo;

public class Salaried extends Employee{
    private float salary;

    public Salaried(SalariedBuilder salariedBuilder) {
        super(salariedBuilder);
        this.salary = salariedBuilder.salary;
    }

    public float getSalary() {
        return salary;
    }

    public float calculateSalary(){
       return salary;
    }

    @Override
    public String toString() {
        return super.toString() + "\nsalario: " + salary;
    }

    public static class SalariedBuilder extends EmployeeBuilder{
        private float salary;
        GetInfo getInfo = new GetInfo();

        @Override
        public SalariedBuilder askEmployeeInfo(int ID, String paymentSchedule) {
            super.askEmployeeInfo(ID, paymentSchedule);

            System.out.println("Salario: ");
            setSalary(getInfo.getFloat());

            return this;
        }

        public SalariedBuilder changeToSalaried(Employee employee){
            setName(employee.getName());
            setAddress(employee.getAddress());

            System.out.println("salario");
            setSalary(getInfo.getFloat());

            setID(employee.getID());
            setPaymentMethod(employee.getPaymentMethod());
            setPaymentSchedule(employee.getPaymentSchedule());

            return this;
        }

        public SalariedBuilder setSalary(float salary){
            this.salary = salary;
            return this;
        }

        public Salaried build(){
            return new Salaried(this);
        }
    }
}
