

package me.TechsCode.EnderPermissions.base.command;

public class ArgumentValue<T>
{
    private final String raw;
    private final T t;
    private final Validity validity;
    
    public static <T> ArgumentValue<T> empty(final String s, final EmptyReason emptyReason) {
        return new ArgumentValue<T>(s, null, Validity.valueOf(emptyReason.name()));
    }
    
    public static <T> ArgumentValue<T> of(final String s, final T t) {
        return new ArgumentValue<T>(s, t, Validity.VALID);
    }
    
    private ArgumentValue(final String raw, final T t, final Validity validity) {
        this.raw = raw;
        this.t = t;
        this.validity = validity;
    }
    
    public String getRaw() {
        return this.raw;
    }
    
    public T get() {
        return this.t;
    }
    
    public Validity getValidity() {
        return this.validity;
    }
    
    public boolean isValid() {
        return this.validity == Validity.VALID;
    }
    
    public boolean isMatch() {
        return this.validity != Validity.NO_MATCH;
    }
}
