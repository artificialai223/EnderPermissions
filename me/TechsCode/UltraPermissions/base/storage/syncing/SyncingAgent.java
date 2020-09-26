

package me.TechsCode.EnderPermissions.base.storage.syncing;

import java.util.ArrayList;
import me.TechsCode.EnderPermissions.base.storage.Storage;
import java.util.List;

public abstract class SyncingAgent
{
    private List<Storage> storages;
    
    public SyncingAgent() {
        this.storages = new ArrayList<Storage>();
    }
    
    public void executeLocalSynchronization(final String anObject, final String s) {
        this.storages.forEach(storage -> {
            if (storage.getModelName().equals(anObject)) {
                storage.syncFromDatabase(s);
            }
        });
    }
    
    public void register(final Storage storage) {
        this.storages.add(storage);
    }
    
    public abstract void notifyNewDataChanges(final Storage p0, final String p1);
    
    public static class EmptyAgent extends SyncingAgent
    {
        @Override
        public void notifyNewDataChanges(final Storage storage, final String s) {
        }
    }
}
