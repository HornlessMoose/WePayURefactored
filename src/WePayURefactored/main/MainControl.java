package WePayURefactored.main;

import WePayURefactored.employees.Commissioned;
import WePayURefactored.employees.Employee;
import WePayURefactored.employees.Hourly;
import WePayURefactored.employees.Salaried;
import WePayURefactored.employees.paymentMethod.CheckInHands;
import WePayURefactored.employees.paymentMethod.CheckPostOffice;
import WePayURefactored.employees.paymentMethod.DepositBankAccount;
import WePayURefactored.employees.paymentSchecule.*;
import WePayURefactored.saleResult.SaleResult;
import WePayURefactored.syndicate.Syndicate;
import WePayURefactored.syndicate.SyndicateAdditionalFee;
import WePayURefactored.timeCard.TimeCard;
import WePayURefactored.utility.GetInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainControl {
    private int option, option2;

    private String description;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");


    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Syndicate> syndicates = new ArrayList<>();

    private ArrayList<String> paymentSchedule = new ArrayList<>();


    private int employeeID = 1, ID, syndicateID = 1;
    private float salary, monthlyFee;

    void initialPaymentSchedule(){
        paymentSchedule.add(Weekly1Friday.getInstance().paymentSchedule());
        paymentSchedule.add(Monthly$.getInstance().paymentSchedule());
        paymentSchedule.add(BiWeekly.getInstance().paymentSchedule());
    }


    void menu() throws ParseException {
        System.out.println("MENU");
        System.out.println( "1: Adicionar empregado\n" +
                            "2: Remover empregado\n" +
                            "3: Lançar cartão de ponto\n" +
                            "4: Lançar resultado de venda\n" +
                            "5: Lançar taxa de serviço\n" +
                            "6: Alterar detalhes de um empregado\n" +
                            "7: Rodar folha de pagamento para hoje\n" +
                            "8: Undo/Redo\n" +
                            "9: Agenda de pagamento\n" +
                            "10: Criar nova agenda de pagamento\n" +
                            "11: Sair.");


        option = GetInfo.getInstance().getInt();

        switch (option){
            case 1:
                addEmployee();
                employeeID++;
                menu();
                break;
            case 2:
                removeEmployee();
                menu();
                break;
            case 3:
                submitTimeCard();
                menu();
                break;
            case 4:
                submitSaleResult();
                menu();
                break;
            case 5:
                submitServiceFee();
                menu();
                break;
            case 6:
                updateEmployee();
                menu();
                break;
            case 7:
                executePayroll();
                menu();
                break;
            case 8:
                System.out.println("não desenvolvido ;(");
                menu();
                break;
            case 9:
                changePaymentSchedule();
                menu();
                break;
            case 10:
                createNewPaymentSchedule();
                menu();
                break;
            case 11:
                break;

            default:
                showEmployees();
                menu();
        }
    }

    private void executePayroll() throws ParseException {
        Calendar payroll = Calendar.getInstance();

        if(payroll.get(Calendar.DAY_OF_MONTH) == 1){
            payrollToday(Monthly1.getInstance().paymentSchedule());
        }

        if(payroll.get(Calendar.DAY_OF_MONTH) == 7){
            payrollToday(Monthly7.getInstance().paymentSchedule());
        }

        if(isUsefulDay(payroll)){
             payrollToday(Monthly$.getInstance().paymentSchedule());
        }

        if (payroll.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            payrollToday(Weekly1Monday.getInstance().paymentSchedule());
        }
        if (payroll.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
            payrollToday(Weekly1Friday.getInstance().paymentSchedule());
        }

    }

    private boolean isUsefulDay(Calendar payroll) {
        int lastDay = payroll.getActualMaximum(Calendar.DAY_OF_MONTH);

        if(payroll.get(Calendar.DAY_OF_MONTH) == lastDay){
            return payroll.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && payroll.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;

        }


        return false;

    }

    private void payrollToday(String s) throws ParseException {

        for (Employee employee:employees) {
            if(employee.getPaymentSchedule().equals(s)){
                salary = employee.calculateSalary();
                salary -= debitFee(employee);

                System.out.println(employee.toString() + salary);
            }
        }
    }

    private float debitFee(Employee employee) {

        float fees = 0;
        for (Syndicate syndicate:syndicates) {
            if(syndicate.getEmployeeID() == employee.getID()){
                fees = syndicate.getMonthlyFee();

                for (SyndicateAdditionalFee syndicateAdditionalFee: syndicate.getSyndicateAdditionalFees()){
                    fees += syndicateAdditionalFee.getAdditionalFee();
                }
                break;
            }
        }

        return fees;
    }

    private void changePaymentSchedule() {
        System.out.println("informe o ID do empregado:");
        ID = GetInfo.getInstance().getInt();

        for (Employee employee: employees) {
            if(employee.getID() == ID){
                System.out.println("informe a nova agenda de pagamentos para o empregado:");
                System.out.println("escolha entre: (mensal 1, mensal 7," +
                        " mensal $, semanal 1 segunda, semanal 1 sexta, semanal 2 segunda).");

                employee.setPaymentSchedule(GetInfo.getInstance().getString());

                return;

            }
        }

        System.out.println("empregado não encontrado.");
    }

    private void createNewPaymentSchedule() {
        System.out.println("Escolha uma das possiveis nova agenda de pagamento:");
        System.out.println( "1: mensal 1\n" +
                            "2: mensal 7\n" +
                            "3: mensal $\n" +
                            "4: semanal 1 segunda\n" +
                            "5: semanal 1 sexta\n" +
                            "6: semanal 2 segunda\n");

        option = GetInfo.getInstance().getInt();

        switch (option){
            case 1:
                addMonthly1();
                break;
            case 2:
                addMonthly7();
                break;
            case 3:
                addMonthly$();
                break;
            case 4:
                addWeekly1Monday();
                break;
            case 5:
                addWeekly1Friday();
                break;
            case 6:
                addWeekly2Monday();
                break;
            default:
                break;
        }
    }

    private void addWeekly2Monday() {

        if(!verifyPaymentSchedule(Weekly2Monday.getInstance().paymentSchedule())){
            paymentSchedule.add(Weekly2Monday.getInstance().paymentSchedule());
            System.out.println("Agenda adicionada");
        }
        else{
            System.out.println("Agenda já adicionada, escolha outra.");
        }
    }

    private void addWeekly1Friday() {
        if(!verifyPaymentSchedule(Weekly1Friday.getInstance().paymentSchedule())){
            paymentSchedule.add(Weekly1Friday.getInstance().paymentSchedule());
            System.out.println("Agenda adicionada");
        }
        else{
            System.out.println("Agenda já adicionada, escolha outra.");
        }
    }

    private void addWeekly1Monday() {
        if(!verifyPaymentSchedule(Weekly1Monday.getInstance().paymentSchedule())){
            paymentSchedule.add(Weekly1Monday.getInstance().paymentSchedule());
            System.out.println("Agenda adicionada");
        }
        else{
            System.out.println("Agenda já adicionada, escolha outra.");
        }
    }

    private void addMonthly$() {
        if(!verifyPaymentSchedule(Monthly$.getInstance().paymentSchedule())){
            paymentSchedule.add(Monthly$.getInstance().paymentSchedule());
            System.out.println("Agenda adicionada");
        }
        else{
            System.out.println("Agenda já adicionada, escolha outra.");
        }
    }

    private void addMonthly7() {
        if(!verifyPaymentSchedule(Monthly7.getInstance().paymentSchedule())){
            paymentSchedule.add(Monthly7.getInstance().paymentSchedule());
            System.out.println("Agenda adicionada");
        }
        else{
            System.out.println("Agenda já adicionada, escolha outra.");
        }
    }

    private void addMonthly1() {

        if(!verifyPaymentSchedule(Monthly1.getInstance().paymentSchedule())){
            paymentSchedule.add(Monthly1.getInstance().paymentSchedule());
            System.out.println("Agenda adicionada");
        }
        else{
            System.out.println("Agenda já adicionada, escolha outra.");
        }
    }

    private boolean verifyPaymentSchedule(String s) {

        for (String paymentSchedule: paymentSchedule) {
            if(paymentSchedule.equals(s)){
                return true;
            }
        }

        return false;

    }

    private void updateEmployee() {
        System.out.println("informe o ID do empregado:");
        ID = GetInfo.getInstance().getInt();


        for (Employee employee: employees) {

            if(employee.getID() == ID){
                System.out.println("informe qual atributo deseja alterar desse funcionario:");
                System.out.println( "1: Nome\n" +
                                    "2: Endereço\n" +
                                    "3: Tipo(horario, comissionado, assalariado)\n" +
                                    "4: Método de pagamento\n" +
                                    "5: Sindicato\n" +
                                    "6: Identificação no sindicato\n" +
                                    "7: Taxa sindical\n");
                option2 = GetInfo.getInstance().getInt();

                switch (option2){
                    case 1:
                        changeName(employee);
                        break;
                    case 2:
                        changeAddress(employee);
                        break;
                    case 3:
                        changeType(employee);
                        break;
                    case 4:
                        changePaymentMethod(employee);
                        break;
                    case 5:
                        changeSyndicate(employee);
                        break;
                    case 6:
                        changeSyndicateID(employee);
                        break;
                    case 7:
                        changeSyndicateFee(employee);
                        break;
                }






                return;
            }


        }

    }

    private void changeSyndicateFee(Employee employee) {

        System.out.println("informe o novo valor da taxa sindical:");
        monthlyFee = GetInfo.getInstance().getFloat();

        for (Syndicate syndicate:syndicates) {
            if(employee.getID() == syndicate.getEmployeeID()){
                syndicate.setMonthlyFee(monthlyFee);
                return;
            }
        }
    }

    private void changeSyndicateID(Employee employee) {
        System.out.println("escolha o novo identificador no sindicato.");

        int newSyndicateID = GetInfo.getInstance().getInt();

        for (Syndicate syndicate: syndicates) {
            if(newSyndicateID == syndicate.getSyndidateID()){
                System.out.println("já existe esse ID no sindicato.\nescolha outro.");
                return;
            }
        }

        for (Syndicate syndicate:syndicates) {
            if(employee.getID() == syndicate.getEmployeeID()){
                syndicate.setSyndidateID(newSyndicateID);
                return;
            }
        }
    }

    private void changeSyndicate(Employee employee) {

        System.out.println("escolha (1: Adicionar ao sindicato, 2: Remover do sindicato.):");

        option2 = GetInfo.getInstance().getInt();

        switch (option2){
            case 1:
                for (Syndicate syndicate: syndicates) {
                    if(employee.getID() == syndicate.getEmployeeID()){
                        System.out.println("o empregado já participa do sindicato.");
                        return;
                    }

                }
                addEmployeeToSyndicate(employee.getID());
                break;
            case 2:
                for (Syndicate syndicate: syndicates) {
                    if(employee.getID() == syndicate.getEmployeeID()){
                        syndicates.remove(syndicate);
                        return;
                    }
                }
                break;

        }


    }

    private void changePaymentMethod(Employee employee) {
        System.out.println("Escolha o novo metodo de pagamento");
        System.out.println("(1: cheque pelos correios, 2: cheque em mãos, 3: depósito em conta bancária)");

        switch (GetInfo.getInstance().getInt()){
            case 1:
                employee.setPaymentMethod(CheckPostOffice.getInstance().paymentMethod());
                break;
            case 2:
                employee.setPaymentMethod(CheckInHands.getInstance().paymentMethod());
                break;
            case 3:
                employee.setPaymentMethod(DepositBankAccount.getInstance().paymentMethod());
                break;
            default:
                System.out.println("invalido.");
        }

        employee.setPaymentMethod(GetInfo.getInstance().getString());
    }

    private void changeType(Employee employee) {

        System.out.println("novo tipo de empregado:\n" +
                "1: Hourly\n" +
                "2: Salaried\n" +
                "3: Commissioned");

        option2 = GetInfo.getInstance().getInt();

        switch (option2){
            case 1:
                changeToHourly(employee);
                break;
            case 2:
                changeToSalaried(employee);
                break;
            case 3:
                changeToCommissioned(employee);
                break;
            default:
                break;
        }

    }

    private void changeToCommissioned(Employee employee) {
        addCommissioned(employee);
        employees.remove(employee);

    }

    private void changeToSalaried(Employee employee) {
        addSalaried(employee);
        employees.remove(employee);

    }


    private void changeToHourly(Employee employee) {

        addHourly(employee);
        employees.remove(employee);


    }


    private void changeAddress(Employee employee) {
        System.out.println("Novo Endereço:");
        employee.setAddress(GetInfo.getInstance().getString());
    }

    private void changeName(Employee employee) {
        System.out.println("Novo Nome:");
        employee.setName(GetInfo.getInstance().getString());
    }

    private void submitServiceFee() {
        System.out.println("informe o ID do empregado:");
        ID = GetInfo.getInstance().getInt();

        System.out.println("informe a descrição do serviço:");
        description = GetInfo.getInstance().getString();

        System.out.println("informe a taxa de serviço:");
        monthlyFee = GetInfo.getInstance().getFloat();

        for (Syndicate syndicate: syndicates) {
            if(syndicate.getEmployeeID() == ID){
                syndicate.getSyndicateAdditionalFees().add(new SyndicateAdditionalFee(description, monthlyFee));
            }
        }

    }

    private void submitSaleResult() {
        Calendar date = Calendar.getInstance();

        System.out.println("informe o ID do empregado:");
        ID = GetInfo.getInstance().getInt();

        System.out.println("informe o valor da venda: ");
        float value = GetInfo.getInstance().getFloat();

        for (Employee employee: employees) {

            if(employee.getID() == ID){
                if (employee instanceof Commissioned) {
                    ((Commissioned) employee).getSaleResults().add(new SaleResult(sdf2.format(date.getTime()), value));

                }
                else{
                    System.out.println("Empregado não é do tipo 'Comissionado' ");
                }
                return;
            }
        }


    }

    private void submitTimeCard() {


        Calendar start = Calendar.getInstance();

        System.out.println("informe o ID do empregado:");
        ID = GetInfo.getInstance().getInt();


        System.out.println("horário de chegada\nhora:");

        start.set(Calendar.HOUR_OF_DAY, GetInfo.getInstance().getInt());

        System.out.println("minutos:");

        start.set(Calendar.MINUTE, GetInfo.getInstance().getInt());

        System.out.println("segundos:");

        start.set(Calendar.SECOND, GetInfo.getInstance().getInt());

        Calendar end = Calendar.getInstance();


        System.out.println("horário de saida\nhora:");

        end.set(Calendar.HOUR_OF_DAY, GetInfo.getInstance().getInt());

        System.out.println("minutos:");

        end.set(Calendar.MINUTE, GetInfo.getInstance().getInt());

        System.out.println("segundos:");

        end.set(Calendar.SECOND, GetInfo.getInstance().getInt());


        for (Employee employee: employees) {

            if(employee.getID() == ID){
                if (employee instanceof Hourly) {
                    ((Hourly) employee).getTimeCards().add(new TimeCard(sdf.format(start.getTime()), sdf.format(end.getTime())));

                }
                else{
                    System.out.println("Empregado não é do tipo 'Horario' ");
                }
                return;
            }
        }


    }

    private void showEmployees() {
        for (Employee employee:employees) {
            System.out.println(employee);



            if (employee instanceof Commissioned) {
                System.out.println(((Commissioned) employee).getSalary());
                System.out.println(((Commissioned) employee).getCommission());

            }

            else if (employee instanceof Salaried) {
                System.out.println(((Salaried) employee).getSalary());

            }
        }
    }


    private void addEmployee() {

        System.out.println("Tipo de empregado:\n" +
                            "1: Hourly\n" +
                            "2: Salaried\n" +
                            "3: Commissioned");

        option2 = GetInfo.getInstance().getInt();

        switch (option2){
            case 1:
                addHourly();
                break;
            case 2:
                addSalaried();
                break;
            case 3:
                addCommissioned();
                break;
            default:
                break;
        }

        System.out.println("Participa do sindicato?(1: Sim, 2: Não):");

        option2 = GetInfo.getInstance().getInt();
        switch (option2){
            case 1:
                addEmployeeToSyndicate(employeeID);
                syndicateID++;
                break;
            case 2:
                break;

            default:
                break;
        }


    }

    private void addEmployeeToSyndicate(int employeeID) {
        System.out.println("Informe a taxa mensal");
        monthlyFee = GetInfo.getInstance().getFloat();

        syndicates.add(new Syndicate(monthlyFee, employeeID, syndicateID));

    }



    private void addHourly() {

        employees.add(new Hourly.HourlyBuilder().askEmployeeInfo(employeeID,"semanal 1 sexta" ).build());

    }

    private void addHourly(Employee employee) {
        employees.add(new Hourly.HourlyBuilder().changeToHourly(employee).build());


    }

    private void addSalaried() {

        employees.add(new Salaried.SalariedBuilder().askEmployeeInfo(employeeID, "mensal $").build());

    }

    private void addSalaried(Employee employee) {

        employees.add(new Salaried.SalariedBuilder().changeToSalaried(employee).build());
    }

    private void addCommissioned() {

        employees.add(new Commissioned.CommissionedBuilder().askEmployeeInfo(employeeID, "bi-semanalmente").build());


    }

    private void addCommissioned(Employee employee) {
        employees.add(new Commissioned.CommissionedBuilder().changeToCommissioned(employee).build());

    }

    private void removeEmployee() {

        System.out.println("informe o ID do empregado que deseja remover:");

        ID = GetInfo.getInstance().getInt();


        for (Employee employee: employees) {

            if(employee.getID() == ID){
                employees.remove(employee);
                System.out.println("empregado removido.");
                return;
            }

        }

        System.out.println("empregado não encontrado.");

    }
}
