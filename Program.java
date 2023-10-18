/*
 * Author: Kevin Wong
 * Assignment: NAND2TETRIS Project 6
 * Date: 10/20/2023
 * Professor: Nima Davarpanah
 * Course: CS3650-01
 * File: Program.java
 * File Description: This Java file serves as the entry point for a program designed to 
 * convert a source file into a corresponding output file. It begins by checking if a valid source file is specified as a command-line 
 * argument and exits with an error message if not. It then determines the source file's name and path, constructs an output file path with 
 * a ".hack" extension, and creates the output file. The code measures the time taken for the translation process, initializes an "Assembler" 
 * object to perform the translation, and outputs a success message if the translation completes without errors. It also handles potential I/O 
 * exceptions and exits with an error message in such cases. Overall, this program is responsible for translating source code files into a specific 
 * output format while handling various error conditions.
 */

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class Program {
    public static void main(String[] args) {
        // Exit with error if no source file specified.
        if (args.length == 0) {
            System.err.println("No source file was specified.");
            System.exit(1);
        }

        // Exit with error if invalid source file specified.
        File sourceFile = new File(args[0].trim());
        if (!sourceFile.exists()) {
            System.err.println("Specified source file could not be found.");
            System.exit(2);
        }

        // Get output file.
        String sourceAbsolutePath = sourceFile.getAbsolutePath();
        String fileName = sourceFile.getName();
        int fileNameExtensionIndex = fileName.lastIndexOf(".");
        String fileNameNoExtension = fileName.substring(0, fileNameExtensionIndex);
        int fileNameIndex = sourceFile.getAbsolutePath().indexOf(sourceFile.getName());
        String sourceDirectory = sourceAbsolutePath.substring(0, fileNameIndex);
        String outputFilePath = sourceDirectory + fileNameNoExtension + ".hack";
        File outputFile = new File(outputFilePath);

        try {
            // Create output file on disk.
            if (outputFile.exists()) {
                outputFile.delete();
            }
            outputFile.createNewFile();

            // Start timer.
            long startTime = System.currentTimeMillis();

            // Translate source file.
            Assembler assembler = new Assembler(sourceFile, outputFile);
            assembler.translate();

            // Stop timer.
            long endTime = System.currentTimeMillis();
            long elaspedTime = endTime - startTime;

            // Output success message if no errors occur.
            StringWriter status = new StringWriter();
            status.append("Translation completed successfully on ");
            status.append(sourceAbsolutePath);
            status.append(" ==> ");
            status.append(outputFilePath);
            status.append(" in ");
            status.append(Long.toString(elaspedTime));
            status.append("ms.");
            System.out.println(status.toString());
        } catch (IOException e) {
            // Exit with error if I/O error occurs.
            System.err.println("An unknown I/O error occurred.");
            System.exit(3);
        }
    }
}