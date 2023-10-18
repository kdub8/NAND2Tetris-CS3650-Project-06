/*
 * Author: Kevin Wong
 * Assignment: NAND2TETRIS Project 6
 * Date: 10/20/2023
 * Professor: Nima Davarpanah
 * Course: CS3650-01
 * File: SymbolTable.java
 * File Description: This Java file defines a class called "SymbolTable" that keeps a correspondence between symbolic labels and numeric addresses. 
 * The symbol table contains predefined symbols and their associated addresses, as well as program and data addresses. The predefined symbols include
 * register names, special memory locations, and more. The class allows for adding symbols and their addresses to the table, checking if a symbol 
 * is present in the table, and retrieving the address associated with a symbol. It also provides methods for incrementing program and data addresses 
 * and retrieving the current program and data addresses.
 */

import java.util.Hashtable;

public class SymbolTable {
    // Define constants for program and data memory address limits.
    private static final int DATA_STARTING_ADDRESS = 16;
    private static final int DATA_ENDING_ADDRESS = 16384;
    private static final int PROGRAM_STARTING_ADDRESS = 0;
    private static final int PROGRAM_ENDING_ADDRESS = 32767;

    // Create a Hashtable to store symbol-address mappings, and initialize memory addresses.
    private Hashtable<String, Integer> symbolAddressMap;
    private int programAddress;
    private int dataAddress;

    // Initialize the symbol table with predefined symbols and set initial memory addresses.
    public SymbolTable() {
        this.initializeSymbolTable();
        this.programAddress = PROGRAM_STARTING_ADDRESS;
        this.dataAddress = DATA_STARTING_ADDRESS;
    }

    private void initializeSymbolTable() {
        // Initialize the symbol-address mapping for predefined symbols.
        this.symbolAddressMap = new Hashtable<String, Integer>();
        this.symbolAddressMap.put("SP", Integer.valueOf(0));
        this.symbolAddressMap.put("LCL", Integer.valueOf(1));
        this.symbolAddressMap.put("ARG", Integer.valueOf(2));
        this.symbolAddressMap.put("THIS", Integer.valueOf(3));
        this.symbolAddressMap.put("THAT", Integer.valueOf(4));
        this.symbolAddressMap.put("R0", Integer.valueOf(0));
        this.symbolAddressMap.put("R1", Integer.valueOf(1));
        this.symbolAddressMap.put("R2", Integer.valueOf(2));
        this.symbolAddressMap.put("R3", Integer.valueOf(3));
        this.symbolAddressMap.put("R4", Integer.valueOf(4));
        this.symbolAddressMap.put("R5", Integer.valueOf(5));
        this.symbolAddressMap.put("R6", Integer.valueOf(6));
        this.symbolAddressMap.put("R7", Integer.valueOf(7));
        this.symbolAddressMap.put("R8", Integer.valueOf(8));
        this.symbolAddressMap.put("R9", Integer.valueOf(9));
        this.symbolAddressMap.put("R10", Integer.valueOf(10));
        this.symbolAddressMap.put("R11", Integer.valueOf(11));
        this.symbolAddressMap.put("R12", Integer.valueOf(12));
        this.symbolAddressMap.put("R13", Integer.valueOf(13));
        this.symbolAddressMap.put("R14", Integer.valueOf(14));
        this.symbolAddressMap.put("R15", Integer.valueOf(15));
        this.symbolAddressMap.put("SCREEN", Integer.valueOf(16384));
        this.symbolAddressMap.put("KBD", Integer.valueOf(24576));
    }

    // Adds the pair (symbol, address) to the table.
    public void addEntry(String symbol, int address) {
        this.symbolAddressMap.put(symbol, Integer.valueOf(address));
    }

    // Does the symbol table contain the given symbol?
    public boolean contains(String symbol) {
        return this.symbolAddressMap.containsKey(symbol);
    }

    // Returns the address associated with the symbol.
    public int getAddress(String symbol) {
        return this.symbolAddressMap.get(symbol);
    }

    // Increment the program address and check for exceeding the program memory limit.
    public void incrementProgramAddress() {
        this.programAddress++;
        if (this.programAddress > PROGRAM_ENDING_ADDRESS) {
            throw new RuntimeException();
        }
    }

    // Increment the data address and check for exceeding the data memory limit.
    public void incrementDataAddress() {
        this.dataAddress++;
        if (this.dataAddress > DATA_ENDING_ADDRESS) {
            throw new RuntimeException();
        }
    }

    //Get current program address
    public int getProgramAddress() {
        return this.programAddress;
    }

    //Get current data address
    public int getDataAddress() {
        return this.dataAddress;
    }
}