

package me.TechsCode.EnderPermissions.dependencies.commons.io.input;

public interface TailerListener
{
    void init(final Tailer p0);
    
    void fileNotFound();
    
    void fileRotated();
    
    void handle(final String p0);
    
    void handle(final Exception p0);
}
