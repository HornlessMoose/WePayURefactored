package WePayURefactored.utility;

import java.util.Scanner;

public class GetInfo {
    Scanner input = new Scanner(System.in);

    public String getString() {

        return input.nextLine();
    }

    public int getInt(){
        return Integer.parseInt(input.nextLine());
    }
    public float getFloat(){
        return Float.parseFloat(input.nextLine());
    }

    private static GetInfo instance;

    public static synchronized GetInfo getInstance(){
        if(instance == null){
            instance = new GetInfo();
        }
        return instance;
    }
}