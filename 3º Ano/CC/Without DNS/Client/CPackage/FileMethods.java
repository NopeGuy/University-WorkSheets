package CPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class FileMethods {

    public static int fileSplitter(String fileName, String filePath, int numBlocks) {
        String outputDir = "./Blocks/";
        int blockSize = 962;

        File inputFile = new File(filePath);

        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byte[] buffer = new byte[blockSize];
            int bytesRead;
            int blockNumber = 1;

            for (int i = 0; i < numBlocks; i++) {
                bytesRead = fis.read(buffer, 0, blockSize);

                String blockFileName = String.format("%s«%04d", fileName, blockNumber);
                try (FileOutputStream fos = new FileOutputStream(outputDir + blockFileName)) {
                    fos.write(buffer, 0, bytesRead);
                }

                blockNumber++;
            }
            return numBlocks;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void fragmentAndSendInfo(OutputStream outputStream) throws IOException {
        StringBuilder messageBuilder = new StringBuilder();
        StringBuilder messageBuilderBlocks = new StringBuilder();
        String message;

        File clientFilesFolder = new File("ClientFiles");
        File[] files = clientFilesFolder.listFiles();

        // First loop: Fragmentation without considering blocks
        for (File file : files) {
            if (file.isFile() && !file.getName().contains("«")) {
                String fileName = file.getName();
                String path = "./ClientFiles/" + fileName;
                Path pathFile = Paths.get("./ClientFiles/" + fileName);
                long fileSize = Files.size(pathFile);

                int numBlocks;
                if (fileSize % 962 == 0) {
                    numBlocks = (int) (fileSize / 962);
                } else {
                    numBlocks = ((int) (fileSize / 962)) + 1;
                }

                fileSplitter(fileName, path, numBlocks);

                // Append to the appropriate message builder
                messageBuilder.append(fileName).append("!").append(numBlocks).append(":");
            }
        }

        // Send message for fragmentation
        if (messageBuilder.length() > 0) {
            messageBuilder.deleteCharAt(messageBuilder.length() - 1);
            message = "1;" + messageBuilder.toString() + "$";
            byte[] ack = message.getBytes(StandardCharsets.UTF_8);
            outputStream.write(ack, 0, ack.length);
            outputStream.flush();
        }

        // Second loop: Search for blocks
        File clientBlocksFolder = new File("Blocks");
        File[] blockFiles = clientBlocksFolder.listFiles();

        // Counter for blocks sent
        int blocksSent = 0;

        for (File blockFile : blockFiles) {
            if (blockFile.isFile() && blockFile.getName().contains("«")) {
                // Handle files with '«' in their name for block information
                messageBuilderBlocks.append(blockFile.getName()).append("|");

                // Increment the counter
                blocksSent++;

                // If 10 blocks have been added to the message, send and reset the builder
                if (blocksSent == 10) {
                    // Send message for block information
                    if (messageBuilderBlocks.length() > 0) {
                        messageBuilderBlocks.deleteCharAt(messageBuilderBlocks.length() - 1);
                        message = "2;" + messageBuilderBlocks.toString() + "$";
                        byte[] ack2 = message.getBytes(StandardCharsets.UTF_8);
                        outputStream.write(ack2, 0, ack2.length);
                        outputStream.flush();

                        // Reset the builder and counter
                        messageBuilderBlocks = new StringBuilder();
                        blocksSent = 0;
                    }
                }
            }
        }

        // Send the remaining blocks if any
        if (messageBuilderBlocks.length() > 0) {
            messageBuilderBlocks.deleteCharAt(messageBuilderBlocks.length() - 1);
            message = "2;" + messageBuilderBlocks.toString() + "$";
            byte[] ack2 = message.getBytes(StandardCharsets.UTF_8);
            outputStream.write(ack2, 0, ack2.length);
            outputStream.flush();
        }

        // Notify if no files found in the 'ClientFiles' folder
        if (files.length == 0) {
            System.out.println("No files found in the 'ClientFiles' folder.");
        }
    }

    public static Boolean recreateFile(String fileName, int numBlocks) {
        String inputDir = "./Blocks/";
        String outputDir = "./ClientFiles/";

        // Check if files with names from "filename«0001" up to "filename«blocknumber"
        // are present
        boolean allBlocksPresent = true;
        for (int i = 1; i <= numBlocks; i++) {
            String blockFileName = String.format("%s«%04d", fileName, i);
            File blockFile = new File(inputDir + blockFileName);
            if (!blockFile.exists()) {
                allBlocksPresent = false;
                break;
            }
        }

        if (allBlocksPresent) {
            try (FileOutputStream fos = new FileOutputStream(outputDir + fileName)) {
                for (int i = 1; i <= numBlocks; i++) {
                    String blockFileName = String.format("%s«%04d", fileName, i);
                    try (FileInputStream fis = new FileInputStream(inputDir + blockFileName)) {
                        byte[] buffer = new byte[962];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
                System.out.println("Download successful.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return allBlocksPresent;
    }

    // // Delete all blocks of the file
    // for (File blockFile : files) {
    // if (!blockFile.delete()) {
    // System.err.println("Failed to delete block file: " + blockFile.getName());
    // }
    // }

    public static int findNullByteIndex(byte[] array) {
        int index = 0;
        while (index < array.length && array[index] != 0) {
            index++;
        }
        return index;
    }

    public static byte[] generateMD5(byte[] data, int length) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data, 0, length); // Update with only the actual data
        return md.digest();
    }
}
