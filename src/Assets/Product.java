package Assets;

public class Product {


    int id;
    String name;
    String brand;
    int quantity;
    float price;

    public Product(int id, String name, String brand, int quantity, float price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
    }

    float getPrice(){
        return this.price;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    //setters


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
