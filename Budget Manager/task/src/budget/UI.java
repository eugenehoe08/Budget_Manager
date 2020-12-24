package budget;

import java.lang.reflect.Array;
import java.util.*;

public class UI {
    Scanner scanner = new Scanner(System.in);
    User user = new User();

    public void showMainUI() {
        int choice;
        do {
            System.out.println("Choose your action:");
            System.out.println("1) Add income");
            System.out.println("2) Add purchase");
            System.out.println("3) Show list of purchases");
            System.out.println("4) Balance");
            System.out.println("5) Save");
            System.out.println("6) Load");
            System.out.println("7) Analyze (Sort)");
            System.out.println("0) Exit");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    showAddIncomeUI();
                    break;
                case 2:
                    showAddPurchaseUI();
                    break;
                case 3:
                    showListOfPurchasesUI();
                    break;
                case 4:
                    showBalanceUI();
                    break;
                case 5:
                    saveUI();
                    break;
                case 6:
                    loadUI();
                    break;
                case 7:
                    sortUI();
                    break;
                case 0:
                    System.out.println();
                    System.out.println("Bye!");
                default:
                    break;
            }
        } while (choice != 0);
    }

    private void showAddIncomeUI() {
        System.out.println();
        System.out.println("Enter income:");
        try {
            user.addIncome(scanner.nextLine());
            System.out.println("Income was added!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid integer.");
        }
        System.out.println();
    }

    private void showAddPurchaseUI() {
        String name;
        String price;
        Type type;
        while (true) {
            System.out.println();
            type = chooseTypeOfPurchase();
            if (type == null) {
                System.out.println();
                break;
            }
            System.out.println("\nEnter purchase name:");
            name = scanner.nextLine();
            System.out.println("Enter its price:");
            price = scanner.nextLine();
            try {
                user.addPurchase(new Product(name, Double.parseDouble(price), type));
                System.out.println("Purchase was added!");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid price");
            }
        }
    }

    private void showListOfPurchasesUI() {
        System.out.println();
        int choice;
        if (user.getProducts().isEmpty()) {
            System.out.println("Purchase list is empty\n");
        } else {
            do {
                System.out.println("Choose the type of purchase");
                System.out.println("1) Food");
                System.out.println("2) Clothes");
                System.out.println("3) Entertainment");
                System.out.println("4) Other");
                System.out.println("5) All");
                System.out.println("6) Back");
                choice = Integer.parseInt(scanner.nextLine());
                System.out.println();
                switch (choice) {
                    case 1:
                        printSumOfPurchases(false, Type.Food);
                        break;
                    case 2:
                        printSumOfPurchases(false, Type.Clothes);
                        break;
                    case 3:
                        printSumOfPurchases(false, Type.Entertainment);
                        break;
                    case 4:
                        printSumOfPurchases(false, Type.Other);
                        break;
                    case 5:
                        printSumOfPurchases(true, null);
                        break;
                    default:
                        break;
                }
                System.out.println();
            } while (choice < 6);
        }
    }

    private void printSumOfPurchases(boolean all, Type type) {
        double total = 0;
        if (all) {
            for (Type types : Type.values()
            ) {
                total += user.sumOfPurchases(types);
            }
        } else {
            total = user.sumOfPurchases(type);
        }
        user.printProductsByType(type);
        System.out.format("Total sum: $%.2f\n", total);
    }

    private void showBalanceUI() {
        System.out.println();
        System.out.format("Balance: $%.2f\n", user.getBalance());
        System.out.println();
    }

    private Type chooseTypeOfPurchase() {
        int choice;
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        System.out.println("5) Back");

        choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                return Type.Food;
            case 2:
                return Type.Clothes;
            case 3:
                return Type.Entertainment;
            case 4:
                return Type.Other;
            default:
                return null;
        }
    }

    private void saveUI() {
        System.out.println();
        SaveLoadFile.saveFile(user);
        System.out.println("Purchases were saved!");
        System.out.println();
    }

    private void loadUI() {
        System.out.println();
        SaveLoadFile.loadFile(user);
        System.out.println("Purchases were loaded!");
        System.out.println();
    }

    private void sortUI() {
        int choice;
        do {
            System.out.println();
            System.out.println("How do you want to sort?");
            System.out.println("1) Sort all purchases");
            System.out.println("2) Sort by type");
            System.out.println("3) Sort certain type");
            System.out.println("4) Back");

            choice = Integer.parseInt(scanner.nextLine());

            if (choice != 4) {
                switch (choice) {
                    case 1:
                        if (user.getProducts().isEmpty()) {
                            System.out.println("\nPurchase list is empty!");
                        } else {
                            sortPurchases(5);
                        }
                        break;
                    case 2:
                        sortByType();
                        break;
                    case 3:
                        sortPurchases(sortCertainTypeUI());
                        break;
                    default:
                        break;
                }
            }
        } while (choice != 4);
        System.out.println();
    }

    private void sortPurchases(int choice) {
        System.out.println();
        ArrayList<Product> products = new ArrayList<>();
        double total = 0;
        // 1 - food, 2 - clothes, 3 - entertainment, 4 - other, 5 - all
        switch (choice) {
            case 1:
                if (user.checkEmpty(Type.Food)) {
                    for (Product product : user.getProducts()
                    ) {
                        if (product.getType() == Type.Food) {
                            products.add(product);
                            total += product.getPrice();
                        }
                    }
                    System.out.println("Food:");
                }
                break;
            case 2:
                if (user.checkEmpty(Type.Clothes)) {
                    for (Product product : user.getProducts()
                    ) {
                        if (product.getType() == Type.Clothes) {
                            products.add(product);
                            total += product.getPrice();
                        }
                    }
                    System.out.println("Clothes:");
                }
                break;
            case 3:
                if (user.checkEmpty(Type.Entertainment)) {
                    for (Product product : user.getProducts()
                    ) {
                        if (product.getType() == Type.Entertainment) {
                            products.add(product);
                            total += product.getPrice();
                        }
                    }
                    System.out.println("Entertainment:");
                }
                break;
            case 4:
                if (user.checkEmpty(Type.Other)) {
                    for (Product product : user.getProducts()
                    ) {
                        if (product.getType() == Type.Other) {
                            products.add(product);
                            total += product.getPrice();
                        }
                    }
                    System.out.println("Other:");
                }
                break;
            case 5:
                if (!user.getProducts().isEmpty()) {
                    for (Product product : user.getProducts()
                    ) {
                        products.add(product);
                        total += product.getPrice();
                    }
                    System.out.println("All:");
                }
                break;
            default:
                break;
        }
        if (products.isEmpty()) {
            System.out.println("Purchase list is empty!");
        } else {
            Collections.sort(products, Collections.reverseOrder());
            for (Product product : products
            ) {
                System.out.println(product.toString());
            }
            System.out.printf("Total sum: $%.2f\n", total);
        }
    }

    private void sortByType() {
        Map<String, Double> map = new TreeMap<>();
        double value;
        for (Type type : Type.values()
        ) {
            map.put(type.toString(), (double) 0);
        }

        for (Product product : user.getProducts()
        ) {
            switch (product.getType()) {
                case Food:
                    value = map.get("Food");
                    value += product.getPrice();
                    map.replace("Food", value);
                    break;
                case Clothes:
                    value = map.get("Clothes");
                    value += product.getPrice();
                    map.replace("Clothes", value);
                    break;
                case Entertainment:
                    value = map.get("Entertainment");
                    value += product.getPrice();
                    map.replace("Entertainment", value);
                    break;
                case Other:
                    value = map.get("Other");
                    value += product.getPrice();
                    map.replace("Other", value);
                    break;
                default:
                    break;
            }
        }

        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        HashMap sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        System.out.println();
        System.out.println("Types:");
        value = 0;
        for (Object string : sortedMap.keySet()
        ) {
            System.out.format("%s - $%.2f\n", string, sortedMap.get(string));
            value += (Double) sortedMap.get(string);
        }
        System.out.format("Total sum: $%.2f\n", value);
//        System.out.println();
    }

    private int sortCertainTypeUI() {
        System.out.println();
        System.out.println("Choose the type of purchase");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
        return Integer.parseInt(scanner.nextLine());
    }
}
