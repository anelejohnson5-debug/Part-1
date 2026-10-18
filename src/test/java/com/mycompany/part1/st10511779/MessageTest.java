/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1.st10511779;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

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
        for int (int x = 0; x < 255; ++x) {
        longText = longText + "a";
    }
        Message testMessage = new Message("0012345678", "27718693002", longText);
        assertEquals("Message exceeds 250 characters by 6, please reduce the size.", testMessage.checkMessageLength());
        
        
    }
    
    @Test
    public void recipientSuccess() {
        Message.resetForTests();
        Message testMessage = new Message("0012345678", "+27718693002", MESSAGE_ONE);
        assertEquals("Cell phone number successfully captured.", testMessage.checkRecipientCell());
        
    }
    
}