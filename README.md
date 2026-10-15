# 🛒 E-Commerce Order & Inventory Manager

A console-based Java application for managing an online store's products, categories, stock, orders, shipping and customer reviews, built on the **Java Collections Framework**.

## Features
- Add, remove, search and list products, with stock tracking
- Browse unique product categories and sort products by price
- Create orders, add/remove items, and calculate totals
- Shipping list (FIFO): add orders and ship the next one
- Order lifecycle: `PENDING → SHIPPED → DELIVERED` (or cancelled)
- Add product reviews and list all reviews for a product
- Remove out-of-stock products safely while iterating
- Sort orders by total value

## Concepts used
| Concept | Where |
| --- | --- |
| `HashMap` | Products and orders indexed by ID |
| `HashSet` | Unique categories |
| `ArrayList` | Cart items and reviews |
| `Queue` (`LinkedList`) | Shipping list |
| `Comparable` / `Comparator` | Sorting products by price and orders by total |
| `Iterator` | Removing out-of-stock products during iteration |
| `Optional` | Safe searching |
| Enums | `OrderStatus` |

## Project structure
```
E-Commerce Order _ Inventory Manager/src
├── Main.java        # Console menu (19 options)
├── Store.java       # Business logic
├── Product.java
├── CartItem.java
├── Order.java
├── OrderStatus.java
└── Review.java
```

## How to run
1. Open the `E-Commerce Order _ Inventory Manager` folder in IntelliJ IDEA (JDK 17+).
2. Run `Main.java`.

Or from the terminal:
```bash
cd "E-Commerce Order _ Inventory Manager/src"
javac *.java && java Main
```
