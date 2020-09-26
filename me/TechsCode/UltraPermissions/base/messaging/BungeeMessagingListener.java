

package me.TechsCode.EnderPermissions.base.messaging;

import net.md_5.bungee.api.config.ServerInfo;

public interface BungeeMessagingListener
{
    void onMessage(final ServerInfo p0, final Message p1);
}
