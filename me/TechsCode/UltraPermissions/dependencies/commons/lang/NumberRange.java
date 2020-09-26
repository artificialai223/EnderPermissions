

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;

public final class NumberRange
{
    private final Number min;
    private final Number max;
    
    public NumberRange(final Number n) {
        if (n == null) {
            throw new NullPointerException("The number must not be null");
        }
        this.min = n;
        this.max = n;
    }
    
    public NumberRange(final Number min, final Number max) {
        if (min == null) {
            throw new NullPointerException("The minimum value must not be null");
        }
        if (max == null) {
            throw new NullPointerException("The maximum value must not be null");
        }
        if (max.doubleValue() < min.doubleValue()) {
            this.max = min;
            this.min = min;
        }
        else {
            this.min = min;
            this.max = max;
        }
    }
    
    public Number getMinimum() {
        return this.min;
    }
    
    public Number getMaximum() {
        return this.max;
    }
    
    public boolean includesNumber(final Number n) {
        return n != null && this.min.doubleValue() <= n.doubleValue() && this.max.doubleValue() >= n.doubleValue();
    }
    
    public boolean includesRange(final NumberRange numberRange) {
        return numberRange != null && this.includesNumber(numberRange.min) && this.includesNumber(numberRange.max);
    }
    
    public boolean overlaps(final NumberRange numberRange) {
        return numberRange != null && (numberRange.includesNumber(this.min) || numberRange.includesNumber(this.max) || this.includesRange(numberRange));
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NumberRange)) {
            return false;
        }
        final NumberRange numberRange = (NumberRange)o;
        return this.min.equals(numberRange.min) && this.max.equals(numberRange.max);
    }
    
    public int hashCode() {
        return 37 * (37 * 17 + this.min.hashCode()) + this.max.hashCode();
    }
    
    public String toString() {
        final StrBuilder strBuilder = new StrBuilder();
        if (this.min.doubleValue() < 0.0) {
            strBuilder.append('(').append(this.min).append(')');
        }
        else {
            strBuilder.append(this.min);
        }
        strBuilder.append('-');
        if (this.max.doubleValue() < 0.0) {
            strBuilder.append('(').append(this.max).append(')');
        }
        else {
            strBuilder.append(this.max);
        }
        return strBuilder.toString();
    }
}
