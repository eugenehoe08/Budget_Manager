package budget;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<Product> products;
    private double balance;

    public User() {
        balance = 0;
        products = new ArrayList<>();
    }

    public void addIncome(String income) {
        balance += Double.parseDouble(income);
    }

    public void addPurchase(Product product) {
        products.add(product);
        balance -= product.getPrice();
    }

    public double getBalance() {
        return balance;
    }

    public double sumOfPurchases(Type type) {
        double total = 0;
        for (Product product : products
        ) {
            if (product.getType() == type) {
                total += product.getPrice();
            }
        }
        return total;
    }

    public void printProductsByType(Type type) {
        if (type == null) {
            for (Product product : products
            ) {
                System.out.println(product.toString());
            }
        } else {
            for (Product product : products
            ) {
                if (product.getType() == type) {
                    System.out.println(product.toString());
                }
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean checkEmpty(Type type) {
        for (Product product : products
        ) {
            if (product.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
