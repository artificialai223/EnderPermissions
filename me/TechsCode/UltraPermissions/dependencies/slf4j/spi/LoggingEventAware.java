

package me.TechsCode.EnderPermissions.dependencies.slf4j.spi;

import me.TechsCode.EnderPermissions.dependencies.slf4j.event.LoggingEvent;

public interface LoggingEventAware
{
    void log(final LoggingEvent p0);
}
