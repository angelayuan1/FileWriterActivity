import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

public class MyFileWriter {

    public static void method1() {
        String name = ".secret.txt";
        String password = "password";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(name))) {
            bufferedWriter.write(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void method2() {
        String folderName = ".topsecret";
        String fileName = "confidentialfile.dat";
        String confidentialInfo = "super top secret confidential info";
        File hiddenFolder = new File(folderName);
        hiddenFolder.mkdir();
        File confidentialFile = new File(hiddenFolder, fileName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(confidentialFile))) {
            bufferedWriter.write(confidentialInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Calculate and print the file size using the File class
    private static void printFileSize(String fileName) {
        File file = new File(fileName);
        System.out.println(file.length());
    }

    public static void main(String[] args) {
        printFileSize("testFile");
    }
}