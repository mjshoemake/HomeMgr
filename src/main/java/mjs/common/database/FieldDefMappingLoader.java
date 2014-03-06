package mjs.common.database;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import mjs.common.aggregation.OrderedMap;
import mjs.common.exceptions.CoreException;
import mjs.common.utils.Loggable;
import mjs.common.utils.StringUtils;
import mjs.common.xml.CastorObjectConverter;
import mjs.common.model.Field;


/**
 * This takes an XML file and converts it into an object, or converts
 * objects into XML. This allows the actions to return XML data in the
 * result to the controllers.
 *
 * @author   mshoemake
 */
@SuppressWarnings("rawtypes")
public class FieldDefMappingLoader extends Loggable
{
   /**
    * Constructor.
    *
    */
   public FieldDefMappingLoader()
   {
      super();
   }

   /**
    * Loads field definition mapping file and returns it as a
    * hashtable where the field name is the hashtable key and the
    * field type is the lookup value. The field type should be one of
    * the following values and is case insensitive: <pre>
    *    key
    *    string
    *    int
    *    long
    *    float
    *    date
    *    boolean
    * </pre>
    *
    * @param mappingFile        Description of Parameter
    * @return                   Description of Return Value
    * @exception CoreException  Description of Exception
    */
   public OrderedMap loadMapping(String mappingFile) throws CoreException
   {
      if (mappingFile == null)
         throw new CoreException("Error occured loading mapping file.  MappingFile is null.");

      try
      {
          URL mappingUrl = FieldDefMappingLoader.class.getResource("/mjs/database/FieldDefMapping.xml");
      	
          // Convert mappingFile to String.
          InputStream stream = FieldDefMappingLoader.class.getResourceAsStream(mappingFile);
          if (stream == null)
              throw new CoreException("Error occured loading mapping file(" + mappingFile + ").  InputStream is null.");

          String xml = StringUtils.inputStreamToString(stream);          
          if (xml == null)
              throw new CoreException("Error occured loading mapping file.  Unable to convert to String object.");

          Object obj = CastorObjectConverter.convertXMLToObject(xml, FieldDefs.class, mappingUrl);
          FieldDefs fieldDefs = (FieldDefs)obj;              	  
          return populateHashtable(fieldDefs);
      }
      catch (java.lang.Exception e)
      {
         throw new CoreException("Error loading database table field type definitions.", e);
      }
   }

   /**
    * Populate a Hashtable with the contents of the
    * FieldDefinitionList so the data layer can easily look up the
    * mapping information for a field.
    *
    * @param result  FieldDefinitionListImpl
    * @return        Hashtable
    */
   private OrderedMap populateHashtable(FieldDefs result) throws CoreException
   {
      OrderedMap hashtable = new OrderedMap();
      log.debug("Results:");
      List list = result.getItems();

      for (Object obj : list) {
    	  if (obj instanceof Field) {
              Field field = (Field)obj;
              String fieldName = field.getName().toLowerCase();
              hashtable.put(fieldName, field);
              log.debug("   name = " + fieldName + ",  type=" + field.getType() + ", format=" + field.getFormat() + ", maxLen=" + field.getMaxLen() + ", isPercent=" + field.getIsPercent());
    	  } else {
    		  throw new CoreException("Object " + obj.toString() + " is not a Field.  Failed to populate Hashtable.");
    	  }

      }
      return hashtable;
   }

}
