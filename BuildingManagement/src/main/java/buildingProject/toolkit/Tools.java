/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buildingProject.toolkit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author flori
 */
public final class Tools {

    private static final Scanner INPUT = new Scanner(System.in);

    private Tools() {
    }

    public static int readNaturalNumber() {
        int number;
        do {
            number = readIntegerNumber();
            if (number < 0) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (number < 0);
        return number;
    }

    public static int readIntegerNumber() {
        int number = 0;
        boolean hasExceptionOccured;
        do {
            try {

                number = INPUT.nextInt();
                hasExceptionOccured = false;
            } catch (InputMismatchException e) {
                hasExceptionOccured = true;
                INPUT.nextLine();
            }
            if (hasExceptionOccured) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (hasExceptionOccured);
        INPUT.nextLine();
        return number;
    }

    public static double readPositiveDoubleNumber() {
        double number;
        do {
            number = readDoubleNumber();
            if (number < 0.0) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (number < 0.0);
        return number;
    }

    public static double readDoubleNumber() {
        double number = 0;
        boolean hasExceptionOccured;
        do {
            try {
                number = INPUT.nextDouble();
                hasExceptionOccured = false;
            } catch (InputMismatchException e) {
                hasExceptionOccured = true;
                INPUT.nextLine();
            }
            if (hasExceptionOccured) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (hasExceptionOccured);
        INPUT.nextLine();
        return number;
    }

    public static String getID(String prefix) {
        //to build the regex for the respective ID
        String regex = prefix + "\\d+";
        String stringId,format = prefix;
        if(prefix.equals("L\\d+R")){
            format = "L###R";
        }
        do {
            stringId = INPUT.nextLine();
            if (!stringId.matches(regex)) {
                System.out.printf("Wrong ID format.\n"
                        + "Should be in the form %s###.Re-enter:", format);
            }
        } while (!stringId.matches(regex));

        return stringId;
    }

    public static char getRoomType() {
        char type;
        System.out.print("Enter the type of the room.\n"
                + "A - Appartment\n"
                + "B - Bedroom\n"
                + "S - Studio\n\n"
                + "Enter letter corresponding to choice:");

        do {
            type = INPUT.nextLine().toLowerCase().charAt(0);
            if (!"asb".contains("" + type)) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (!"asb".contains("" + type));

        return type;

    }

    public static char yesOrNo() {
        char answer;
        System.out.print("Yes(Y) or No(N): ");
        do {
            answer = INPUT.nextLine().toLowerCase().charAt(0);
            if (!"ny".contains("" + answer)) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (!"ny".contains("" + answer));

        return answer;
    }

    public static Date getDate() throws ParseException {
        String format = "^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/[1-9][0-9]{3}$";
        String date;
        boolean isDateValid = false, isDateAfter = false;
        do {
            date = INPUT.nextLine();

            if (!date.matches(format)) {
                System.out.print("Wrong date format.\n"
                        + "The date entered should be in the form dd/MM/yyyy.\nRe-enter:");
            } else {
                String[] tokens = date.split("/");
                int day = Integer.parseInt(tokens[0]);
                int year = Integer.parseInt(tokens[2]);
                int month = Integer.parseInt(tokens[1]);
                isDateValid = isValidDate(day, (month - 1), year);
                if (!isDateValid) {
                    System.out.print("Not valid date.\n"
                            + "The date entered should be a valid one\n.Re-enter:");
                } else {
                    isDateAfter = new SimpleDateFormat("dd/MM/yyyy").parse(date).after(new Date());
                    if (isDateAfter) {
                        System.out.print("The date entered is after today's date.\n"
                                + "Re-enter:");
                    }
                }
            }

        } while (!date.matches(format) || !isDateValid || isDateAfter);

        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }
    
     public static void displayDate(Date date) {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String newDate = simpleDateFormat.format(date);
        System.out.print(newDate+"\t\t");
    }
    
     public static String newDisplayDate(Date date){
         String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
         return simpleDateFormat.format(date);
     }
    
    public static boolean isValidDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);

        try {
            calendar.set(year, month, day);
            calendar.getTime();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String getPhoneNumber() {
        String format = "(2[234][0-9]|[56789]{3}|222)-[0-9]{3}-[0-9]{3}$";
        String phoneNumber;
        do {
            phoneNumber = INPUT.nextLine();
            if (!phoneNumber.matches(format)) {
                System.out.print("Wrong phone number format.\n"
                        + "The phone number entered should be in the form ###-###-### and shuld be a valid one.Re-enter:");
            }
        } while (!phoneNumber.matches(format));
        return phoneNumber;
    }

    public static String getNationalIdNumber() {
        String format = "\\d+";
        String nationalIdNumber;
        do {
            nationalIdNumber = INPUT.nextLine();
            if (!nationalIdNumber.matches(format)) {
                System.out.print("Wrong national ID format format.\n"
                        + "The National Id number entered should consist just of numbers:");
            }
        } while (!nationalIdNumber.matches(format));
        return nationalIdNumber;
    }

    public static char maleOrFemale() {
        char gender;
        System.out.print("Male(M) or female(F): ");
        do {
            gender = INPUT.nextLine().toLowerCase().charAt(0);
            if (!"fm".contains("" + gender)) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (!"fm".contains("" + gender));

        return gender;
    }

    public static String getMaritalStatus() {
        int choice;
        String status = null;
        System.out.println("Enter: (1).Single or  (2).Married\n");
        do {
            choice = Tools.readIntegerNumber();
            if (choice < 0 || choice > 2) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (choice < 0 || choice > 2);
        switch (choice) {
            case 1:
                status = "Single";
                break;
            case 2:
                status = "Married";
                break;

        }
        return status;
    }

    public static String formatString(String string){
        StringBuilder stringBuilder = new StringBuilder(string.toLowerCase());
        stringBuilder.setCharAt(0, Character.toUpperCase(string.charAt(0)));
        
        return stringBuilder.toString();
        
    }

    public static Date getSimpleDate() throws ParseException{

        String format = "^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/[1-9][0-9]{3}$";
        String date;
        boolean isDateValid = false;
        do {
            date = INPUT.nextLine();

            if (!date.matches(format)) {
                System.out.print("Wrong date format.\n"
                        + "The date entered should be in the form dd/MM/yyyy.\nRe-enter:");
            }else{
                String[] tokens = date.split("/");
                int day = Integer.parseInt(tokens[0]);
                int year = Integer.parseInt(tokens[2]);
                int month = Integer.parseInt(tokens[1]);
                isDateValid = isValidDate(day, (month - 1), year);
                if (!isDateValid) {
                    System.out.print("Not valid date.\n"
                            + "The date entered should be a valid one\n.Re-enter:");
                }
            }
    }while(!date.matches(format) || !isDateValid);
    return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }
    
    public static char getBillType(){
        char type;
        System.out.print("Enter the type of the Bill.\n"
                + "E - Electricity\n"
                + "W - Water\n\n"
                + "Enter letter corresponding to choice:");

        do {
            type = INPUT.nextLine().toLowerCase().charAt(0);
            if (!"we".contains("" + type)) {
                System.out.print("Wrong input.Re-enter:");
            }
        } while (!"we".contains("" + type));

        return type;
    }

}
