/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1.st10511779;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */

public class LoginTest {
    //Emeris School of Computer Science, 2025. PROG5121 Unit Testing Getting started. Available at: https://www.youtube.com/watch?v=MOhiM2SXZl0 [Accessed 17 November 2026]


    @Test
    public void testUsernameCorrectlyFormatted() { //this test tests the method and checks whether the username "kyl_1"  is correctly formatted which it is so it will return a true value
        Login obj = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(true, obj.CheckUsername("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login obj = new Login("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(false, obj.CheckUsername("kyle!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexity() {
        Login obj = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(true, obj.CheckPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login obj = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        assertEquals(false, obj.CheckPasswordComplexity("password"));
    }

    @Test
    public void testCellphoneCorrectlyFormatted() {
        Login obj = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        assertEquals(true, obj.CheckCellphoneNum("+27838968976"));
    }

    @Test
    public void testCellphoneIncorrectlyFormatted() {
        Login obj = new Login("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        assertEquals(false, obj.CheckCellphoneNum("08966553"));
    }

    @Test
    public void testRegisterUserSuccess() {
        Login obj = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        String result = obj.RegisterUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("The two above conditions have been met and the user has been registered successfully", result);
    }

    @Test
    public void testRegisterUserBadUsername() {
        Login obj = new Login("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        String result = obj.RegisterUser("kyle!!!!!!", "Ch&&sec@ke99!");
        assertEquals("The Username is incorrectly formatted", result);
    }

    @Test
    public void testRegisterUserBadPassword() {
        Login obj = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        String result = obj.RegisterUser("kyl_1", "password");
        assertEquals("The password does not meet the complexity requirements", result);
    }
    
    @Test
    public void testLoginUserSuccessful() {
   
    Login obj = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
    boolean result = obj.LoginUser("kyl_1", "password", "kyl_1", "password");
    assertTrue(result);
    }
    
    @Test
    public void testLoginUserUnsuccessful(){
    Login obj = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
    boolean result = obj.LoginUser("kyl_1", "password", "kyl_1", "password123");
    assertFalse(result);
    }
    
    @Test
    public void testReturnLoginStatusSuccess() {
    Login obj = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
    String expected = "A successful login";
    String actual = obj.returnLoginStatus("kyl_1", "password", "kyl_1", "password");
    assertEquals(expected, actual);
}

    @Test
    public void testReturnLoginStatusFailure() {
    Login obj = new Login("kyl_1", "password", "+27838968976", "Kyle", "Smith");
    String expected = "A failed login";
    String actual = obj.returnLoginStatus("kyl_1", "wrong_password", "kyl_1", "password");
    assertEquals(expected, actual);
}

    }
    
    
        
        
       
    
    

