import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private String customerName;
    private List<CartItem> items;
    private double total;
    private OrderStatus status;

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.total = 0.0;
    }

    public int getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public double getTotal() { return total; }
    public List<CartItem> getItems() { return items; }

    // Java 8: Using Stream API filter(), findFirst(), and Optional.ifPresentOrElse() to avoid boolean flags
    public void addItem(Product p, int qty) {
        if (status != OrderStatus.PENDING) return;

        items.stream()
                .filter(item -> item.getProduct().getId() == p.getId())
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + qty),
                        () -> items.add(new CartItem(p, qty))
                );

        calculateTotal();
    }

    // Java 8: Using Collection.removeIf() with Lambda expression to remove matching item
    public void removeItem(int productId) {
        if (status != OrderStatus.PENDING) return;
        items.removeIf(item -> item.getProduct().getId() == productId);
        calculateTotal();
    }

    // Java 8: Using Stream API mapToDouble() and sum() to compute total without manual accumulation loop
    public void calculateTotal() {
        this.total = items.stream()
                .mapToDouble(CartItem::calculateSubtotal)
                .sum();
    }

    // Java 8: Using forEach() with Lambda expression to display cart items
    public void displayOrder() {
        System.out.println("Order #" + orderId + " - Customer: " + customerName + " [" + status + "]");
        items.forEach(item -> System.out.println("  -> " + item.getProduct().getName()
                + " x" + item.getQuantity()
                + " = $" + item.calculateSubtotal()));
        System.out.println("Total: $" + total);
    }
}