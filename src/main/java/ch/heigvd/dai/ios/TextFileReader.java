package ch.heigvd.dai.ios;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TextFileReader {

    public static BufferedReader reader(String filename, Charset encoding) {
        File f = new File(filename);
        if(!f.exists() || !f.isFile()){
            return null;
        }
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename, encoding));
            return reader;
        } catch (FileNotFoundException e) {
            // TODO
        } catch (IOException e) {
            // TODO
            System.err.println("Erreur lors de la lecture du fichier");
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
