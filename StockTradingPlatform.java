import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void updatePrice() {
        // Simulate price change
        double change = (Math.random() - 0.5) * 10;
        price = Math.round((price + change) * 100.0) / 100.0;
    }
}

class Transaction {
    String stockSymbol;
    int quantity;
    double price;
    String type; // BUY or SELL

    Transaction(String stockSymbol, int quantity, double price, String type) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }
}

class User {
    String name;
    double balance;
    Map<String, Integer> portfolio = new HashMap<>();
    List<Transaction> history = new ArrayList<>();

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void buyStock(Stock stock, int qty) {
        double cost = stock.price * qty;
        if (balance >= cost) {
            balance -= cost;
            portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + qty);
            history.add(new Transaction(stock.symbol, qty, stock.price, "BUY"));
            System.out.println("Bought " + qty + " shares of " + stock.symbol + " at ₹" + stock.price);
        } else {
            System.out.println("Insufficient balance to buy.");
        }
    }

    public void sellStock(Stock stock, int qty) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);
        if (owned >= qty) {
            balance += stock.price * qty;
            portfolio.put(stock.symbol, owned - qty);
            history.add(new Transaction(stock.symbol, qty, stock.price, "SELL"));
            System.out.println("Sold " + qty + " shares of " + stock.symbol + " at ₹" + stock.price);
        } else {
            System.out.println("Not enough shares to sell.");
        }
    }

    public void showPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- Portfolio for " + name + " ---");
        System.out.println("Cash Balance: ₹" + balance);
        for (String symbol : portfolio.keySet()) {
            int qty = portfolio.get(symbol);
            Stock stock = market.get(symbol);
            double value = qty * stock.price;
            System.out.println(symbol + " - Qty: " + qty + " | Current Price: ₹" + stock.price + " | Value: ₹" + value);
        }
    }

    public void showTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : history) {
            System.out.println(t.type + " - " + t.stockSymbol + " x " + t.quantity + " @ ₹" + t.price);
        }
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Initialize sample stocks
        Map<String, Stock> market = new HashMap<>();
        market.put("TCS", new Stock("TCS", 3500.00));
        market.put("INFY", new Stock("INFY", 1500.00));
        market.put("RELI", new Stock("RELI", 2800.00));

        // Create a user
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        User user = new User(name, 50000.0);

        int choice;
        do {
            // Simulate market price update
            for (Stock s : market.values()) {
                s.updatePrice();
            }

            System.out.println("\n=== Stock Trading Menu ===");
            System.out.println("1. View Market Prices");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Market Prices ---");
                    for (Stock s : market.values()) {
                        System.out.println(s.symbol + ": ₹" + s.price);
                    }
                    break;

                case 2:
                    sc.nextLine(); // consume newline
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = sc.nextLine().toUpperCase();
                    if (market.containsKey(buySymbol)) {
                        System.out.print("Enter quantity to buy: ");
                        int qty = sc.nextInt();
                        user.buyStock(market.get(buySymbol), qty);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;

                case 3:
                    sc.nextLine(); // consume newline
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = sc.nextLine().toUpperCase();
                    if (market.containsKey(sellSymbol)) {
                        System.out.print("Enter quantity to sell: ");
                        int qty = sc.nextInt();
                        user.sellStock(market.get(sellSymbol), qty);
                    } else {
                        System.out.println("Invalid stock symbol.");
                    }
                    break;

                case 4:
                    user.showPortfolio(market);
                    break;

                case 5:
                    user.showTransactionHistory();
                    break;

                case 6:
                    System.out.println("Thank you for using Stock Trading Platform!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
