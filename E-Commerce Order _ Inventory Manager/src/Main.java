import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Store store = new Store();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- E-Commerce Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Display All Products");
            System.out.println("4. Search Product by ID");
            System.out.println("5. Show All Categories");
            System.out.println("6. Display Products Ordered by Price");
            System.out.println("7. Create Order");
            System.out.println("8. Add Item to Order");
            System.out.println("9. Remove Item from Order");
            System.out.println("10. Display Order");
            System.out.println("11. Add Order to Shipping List");
            System.out.println("12. Ship Next Order");
            System.out.println("13. Cancel Order");
            System.out.println("14. Search Order by ID");
            System.out.println("15. Add Review to Product");
            System.out.println("16. Show All Reviews for Product");
            System.out.println("17. Remove Out-of-Stock Products");
            System.out.println("18. Display Orders Ordered by Total");
            System.out.println("19. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            if (choice == 19) break;

            switch (choice) {
                case 1:
                    System.out.print("ID: "); int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Price: "); double price = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Category: "); String cat = sc.nextLine();
                    System.out.print("Stock: "); int stock = sc.nextInt();
                    if (!store.addProduct(new Product(id, name, price, cat, stock))) {
                        System.out.println("Error: Duplicate Product ID!");
                    }
                    break;
                case 2:
                    System.out.print("Enter Product ID to remove: ");
                    store.removeProduct(sc.nextInt());
                    break;
                case 3:
                    store.displayAllProducts();
                    break;
                case 4:
                    System.out.print("Product ID: ");
                    Product p = store.searchProduct(sc.nextInt());
                    System.out.println(p != null ? p : "Not Found!");
                    break;
                case 5:
                    store.showCategories();
                    break;
                case 6:
                    store.displayProductsByPrice();
                    break;
                case 7:
                    System.out.print("Order ID: "); int oId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Customer Name: "); String cName = sc.nextLine();
                    store.createOrder(oId, cName);
                    break;
                case 8:
                    System.out.print("Order ID: "); int ordId = sc.nextInt();
                    System.out.print("Product ID: "); int prId = sc.nextInt();
                    System.out.print("Qty: "); int q = sc.nextInt();
                    Order o = store.getOrder(ordId);
                    Product pr = store.searchProduct(prId);
                    if (o != null && pr != null) o.addItem(pr, q);
                    break;
                case 9:
                    System.out.print("Order ID: "); int rOrdId = sc.nextInt();
                    System.out.print("Product ID: "); int rPrId = sc.nextInt();
                    Order rOrder = store.getOrder(rOrdId);
                    if (rOrder != null) rOrder.removeItem(rPrId);
                    break;
                case 10:
                    System.out.print("Order ID: ");
                    Order dispO = store.getOrder(sc.nextInt());
                    if (dispO != null) dispO.displayOrder();
                    break;
                case 11:
                    System.out.print("Order ID to ship queue: ");
                    store.addToShipping(sc.nextInt());
                    break;
                case 12:
                    store.shipNextOrder();
                    break;
                case 13:
                    System.out.print("Order ID to cancel: ");
                    store.cancelOrder(sc.nextInt());
                    break;
                case 14:
                    System.out.print("Order ID: ");
                    Order ordFound = store.getOrder(sc.nextInt());
                    if (ordFound != null) ordFound.displayOrder();
                    break;
                case 15:
                    System.out.print("Product ID: "); int revPid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Name: "); String rName = sc.nextLine();
                    System.out.print("Comment: "); String comm = sc.nextLine();
                    store.addReview(revPid, rName, comm);
                    break;
                case 16:
                    System.out.print("Product ID: ");
                    store.showReviewsForProduct(sc.nextInt());
                    break;
                case 17:
                    store.removeOutOfStock();
                    System.out.println("Removed out of stock items.");
                    break;
                case 18:
                    store.displayOrdersByTotal();
                    break;
            }
        }
    }
}