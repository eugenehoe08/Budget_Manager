package budget;


public class Product implements Comparable<Product> {

    String name;
    double price;
    Type type;

    public Product(String name, double price, Type type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, price);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int compareTo(Product o) {
        if (this.getPrice() == o.getPrice()) {
            return 0;
        } else if (this.getPrice() > o.getPrice()) {
            return 1;
        } else {
            return -1;
        }
    }
}
