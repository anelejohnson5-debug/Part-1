/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1.st10511779;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


//Emeris School of Computer Science, 2024. PROG5121 Maven Automated testing. Available at: https://youtu.be/oz0Qd5H4Onk [Accessed 18 October 2026].

public class MessageTest {
    static final String MESSAGE_ONE = "Hi Mike, can you join us for dinner tonight?";
    static final String MESSAGE_TWO = "Hi Keegan, did you receive the payment?";
    
    @Test
    public void messageLengthSuccess() {
        Message.resetForTests();
        Message testMessage = new Message("0012345678", "+27718693002", MESSAGE_ONE);
        assertEquals("Message ready to send.", testMessage.checkMessageLength());
    }
    
    @Test
    public void messageLengthFailure() {
        Message.resetForTests();
        String longText = "";
        for (int x = 0; x < 255; ++x) {
        longText = longText + "a";
    }
        Message testMessage = new Message("0012345678", "08575975889", longText);
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", testMessage.checkMessageLength());
        
        
    }
    
    @Test
    public void recipientSuccess() {
        Message.resetForTests();
        Message testMessage = new Message("0012345678", "+27718693002", MESSAGE_ONE);
        assertEquals("Cell phone number successfully captured.", testMessage.checkRecipientCell());
        
    }
    @Test
    public void recipientFailure(){
        Message.resetForTests();
        Message testMessage = new Message("0012345678", "08575975889", MESSAGE_TWO);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an"
                + " international code. Please correct the number and try again.", testMessage.checkRecipientCell());
        
    }
    
    @Test
    public void messageHashTestCase1() {
        Message.resetForTests();
        Message testMessage = new Message("0012345678", "+27718693002", MESSAGE_ONE);
        assertEquals("00:0:HITONIGHT", testMessage.createMessageHash());
    }
    @Test
   
    public void messageHashLoop() {
        for (int x = 0; x < 2; ++x) {
            Message.resetForTests();
            String messageID;
            String messageText;
            String expectedHash;
            if (x == 0) {
                messageID = "0012345678";
                messageText = MESSAGE_ONE;
                expectedHash = "00:0:HITONIGHT";
            } else {
                messageID = "1234567890";
                messageText = MESSAGE_TWO;
                expectedHash = "12:0:HIPAYMENT";
            }
            Message testMessage = new Message(messageID, "+27718693002", messageText);
            assertEquals(expectedHash, testMessage.createMessageHash());
        }
    }
    
    @Test
    public void messageIDCreated() {
        Message.resetForTests();
        Message testMessage = new Message("+27718693002", MESSAGE_ONE);
        assertTrue(testMessage.checkMessageID());
        assertEquals(10, testMessage.getMessageID().length());
    }
    @Test
    public void messageSentOptions() {
        Message.resetForTests();
        Message testMessage = new Message("0012345678", "+27718693002", MESSAGE_ONE);
        assertEquals("Message successfully sent.", testMessage.sentMessage(1));
        assertEquals("Press 0 to delete the message.", testMessage.sentMessage(2));
        assertEquals("Message successfully stored.", testMessage.sentMessage(3));
        
    }
    @Test
    public void totalMessageSent() {
        Message.resetForTests();
        Message firstMessage = new Message("0012345678", "+27718693002", MESSAGE_ONE);
        firstMessage.sentMessage(1);
        Message secondMessage = new Message("1234567890", "08575975889", MESSAGE_TWO);
        secondMessage.sentMessage(2); // not counted
        assertEquals(1, firstMessage.returnTotalMessages());
    }
    
    
}
