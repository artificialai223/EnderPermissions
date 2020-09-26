

package me.TechsCode.EnderPermissions.dependencies.slf4j.nop;

import me.TechsCode.EnderPermissions.dependencies.slf4j.helpers.NOPLogger;
import me.TechsCode.EnderPermissions.dependencies.slf4j.Logger;
import me.TechsCode.EnderPermissions.dependencies.slf4j.ILoggerFactory;

public class NOPLoggerFactory implements ILoggerFactory
{
    @Override
    public Logger getLogger(final String s) {
        return NOPLogger.NOP_LOGGER;
    }
}
