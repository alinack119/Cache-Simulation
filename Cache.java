/**
 * Project 2: Direct Mapped Write Back Cache simulation
 * Class Name: cache
 * 
 * Author: Alina Kenny
 * Date: Oct 30, 2024
 * 
 * Purpose:
 * The cache class creates a cache with 16 slots and executes on cache
 * 
 * Features:
 * - initialize a cache
 * - methods to modify and edit the cache
 * - write to and read from cache
 * - print cache method
 *  
 * Dependencies:
 * This class depends on the Slot class.
 * 
 **/

package P2;

public class Cache {

    private Slot cache[] = new Slot[16];

    //initialize cache
    public Cache () {
        for (int i = 0; i < cache.length; i++) {
            cache[i] = new Slot(); 
        }
    }

    //get slot needed to modify or check from cache
    public Slot getSlotRow(int slotNumber) {
        return cache[slotNumber];
        
    }

    //check for hit or miss
    public int checkCache(Slot s, int t) {
        if (s.getValidBit() == 1 && s.getTag() == t) {
            return 1; //hit
        }
        else {
            return 0; //miss
        }
    }

    //get the cache value needed
    public short readCacheValue(Slot s, int blockOffset) {
        short[] data = s.getData();
        short value = data[blockOffset];
        return value;
    }

    //fill the cache with data from main memory
    public void fillCache(Slot s, short[] MM, int blockBegin) {
        short[] data = s.getData();
        for (int i = 0; i < data.length; i++) {
            data[i] = MM[blockBegin];
            blockBegin++;
        }
    }

    //write to the cache specific data provided by user
    public void writeToCache(Slot s, int blockOffset, int input) {
        short[] data = s.getData();
        data[blockOffset] = (short) input;
    }


    //print the cache with format
    public void printCache() {
        System.out.printf("%-4s %-5s %-5s %-5s %s%n", "Slot", "Valid", "Dirty", "Tag", "Data");

        for (int i = 0; i < cache.length; i++) {
            Slot slot = cache[i];
            int valid = slot.getValidBit();
            int dirty = slot.getDirtyBit();
            int tag = slot.getTag(); 
            String data = slot.dataToString();

            //print slot index, valid bit, tag, and data (aligning values with formatted output)
            System.out.printf("%-4X %-5X %-5X %-5X %s%n", i, valid, dirty, tag, data);
        }
    }

    //print the cache slot
    public void printCacheSlot(int s) {
        System.out.printf("%-4s %-5s %-5s %-5s %s%n", "Slot", "Valid", "Dirty", "Tag", "Data");

        Slot slot = cache[s];
        int valid = slot.getValidBit();
        int dirty = slot.getDirtyBit();
        String tag = String.format("%X", slot.getTag());
        String data = slot.dataToString();

        //print slot index, valid bit, tag, and data (aligning values with formatted output)
        System.out.printf("%-4X %-5X %-5X %-5s %s%n", s, valid, dirty, tag, data);

    }

    
    

   

}
