package com.peanutBarrel.runtimeExceptions;


@SuppressWarnings("serial")
public class DatabaseConnectivityException extends RuntimeException
{

    public DatabaseConnectivityException()
    {
    }

    public DatabaseConnectivityException(String arg0)
    {
        super(arg0);
    }

    public DatabaseConnectivityException(Throwable arg0)
    {
        super(arg0);
    }

    public DatabaseConnectivityException(String arg0, Throwable arg1)
    {
        super(arg0, arg1);
    }

    public DatabaseConnectivityException(String arg0, Throwable arg1, boolean arg2, boolean arg3)
    {
        super(arg0, arg1, arg2, arg3);
    }
}
