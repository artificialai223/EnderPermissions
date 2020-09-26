

package me.TechsCode.EnderPermissions.storage.collection;

import java.util.Comparator;
import java.util.function.Function;
import me.TechsCode.EnderPermissions.storage.objects.Group;
import java.util.Collection;
import me.TechsCode.EnderPermissions.storage.objects.UserRankup;
import java.util.ArrayList;

public class RankupList extends ArrayList<UserRankup>
{
    public RankupList(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public RankupList() {
    }
    
    public RankupList(final Collection<? extends UserRankup> c) {
        super(c);
    }
    
    public RankupList bestToWorst() {
        this.sort(Comparator.comparing(userRankup -> userRankup.getGroup().get().map((Function<? super Group, ?>)Group::getPriority).orElse(0)));
        return this;
    }
    
    public RankupList worstToBest() {
        this.sort(Comparator.comparing(userRankup -> userRankup.getGroup().get().map((Function<? super Group, ?>)Group::getPriority).orElse(0), Comparator.reverseOrder()));
        return this;
    }
}
