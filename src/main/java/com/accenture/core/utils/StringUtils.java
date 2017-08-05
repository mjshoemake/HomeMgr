/*******************************************************************************
 * $Id$
 *
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, 2011, Cisco Systems, Inc.
 *******************************************************************************/
package com.accenture.core.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.StringBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This is a utility class which contains String conversion utility functions.
 */
public class StringUtils {
    /**
     * Lazy initialization holder class to build the table when first referenced
     * rather than when the inclosing class is loaded.
     */
    private static final class HexDigitsFactory {
        /** Table to speed up binary to hex string conversions. */
        public static final String[] s_hexDigits = { "00", "01", "02", "03", "04", "05", "06",
                    "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f", "10", "11", "12", "13",
                    "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d", "1e", "1f", "20",
                    "21", "22", "23", "24", "25", "26", "27", "28", "29", "2a", "2b", "2c", "2d",
                    "2e", "2f", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3a",
                    "3b", "3c", "3d", "3e", "3f", "40", "41", "42", "43", "44", "45", "46", "47",
                    "48", "49", "4a", "4b", "4c", "4d", "4e", "4f", "50", "51", "52", "53", "54",
                    "55", "56", "57", "58", "59", "5a", "5b", "5c", "5d", "5e", "5f", "60", "61",
                    "62", "63", "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d", "6e",
                    "6f", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7a", "7b",
                    "7c", "7d", "7e", "7f", "80", "81", "82", "83", "84", "85", "86", "87", "88",
                    "89", "8a", "8b", "8c", "8d", "8e", "8f", "90", "91", "92", "93", "94", "95",
                    "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f", "a0", "a1", "a2",
                    "a3", "a4", "a5", "a6", "a7", "a8", "a9", "aa", "ab", "ac", "ad", "ae", "af",
                    "b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc",
                    "bd", "be", "bf", "c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9",
                    "ca", "cb", "cc", "cd", "ce", "cf", "d0", "d1", "d2", "d3", "d4", "d5", "d6",
                    "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df", "e0", "e1", "e2", "e3",
                    "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef", "f0",
                    "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "fa", "fb", "fc", "fd",
                    "fe", "ff" };
    }

    /**
     * Right justifies the input String by padding spaces to the left, making it
     * the specified length.
     * 
     * @param value String
     * @param length int
     * @return String
     */
    public static String rightJustify(String value,
                                      int length) {
        StringBuffer buffer = new StringBuffer();

        for (int C = 0; C <= (length - value.length()) - 1; C++)
            buffer.append(" ");
        buffer.append(value);
        return buffer.toString();
    }

    /**
     * Right justifies the input String by padding zeros to the left, making it
     * the specified length.
     * 
     * @param value String
     * @param length int
     * @return String
     */
    public static String leadingZeros(int value,
                                      int length) {
        StringBuffer buffer = new StringBuffer();
        String input = value + "";

        for (int C = 0; C <= (length - input.length()) - 1; C++)
            buffer.append("0");
        buffer.append(input);
        return buffer.toString();
    }

    /**
     * Left justifies the input String by padding spaces to the right, making it
     * the specified length.
     * 
     * @param value String
     * @param length int
     * @return String
     */
    public static String leftJustify(String value,
                                     int length) {
        StringBuffer buffer = new StringBuffer();

        if (value == null)
            value = "null";
        buffer.append(value);
        for (int C = 0; C <= (length - value.length()) - 1; C++)
            buffer.append(" ");
        return buffer.toString();
    }

    /**
     * Remove the package specifier from this class name.
     * 
     * @param classname String
     * @return String
     */
    public static String removePackage(String classname) {
        int lastDot = classname.lastIndexOf(".");

        return classname.substring(lastDot + 1);
    }

    /**
     * Convert an ArrayList object into an array of String objects.
     * 
     * @param list ArrayList
     * @return String[]
     */
    public static String[] toArray(ArrayList<String> list) {
        String[] result = new String[list.size()];

        for (int C = 0; C <= list.size() - 1; C++)
            result[C] = list.get(C).toString();
        return result;
    }

    /**
     * Force the length of the the specified String, truncating if necessary.
     * 
     * @param value String
     * @param length value
     * @return String
     */
    public static String forceLength(String value,
                                     int length) {
        return forceLength(value, length, 3);
    }

    /**
     * Force the length of the the specified String, truncating if necessary.
     * 
     * @param value String
     * @param length value
     * @return String
     */
    public static String forceLength(String value,
                                     int length,
                                     int buffer) {
        if (value == null)
            value = "null";
        int current = value.length();
        if (current > length - buffer)
            current = length - buffer;
        return leftJustify(value.substring(0, current), length);
    }

    /**
     * Truncates a string after last occurrence of the specified character. The
     * specified character remains but everything after the last occurrence of
     * that character is removed from the String.
     * 
     * @param input String
     * @param marker
     * @return String - result
     */
    public static String truncateAfterLastOccurrence(String input,
                                                     String marker) {
        int pos = input.lastIndexOf(marker);
        if (pos == -1)
            return input;
        else
            return input.substring(0, (pos + marker.length()));
    }

    /**
     * Accepts Date and converts to a String with a default date format.
     * 
     * @param date
     * @return String
     */
    public static String dateToString(Date date) {
        return dateToString(date, null);
    }

    /**
     * Accepts Date and converts to a String with the specified format.
     * 
     * @param date
     * @param format
     * @return String
     */
    public static String dateToString(Date date,
                                      String format) {
        if (format == null)
            format = "MM-dd-yyyy HH:mm:ss-SSS";

        SimpleDateFormat df = new SimpleDateFormat(format);

        df.setLenient(false);
        return df.format(date);
    }

    /**
     * Converts a null value (if found) to an empty string.  If not
     * null, it returns the unmodified input string.
     * @param input String
     * @return String
     */
    public static String nullToEmptyString(String input) {
        if (input == null) {
            return "";
        } else {
            return input;
        }
    }

    /**
     * Accepts time and converts to a String with the specified format.
     * @return String
     */
    public static String timeToDateString(long time,
                                          String format) {
        if (format == null)
            format = "MM-dd-yyyy HH:mm:ss-SSS";

        SimpleDateFormat df = new SimpleDateFormat(format);

        df.setLenient(false);
        return df.format(new Date(time));
    }

    /**
     * Reports current date in a string format that is naturally
     * (alphanumerically) sortable.
     */
    public static String getDateString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");
        df.setLenient(false);
        return df.format(new Date());
    }

    /**
     * Convert a String object to an InputStream objct.
     * 
     * @param value String
     * @return InputStream
     * @throws Exception
     */
    public static InputStream stringToInputStream(String value) throws Exception {
        return new ByteArrayInputStream(value.getBytes("UTF-8"));
    }

    /**
     * Convert an InputStream object to a String object.
     * 
     * @param is InputStream
     * @return String
     * @throws Exception
     */
    public static String inputStreamToString(InputStream is) throws Exception {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * Parses command-line arguments/flags into a map. This method skips any
     * positional arguments. All flags must be preceded with a dash. Flags can
     * have values of not. For example, "-arg1 -output file -v" gets returned as
     * a map: arg1->null, output->file, v->null.
     */
    public static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<String, String>(args.length);
        String lastArg = null;
        for (String arg : args) {
            if (arg.startsWith("-")) {
                if (lastArg != null)
                    map.put(lastArg, null);
                lastArg = arg;
            } else {
                if (lastArg != null) {
                    map.put(lastArg, arg);
                    lastArg = null;
                }
            }
        }

        if (lastArg != null)
            map.put(lastArg, null);

        return map;
    }

    /**
     * Converts version string like 5.0.1.0.0 to number 5000100000, 5.0 to
     * 5000000000. Numeric number allows version comparisons for
     * greater/smaller. Any version strings beyond 5 digits are stripped at the
     * end. Also, handle special values which start with 0, such as "018". These
     * get treated as 0.1.8 (or 0.1.8.0.0) which gets translated to 10800000, in
     * this example.
     */
    public static long convertVersionToNumber(String version) {
        if (version == null || version.length() == 0)
            return 0;

        // handle special version strings which have no dots
        if (version.indexOf('.') == -1) {
            // handle special values exactly 3 digits with one leading 0
            if (version.startsWith("0") && version.length() == 3) {
                long part1 = (long) Integer.valueOf(version.charAt(1) + "") * 10000000;
                long part2 = (long) Integer.valueOf(version.charAt(2) + "") * 100000;
                return part1 + part2;
            }

            // assume the number is just a major version
            if (version.length() > 1)
                return (long) Integer.valueOf(version) * 100000000;
            else
                return (long) Integer.valueOf(version) * 1000000000;
        }

        // split the string
        StringTokenizer st = new StringTokenizer(version, ".");
        long multiplier = 1000000000;
        long swVersion = 0;
        int count = 0;
        while (st.hasMoreElements()) {
            long num = Integer.valueOf((String) st.nextElement()).intValue();
            if (num > 9)
                swVersion += num * multiplier / 10;
            else
                swVersion += num * multiplier;

            multiplier = multiplier / 100;
            count++;
            if (count == 5)
                break;
        }

        return swVersion;
    }

    /**
     * Parse CSV line. Un-quote any values containing a comma. Skip commas in
     * quotes strings. Line has to be passed in without any line breaks
     */
    public static List<String> parseCsvLine(String line) {
        List<String> list = new ArrayList<String>();
        int startIndex = 0;
        boolean quoted = false;
        boolean newValue = true;

        StringBuffer inputLine = new StringBuffer(line);

        for (int i = 0; i < inputLine.length(); i++) {
            if (newValue) {
                if (inputLine.charAt(i) == '"') {
                    quoted = true;
                    startIndex = i + 1;
                    newValue = false;
                    continue;
                } else {
                    quoted = false;
                    startIndex = i;
                    newValue = false;
                }
            }

            // look for end of quoted string
            if (quoted) {
                if (inputLine.charAt(i) == '"') {

                    // Handling embedded quotes
                    if (inputLine.length() != i + 1 && inputLine.charAt(i + 1) == '"') {
                        inputLine.deleteCharAt(i);
                        continue;
                    }

                    // check for end of string
                    if (inputLine.length() == i + 1) {
                        list.add(inputLine.substring(startIndex, inputLine.length() - 1));
                        break;
                    }

                    // check for comma following a quote, otherwise it can be a
                    // quoted quote
                    else if (inputLine.charAt(i + 1) == ',') {
                        list.add(inputLine.substring(startIndex, i));
                        startIndex = i + 3;
                        quoted = false;
                        newValue = true;

                        // check for end of line
                        if (inputLine.length() == i + 2) {
                            list.add("");
                            break;
                        }

                        i++;
                        continue;
                    }
                }

                // if did not find quote or end of line, skip until we do
                else
                    continue;
            }

            if (inputLine.charAt(i) == ',') {
                list.add(inputLine.substring(startIndex, i));
                newValue = true;
                startIndex = i + 1;

                // check for empty end of line
                if (inputLine.length() == i + 1) {
                    list.add("");
                }

                continue;
            }

            // check for end of line
            if (inputLine.length() == i + 1) {
                list.add(inputLine.substring(startIndex));
                break;
            }
        }

        return list;
    }

    /**
     * Writes CVS line. Quotes any values containing a comma.
     */
    public static void writeCsvLine(Collection<?> values,
                                    PrintWriter output) {
        boolean first = true;
        for (Object value : values) {
            if (first)
                first = false;
            else
                output.print(',');

            if (value == null)
                continue;

            // escape string which contain commas
            if (value.toString().indexOf(',') != -1) {
                output.print('"');
                output.print(value);
                output.print('"');
            } else
                output.print(value);
        }
        output.println();
    }

    /**
     * Writes CVS line. Quotes any values containing a comma.
     */
    public static void writeCsvLine(Collection<?> values,
                                    FileWriter output) {
        StringBuffer sb = new StringBuffer();
        boolean valueFound = false;
        for (Object value : values) {
            if (value == null) {
                sb.append(",");
                valueFound = false;
            } else {
                sb.append(value).append(",");
                valueFound = true;
            }
        }
        if (valueFound) {
            if (sb.toString().endsWith(","))
                sb.deleteCharAt(sb.length() - 1);
        }
        if (output != null) {
            try {
                output.println(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return Values as string with specified Delimiter.
     */
    public static String convertToStrWithDelimiter(Collection<?> values,
                                                   String delimiter) {
        StringBuffer sb = new StringBuffer();
        // Defaulting to comma
        if (delimiter == null)
            delimiter = ",";

        for (Object value : values) {
            sb.append(value).append(delimiter);
        }

        if (sb.toString().endsWith(delimiter))
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Break this string into tokens using the specified delimiter. The tokens
     * will be returned as an array of String objects.
     * 
     * @param text
     * @param delimiter
     * @return String[]
     */
    public static String[] getTokens(String text,
                                     char delimiter) {
        // Remove underscore characters.
        ArrayList<String> list = new ArrayList<String>();

        int next = text.indexOf(delimiter);

        while (next > -1) {
            list.add(text.substring(0, next));
            text = text.substring(next + 1);
            next = text.indexOf(delimiter);
        }
        list.add(text);

        String[] result = new String[list.size()];

        for (int C = 0; C <= list.size() - 1; C++)
            result[C] = list.get(C).toString();
        return result;
    }

    /**
     * Break this string into tokens using the specified delimiter. The tokens
     * will be returned as an array of String objects.
     * 
     * @param text
     * @param delimiter
     * @return String[]
     */
    public static String[] getTokens(String text,
                                     String delimiter) {
        // Remove underscore characters.
        ArrayList<String> list = new ArrayList<String>();

        int next = text.indexOf(delimiter);

        while (next > -1) {
            list.add(text.substring(0, next).trim());
            text = text.substring(next + delimiter.length());
            next = text.indexOf(delimiter);
        }
        list.add(text.trim());

        String[] result = new String[list.size()];

        for (int C = 0; C <= list.size() - 1; C++)
            result[C] = list.get(C).toString();
        return result;
    }

    /**
     * Parse the date which contains 'T' and without 'T'
     * 
     * @param format String
     * @param date String
     * @return Date
     */
    public static Date parseDateString(String format,
                                       String date) throws Exception {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date formatdate = null;
        try {
            formatdate = dateFormat.parse(date);

        } catch (Exception e) {
            throw new Exception("Parser Exception while parsing date string " + e.getMessage());
        }
        return formatdate;

    }

    /**
     * Returns the current date into the ISO8601 format date/time with
     * milliseconds
     * 
     * @return String
     */
    public static String customFormatToISO8601Format() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String month = String.format("%02d",now.get(Calendar.MONTH)+1);
        String day = String.format("%02d",now.get(Calendar.DAY_OF_MONTH));
        String hour = String.format("%02d",now.get(Calendar.HOUR_OF_DAY));
        String minute = String.format("%02d",now.get(Calendar.MINUTE));
        String second = String.format("%02d",now.get(Calendar.SECOND));
        String millis = String.format("%03d",now.get(Calendar.MILLISECOND));
        String date_T_Z_with_Millies = year + "-" + month + "-" + day + "T" + hour + ":"
            + minute + ":" + second + "." + millis + "Z";
        return date_T_Z_with_Millies;
    }

    public static long msgEventTimeInMilliseconds(Date msgEventDate) {
        Calendar msgEventTimecal = Calendar.getInstance();
        msgEventTimecal.setTime(msgEventDate);
        long msgEventTimeMilliseconds = msgEventTimecal.getTimeInMillis();
        return msgEventTimeMilliseconds;
    }

    /**
     * Returns a hex string for the given byte[] with the given separator
     * string. Each byte will always be represented by 2 hex digits.
     * 
     * @param bytes the bytes to convert to a hex string
     * @param separator the separator
     * @return a hex string for the given byte[]
     */
    public static String toHexString(final byte[] bytes,
                                     final char separator) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }

        final int charsLength = (bytes.length * 3) - 1;
        final char[] chars = new char[charsLength];

        int dest = 0;
        for (int a = 0; a < bytes.length; a++) {
            if (a != 0) {
                chars[dest++] = separator;
            }
            final String elementAsHexString = HexDigitsFactory.s_hexDigits[bytes[a] & 0xFF];
            chars[dest++] = elementAsHexString.charAt(0);
            chars[dest++] = elementAsHexString.charAt(1);
        }
        return new String(chars);
    }
    
    /**
     * This method replaces tokens that start with "${" and
     * end with "}" with the value of the specified environment 
     * variable.
     * <p/>
     * Ex. ${RMS-CONF}/dcc.properties  
     *     ${RMS-CONF} is replaced by the value of the environment
     *     variable named "RMS-CONF".
     * <p/>
     * In addition, the token "WORKING_DIR" can be used to substitute
     * in the current working directory.  This is especially useful 
     * for scripts where you want the logs to be generated to the
     * current working directory (the directory from which the script
     * was called) instead of a predefined directory.
     * <p/>
     * Ex. ${WORKING_DIR}/logs/audit.log     
     *     
     * @param input String
     * @return String
     */
/*
    public static String replaceTokensWithEnvVariables(String input) {
    	String timestamp = StringUtils.dateToString(new Date(), "yyyyMMdd-HHmmss"); 
    	return replaceTokensWithEnvVariables(input, null, timestamp);
    }
*/
    /**
     * This method replaces tokens that start with "${" and
     * end with "}" with the value of the specified environment 
     * variable.
     * <p/>
     * Ex. ${RMS-CONF}/dcc.properties  
     *     ${RMS-CONF} is replaced by the value of the environment
     *     variable named "RMS-CONF".
     * <p/>
     * In addition, the token "WORKING_DIR" can be used to substitute
     * in the current working directory.  This is especially useful 
     * for scripts where you want the logs to be generated to the
     * current working directory (the directory from which the script
     * was called) instead of a predefined directory.
     * <p/>
     * Ex. ${WORKING_DIR}/logs/audit.log     
     *     
     * @param input String
     * @param scriptName String Optional. The name of the script to use when 
     *                          replacing ${SCRIPT_NAME}.
     * @return String
     */
/*
    public static String replaceTokensWithEnvVariables(String input,
    		                                           String scriptName,
    		                                           String timestamp) {
    	// If received null, return null.
    	if (input == null)
    		return null;
    	
    	String current = input;
    	StringBuilder builder = new StringBuilder();
    	int startPos = current.indexOf("${");
    	EnvironmentVariables env = EnvironmentVariablesLive.getInstance();
    	if (scriptName != null) {
    	    scriptName = FileUtils.stripClassPath(scriptName);
    	}    
    	
    	while (startPos != -1) {
    		
        	int endPos = current.indexOf("}");

        	// Found token.  Keep stuff before token.
            if (startPos != 0) {
                String prefix = current.substring(0,startPos);
                builder.append(prefix);
            }
            String name = current.substring(startPos+2, endPos);
            String remainder = current.substring(endPos+1);
            String value = null;
            if (name.equals("WORKING_DIR")) {
            	value = System.getProperty("user.dir"); 
            } else if (name.equals("SCRIPTNAME")) {
                value = scriptName; 
            } else if (name.equals("TIMESTAMP")) {
            	value = timestamp;
            } else {	
                value = env.getValue(name);
            }    
            if (value != null) {
                builder.append(value);
            } else {
            	builder.append("${");
            	builder.append(name);
            	builder.append("}");
            }
            current = remainder;
            
            // Look for more tokens.
        	startPos = current.indexOf("${");
    	}
    	builder.append(current);
    	return builder.toString().trim();
    }
*/
}
