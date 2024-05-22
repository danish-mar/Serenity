//important imports


import Assets.ProductManager;
import Assets.Views.Auth.LoginView;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;


public class Main {
    public static void main(String[] args) {

        String password = "a4abce850ef890577152968be9b825d5fe5e8f853ccbcf52e1daa021bee41905";
        String username = "keqing";

        System.out.println("[Serenity] : Starting main thread \uD83D\uDC4B\uD83C\uDFFB");


        ProductManager productManager = new ProductManager();

        productManager.addNewProduct("Galaxy J7","Samsung",299.5f,4);
        productManager.addNewProduct("S22 Ultra", "Samsung", 799.4f, 5);


        // some more bunch
        // Adding a bunch of products
        productManager.addNewProduct("iPhone 13", "Apple", 999.99f, 8);
        productManager.addNewProduct("MacBook Pro", "Apple", 2399.99f, 3);
        productManager.addNewProduct("Pixel 6", "Google", 599.99f, 10);
        productManager.addNewProduct("ThinkPad X1", "Lenovo", 1299.99f, 4);
        productManager.addNewProduct("XPS 13", "Dell", 1199.99f, 6);
        productManager.addNewProduct("Galaxy Tab S7", "Samsung", 649.99f, 7);
        productManager.addNewProduct("Surface Pro 7", "Microsoft", 749.99f, 9);
        productManager.addNewProduct("AirPods Pro", "Apple", 249.99f, 20);
        productManager.addNewProduct("Galaxy Buds", "Samsung", 129.99f, 15);
        productManager.addNewProduct("PlayStation 5", "Sony", 499.99f, 5);
        productManager.addNewProduct("Xbox Series X", "Microsoft", 499.99f, 5);
        productManager.addNewProduct("Switch", "Nintendo", 299.99f, 12);
        productManager.addNewProduct("Echo Dot", "Amazon", 49.99f, 30);
        productManager.addNewProduct("Nest Thermostat", "Google", 129.99f, 10);
        productManager.addNewProduct("Chromebook", "Acer", 399.99f, 7);
        productManager.addNewProduct("Razer Blade 15", "Razer", 1899.99f, 2);
        productManager.addNewProduct("Watch Series 7", "Apple", 399.99f, 10);
        productManager.addNewProduct("Fitbit Charge 5", "Fitbit", 179.99f, 10);
        productManager.addNewProduct("Oculus Quest 2", "Meta", 299.99f, 5);

//         initially launch the login screen
       LoginView lnr = new LoginView(password,username, productManager);

        FlatMacLightLaf.setup();



    }
}