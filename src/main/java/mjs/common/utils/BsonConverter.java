package mjs.common.utils;

import mjs.common.exceptions.CoreException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bson.*;
import org.bson.conversions.Bson;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * This is a utility class for use Bson and Mongo.  It converts an object or Map or List
 * to it's Bson equivalent so it's compatible with Mongo.
 */
@SuppressWarnings({"rawtypes","deprecation"})
public class BsonConverter {
    /**
     * The log4j logger.
     */
    private static final Logger log = Logger.getLogger("Core");

    /**
     * Convert a bean to an array of String objects that contain the properties of the bean. This is
     * used to log out the details of the bean.
     *
     * @param bean
     *            Object
     * @return String[]
     */
    public static Bson objectToBson(Object bean) throws CoreException {
        try {
            return processBean(bean, 0);
        } catch (Exception e) {
            CoreException ex = new CoreException("Error converting bean (" + bean.getClass().getName() + ") to Bson.", e);
            throw ex;
        }
    }

    public static BsonValue valueToBson(Object val) throws CoreException {

    }

    private static BsonValue processBean(Object bean, int level) throws CoreException {

        if (bean == null) {
            return null;
        }

        try {
            Object[] args = new Object[0];
            String fieldName = null;
            PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(bean.getClass());

            if (pds == null || pds.length == 0) {
                return new BsonDocument();
            }

            if (bean instanceof Integer) {
                return new BsonInt32(((Integer)bean).intValue());
            } else if (bean instanceof Long) {
                return new BsonInt64(((Long)bean).longValue());
            } else if (bean instanceof Float) {
                return new BsonDouble(((Float)bean).doubleValue());
            } else if (bean instanceof Double) {
                return new BsonDouble(((Double)bean).doubleValue());
            } else if (bean instanceof Boolean) {
                return new BsonBoolean(((Boolean)bean).booleanValue());
            } else if (bean instanceof Date) {
                return new BsonInt64(((Date)bean).getTime());
            } else if (bean instanceof String) {
                return new BsonString(bean.toString());
            } else if (bean instanceof BigDecimal) {
                return new BsonDouble(((BigDecimal)bean).doubleValue());
            } else if (bean instanceof BigInteger) {
                return new BsonInt64(((BigInteger)bean).longValue());
            } else if (bean instanceof Map) {
                BsonDocument result = new BsonDocument();
                Map map = (Map)bean;
                Iterator keys = map.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next().toString();
                    Object val = map.get(key);

                    if (val instanceof Integer ||
                        val instanceof Long ||
                        val instanceof Double ||
                        val instanceof Boolean ||
                        val instanceof Date ||
                        val instanceof String ||
                        val instanceof Double) {
                        BsonValue value = processBean(val, level + 1);
                        result.put(key, value);
                    }
                }
            } else if (bean instanceof Collection) {
                // Process the collection.
                Collection coll = (Collection) bean;
                Object[] list = coll.toArray();
                //log.debug(indent(level) + "Bean is a collection...   count=" + list.length);

                if (list.length == 0)
                   lines.add(indent(level) + "List is empty.");

                for (int k = 0; k < list.length; k++) {
                    StringBuffer nextItem = new StringBuffer();

                    // Add indentation to simulate object hierarchy.
                    nextItem.append(indent(level));
                    if (list[k] instanceof String)
                        nextItem.append("Item #" + k + ": " + list[k]);
                    else if (showTypes)
                        nextItem.append("Item #" + k + ": " + list[k].getClass().getName());
                    else
                        nextItem.append("Item #" + k + ": ");

                    lines.add(nextItem.toString());

                    // Process the next bean.
                    if (! (list[k] instanceof String))
                        processBean(list[k], level + 1, lines, showTypes);
                }
            } else {
                // Loop through the properties of the bean.
                //log.debug(indent(level) + "Extracting bean properties...   count=" + pds.length);
                for (int i = 0; i < pds.length; i++) {
                    fieldName = pds[i].getName();

                    try {
                        if (! (fieldName.equalsIgnoreCase("class") ||
                               fieldName.equalsIgnoreCase("parent") ||
                               fieldName.equalsIgnoreCase("declaringclass"))) {
                            //log.debug(indent(level) + "   " + fieldName);

                            // Get the getter method for this property.
                            Method method = pds[i].getReadMethod();

                            if (method != null) {
                                Object value = method.invoke(bean, args);

                                if (value == null)
                                    value = "null";

                                // Add indentation to simulate object hierarchy.
                                line = new StringBuffer();
                                line.append(indent(level));
                                line.append(fieldName);
                                line.append(" = ");
                                if (fieldName.endsWith("Class") || fieldName.endsWith("class")) {
                                    line.append("SKIPPING");
                                } else {
                                    if (value instanceof Map) {
                                        if (showTypes)
                                            line.append(value.getClass().getName());
                                        else
                                            line.append("Map");

                                        lines.add(line.toString());
                                        //log.debug(indent(level) + "    Property is a map.  Calling processBean()...");
                                        processBean(value, level + 1, lines, showTypes);
                                    }
                                    else if (value instanceof Collection) {
                                        // Process the collection.
                                        if (showTypes)
                                            line.append(value.getClass().getName());
                                        else
                                            line.append("List");

                                        lines.add(line.toString());
                                        //log.debug(indent(level) + "    Property is a collection.  Calling processBean()...");
                                        processBean(value, level + 1, lines, showTypes);
                                    } else if (value instanceof Integer
                                            || value instanceof Long
                                            || value instanceof Double
                                            || value instanceof Boolean
                                            || value instanceof BigDecimal
                                            || value instanceof BigInteger
                                            || value instanceof Date
                                            || value instanceof Float
                                            || value instanceof String) {
                                        // Append the actual property value converted to a String.
                                        line.append(value.toString());
                                        lines.add(line.toString());
                                    } else {
                                        // Append the actual property value converted to a String.
                                        line.append(value.getClass().getName());
                                        lines.add(line.toString());

                                        // Process the next bean.
                                        processBean(value, level + 1, lines, showTypes);
                                    }
                                }

                            } else {
                                line = new StringBuffer();
                                line.append("   ");
                                line.append(fieldName);
                                line.append(" = NO GET METHOD FOUND.");
                            }
                        }
                    } catch (Exception e) {
                        line = new StringBuffer();
                        line.append(indent(level));
                        line.append(fieldName);
                        line.append(" = ERROR: " + e.getMessage());
                        lines.add(line.toString());
                    }
                }
            }

            return lines;
        } catch (Exception e) {
            String className = null;
            if (bean != null)
                className = bean.getClass().getName();
            throw new CoreException("Error processing bean to log data: "
                                               + className + ".", e);
        }
    }

    /**
     * Indent to the desired level (3 spaces per level).
     * @param level int
     * @return String
     */
    public static String indent(int level) {
        StringBuffer buffer = new StringBuffer();
        for (int m = 0; m <= level-1; m++)
            buffer.append("   ");
        return buffer.toString();
    }
    
    
    /**
     * Convert milliseconds to String representation of duration (HH:MM:SS).
     * 
     * @param value
     *            long
     * @return String
     */
    public static String longToDuration(long value) {
        StringBuffer result = new StringBuffer();
        String seconds = null;
        String minutes = null;
        String hours = null;

        // Seconds
        int milliseconds = (int)(value % 1000);
        long newValue = value / 1000;
        long remainder = newValue % 60;

        if (remainder < 10)
            seconds = "0" + remainder;
        else
            seconds = "" + remainder;

        // Minutes
        newValue = newValue / 60;
        remainder = newValue % 60;
        if (remainder < 10)
            minutes = "0" + remainder;
        else
            minutes = "" + remainder;

        // Hours
        remainder = newValue / 60;
        if (remainder < 10)
            hours = "0" + remainder;
        else
            minutes = "" + remainder;

        result.append(hours);
        result.append(':');
        result.append(minutes);
        result.append(':');
        result.append(seconds);
        result.append('.');
        result.append(StringUtils.leadingZeros(milliseconds, 3));
        return result.toString();
    }
    
    /**
     * This method reads a specified number of lines from the bottom of a
     * log file.
     * 
     * @param fileName String - The name and path of the log file to read.
     * @param bytesFromEndToStart int - The number of bytes from the end of the file
     *                                  to start reading from.
     * @param linesToReturn int - The number of lines to return.
     * @return ArrayList<String> - The bottom of the log file.
     * @throws Exception
     */
    public static ArrayList<String> readLogFile(String fileName, 
                                                int bytesFromEndToStart, 
                                                int linesToReturn) throws Exception {     
        ArrayList<String> list = new ArrayList<String>();

        // Open the file.
        File f = new File(fileName);
        long fileLength = f.length();
        RandomAccessFile file = new RandomAccessFile(f, "r");
        
        // Go to the end of the file minus a specified number of bytes.
        if (fileLength - bytesFromEndToStart > 0)
            file.seek(fileLength - bytesFromEndToStart);
        // Clear the first line.  It's probably a partial.
        String line = file.readLine(); 
        // Read the file from this point.
        while( line != null )
        {
            list.add(line);
            // Only keep a certain number of lines.
            if (list.size() > linesToReturn)
                list.remove(0);
            line = file.readLine();
        }

        return list;            
    }
    
    
    /**
     * To write the user actions to the useredits.log in a standardised format
     * @param username
     * @param action
     * @param status
     * @throws CoreException
     */
    public static void userEditsLog(String username,String action,String status ) throws Exception { 
    	
    	String msg = "";
   		msg = "USERNAME: "+username+" ACTION: "+action+" STATUS: "+status;
    	userLog.info(msg);

    }
    
	/**
	 * Returns true if it appears that log4j have been previously configured.
	 * This code checks to see if there are any appenders defined for log4j
	 * which is the definitive way to tell if log4j is already initialized.
	 *
	 * NOTE:  This is used because in Ops-Tools, the dcc.properties file must
	 * be loaded before log4j is configured.  Use of this method prevents
	 * notifications to the console saying that log4j is not configured.
	 */
	public static boolean isLoggingConfigured() {
		Enumeration appenders = Logger.getRoot().getAllAppenders();
		if (appenders.hasMoreElements()) {
			return true;
		} else {
			Enumeration loggers = LogManager.getCurrentLoggers();
			while (loggers.hasMoreElements()) {
				Logger c = (Logger) loggers.nextElement();
				if (c.getAllAppenders().hasMoreElements())
					return true;
			}
		}
		return false;
	}

    public static void initializeLogging() {
        try {
            // Lookup the file name for log4j configuration
            String fileName = LOG4J_PROP_FILE_LOCATION;
            Properties props = FileUtils.getContents(fileName, true);
            PropertyConfigurator.configure(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
