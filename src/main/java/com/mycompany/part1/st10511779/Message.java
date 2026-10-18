/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1.st10511779;
 
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.Random;
/**
 *
 * @author Anele
 */
public class Message {
   private static int totalSent = 0;
   private static String sentLog = "";
   private static String storedJson = "";
   private static int storedCount = 0;
   
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
                totalSent++;
                outcome = "Message successfully sent.";
                break;
            case 2:
                outcome = "Press 0 to delete the message.";
                break;
            default:
                outcome = "Invalid option.";
                break;
                case 3:
                outcome = "Message successfully stored.";
            break;
                
                
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
    public void storeMessage(){  //stores the message in storedMessages.json
        String safeText = text.replace('"', '\''); // keeps JSON valid
        if(storedCount > 0){
            storedJson = storedJson + ",\n";
        }
        storedJson = storedJson + "  {\"messageID\": \"" + messageID
                + "\", \"messageHash\": \"" + createMessageHash()
                + "\", \"recipient\": \"" + recipient
                + "\", \"message\": \"" + safeText + "\"}";
        storedCount++;
 
        Path filePath = Paths.get("storedMessages.json");
        try {
            OutputStream output = new BufferedOutputStream(Files.newOutputStream(filePath, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
            String fileText = "[\n" + storedJson + "\n]";
            writer.write(fileText, 0, fileText.length());
            writer.close();
        } catch (Exception e) {
            System.out.println("Message: " + e);
        }
        
    }
    public static void resetForTests(){ //reset totals method
        totalSent = 0;
        sentLog = "";
        storedJson = "";
        storedCount = 0;
    }
    public String getMessageID(){
        return messageID;
    }
}
