/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author User
 */
public class InvalidAccessRightException extends Exception{

    public InvalidAccessRightException() {
    }

    public InvalidAccessRightException(String message) {
        super(message);
    }
    
}
