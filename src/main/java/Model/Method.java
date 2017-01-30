package Model;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by slima_000 on 24/12/2016.
 */
public interface Method {
    void readDataFromFileSrc() throws ParseException;

    void readDataFromFileDst() throws ParseException;

    void saveDataInFile() throws IOException, ParseException;
}
