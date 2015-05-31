package com.sokrati.sokexmobile.services;

import java.util.Map;

import com.sokrati.sokexmobile.model.Model;
import com.sokrati.sokexmobile.model.ModelMarshaller;
import com.sokrati.sokexmobile.model.ModelSerializer;
import com.sokrati.sokexmobile.model.UploadModel;
import com.sokrati.sokexmobile.storage.Storage;

//writing some parameters to a database/file to keep them persist

public class PersistenceProcessor<M extends Model, O extends UploadModel>
{

    private ModelMarshaller<M> modelMarshaller;
    private ModelSerializer modelSerializer;
    private Storage<M,O> storage;
    
    public PersistenceProcessor(ModelMarshaller<M> modelMarshaller,
                                ModelSerializer modelSerializer,
                                Storage<M,O> storage)           //templates
    {
        this.modelMarshaller = modelMarshaller;
        this.modelSerializer = modelSerializer;
        this.storage = storage;
    }

    void persist(M model)
    {
        Map<String, Object> marshall = modelMarshaller.marshall(model);
        String serialize = modelSerializer.serialize(marshall);
        model.setSerializedParams(serialize);
        storage.storeParams(model);
    }

}
