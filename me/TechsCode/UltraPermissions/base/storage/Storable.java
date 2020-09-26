

package me.TechsCode.EnderPermissions.base.storage;

import me.TechsCode.EnderPermissions.base.TechPlugin;
import com.google.gson.JsonObject;

public abstract class Storable
{
    protected Storage storage;
    private JsonObject lastSyncedState;
    
    public void setStorage(final Storage storage) {
        this.storage = storage;
    }
    
    protected void sync() {
        if (this.storage != null) {
            final JsonObject state = this.getState();
            if (this.lastSyncedState == null || !this.lastSyncedState.toString().equalsIgnoreCase(state.toString())) {
                this.storage.update(this, state);
                this.lastSyncedState = state;
            }
        }
    }
    
    public void setLastSyncedState(final JsonObject lastSyncedState) {
        this.lastSyncedState = lastSyncedState;
    }
    
    protected void destroy() {
        if (this.storage != null) {
            this.storage.destroy(this);
            this.storage = null;
        }
    }
    
    public abstract String getKey();
    
    public abstract void setKey(final String p0);
    
    public abstract JsonObject getState();
    
    public abstract void setState(final JsonObject p0, final TechPlugin p1);
    
    public boolean isLinkedToStorage() {
        return this.storage != null;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Storable && ((Storable)o).getKey().equals(this.getKey());
    }
    
    @Override
    public int hashCode() {
        return this.getKey().hashCode();
    }
    
    public abstract void onMount(final TechPlugin p0);
}
