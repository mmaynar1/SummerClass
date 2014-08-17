package com.summerclass.domain;

public class Result
{
    public enum Status
    {
        success( true ),
        failure( false );

        private boolean worked;

        Status( boolean worked )
        {
            this.worked = worked;
        }

        public boolean isWorked()
        {
            return worked;
        }
    }

    private boolean worked;
    private String message;

    @Override
    public String toString()
    {
        return "Result{" +
               "worked=" + isWorked() +
               ", message='" + getMessage() + '\'' +
               '}';
    }

    public Result()
    {
    }

    public Result( Status status, String message )
    {
        this.worked = status.isWorked();
        this.message = message;
    }

    public boolean isWorked()
    {
        return worked;
    }

    public String getMessage()
    {
        return message;
    }
}
