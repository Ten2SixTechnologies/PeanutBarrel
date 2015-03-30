package com.peanutBarrel.runtimeExceptions;


@SuppressWarnings("serial")
public class AlreadyCheckedInException extends RuntimeException
{

    public AlreadyCheckedInException()
    {
    }

    public AlreadyCheckedInException(String message)
    {
        super(message);
    }

    public AlreadyCheckedInException(Throwable cause)
    {
        super(cause);
    }

    public AlreadyCheckedInException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AlreadyCheckedInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
