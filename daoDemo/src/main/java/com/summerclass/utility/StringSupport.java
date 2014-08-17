package com.summerclass.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSupport
{
    private final static String GUID_PATTERN = "^[a-fA-F0-9]{32}$";
    public static final int GUID_LENGTH = 32;

    public static boolean isGuid( String source )
    {
        boolean guid = false;

        if ( source != null && source.length() == GUID_LENGTH )
        {
            Pattern pattern = Pattern.compile( GUID_PATTERN );
            Matcher matcher = pattern.matcher( source );
            guid = matcher.matches();
        }

        return guid;
    }

    public static int safeLength(String source)
    {
       int length = 0;
       if (null != source)
       {
          length = source.length();
       }
       return length;
    }

    public static String safeTrim(String source)
    {
       if (source == null)
       {
          return null;
       }
       else
       {
          return source.trim();
       }
    }

    public static boolean isEmptyString(String source)
    {
       return (safeLength(safeTrim(source)) == 0);
    }

    public static boolean safeEqual( String a, String b )
    {
        boolean same;

        if ( a == null )
        {
            same = (b == null);
        }
        else
        {
            if ( b == null )
            {
                same = false;
            }
            else
            {
                same = a.equals( b );
            }
        }

        return same;
    }

    public static int getHashCode( Object object )
    {
        return (object == null ? 0 : object.hashCode());
    }
}
