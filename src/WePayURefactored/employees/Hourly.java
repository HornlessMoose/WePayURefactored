package WePayURefactored.employees;


import WePayURefactored.timeCard.TimeCard;
import WePayURefactored.utility.GetInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

public class Hourly extends Employee{

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    private ArrayList<TimeCard> timeCards = new ArrayList<>();

    private float hourlySalary;

    public Hourly(HourlyBuilder hourlyBuilder) {
        super(hourlyBuilder);
        this.hourlySalary = hourlyBuilder.hourlySalary;

    }


    public ArrayList<TimeCard> getTimeCards() {
        return timeCards;
    }


    @Override
    public String toString() {
        return super.toString() + "\nsalario horario: " + getHourlySalary();
    }

    public float getHourlySalary() {
        return hourlySalary;
    }

    public float calculateSalary() throws ParseException {
        float salary = 0;
        long hours;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        for(TimeCard timeCard:timeCards){
             c1.setTime(sdf.parse(timeCard.getStart()));
             c2.setTime(sdf.parse(timeCard.getEnd()));

             hours = ChronoUnit.HOURS.between(c1.toInstant(),c2.toInstant());

             if(hours <= 8){
                salary += hours * hourlySalary;
             }
             else {
                salary += (8 * hourlySalary + ((hours - 8) * 1.5 * hourlySalary));
             }
        }

        return salary;
    }

    public static class HourlyBuilder extends EmployeeBuilder{
        private float hourlySalary;
        GetInfo getInfo = new GetInfo();

        @Override
        public HourlyBuilder askEmployeeInfo(int ID, String paymentSchedule) {
            super.askEmployeeInfo(ID, paymentSchedule);

            System.out.println("salario por hora:");
            setHourlySalary(getInfo.getFloat());

            return this;
        }

        public HourlyBuilder changeToHourly(Employee employee){
            setName(employee.getName());
            setAddress(employee.getAddress());

            System.out.println("salario por hora:");
            setHourlySalary(getInfo.getFloat());

            setID(employee.getID());
            setPaymentMethod(employee.getPaymentMethod());
            setPaymentSchedule(employee.getPaymentSchedule());

            return this;
        }

        public HourlyBuilder setHourlySalary(float hourlySalary){
            this.hourlySalary = hourlySalary;
            return this;
        }

        public Hourly build(){
            return new Hourly(this);
        }
    }
}
