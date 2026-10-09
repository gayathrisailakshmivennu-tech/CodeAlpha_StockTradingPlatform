
import java.util.Scanner;

public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);

    static String[] symbols = {"AAPL", "GOOG", "AMZN"};
    static double[] prices = {180.0, 200.0, 175.0};
    static int[] quantities = {0, 0, 0};

    static double balance = 10000.0;

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. View Stocks");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    viewStocks();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    viewPortfolio();
                    break;

                case 5:
                    System.out.printf(
                        "Balance: Rs. %.2f%n", balance
                    );
                    break;

                case 6:
                    System.out.println(
                        "Thank you for using the platform!"
                    );
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Enter a number from 1 to 6."
                    );
            }

        } while (choice != 6);

        sc.close();
    }

    static int readInt() {
        while (true) {
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid whole number: ");
            }
        }
    }

    static void viewStocks() {
        System.out.println("\n----- AVAILABLE STOCKS -----");

        for (int i = 0; i < symbols.length; i++) {
            System.out.printf(
                "%d. %s - Rs. %.2f%n",
                i + 1, symbols[i], prices[i]
            );
        }
    }

    static void buyStock() {
        viewStocks();

        System.out.print("Choose stock (1-3): ");
        int stock = readInt();

        if (stock < 1 || stock > 3) {
            System.out.println("Invalid stock selection.");
            return;
        }

        System.out.print("Enter quantity to buy: ");
        int quantity = readInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        int index = stock - 1;
        double total = prices[index] * quantity;

        if (total > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= total;
        quantities[index] += quantity;

        System.out.println("Stock purchased successfully!");
        System.out.println("Stock: " + symbols[index]);
        System.out.println("Quantity: " + quantity);
        System.out.printf("Total cost: Rs. %.2f%n", total);
        System.out.printf("Remaining balance: Rs. %.2f%n", balance);
    }

    static void sellStock() {
        viewPortfolio();

        System.out.print("Choose stock to sell (1-3): ");
        int stock = readInt();

        if (stock < 1 || stock > 3) {
            System.out.println("Invalid stock selection.");
            return;
        }

        int index = stock - 1;

        if (quantities[index] == 0) {
            System.out.println("You do not own this stock.");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int quantity = readInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        if (quantity > quantities[index]) {
            System.out.println("You do not own enough shares.");
            return;
        }

        double total = prices[index] * quantity;

        quantities[index] -= quantity;
        balance += total;

        System.out.println("Stock sold successfully!");
        System.out.println("Stock: " + symbols[index]);
        System.out.println("Quantity sold: " + quantity);
        System.out.printf("Amount received: Rs. %.2f%n", total);
        System.out.printf("Updated balance: Rs. %.2f%n", balance);
    }

    static void viewPortfolio() {
        System.out.println("\n----- YOUR PORTFOLIO -----");

        boolean hasStocks = false;
        double totalValue = 0.0;

        for (int i = 0; i < symbols.length; i++) {
            if (quantities[i] > 0) {
                double value = quantities[i] * prices[i];
                totalValue += value;
                hasStocks = true;

                System.out.printf(
                    "%s | Quantity: %d | Value: Rs. %.2f%n",
                    symbols[i], quantities[i], value
                );
            }
        }

        if (!hasStocks) {
            System.out.println("Your portfolio is empty.");
        }

        System.out.printf(
            "Total stock value: Rs. %.2f%n", totalValue
        );
        System.out.printf(
            "Available balance: Rs. %.2f%n", balance
        );
    }
}