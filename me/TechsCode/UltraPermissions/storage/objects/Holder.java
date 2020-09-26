

package me.TechsCode.EnderPermissions.storage.objects;

import java.util.Optional;
import me.TechsCode.EnderPermissions.base.storage.Storable;
import me.TechsCode.EnderPermissions.base.storage.Storage;
import me.TechsCode.EnderPermissions.base.misc.Getter;
import me.TechsCode.EnderPermissions.StorageController;
import me.TechsCode.EnderPermissions.base.TechPlugin;
import com.google.gson.JsonElement;
import me.TechsCode.EnderPermissions.base.storage.Stored;

public class Holder
{
    private final Stored<User> userHolder;
    private final Stored<Group> groupHolder;
    
    public static Holder fromUser(final User user) {
        return new Holder(user.toStored(), null);
    }
    
    public static Holder fromGroup(final Group group) {
        return new Holder(null, group.toStored());
    }
    
    public static Holder fromKey(final JsonElement jsonElement, final TechPlugin<?> techPlugin) {
        final StorageController storageController = (StorageController)techPlugin;
        if (jsonElement.getAsString().length() > 10) {
            return new Holder(new Stored<User>(jsonElement, (Getter<Storage<Storable>>)storageController::getUserStorage), null);
        }
        return new Holder(null, new Stored<Group>(jsonElement, (Getter<Storage<Storable>>)storageController::getGroupStorage));
    }
    
    private Holder(final Stored<User> userHolder, final Stored<Group> groupHolder) {
        this.userHolder = userHolder;
        this.groupHolder = groupHolder;
    }
    
    public boolean isUser() {
        return this.userHolder != null;
    }
    
    public boolean isGroup() {
        return this.groupHolder != null;
    }
    
    public Optional<PermissionHolder> get() {
        return (Optional<PermissionHolder>)((this.userHolder != null) ? Optional.ofNullable(this.userHolder.get().orElse(null)) : Optional.ofNullable(this.groupHolder.get().orElse(null)));
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Holder && this.toStored().equals(((Holder)o).toStored());
    }
    
    public Stored<?> toStored() {
        return (Stored<?>)((this.userHolder != null) ? this.userHolder : this.groupHolder);
    }
}
