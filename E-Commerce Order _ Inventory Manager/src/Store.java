import java.util.*;

public class Store {
    private List<Product> productList = new ArrayList<>();
    private Map<Integer, Product> productMap = new HashMap<>();
    private Set<String> categories = new HashSet<>();

    private Map<Integer, Order> allOrders = new HashMap<>();
    private Queue<Order> shippingQueue = new LinkedList<>();
    private List<Order> deliveredOrders = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    public boolean addProduct(Product p) {
        if (productMap.containsKey(p.getId())) {
            return false;
        }
        productList.add(p);
        productMap.put(p.getId(), p);
        categories.add(p.getCategory());
        return true;
    }

    private void deleteProductEverywhere(int id) {
        Product p = productMap.remove(id);
        if (p != null) {
            productList.remove(p);
        }
    }

    public boolean removeProduct(int id) {
        if (productMap.containsKey(id)) {
            deleteProductEverywhere(id);
            return true;
        }
        return false;
    }

    // Java 8: Using Iterable.forEach() with Method Reference for cleaner iteration
    public void displayAllProducts() {
        productList.forEach(System.out::println);
    }

    public Product searchProduct(int id) {
        return productMap.get(id);
    }

    // Java 8: Using forEach() with Lambda Expression
    public void showCategories() {
        categories.forEach(c -> System.out.println("- " + c));
    }

    // Java 8: Using Stream API sorted() to sort and display without modifying original list or using manual loops
    public void displayProductsByPrice() {
        productList.stream()
                .sorted()
                .forEach(System.out::println);
    }

    public boolean createOrder(int id, String customer) {
        if (allOrders.containsKey(id)) return false;
        Order order = new Order(id, customer);
        allOrders.put(id, order);
        return true;
    }

    public Order getOrder(int id) {
        return allOrders.get(id);
    }

    public boolean addToShipping(int orderId) {
        Order o = allOrders.get(orderId);
        if (o == null || o.getStatus() != OrderStatus.PENDING || o.getItems().isEmpty()) {
            return false;
        }
        if (shippingQueue.contains(o)) return false;

        o.setStatus(OrderStatus.SHIPPED);
        shippingQueue.add(o);
        return true;
    }

    public void shipNextOrder() {
        if (shippingQueue.isEmpty()) {
            System.out.println("Shipping list is empty.");
            return;
        }
        Order o = shippingQueue.peek();
        if (o.getItems().isEmpty()) {
            System.out.println("This order has no items and cannot be shipped.");
            return;
        }
        shippingQueue.poll();
        o.setStatus(OrderStatus.DELIVERED);
        deliveredOrders.add(o);
        System.out.println("Shipped order #" + o.getOrderId());
    }

    public boolean cancelOrder(int orderId) {
        Order o = allOrders.get(orderId);
        if (o == null || o.getStatus() == OrderStatus.DELIVERED || o.getStatus() == OrderStatus.CANCELLED) {
            return false;
        }
        if (o.getStatus() == OrderStatus.SHIPPED) {
            shippingQueue.remove(o);
        }
        o.setStatus(OrderStatus.CANCELLED);
        return true;
    }

    public void addReview(int pId, String customer, String text) {
        if (productMap.containsKey(pId)) {
            reviews.add(new Review(pId, customer, text));
        }
    }

    // Java 8: Using Stream API filter() and forEach() to replace manual conditional loop
    public void showReviewsForProduct(int pId) {
        reviews.stream()
                .filter(r -> r.getProductId() == pId)
                .forEach(System.out::println);
    }

    // Java 8: Using Collection.removeIf() and Stream API map() to replace manual Iterator loop
    public void removeOutOfStock() {
        productList.stream()
                .filter(p -> p.getStockQuantity() == 0)
                .map(Product::getId)
                .forEach(productMap::remove);

        productList.removeIf(p -> p.getStockQuantity() == 0);
    }

    // Java 8: Using Stream API with Comparator.comparingDouble() and reversed() to replace anonymous Comparator
    public void displayOrdersByTotal() {
        allOrders.values().stream()
                .sorted(Comparator.comparingDouble(Order::getTotal).reversed())
                .forEach(Order::displayOrder);
    }
}