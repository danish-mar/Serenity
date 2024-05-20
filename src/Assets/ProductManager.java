package Assets;

import java.util.ArrayList;
import java.util.Comparator;

public class ProductManager {


    private ArrayList<Product> inventory;
    int productCount;

    public ProductManager(){
        inventory = new ArrayList<>();
        inventory.size();
    }




    // C - operations : 11:22 pm completed
    public int addNewProduct(String name, String brand, float price, int availabeQuantity){

        Product tempProduct = new Product(0, name, brand, availabeQuantity, price);

        // duplicate product checking loop
        for(int i = 0; i < inventory.size(); i++){


            Product listProductPlaceHolder = inventory.get(i);

            //checks for any matching info
            if(listProductPlaceHolder.getName().equals(tempProduct.getName())) {
                System.err.println("[Serenity]->(Core) : Product " + tempProduct.getName() + " Already exists");

                return 202;
                // returns 202 if there is a duplicate
            }
        }
        inventory.add(tempProduct);
        tempProduct.setId(productCount++);
        System.out.println("[Serenity]->(Core) : Product " + name + " has been added into the inventory");
        return 200;
    }

    public int addNewProduct(Product productToBeAdded){
        if(inventory.contains(productToBeAdded)){
            System.err.println("[Serenity]->(Core) : The product " + productToBeAdded.getName() + "..." + " is already added in inventory");
            return 202;
        }else{
            inventory.add(productToBeAdded);
            productCount++;
            System.out.println("[Serenity]->(Core) : added successfully");
            return 200;
        }
    }


    // R Operations - 11:22 PM
    public int deleteProduct(int id) {
        for (int i = 0; i < inventory.size(); i++) {
            Product listProductPlaceholder = inventory.get(i);
            if (listProductPlaceholder.getId() == id) {
                System.out.println("[Serenity]->(Core) : removed product " + listProductPlaceholder.getName());
                inventory.remove(i);
                reassignProductIds();  // Reassign IDs after deletion
                return 200; // remove the product from the list and exit
            }
        }
        System.err.println("[Serenity]->(Core) : Product with id " + id + " was not found in the inventory");
        return 201; // prints the error and pass the error message
    }

    private void reassignProductIds() {
        for (int i = 0; i < inventory.size(); i++) {
            inventory.get(i).setId(i);
        }
        productCount = inventory.size();
    }


    public int deleteProduct(Product productToBeDeleted){
        if(inventory.contains(productToBeDeleted)){
            inventory.remove(productToBeDeleted);
            System.out.println("[Serenity]->(Core) : the product " + productToBeDeleted.getName() + " is removed from the inventory");
            return 200;
        }else{
            System.err.println("[Serenity]->(Core) : the product " + productToBeDeleted.getName() + " was not found");
            return 201;
        }
    }



    // U - operations

    public int updateProduct(int id, Product updatedProduct, Product oldProduct){
        //removed the previous product
        if(inventory.contains(oldProduct)){
            inventory.remove(oldProduct);
            inventory.add(updatedProduct);
            System.out.println("[Serenity]->(Core) : the product " + oldProduct.getName() + " is updated");
            inventory.sort(Comparator.comparingInt(Product::getId));
            return 200;
        }else{
            System.err.println("[Serenity]->(Core) : the product " + oldProduct.getName() + " was not found");
            return 201;
        }
    }



    public int updateStock(Product productToBeStocked, int qty){
        if(inventory.contains(productToBeStocked)){
            if(qty <= 0){
                System.err.println("[Serenity]->(Core) : Invalid value " + qty);
                return 201;
            }else{



                // newer method
                int index = inventory.indexOf(productToBeStocked);
                Product tempProductHolder = inventory.get(index);

                inventory.remove(tempProductHolder);

                //duplication of updated product
                productToBeStocked.setQuantity(qty);
                inventory.add(productToBeStocked);


                System.out.println("[Serenity]->(Core) : stocks updated ");
                return 200;
            }
        }else{
            return 201;
        }

    }

    public int updateStock(int id, int newStock){
        if(inventory.size() < id){
            System.err.println("[Serenity]->(Core) : invalid offset " + id + " size of inventory " + inventory.size());
            return 201;
        }else{
            Product oldProductHolder = inventory.get(id);
            int oldStock = oldProductHolder.getQuantity();
            oldProductHolder.setQuantity(newStock);
            System.out.println("[Serenity]->(Core) : the stocks has been updated " + oldProductHolder.getName() + "->" + oldStock + " to " + newStock);
            return 200;
        }
    }

    public int updateProductById(int id, float price){
        if(inventory.size() < id){
            System.err.println("[Serenity]->(Core) : invalid offset " + id + " size of inventory " + inventory.size());
            return 201;
        }else{
            Product oldProductPlaceHolder = inventory.get(id);
            float oldProductPrice = oldProductPlaceHolder.getPrice();
            oldProductPlaceHolder.setPrice(price);
            System.out.println("[Serenity]->(Core) : the price has been updated " + oldProductPlaceHolder.getName() + "->" + oldProductPrice + " to " + price);
            return 200;
        }
    }

    public int updateProductById(int id, String name){
        if(inventory.size() < id){
            System.err.println("[Serenity]->(Core) : invalid offset " + id + " size of inventory " + inventory.size());
            return 201;
        }else{
            Product oldProductPlaceHolder = inventory.get(id);
            String oldProductName = oldProductPlaceHolder.getName();
            oldProductPlaceHolder.setName(name);
            System.out.println("[Serenity]->(Core) : the price has been updated " + oldProductName + " to " + oldProductPlaceHolder.getName());
            return 200;
        }
    }

    public Product getProductById(int id){
        if(id >= getProductCount()){
            System.out.println("[Serenity]->(Core) : invalid offset" + id + " size of memory " + inventory.size());
            return new Product(0,null,null,1,1);
        }else{
            return inventory.get(id);
        }
    }

    // D operations

    public ArrayList<Product> getInventory(){
        return this.inventory;
    }

    public int getProductCount() {
        return productCount;
    }

    public void listAllProducts() {
        if (inventory.isEmpty()) {
            System.out.println("📦 Inventory is empty. No products to display.");
            return;
        }

        System.out.println("📋 Here are the products in the inventory:");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("| 🆔 ID | 📛 Name          | 🏷️ Brand   | 💲 Price  | 📦 Quantity |");
        System.out.println("-------------------------------------------------------------------");

        for (Product product : inventory) {
            System.out.printf("| %4d | %-17s | %-10s | $%7.2f | %10d |\n",
                    product.getId(), product.getName(), product.getBrand(), product.getPrice(), product.getQuantity());
        }

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Total Products: " + productCount);
    }

}
