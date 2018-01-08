package service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DataSerializer {
    public static void serialize(String serializedString, String outputDestination) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outputDestination);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(serializedString);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
