import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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


    public String toString() {
        return "Hello World";
    }

    public static String hashFile(String filepath) {
        try (FileInputStream f = new FileInputStream(filepath)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bt = new byte[8192];
            int i;
            while ((i = f.read(bt)) != -1) {
                md.update(bt, 0, i);
            }
            byte[] hashedBytes = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b & 0xff));
            }
            return hexString.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found " + filepath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error while reading file: " + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: SHA-256 algorithm not available.");
            e.printStackTrace();
        }
        return null;
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