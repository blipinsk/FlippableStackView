package com.bartoszlipinski.flippablestackview.exception;

/**
 * Created by Bartosz Lipinski
 * 31.01.15
 *
 * An Exception being thrown when one of the values of the StackPageTransformer is not correctly
 * defined.
 */

public class WrongValueException extends RuntimeException {
    public WrongValueException(String msg) {
        super(msg);
    }
}
