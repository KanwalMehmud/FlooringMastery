/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Kanwal
 */
public class UserIOConsoleImpl implements UserIO {

    Scanner userInput = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        double FlooringMasteryData = userInput.nextDouble();
        return FlooringMasteryData;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        boolean keepGoing = true;
        double value = 0;
        while (keepGoing) {
            try {
                System.out.println(prompt + " " + min + " " + max + " ");
                String sc = userInput.nextLine();
                value = Integer.parseInt(sc);
                return value;
            } catch (Exception e) {
                System.out.println("Invalid selection. Please select from the given menu.");
            }
        }
        return value;
    }

    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        float FlooringMasteryData = userInput.nextFloat();
        return FlooringMasteryData;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt);
        float FlooringMasteryData = userInput.nextFloat();
        return FlooringMasteryData;
    }

    @Override
    public int readInt(String prompt) {
        boolean keepGoing = true;
        int value = 0;
        while (keepGoing) {
            try {
                System.out.println(prompt);
                String sc = userInput.nextLine();
                value = Integer.parseInt(sc);
                return value;
            } catch (Exception e) {
                System.out.println("Invalid selection. Please select from the given menu.");
            }
        }
        return value;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        boolean keepGoing = true;
        int value = 0;
        while (keepGoing) {
            try {
                System.out.println(prompt + " " + min + " " + max + " ");
                String sc = userInput.nextLine();
                value = Integer.parseInt(sc);
                return value;
            } catch (Exception e) {
                System.out.println("Invalid selection. Please select from the given menu.");
            }
        }
        return value;
    }

    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        Long vendingMachineData = userInput.nextLong();
        return vendingMachineData;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt);
        Long vendingMachineData = userInput.nextLong();
        return vendingMachineData;
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        String FlooringMasteryData = userInput.nextLine();
        

        return FlooringMasteryData;

    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        boolean keepGoing = true;
        BigDecimal value = new BigDecimal("0");
        while (keepGoing) {
            try {
                System.out.println(prompt);
                String sc = userInput.nextLine();
                value = new BigDecimal(sc);
                keepGoing = false;
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input.");
                keepGoing = true;
            }
        }
        return value;
    }

    @Override
    public LocalDate readDate(String prompt) {
        boolean keepGoing = true;
        LocalDate date = null;
        while (keepGoing) {
            try {
                System.out.println(prompt);
                String sc = userInput.nextLine();
                date = LocalDate.parse(sc, DateTimeFormatter.ofPattern("MMddyyyy"));

                keepGoing = false;
            } catch (Exception e) {
                System.out.println("Invalid date input.");

            }

        }
        return date;

    }

    @Override
    public LocalDate readDateEdit(String prompt) {
        boolean keepGoing = true;
        LocalDate date = null;

        while (keepGoing) {
            System.out.println(prompt);
            String sc = userInput.nextLine();
            if (sc.equals("")) {
                return null;
            }
            try {

                date = LocalDate.parse(sc, DateTimeFormatter.ofPattern("MMddyyyy"));

                keepGoing = false;
            } catch (Exception e) {
                System.out.println("Invalid date input.");

            }

        }
        return date;

    }

    @Override
    public BigDecimal readBigDecimalEdit(String prompt) {
        boolean keepGoing = true;
        BigDecimal value = new BigDecimal("0");

        System.out.println(prompt);
        String sc = userInput.nextLine();

        while (keepGoing) {
            if (sc.equals("")) {
                return new BigDecimal("-1");
            }
            try {
                value = new BigDecimal(sc);
                keepGoing = false;
                return value;
            } catch (Exception e) {
                System.out.println("Invalid input.");
                keepGoing = true;
            }
        }
        return value;
    }

}
