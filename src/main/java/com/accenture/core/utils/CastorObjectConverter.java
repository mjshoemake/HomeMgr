package com.accenture.core.utils;

import java.io.StringReader;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import java.io.StringWriter;


public class CastorObjectConverter {
    /**
     * Converts xml string into objects, based on a mapping file.
     *
     * @param xmlString The xml string to convert into an object
     * @param castedClass The Class of the objject that the xml will be converted into.
     * @param mappingURL The file name of the mapping file.
     */
    public static Object convertXMLToObject(String xmlString, Class castedClass, java.net.URL mappingURL)throws Exception
    {
        // The mapping file to use for the conversion.
        Mapping mapping = null;
        // The object to return.
        Object castedObject = null;
        // The StringReader used by the Unmarshaller.
        StringReader stringReader = null;

        // Load mapping file
        try
        {
            // Load the mapping file.
            mapping = new Mapping();
            mapping.loadMapping(mappingURL);

            // Unmarshall the object
            Unmarshaller unmarshaller = new Unmarshaller(castedClass);
            unmarshaller.setMapping(mapping);
            stringReader = new StringReader(xmlString);
            castedObject =  unmarshaller.unmarshal( stringReader );
        }
        catch (Exception e)
        {
            e.printStackTrace();
				throw e;
        }
        finally
        {
           if( stringReader != null )
               stringReader.close();
        }
        return castedObject;
    }

    /**
     * Converts objects into xml string based on a mapping file.  Returns the XML
     * string.
     *
     * @param object The object to use to generate the XML.
     * @param castedClass The Class of the objject that the xml will be converted into.
     * @param mappingURL The file name of the mapping file.
     */


    public static String convertObjectToXML(Object object, Class castedClass, java.net.URL mappingURL)
		                                                                                throws Exception
    {
      // The mapping file to use for the conversion.
        Mapping mapping = null;

        // The StringReader used by the Unmarshaller.
        StringWriter stringWriter = new StringWriter();


      try
      {

        // Load the mapping file.
        mapping = new Mapping();
        mapping.loadMapping(mappingURL);

        // Create a File to marshal to
        //FileWriter writer = new FileWriter("test.xml")

        Marshaller marshaller = new Marshaller(stringWriter);
        marshaller.setMapping(mapping);

       // Marshal the person object
        marshaller.marshal(object);

      }
      catch (Exception e)
      {
        e.printStackTrace();
        throw e;
      }
      return stringWriter.toString();


    }




}


