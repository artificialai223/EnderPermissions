

package me.TechsCode.EnderPermissions.dependencies.commons.io.output;

import java.io.OutputStream;

public class AppendableOutputStream<T extends Appendable> extends OutputStream
{
    private final T appendable;
    
    public AppendableOutputStream(final T appendable) {
        this.appendable = appendable;
    }
    
    @Override
    public void write(final int n) {
        this.appendable.append((char)n);
    }
    
    public T getAppendable() {
        return this.appendable;
    }
}
