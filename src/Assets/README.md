# Serenity

📦 **Serenity** is a billing application built in Java. It provides a robust and easy-to-use platform for managing products, inventory, and billing operations.

## Features

- 📋 **Add New Products**: Easily add new products with details such as name, brand, price, and quantity.
- 📝 **Update Products**: Update product details including price, quantity, and name.
- 🗑️ **Delete Products**: Remove products from the inventory.
- 📈 **View Inventory**: List all products in a well-organized manner with detailed information.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- An IDE or text editor (e.g., IntelliJ IDEA, Eclipse, VS Code)

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/danish-mar/Serenity.git
    ```

2. Open the project in your preferred IDE.

3. Compile and run the project.

### Usage

Here's an example of how to use the `ProductManager` class to manage products:

```java
public class Main {
    public static void main(String[] args) {
        ProductManager manager = new ProductManager();
        
        // Adding products
        manager.addNewProduct("S22 Ultra", "Samsung", 799.4f, 5);
        manager.addNewProduct("iPhone 13", "Apple", 999.99f, 8);
        manager.addNewProduct("MacBook Pro", "Apple", 2399.99f, 3);
        
        // List all products
        manager.listAllProducts();
    }
}
```

### Methods

- `addNewProduct(String name, String brand, float price, int availableQuantity)`
- `deleteProduct(int id)`
- `updateProduct(int id, Product updatedProduct, Product oldProduct)`
- `updateStock(int id, int newStock)`
- `listAllProducts()`

### Example

```java
// Adding products
manager.addNewProduct("Pixel 6", "Google", 599.99f, 10);
manager.addNewProduct("ThinkPad X1", "Lenovo", 1299.99f, 4);
manager.addNewProduct("AirPods Pro", "Apple", 249.99f, 20);

// Listing all products
manager.listAllProducts();
```

## Contributing

Contributions are welcome! Please fork this repository and submit pull requests.

## License

This project is licensed under the MIT License.

## Contact

For any inquiries or issues, please open an issue on this repository or contact the author.

---

👨‍💻 **Author**: danish-mar
