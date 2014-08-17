package com.summerclass.domain;

import java.util.List;

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

    public Result()
    {
    }

    public boolean resultsAreValid( List<Result> results )
    {
        boolean valid = true;
        for (Result result : results)
        {
            if ( !result.isWorked() )
            {
                valid = false;
                break;
            }
        }

        return valid;
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
