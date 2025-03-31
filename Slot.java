/**
 * Project 2: Direct Mapped Write Back Cache simulation
 * Class Name: Slot
 * 
 * Author: Alina Kenny
 * Date: Oct 30, 2024
 * 
 * Purpose:
 * The slot class creates the slots needed for the cache as arrays of data
 * 
 * Features:
 * - each slot has necessary variables
 * - block data represented in array
 * - get and set method for variables in slot
 *  
 * Dependencies:
 * None
 * 
 **/

package P2;

public class Slot {

    //variables
    private int tag = 0;
    private int slotNum = 0;
    private short[] data = new short[16];
    private int validBit = 0;
    private int dirtyBit = 0;
    private int blockOffset = 0;
    private int blockBegin = 0;
    private int originalAddress;

    

    public int getTag() {
        return this.tag;
    }

    public short[] getData() {
        return this.data;
    }

    public int getDirtyBit() {
        return this.dirtyBit;
    }

    public int getValidBit() {
        return this.validBit;
    }

    public void setValidBit(int v) {
        this.validBit = v;
    }

    public void setDirtyBit(int d) {
        this.dirtyBit = d;
    }

    public void setTag(int t) {
        this.tag = t;
    }

    public int findBlockOffset(Short adr) {
        this.blockOffset = (adr & 0x00F);
        return this.blockOffset;
    }

    public int findSlotValue(Short adr) {
        this.slotNum = (adr & 0x0F0) >> 4;
        return this.slotNum;
    }

    public int findTagValue(Short adr) {
        this.tag = (adr & 0xF00) >> 8;
        return this.tag;
    }

    public int findBlockBegin(Short adr) {
        this.blockBegin = (adr & 0xFF0);
        return this.blockBegin;
    }

    //for original block beginning address needed to be written back to the memory
    public void setOriginalAddress(int bb) {
        this.originalAddress = bb;
    }

    public int getOriginalAddress() {
        return this.originalAddress;
    }

    //to string method for printing the data (used in the printCache method in cache class)
    public String dataToString() {
        String result = "";
        for (int i = 0; i < data.length; i++) {
            result += String.format("%02X", data[i]) + " ";  // Concatenate two-digit hex + space
        }
        return result.trim();  // Remove the trailing space
    }
    


}
