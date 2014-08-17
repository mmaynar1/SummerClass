package com.summerclass.utility;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JdbcSupport
{
   public static final int MAX_ROWS = 100;
   private static int index = 1;

   public static final String wildcardCharacter = "*";

   private static String getQuotedValue( Object value )
   {
      String quotedValue;

      if ( Timestamp.class == value.getClass() )
      {
         quotedValue = formatSqlDateTime( (Timestamp)value );
      }
      else if ( Date.class == value.getClass() )
      {
         quotedValue = formatSqlDate( (Date)value );
      }
      else if ( Integer.class == value.getClass() )
      {
         quotedValue = value.toString();
      }
      else
      {
         quotedValue = "'" + value.toString() + "'";
      }

      return quotedValue;
   }

   @SuppressWarnings( "unused" )
   public static String smash( String sql, MapSqlParameterSource source )
   {
      return resolveParameters( sql, source );
   }

   // this mostly works, you may to touch it up some with dates, but it's way better than nothing.  Denny
   public static String resolveParameters( String sql, List<Object> parameters )
   {
      for ( Object parameter : parameters )
      {
         sql = sql.replaceFirst( "\\?", "'" + parameter.toString() + "'" );
      }

      return sql;
   }

   public static String resolveParameters( String sql, MapSqlParameterSource source )
   {
      String result = sql;

      for ( Map.Entry<String, Object> entry : source.getValues().entrySet() )
      {
         String value = null;
         Object entryValue = entry.getValue();
         if ( entryValue != null )
         {
            if ( entryValue instanceof List && ( (List) entryValue ).size() > 0 )
            {
               value = "";
               for ( Object object : (List) entryValue )
               {
                  if ( value.length() > 0 )
                  {
                     value += ",";
                  }

                  value += getQuotedValue( object );
               }
            }
            else
            {
               value = getQuotedValue( entryValue );
            }
         }

         result = result.replaceAll( ":" + entry.getKey() + "\\b", value );
      }

      return result;
   }

   public enum ParameterRequired
   {
      yes, no
   }

   public enum ParameterUpperCase
   {
      yes, no
   }

   public enum CompareType
   {
      equal, not_equal, less_than, greater_than, less_than_equal, greater_than_equal, in, startsWith, not_in, contains
   }

   public static String getString( SimpleJdbcTemplate simpleJdbcTemplate, String sql, MapSqlParameterSource source ) throws Exception
   {
      String value;
      try
      {
         value = simpleJdbcTemplate.queryForObject( sql, String.class, source );
      }
      catch ( EmptyResultDataAccessException exception )
      {
         value = null;
      }
      catch ( Exception exception )
      {
         throw exception;
      }

      return value;
   }






   public static boolean isNameInUse( SimpleJdbcTemplate simpleJdbcTemplate, String tableName, String idColumn, String id, String nameColumn, String name ) throws Exception
   {
      StringBuffer sql = new StringBuffer( "select count(*) from " );
      sql.append( tableName );
      sql.append( " where UPPER(" );
      sql.append( nameColumn );
      sql.append( ") = UPPER(:name) and " );
      sql.append( idColumn );
      sql.append( " <> COALESCE(:id, ' ')" );

      MapSqlParameterSource source = new MapSqlParameterSource();
      source.addValue( "name", name );
      source.addValue( "id", id );

      return ( simpleJdbcTemplate.queryForInt( sql.toString(), source ) > 0 );
   }




   private static String getIndex()
   {
      if ( ++index > 1000 )
      {
         index = 1;
      }

      return index + "";
   }

   public static String getKey( String base )
   {
      return base + "_" + getIndex();
   }
   
   public static String getSqlSafeKey( String base )
   {
      return getKey( base.replaceAll( "[^a-zA-Z0-9]", "\\_" ) );
   }



   public static String createDateRangeSql( MapSqlParameterSource source, Object lowDate, Object highDate, String columnName )
   {
      String sql = " and ";

      String startKey = getSqlSafeKey( columnName );
      String endKey = getSqlSafeKey( columnName );

      source.addValue( startKey, lowDate );
      source.addValue( endKey, highDate );

      if ( lowDate != null )
      {
         if ( highDate != null )
         {
            sql += String.format( "%s between :%s and :%s", columnName, startKey, endKey );
         }
         else
         {
            sql += String.format( "%s >= :%s", columnName, startKey );
         }
      }
      else
      {
         if ( highDate != null )
         {
            // we have just an end date
            sql += String.format( "%s <= :%s", columnName, endKey );
         }
         else
         {
            // we have no dates
            sql += "1 = 1";
         }
      }

      return sql + " ";
   }

   private static String formatSqlDate( Date date )
   {
      String sqlDate = null;

      if ( null != date )
      {
         String formattedDate = new SimpleDateFormat( "MM/dd/yyyy" ).format( date );
         sqlDate = String.format( "to_date('%s', 'mm/dd/yyyy')", formattedDate );
      }

      return sqlDate;
   }

   private static String formatSqlDateTime( Timestamp timestamp )
   {
      String sqlDate = null;

      if ( null != timestamp )
      {
         String formattedTimestamp = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss" ).format( timestamp );
         sqlDate = String.format( "to_date('%s', 'mm/dd/yyyy hh24:mi:ss')", formattedTimestamp );
      }

      return sqlDate;
   }
   	
   private static boolean needsAnd( StringBuffer sql )
   {
      boolean needsAnd = true;
      String trimmedSql = sql.toString().trim();
      if ( trimmedSql.endsWith( "(" ) )
      {
         needsAnd = false;
      }

      return needsAnd;
   }

   public static void singleRowUpdate( SimpleJdbcTemplate simpleJdbcTemplate, String sql, MapSqlParameterSource source ) throws Exception
   {
      int count = simpleJdbcTemplate.update( sql, source );
      if ( count != 1 )
      {
         throw new Exception( "singleRowUpdate updated " + count + " rows." );
      }
   }
}
