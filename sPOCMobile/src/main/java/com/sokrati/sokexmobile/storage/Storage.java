package com.sokrati.sokexmobile.storage;

import java.util.List;

import com.sokrati.sokexmobile.model.Model;
import com.sokrati.sokexmobile.model.UploadModel;

public interface Storage<T extends Model, O extends UploadModel>
{
    void storeParams(T model);
    long getCurrentStorageSize();
    List<O> getConsumableParams();
    void delete(O model);
}
