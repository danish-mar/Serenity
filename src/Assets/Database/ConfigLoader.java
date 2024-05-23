package Assets.Database;

import Assets.Views.Dialogs.CreateNewConfig;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigLoader {

    // config path
    public static String configFilePath = "config/config.json";

    public static String fileToString(String configFilePath, String statusString){
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(configFilePath))){
            statusString = "Reading config/config.json";

            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            return content.toString();

        }catch (FileNotFoundException e){
            String jsonConfig = "";
            CreateNewConfig createNewConfig = new CreateNewConfig(jsonConfig);
            System.out.println("JsonConfig");
            return jsonConfig;
        }
    }

    ConfigLoader(){



    }

}
