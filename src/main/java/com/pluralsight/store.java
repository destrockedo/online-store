package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Learners will complete the TODO sections to make the program work.
 * <p>
 * What is already done for you: the two lists, the Home Screen loop and
 * the method headers. Each TODO says what that method has to do.
 * Product.java is complete; products.csv sits at the project root.
 * You will need extra imports (for example java.io.BufferedReader,
 * java.io.FileReader, java.io.IOException) as you fill the TODOs in.
 */
public class store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<product> inventory = new ArrayList<>();
        ArrayList<product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: SKU|Product Name|Price|Department)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    public static void loadInventory(String fileName, ArrayList<product> inventory) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            reader.readLine(); //skips header in procusts csv
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                //Split because reader reads it as one long string which is also why parse is needed

                String sku = parts[0];
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]); //changing price from string to double
                String department = parts[3];
                product item = new product(sku, productName, price, department);

                inventory.add(item); //reads every line and add every product
            }
        } catch (IOException e) {
            System.out.println("Erroe loading inventory: ");
        }
    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<product> inventory, ArrayList<product> cart, Scanner scanner) {
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println(inventory.get(i)); //get(i) instead of [i] for arrayList
        }

        System.out.println("Enter ID of product desired to add to cart");
        String sku = scanner.nextLine();
        //finds product
        product selectedProduct = findProductById(sku, inventory); //works because sku as passing in id peramemeter

        if (selectedProduct != null) {
            cart.add(selectedProduct);//adds product to cart array
            System.out.println(selectedProduct + " Has been added to your cart");
        } else {
            System.out.println("Your out of luck bozo");
        }
    }


    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<product> cart, Scanner scanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            System.out.println(cart.get(i));
            total = total + cart.get(i).getPrice(); //adds by loops
        }
        System.out.println("Total: $" + total);
        System.out.println("If your finished Select C to checkout or X to return");
        String CX = scanner.nextLine();
        if (CX.equalsIgnoreCase("C")){
            checkOut(cart, total, scanner);

        }else{
            System.out.println("returning");;
        }

    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<product> cart, double total, Scanner scanner) {
        // TODO: implement steps listed above
        System.out.println("Are you absoulutly sure this is what you want to buy? Y/N");
        String YN = scanner.nextLine();
        if (YN.equalsIgnoreCase("Y")){
            //proceed
            System.out.println("How much are you paying?");
            double money = scanner.nextDouble();
            double change = money - total;
            if (money >= total){
                System.out.println("Here's Your Change  " + change);


                System.out.println(" RECEIPT ");

                for (int i = 0; i < cart.size(); i++) {
                    System.out.println(cart.get(i)); //prints everything checkout
                }

                System.out.println("Total: $" + total); // totall of all itemss
                System.out.println("Paid: $" + money); // Total munyun spent
                System.out.println("Change: $" + change);// change from munyun spent
                cart.clear();

            }else {
                System.out.println("Your Broke get out of my swamp");
                cart.clear();

            }
        } else {
            System.out.println("returning");
        }


    }


    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static product findProductById(String id, ArrayList<product> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            // if inventory = the entered sku stored by getsku print/retrun that item to the method displayproduct
            if (inventory.get(i).getSku().equals(id)) {
                //sku gets pased to id peramenter whichi is why it works in display products "findProductById(sku, inventory);"
                return inventory.get(i);
            }

        }
        return null;
    }
}

