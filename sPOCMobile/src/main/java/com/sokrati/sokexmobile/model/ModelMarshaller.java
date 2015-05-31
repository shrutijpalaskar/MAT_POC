package com.sokrati.sokexmobile.model;

import java.util.Map;

//abstract class; one interface
// T is a template
public interface ModelMarshaller<T extends Model>
{

    Map<String, Object> marshall(T obj);    //model is passed which returns key-string and value-object

}
// who is implementing this model marshaller interface---lead  marshaller