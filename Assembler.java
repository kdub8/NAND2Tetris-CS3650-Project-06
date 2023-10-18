/*
 * Author: Kevin Wong
 * Assignment: NAND2TETRIS Project 6
 * Date: 10/20/2023
 * Professor: Nima Davarpanah
 * Course: CS3650-01
 * File: Assembler.java
 * File Description: This file defines an "Assembler" class, which is designed to convert assembly code into machine language 
 * It takes as input an assembly code file and an output target file, utilizing components like 
 * a `Parser` for reading and interpreting assembly code, a `SymbolTable` for managing symbols and their addresses, and a `Code` component 
 * for encoding machine language instructions. The process involves two primary phases: first, it scans the assembly code to record label addresses, 
 * and second, it translates the assembly code line by line, parsing each instruction into A-Instructions for constants and variables, and 
 * C-Instructions for computation. These instructions are formatted into binary machine code and written to the target file. 
 * The class is responsible for converting assembly code into machine code, managing symbols, and ensuring the correct translation of instructions, 
 * while also handling input/output operations.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class Assembler {
    private File assemblyCode;
    private BufferedWriter machineCode;
    private Code encoder;
    private SymbolTable symbolTable;

    public Assembler(File source, File target) throws IOException {
        this.assemblyCode = source;

        // Create buffered writer to write machine code to the target file.
        FileWriter fw = new FileWriter(target);
        this.machineCode = new BufferedWriter(fw);

        // Initialize assembler components, including encoder and symbol table.
        this.encoder = new Code();
        this.symbolTable = new SymbolTable();
    }

    // Translate assembly file to machine language.
    public void translate() throws IOException {
        this.recordLabelAddress();
        this.parse();
    }

    // Step 1: Record labels and their addresses.
    private void recordLabelAddress() throws IOException {
        Parser parser = new Parser(this.assemblyCode);
        while (parser.hasMoreCommands()) {
            parser.advance();

            CommandType commandType = parser.commandType();

            if (commandType.equals(CommandType.L_COMMAND)) {
                String symbol = parser.symbol();
                int address = this.symbolTable.getProgramAddress();
                this.symbolTable.addEntry(symbol, address);
            } else {
                this.symbolTable.incrementProgramAddress();
            }
        }
        parser.close();
    }

    // Step 2: Parse the source file and translate instructions.
    private void parse() throws IOException {
        Parser parser = new Parser(this.assemblyCode);
        while (parser.hasMoreCommands()) {
            parser.advance();

            CommandType commandType = parser.commandType();
            String instruction = null;

            if (commandType.equals(CommandType.A_COMMAND)) {
                // Format A-Instruction.
                String symbol = parser.symbol();
                Character firstCharacter = symbol.charAt(0);
                boolean isSymbol = (!Character.isDigit(firstCharacter));

                String address = null;
                if (isSymbol) {
                    boolean symbolExists = this.symbolTable.contains(symbol);

                    // Record address if symbol does not exist (variable).
                    if (!symbolExists) {
                        int dataAddress = this.symbolTable.getDataAddress();
                        this.symbolTable.addEntry(symbol, dataAddress);
                        this.symbolTable.incrementDataAddress();
                    }

                    // Get address of symbol.
                    address = Integer.toString(
                            this.symbolTable.getAddress(symbol));
                } else {
                    address = symbol;
                }

                instruction = this.formatAInstruction(address);
            } else if (commandType.equals(CommandType.C_COMMAND)) {
                // Format C-Instruction
                String comp = parser.comp();
                String dest = parser.dest();
                String jump = parser.jump();
                instruction = this.formatCInstruction(comp, dest, jump);
            }

            if (!commandType.equals(CommandType.L_COMMAND)) {
                // Write binary instruction to file.
                this.machineCode.write(instruction);
                this.machineCode.newLine();
            }
        }

        // Release resources.
        parser.close();
        this.machineCode.flush();
        this.machineCode.close();
    }

    // Machine-format an A-Instruction.
    private String formatAInstruction(String address) {
        String formattedNumber = this.encoder.formatNumberAsBinary(address);
        return "0" + formattedNumber;
    }

    // Machine-format a C-Instruction.
    private String formatCInstruction(String comp, String dest, String jump) {
        StringWriter instruction = new StringWriter();
        instruction.append("111");
        instruction.append(this.encoder.comp(comp));
        instruction.append(this.encoder.dest(dest));
        instruction.append(this.encoder.jump(jump));
        return instruction.toString();
    }
}