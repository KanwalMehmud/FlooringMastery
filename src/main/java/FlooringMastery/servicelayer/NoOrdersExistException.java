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
public class NoOrdersExistException extends Exception {

    public NoOrdersExistException(String message) {
        super(message);
    }

    public NoOrdersExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
