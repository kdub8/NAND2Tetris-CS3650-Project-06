import java.util.Hashtable;

public class Code {
    private Hashtable<String, String> destMnem;
    private Hashtable<String, String> compMnem;
    private Hashtable<String, String> jumpMnem;

    public Code() {
        this.jumpMnem = new Hashtable<String, String>();
        this.populateJumpMnem();
        this.compMnem = new Hashtable<String, String>();
        this.populateCompMnem();
        this.destMnem = new Hashtable<String, String>();
        this.populateDestMnem();
    }

    private void populateJumpMnem() {
        this.jumpMnem.put("NULL", "000");
        this.jumpMnem.put("JGT", "001");
        this.jumpMnem.put("JEQ", "010");
        this.jumpMnem.put("JGE", "011");
        this.jumpMnem.put("JLT", "100");
        this.jumpMnem.put("JNE", "101");
        this.jumpMnem.put("JLE", "110");
        this.jumpMnem.put("JMP", "111");
    }

    private void populateCompMnem() {
        this.compMnem.put("0", "0101010");
        this.compMnem.put("1", "0111111");
        this.compMnem.put("-1", "0111010");
        this.compMnem.put("D", "0001100");
        this.compMnem.put("A", "0110000");
        this.compMnem.put("M", "1110000");
        this.compMnem.put("!D", "0001101");
        this.compMnem.put("!A", "0110001");
        this.compMnem.put("!M", "1110001");
        this.compMnem.put("-D", "0001111");
        this.compMnem.put("-A", "0110011");
        this.compMnem.put("-M", "1110011");
        this.compMnem.put("D+1", "0011111");
        this.compMnem.put("A+1", "0110111");
        this.compMnem.put("M+1", "1110111");
        this.compMnem.put("D-1", "0001110");
        this.compMnem.put("A-1", "0110010");
        this.compMnem.put("M-1", "1110010");
        this.compMnem.put("D+A", "0000010");
        this.compMnem.put("D+M", "1000010");
        this.compMnem.put("D-A", "0010011");
        this.compMnem.put("D-M", "1010011");
        this.compMnem.put("A-D", "0000111");
        this.compMnem.put("M-D", "1000111");
        this.compMnem.put("D&A", "0000000");
        this.compMnem.put("D&M", "1000000");
        this.compMnem.put("D|A", "0010101");
        this.compMnem.put("D|M", "1010101");
    }

    private void populateDestMnem() {
        this.destMnem.put("NULL", "000");
        this.destMnem.put("M", "001");
        this.destMnem.put("D", "010");
        this.destMnem.put("MD", "011");
        this.destMnem.put("A", "100");
        this.destMnem.put("AM", "101");
        this.destMnem.put("AD", "110");
        this.destMnem.put("AMD", "111");
    }

    // Returns the binary code of the dest mnemonic.
    public String dest(String mnemonic) {
        if (mnemonic == null || mnemonic.isEmpty()) {
            mnemonic = "NULL";
        }

        return this.destMnem.get(mnemonic);
    }

    // Returns the binary code of the comp mnemonic.
    public String comp(String mnemonic) {
        return this.compMnem.get(mnemonic);
    }

    // Returns the binary code of the jump mnemonic.
    public String jump(String mnemonic) {
        if (mnemonic == null || mnemonic.isEmpty()) {
            mnemonic = "NULL";
        }

        return this.jumpMnem.get(mnemonic);
    }

    // Format a number as a 15-bit, 0-padded binary number.
    public String formatNumberAsBinary(String number) {
        int value = Integer.parseInt(number);
        String binaryNumber = Integer.toBinaryString(value);
        String formattedBinaryNumber = String.format("%15s", binaryNumber).replace(' ', '0');
        return formattedBinaryNumber;
    }
}