package com.peanutBarrel.runtimeExceptions;


@SuppressWarnings("serial")
public class NotACurrentClientException extends RuntimeException
{

    public NotACurrentClientException()
    {
    }

    public NotACurrentClientException(String arg0)
    {
        super(arg0);
    }

    public NotACurrentClientException(Throwable arg0)
    {
        super(arg0);
    }

    public NotACurrentClientException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

    public NotACurrentClientException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
    {
        super(arg0, arg1, arg2, arg3);
    }
}
