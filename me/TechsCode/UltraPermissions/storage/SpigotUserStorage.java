

package me.TechsCode.EnderPermissions.storage;

import me.TechsCode.EnderPermissions.base.storage.Storable;
import org.bukkit.event.Event;
import me.TechsCode.EnderPermissions.events.user.UserCreationEvent;
import me.TechsCode.EnderPermissions.storage.objects.User;
import me.TechsCode.EnderPermissions.base.storage.StorageImplementation;
import me.TechsCode.EnderPermissions.base.TechPlugin;

public class SpigotUserStorage extends UserStorage
{
    public SpigotUserStorage(final TechPlugin techPlugin, final Class<? extends StorageImplementation> clazz) {
        super(techPlugin, clazz);
    }
    
    @Override
    public void onCreation(final User user) {
        super.onCreation(user);
        final UserCreationEvent userCreationEvent = new UserCreationEvent(user);
        userCreationEvent.call(this.plugin, userCreationEvent);
    }
    
    @Override
    public void onChange(final User user) {
        super.onChange(user);
    }
}
