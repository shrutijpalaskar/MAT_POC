package com.sokrati.sokexmobile.storage;

import java.util.List;

import com.sokrati.sokexmobile.model.Lead;
import com.sokrati.sokexmobile.model.LeadData;
import com.sokrati.sokexmobile.storage.LeadStorageHelper.LEAD_STATE;

public class LeadStorage implements Storage<Lead, LeadData>
{

    LeadStorageHelper storageHelper;

    public LeadStorage(LeadStorageHelper storageHelper)
    {
        super();
        this.storageHelper = storageHelper;
    }

    @Override
    public void storeParams(Lead lead)
    {
        storageHelper.addLead(lead);        //actually adds lead to db
    }

    @Override
    public long getCurrentStorageSize()
    {
        long size = storageHelper.getCount();
        return size;
    }

    @Override
    public List<LeadData> getConsumableParams()
    {
        storageHelper.updateLeadState(LEAD_STATE.READY);
        List<LeadData> leads = storageHelper.getLeads(LEAD_STATE.READY);
        return leads;
    }

    @Override
    public void delete(LeadData model)
    {
        storageHelper.delete(model.getId());
    }

}
