package com.peanutBarrel.runtimeExceptions;


@SuppressWarnings("serial")
public class AlreadyCheckedOutException extends RuntimeException
{

    public AlreadyCheckedOutException()
    {
    }

    public AlreadyCheckedOutException(String message)
    {
        super(message);
    }

    public AlreadyCheckedOutException(Throwable cause)
    {
        super(cause);
    }

    public AlreadyCheckedOutException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AlreadyCheckedOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
