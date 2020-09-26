

package me.TechsCode.EnderPermissions.dependencies.slf4j;

public class LoggerFactoryFriend
{
    public static void reset() {
        LoggerFactory.reset();
    }
    
    public static void setDetectLoggerNameMismatch(final boolean detect_LOGGER_NAME_MISMATCH) {
        LoggerFactory.DETECT_LOGGER_NAME_MISMATCH = detect_LOGGER_NAME_MISMATCH;
    }
}
