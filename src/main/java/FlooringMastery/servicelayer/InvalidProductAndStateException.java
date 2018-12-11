/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.servicelayer;

/**
 *
 * @author Kanwal
 */
public class InvalidProductAndStateException extends Exception {

    public InvalidProductAndStateException(String message) {
        super(message);
    }

    public InvalidProductAndStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
