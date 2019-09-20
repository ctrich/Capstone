/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

/**
 *
 * @author Chris Richardson
 * Student ID: 000895452
 * email: cric215@wgu.edu
 * Class: C195
 */
public class OverlapAppException extends Exception {

    public OverlapAppException() {
    }

    public OverlapAppException(String message) {
        super(message);
    }

    public OverlapAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverlapAppException(Throwable cause) {
        super(cause);
    }

    
}
