package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slima_000 on 22/01/2017.
 */
public class Label {
    public static List listOfLabel = new ArrayList<>();
    private BufferedReader reader;
    private String line;

    public void readFIle() {
        listOfLabel.removeAll(listOfLabel);
        try {
            File file = new File("ListOfLabel.txt");
            if (file.exists() && file.length() != 0) {
                reader = new BufferedReader(new FileReader("ListOfLabel.txt"));
                if (!reader.ready()) {
                    throw new IOException();
                }
                if (listOfLabel.isEmpty()) {
                    while ((line = reader.readLine()) != null) {
                        listOfLabel.add(line);
                    }
                }
                reader.close();
            } else {
                System.out.println("No Lable");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
