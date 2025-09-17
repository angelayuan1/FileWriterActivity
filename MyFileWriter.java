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
    private static void printFileSize(String... fileNames) {
    long totalSize = 0;
    for (String fileName : fileNames) {
        File file = new File(fileName);

        if (file.exists()) {
            totalSize += file.length();

        }
    }

    System.out.println("Total size of all files: " + totalSize + " bytes");

}

    public static void main(String[] args) {
        // test each file individually
        printFileSize("testFile.txt"); // 13
        printFileSize("testFile2.txt"); // 39
        printFileSize("testFile3.txt"); // 15

        // all together
        printFileSize("testFile.txt", "testFile2.txt", "testFile3.txt"); // 67

    }
}