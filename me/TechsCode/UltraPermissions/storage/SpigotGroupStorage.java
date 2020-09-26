

package me.TechsCode.EnderPermissions.storage;

import me.TechsCode.EnderPermissions.base.storage.Storable;
import me.TechsCode.EnderPermissions.events.group.GroupDeletionEvent;
import org.bukkit.event.Event;
import me.TechsCode.EnderPermissions.events.group.GroupCreationEvent;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class SpigotGroupStorage extends GroupStorage
{
    public SpigotGroupStorage(final TechPlugin techPlugin, final Class<? extends StorageImplementation> clazz) {
        super(techPlugin, clazz);
    }
    
    @Override
    public void onCreation(final Group group) {
        super.onCreation(group);
        final GroupCreationEvent groupCreationEvent = new GroupCreationEvent(group);
        groupCreationEvent.call(this.plugin, groupCreationEvent);
    }
    
    @Override
    public void onDestroy(final Group group) {
        super.onDestroy(group);
        final GroupDeletionEvent groupDeletionEvent = new GroupDeletionEvent(group);
        groupDeletionEvent.call(this.plugin, groupDeletionEvent);
    }
    
    @Override
    public void onChange(final Group group) {
        super.onChange(group);
    }
}
