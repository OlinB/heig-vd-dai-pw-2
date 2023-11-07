package ch.heigvd.dai.string;

import ch.heigvd.dai.ios.TextFileReader;
import ch.heigvd.dai.ios.TextFileWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;

public class ReplaceWord {

    public static String replaceInString(String text, String word, String replacement) {
        return text.replaceAll(word, replacement);
    }

    public static void replaceInFile(String inputFilename, String outputFilename, String word, String replacement) {
        String line;
        String newLine;
        try(BufferedReader reader = TextFileReader.reader(inputFilename, StandardCharsets.UTF_8);
            BufferedWriter writer = TextFileWriter.writer(outputFilename, StandardCharsets.UTF_8)) {
            while ((line = reader.readLine()) != null) {
                newLine = replaceInString(line, word, replacement);
                writer.write(newLine);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
