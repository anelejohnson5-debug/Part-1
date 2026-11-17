/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1.st10511779;
 
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author Anele
 */
public class Message {
   private static int totalSent = 0;
   private static String sentLog = "";
   private static String storedJson = "";
   private static int storedCount = 0;
   
  
   private static final int MAX_MESSAGES = 100;
   private static String storedFilePath = "storedMessages.json";
   private static String[] sentMessages = new String[MAX_MESSAGES];        // text of every sent message
   private static String[] disregardedMessages = new String[MAX_MESSAGES]; // text of every disregarded message
   private static String[] storedMessages = new String[MAX_MESSAGES];      // text of every stored message
   private static String[] messageHashes = new String[MAX_MESSAGES];       // hash of every message
   private static String[] messageIDs = new String[MAX_MESSAGES];          // ID of every message
   
   private static String[] messageRecipients = new String[MAX_MESSAGES];
   private static String[] messageTexts = new String[MAX_MESSAGES];
   private static String[] messageFlags = new String[MAX_MESSAGES];        // "Sent", "Stored" or "Disregard"
   private static int sentCount = 0;
   private static int disregardedCount = 0;
   private static int recordedCount = 0;                                   // entries in the parallel arrays
   
   private String messageID;
   private int messageNumber;
   private String recipient;
   private String text;
   
   public Message(String recipient, String text) {  //constructor that runs everytime a new message is 
       this.messageID = generateID();
       this.messageNumber = totalSent;
       this.recipient = recipient;
       this.text = text;
   }
      public Message(String messageID, String recipient, String text) { // 2 constructors, 1 which builds a message for normal use
                                                                        // and the other lets you test with a predictable ID
       this.messageID = messageID;
       this.messageNumber = totalSent;
       this.recipient = recipient;
       this.text = text;
   }
   
   public String generateID(){ //used to generate ID randomly
       Random random = new Random();
       messageID = String.valueOf(1+ random.nextInt(9));
       for (int i = 1; i < 10; i ++) {
           messageID = messageID + random.nextInt(10);
       }
       return messageID;
       
       
   }
   public boolean checkMessageID(){
       return messageID.length()== 10; //checking if the ID is exactly 10 characters
   }
   
   public String checkMessageLength(){ //makes sure that description is not longer than 250 characters
       if (text.length() <= 250) {
           return "Message ready to send.";
   } else {
    return "Message exceeds 250 characters by " + (text.length() - 250)
        + "; please reduce the size.";
    
       }
       
       
   }
          
   public String checkRecipientCell(){  // starts with "+" and is followed by digits only. 10-14 characters
        boolean valid = recipient.length() >= 10 && recipient.length() <=14
               && recipient.charAt(0) == '+';
       for (int x = 1; x < recipient.length() && valid; ++x) {
           if (!Character.isDigit(recipient.charAt(x))) {
               valid = false;   
           }
       }
       if (valid) {
           return "Cell phone number successfully captured.";
       } else {
           return "Cell phone number is incorrectly formatted or does not contain an international code."
                   + " Please correct the number and try again.";
           
       }
   }
    private String cleanWord(String word) {    // removes anything that is not a letter or digit
        String clean = "";
        for (int x = 0; x < word.length(); ++x){
            char currentChar = word.charAt(x);
            if (Character.isLetter(currentChar) || Character.isDigit(currentChar)) {
                clean = clean + currentChar;
            }
        }
        return clean;
    }
    public String createMessageHash(){  //tracking code(message hash) which has the first 2 numbers of the ID, a colon
                                        //the number of the message, and the first and last words in the message
                                        //the hash will be displayed in CAPS
        int firstSpace = text.indexOf(' ');
        int lastSpace = text.lastIndexOf(' ');
        String firstWord;
        String lastWord;
        if(firstSpace == -1){    // message is only one word
            firstWord = text;
            lastWord = text;
        } else {
            firstWord = text.substring(0, firstSpace);
            lastWord = text.substring(lastSpace + 1);
        }
        String hash = messageID.substring(0, 2) + ":" + messageNumber + ":"
                + cleanWord(firstWord) + cleanWord(lastWord);
        return hash.toUpperCase();
    }
    
    public String sentMessage(int choice){ // 1-Message successfully sent
                                           //2- press 0 to delete message
                                           //3-Message successfully stored
                                          
        String outcome;
        switch(choice){
            case 1:
                sentLog = sentLog + details() + "\n";
                record(messageID, createMessageHash(), recipient, text, "Sent");
                totalSent++;
                outcome = "Message successfully sent.";
                break;
            case 2:
                record(messageID, createMessageHash(), recipient, text, "Disregard");
                outcome = "Press 0 to delete the message.";
                break;
            case 3:
                storeMessage();
                outcome = "Message successfully stored.";
            break;
            default:
                outcome = "Invalid option.";
             
                
                
        }
        return outcome;
    }
    public String printMessages(){
        return sentLog;
    }
    public int returnTotalMessages(){
        return totalSent;
    }
    public String details(){ //method to display details at the end of the report
        return "Message ID: " + messageID + "\nMessage Hash: " + createMessageHash()
                + "\nRecipient: " + recipient + "\nMessage: " + text + "\n";
        
    }
    public void storeMessage(){  //stores the message in storedMessages.json    //TechCode, 2024. JSON Tutorial For Beginners - Full Course. Available at: https://www.youtube.com/watch?v=IWcUJLUAO2A [Accessed 18 October 2026].
        if (record(messageID, createMessageHash(), recipient, text, "Stored")) {
            writeStoredFile();
        }
    }
    
    // rebuilds the JSON text from the stored entries in the arrays and saves it
    private static void writeStoredFile(){
        storedJson = "";
        boolean first = true;
        for (int i = 0; i < recordedCount; i++) {
            if (messageFlags[i].equals("Stored")) {
                String safeText = messageTexts[i].replace('"', '\''); // keeps JSON valid
                if (!first) {
                    storedJson = storedJson + ",\n";
                }
                storedJson = storedJson + "  {\"messageID\": \"" + messageIDs[i]
                        + "\", \"messageHash\": \"" + messageHashes[i]
                        + "\", \"recipient\": \"" + messageRecipients[i]
                        + "\", \"message\": \"" + safeText + "\"}";
                first = false;
            }
        }
        Path filePath = Paths.get(storedFilePath);
        try {
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(filePath, CREATE, TRUNCATE_EXISTING));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            String fileText = "[\n" + storedJson + "\n]";
            writer.write(fileText, 0, fileText.length());
            writer.close();
        } catch (Exception e) {
            System.out.println("Message: " + e);
        }
    }
    
    // adds a message to the category array and to the parallel arrays
    private static boolean record(String id, String hash, String recipient, String text, String flag){
        if (recordedCount >= MAX_MESSAGES) {
            return false;
        }
        messageIDs[recordedCount] = id;
        messageHashes[recordedCount] = hash;
        messageRecipients[recordedCount] = recipient;
        messageTexts[recordedCount] = text;
        messageFlags[recordedCount] = flag;
        if (flag.equals("Sent")) {
            sentMessages[sentCount++] = text;
        } else if (flag.equals("Stored")) {
            storedMessages[storedCount++] = text;
        } else {
            disregardedMessages[disregardedCount++] = text;
        }
        recordedCount++;
        return true;
    }
    
    
    public static int loadStoredMessages(){
        int loaded = 0;
        Path file = Paths.get(storedFilePath);
        if (!Files.exists(file)) {
            return 0; // no file yet: start with empty arrays
        }
        try {
            InputStream input = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String content = "";
            String line = reader.readLine();
            while (line != null) {
                content = content + line + "\n";
                line = reader.readLine();
            }
            input.close();
            JSONArray array = new JSONArray(content);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                if (record(obj.getString("messageID"), obj.getString("messageHash"),
                        obj.getString("recipient"), obj.getString("message"), "Stored")) {
                    loaded++;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not read " + storedFilePath + ": " + e);
        }
        return loaded;
    }
    
    public static String displayStoredMessages(){ // shows what was read from the JSON file
        String output = "";
        for (int i = 0; i < recordedCount; i++) {
            if (messageFlags[i].equals("Stored")) {
                output = output + "ID: " + messageIDs[i] + " | Recipient: " + messageRecipients[i]
                        + " | Message: " + messageTexts[i] + "\n";
            }
        }
        return output.equals("") ? "No stored messages." : output;
    }
    
    
    public static String displaySendersAndRecipients(String sender){ // a
        String output = "";
        for (int i = 0; i < recordedCount; i++) {
            if (messageFlags[i].equals("Stored")) {
                output = output + "Sender: " + sender + " | Recipient: " + messageRecipients[i] + "\n";
            }
        }
        return output.equals("") ? "No stored messages." : output;
    }
    
    public static String getLongestStoredMessage(){ // b (null when nothing is stored)
        String longest = null;
        for (int i = 0; i < storedCount; i++) {
            if (longest == null || storedMessages[i].length() > longest.length()) {
                longest = storedMessages[i];
            }
        }
        return longest;
    }
    
    public static String[] searchByMessageID(String id){ // c - returns {recipient, message} or null
        for (int i = 0; i < recordedCount; i++) {
            if (messageIDs[i].equals(id)) {
                return new String[]{messageRecipients[i], messageTexts[i]};
            }
        }
        return null;
    }
    
    public static String[] searchByRecipient(String recipient){ // d - sent or stored messages
        int count = 0;
        for (int i = 0; i < recordedCount; i++) {
            if (messageRecipients[i].equals(recipient) && !messageFlags[i].equals("Disregard")) {
                count++;
            }
        }
        String[] results = new String[count];
        int position = 0;
        for (int i = 0; i < recordedCount; i++) {
            if (messageRecipients[i].equals(recipient) && !messageFlags[i].equals("Disregard")) {
                results[position++] = messageTexts[i];
            }
        }
        return results;
    }
    
    public static String deleteByHash(String hash){ // e
        for (int i = 0; i < recordedCount; i++) {
            if (messageHashes[i].equals(hash)) {
                String deletedText = messageTexts[i];
                String flag = messageFlags[i];
                if (flag.equals("Sent")) {
                    sentCount = removeText(sentMessages, sentCount, deletedText);
                } else if (flag.equals("Stored")) {
                    storedCount = removeText(storedMessages, storedCount, deletedText);
                } else {
                    disregardedCount = removeText(disregardedMessages, disregardedCount, deletedText);
                }
                for (int j = i; j < recordedCount - 1; j++) { // shift the parallel arrays left
                    messageIDs[j] = messageIDs[j + 1];
                    messageHashes[j] = messageHashes[j + 1];
                    messageRecipients[j] = messageRecipients[j + 1];
                    messageTexts[j] = messageTexts[j + 1];
                    messageFlags[j] = messageFlags[j + 1];
                }
                recordedCount--;
                messageIDs[recordedCount] = null;
                messageHashes[recordedCount] = null;
                messageRecipients[recordedCount] = null;
                messageTexts[recordedCount] = null;
                messageFlags[recordedCount] = null;
                if (flag.equals("Stored")) {
                    writeStoredFile(); // keep the JSON file in step with the array
                }
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }
    
    private static int removeText(String[] array, int count, String text){
        for (int i = 0; i < count; i++) {
            if (array[i].equals(text)) {
                for (int j = i; j < count - 1; j++) {
                    array[j] = array[j + 1];
                }
                array[count - 1] = null;
                return count - 1;
            }
        }
        return count;
    }
    
    public static String buildReport(){ // f - full details of all stored messages
        String report = "--- STORED MESSAGES REPORT ---\n";
        for (int i = 0; i < recordedCount; i++) {
            if (messageFlags[i].equals("Stored")) {
                report = report + "Message Hash: " + messageHashes[i] + "\n"
                        + "Message ID: " + messageIDs[i] + "\n"
                        + "Recipient: " + messageRecipients[i] + "\n"
                        + "Message: " + messageTexts[i] + "\n\n";
            }
        }
        return report;
    }
    
    // getters for the unit tests
    public static int getSentCount(){
        return sentCount;
    }
    public static String getSentMessage(int index){
        return sentMessages[index];
    }
    public static int getStoredTotal(){
        return storedCount;
    }
    public static void setStoredFilePath(String path){ // lets tests use a temporary file
        storedFilePath = path;
    }
    
    public static void resetForTests(){ //reset totals method
        totalSent = 0;
        sentLog = "";
        storedJson = "";
        storedCount = 0;
        sentCount = 0;
        disregardedCount = 0;
        recordedCount = 0;
        sentMessages = new String[MAX_MESSAGES];
        disregardedMessages = new String[MAX_MESSAGES];
        storedMessages = new String[MAX_MESSAGES];
        messageHashes = new String[MAX_MESSAGES];
        messageIDs = new String[MAX_MESSAGES];
        messageRecipients = new String[MAX_MESSAGES];
        messageTexts = new String[MAX_MESSAGES];
        messageFlags = new String[MAX_MESSAGES];
        storedFilePath = "storedMessages.json";
    }
    public String getMessageID(){
        return messageID;
    }
}
