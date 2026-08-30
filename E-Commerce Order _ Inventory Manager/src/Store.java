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

    public void displayAllProducts() {
        for (Product p : productList) System.out.println(p);
    }

    public Product searchProduct(int id) {
        return productMap.get(id);
    }

    public void showCategories() {
        for (String c : categories) System.out.println("- " + c);
    }

    public void displayProductsByPrice() {
        List<Product> temp = new ArrayList<>(productList);
        Collections.sort(temp);
        for (Product p : temp) System.out.println(p);
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

    public void showReviewsForProduct(int pId) {
        for (Review r : reviews) {
            if (r.getProductId() == pId) System.out.println(r);
        }
    }

    public void removeOutOfStock() {
        Iterator<Product> it = productList.iterator();
        while (it.hasNext()) {
            Product p = it.next();
            if (p.getStockQuantity() == 0) {
                productMap.remove(p.getId());
                it.remove(); // Safe removal inside loop
            }
        }
    }

    public void displayOrdersByTotal() {
        List<Order> sorted = new ArrayList<>(allOrders.values());
        sorted.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o2.getTotal(), o1.getTotal());
            }
        });
        for (Order o : sorted) o.displayOrder();
    }
}