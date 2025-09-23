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

    public static void testHashFileEmptyFiles() { //tested if the hashFile worked for empty files, ended up producing correct hash for nothing in the file
        try {
            File testFile = File.createTempFile("testFile1", ".txt");
            String hash = hashFile(testFile.getAbsolutePath());
            String h = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

            System.out.println("Result: " + hash);
            System.out.println("Expected: " + h);
            System.out.println(hash.equals(h));

        } catch (IOException e) {
            System.out.println("Error creating empty test file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testHashFileLargeFiles() { //check to see if it can hash big files, successfully produced a hash 
        try {
            File testFile = File.createTempFile("testFile2", ".txt");
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testFile))) {
                for (int i = 0; i < 1000000; i++) {
                    bufferedWriter.write("This is a line in a large file for hashing.\n");
                }
            }
            String hash = hashFile(testFile.getAbsolutePath());
            System.out.println(hash);
            System.out.println("PASSED (did not run out of memory)");

        } catch (IOException e) {
            System.out.println("Error creating large test file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testHashFileSpecialChars() {// check to see if can hash emojis and chinese characters. successful (same as online hashing website)
        try {
            File testFile = File.createTempFile("testFile3", ".txt");
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testFile))) {
                bufferedWriter.write("Hello World 🌍🐱🐶 你好世界");
            }
            String hash = hashFile(testFile.getAbsolutePath());
            System.out.println(hash);
            System.out.println("PASSED (emojis & other languages work)");

        } catch (IOException e) {
            System.out.println("Error creating special chars test file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testHashFileNonExistent() {//produced an error saying fake.txt not found, which is what should happen
        String fake = "fake.txt";
        String hash = hashFile(fake);

        System.out.println("Fake file test:");
        if (hash == null) System.out.println("PASSED");
        else System.out.println("FAILURE");
    }

    public static void main(String[] args) {
        // test each file individually
        printFileSize("testFile.txt"); // 13
        printFileSize("testFile2.txt"); // 39
        printFileSize("testFile3.txt"); // 15

        // all together
        printFileSize("testFile.txt", "testFile2.txt", "testFile3.txt"); // 67

        testHashFileEmptyFiles();
        testHashFileLargeFiles();
        testHashFileSpecialChars();
        testHashFileNonExistent();
    }
}