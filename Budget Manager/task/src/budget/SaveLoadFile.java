package budget;

import java.io.*;
import java.util.Scanner;

public class SaveLoadFile {


    //Check if file exists

    public static void loadFile(User user) {
        try {
            File file = new File("purchases.txt");

            if (file.createNewFile()) {
                System.out.println("File created.");
            } else {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String[] purchased = scanner.nextLine().split(":");
                    if (purchased[0].startsWith("Balance")) {
//                        System.out.println("Balance");
                        user.setBalance(Double.parseDouble(purchased[1].split("\\$")[1]));
                    } else {
//                        System.out.println("Loading product to text file");
                        user.addPurchase(new Product(purchased[0], Double.parseDouble(purchased[1].split("\\$")[1]), Type.valueOf(purchased[2])));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveFile(User user) {
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream("purchases.txt", false))) {
            for (Product product : user.getProducts()
            ) {
//                System.out.println("Saving product to text file");
                printWriter.printf("%s:$%.2f:%s\n", product.getName(), product.getPrice(), product.getType().toString());
            }
            printWriter.printf("Balance:$%.2f", user.getBalance());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
