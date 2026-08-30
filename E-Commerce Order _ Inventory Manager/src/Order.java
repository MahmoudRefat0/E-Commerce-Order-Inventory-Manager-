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

    public void addItem(Product p, int qty) {
        if (status != OrderStatus.PENDING) return;

        boolean found = false;
        for (CartItem item : items) {
            if (item.getProduct().getId() == p.getId()) {
                item.setQuantity(item.getQuantity() + qty);
                found = true;
                break;
            }
        }
        if (!found) {
            items.add(new CartItem(p, qty));
        }
        calculateTotal();
    }

    public void removeItem(int productId) {
        if (status != OrderStatus.PENDING) return;
        items.removeIf(item -> item.getProduct().getId() == productId);
        calculateTotal();
    }

    public void calculateTotal() {
        double sum = 0;
        for (CartItem item : items) {
            sum += item.calculateSubtotal();
        }
        this.total = sum;
    }

    public void displayOrder() {
        System.out.println("Order #" + orderId + " - Customer: " + customerName + " [" + status + "]");
        for (CartItem item : items) {
            System.out.println("  -> " + item.getProduct().getName() + " x" + item.getQuantity() + " = $" + item.calculateSubtotal());
        }
        System.out.println("Total: $" + total);
    }
}