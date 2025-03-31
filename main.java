/**
 * Project 2: Direct Mapped Write Back Cache simulation
 * Class Name: main
 * 
 * Author: Alina Kenny
 * Date: Oct 30, 2024
 * 
 * Purpose:
 * The main class is the main program that reads input instructions and simulates a cache
 * 
 * Features:
 * - Execute 3 instructions upon input file (read, write, display)
 * - creates main memory array
 *  
 * Dependencies:
 * This class depends on the Cache and Slot classes.
 * 
 **/

package P2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class main {
    public static void main(String[] args) throws Exception {

        String filePath = "C:\\Users\\Alina\\Documents\\Boston Unviersity\\Masters\\CS472 Computer Architecture\\Project2_inputFile.txt";
        
        //initialize main memory
        short MM[] = new short [2048];

        for (short i = 0x0; i < MM.length ; i++) {
            if (i <= 0xFF) {
                MM[i] = i;
            }
            else {
                MM[i] = (short) (i & 0xFF); //bitwise AND operation
            }
        }

        //variables in main
        Cache cache = new Cache();
        int bo;
        int slot;
        int tag;
        int blockBegin;
        String hexAddress;
        Short address;
        int result;
        int originalBB;
    
        //create new variable check for slots in the cache
        Slot check = new Slot();


        //try catach statement for reading input file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line and process based on command type
            while ((line = reader.readLine()) != null) {
                System.out.println("(R)ead, (W)rite, or (D)isplay Cache?");
                char command = line.charAt(0);

                switch (command) {
                    case 'R'://Read
                        System.out.println("R");
                        // Read the next line for the address
                        System.out.println("What address would you like to read?");
                        hexAddress = reader.readLine();  //Read the address as a String
                        address = (short) Integer.parseInt(hexAddress, 16);  //Parse and convert to Short for hex value
                        System.out.printf("%X %n", address);

                        //find values needed to search cache
                        bo = check.findBlockOffset(address);
                        slot = check.findSlotValue(address);
                        tag = check.findTagValue(address);
                        blockBegin = check.findBlockBegin(address);

                        //get slot needed to check int the cache
                        check = cache.getSlotRow(slot);

                        result = cache.checkCache(check, tag);

                       
                        if (result == 0) { //cache miss
                            
                                            
                            originalBB = check.getOriginalAddress(); //get the original address of block from before to write back to memory
                            
                            if (check.getDirtyBit() == 1) { //when memory needs to be updated
                                short[] data = check.getData();
                                
                                //update memory using original address of block
                                for (int i = 0; i < data.length; i++) {
                                    MM[originalBB+i] = data[i];
                                }

                                check.setDirtyBit(0);
                                check.setValidBit(1);
                                check.setTag(tag);
                                cache.fillCache(check, MM, blockBegin);
                                
                                
                            }
                            else { //just fill cache with new data
                                check.setValidBit(1);
                                check.setTag(tag);
                                cache.fillCache(check, MM, blockBegin);
                                                            
                            }
                            short value = cache.readCacheValue(check, bo);
                            System.out.printf("%s %X%s %n", "At that byte there is the value" , value,  ". (Cache Miss)");
                        }
                        else { //cache hit
                            short value = cache.readCacheValue(check, bo);
                            System.out.printf("%s %X%s %n", "At that byte there is the value" , value,  ". (Cache Hit)");
                        }

                        
                        check = new Slot(); //create new Slot so no overwrites
                        

                        break;
                        
                    case 'D'://Display
                        System.out.println("D");
                        cache.printCache();
                        break;
                        
                    case 'W'://Write
                        System.out.println("W");
                        System.out.println("What address would you like to write to?");
                        // Read the next line for the address
                        hexAddress = reader.readLine();  // Read the address as a String
                        address = (short) Integer.parseInt(hexAddress, 16);  // Parse and convert to Short
                        System.out.printf("%X %n", address);
                        System.out.println("What data would you like to write at that address?");
                        String hexWriteData = reader.readLine();  // Read the address as a String
                        Short writeData = (short) Integer.parseInt(hexWriteData, 16);  // Parse and convert to Short
                        System.out.printf("%X %n", writeData);
                        

                        //find values needed to search cache
                        bo = check.findBlockOffset(address);
                        slot = check.findSlotValue(address);
                        tag = check.findTagValue(address);
                        blockBegin = check.findBlockBegin(address);
                        
                        //get slot needed to check int the cache
                        check = cache.getSlotRow(slot);

                        result = cache.checkCache(check, tag);

                        originalBB = blockBegin; 

                        if (result == 0) { //cache miss

                            
                            if (check.getDirtyBit() == 1) {
                                
                                
                                check.setDirtyBit(0);
                                check.setValidBit(1);
                                check.setTag(tag);
                                cache.fillCache(check, MM, originalBB);
                                System.out.printf("%X %n", MM[address]);
                            }
                            else {
                                check.setValidBit(1);
                                check.setTag(tag);
                                cache.fillCache(check, MM, originalBB);
                                
                            }

                            cache.writeToCache(check, bo, writeData);
                            check.setDirtyBit(1);
                            check.setOriginalAddress(originalBB); //set the original address of block so block gets written back to memory before the new block enters

                            System.out.printf("%s%X%s%X%s%n", "Value ", writeData, " has been written to address ", address,  ". (Cache Miss)");
                        }
                        else { //cache hit
                            cache.writeToCache(check, bo, writeData);
                            check.setDirtyBit(1);
                            check.setOriginalAddress(originalBB); //set the original address of block so block gets written back to memory before the new block enters
    
    
                            System.out.printf("%s%X%s%X%s%n", "Value ", writeData, " has been written to address ", address,  ". (Cache Hit)");
    
                        }

                        check = new Slot(); //create new Slot so no overwrites

                        break;
                        
                    default:
                        System.out.println("Unknown command: " + command);
                }
            }
        } catch (IOException e) { //couldn't read file
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    






    }

}
