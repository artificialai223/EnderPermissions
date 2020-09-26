

package me.TechsCode.EnderPermissions.storage.objects;

import java.util.function.Consumer;
import me.TechsCode.EnderPermissions.base.storage.Stored;

public class UserRankup
{
    private final User user;
    private final Stored<Group> group;
    private final long expiry;
    
    public UserRankup(final User user, final Stored<Group> group, final long expiry) {
        this.user = user;
        this.group = group;
        this.expiry = expiry;
    }
    
    public User getUser() {
        return this.user;
    }
    
    public Stored<Group> getGroup() {
        return this.group;
    }
    
    public long getExpiry() {
        return this.expiry;
    }
    
    public long getLeftDuration() {
        return this.expiry - System.currentTimeMillis();
    }
    
    public boolean isPermanent() {
        return this.expiry == 0L;
    }
    
    public boolean isExpired() {
        return !this.isPermanent() && this.getLeftDuration() < 0L;
    }
    
    public void remove() {
        this.group.get().ifPresent(this.user::removeGroup);
    }
}
