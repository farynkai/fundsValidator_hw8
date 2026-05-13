package app;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        double balance = getBalance();

        Scanner scanner = new Scanner(System.in);
        boolean proceed = true;

        System.out.printf("Balance is USD %.2f.%n", balance);

        while (balance > 0 && proceed) {
            try {
                balance = processPurchase(balance, getAmount(scanner));
                printPurchaseSuccess(balance);
            } catch (FundsException ex) {
                System.out.println("Purchase failed. " + ex.getMessage());
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }

            if (balance > 0) {
                proceed = askToProceed(scanner);
            }
        }

        if (balance <= 0) {
            System.out.println("Balance is 0. Program finished.");
        } else {
            System.out.printf("Program finished. Final balance is USD %.2f.%n", balance);
        }
    }

    private static double processPurchase(double balance, double withdrawal) throws FundsException {
        validateAmount(balance, withdrawal);
        return calculateBalance(balance, withdrawal);
    }

    private static void printPurchaseSuccess(double updatedBalance) {
        System.out.printf("Funds are OK and purchase is paid. Your current balance is USD %.2f%n", updatedBalance);
    }

    private static double getBalance() {
        return 1000.00;
    }

    private static double getAmount(Scanner scanner) {
        System.out.print("Enter purchase amount, USD: ");
        return scanner.nextDouble();
    }

    private static void validateAmount(double balance, double withdrawal) throws FundsException {
        if (withdrawal <= 0) {
            throw new FundsException("Purchase amount must be greater than 0.");
        }

        if (withdrawal > balance) {
            throw new FundsException("Insufficient funds!");
        }
    }

    private static double calculateBalance(double balance, double withdrawal) {
        return balance - withdrawal;
    }

    private static boolean askToProceed(Scanner scanner) {
        System.out.print("Do you want to proceed? Enter yes/no: ");
        String answer = scanner.next();

        return answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y");
    }
}
