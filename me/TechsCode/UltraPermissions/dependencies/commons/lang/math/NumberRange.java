

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.io.Serializable;

public final class NumberRange extends Range implements Serializable
{
    private static final long serialVersionUID = 71849363892710L;
    private final Number min;
    private final Number max;
    private transient int hashCode;
    private transient String toString;
    
    public NumberRange(final Number n) {
        this.hashCode = 0;
        this.toString = null;
        if (n == null) {
            throw new IllegalArgumentException("The number must not be null");
        }
        if (!(n instanceof Comparable)) {
            throw new IllegalArgumentException("The number must implement Comparable");
        }
        if (n instanceof Double && ((Double)n).isNaN()) {
            throw new IllegalArgumentException("The number must not be NaN");
        }
        if (n instanceof Float && ((Float)n).isNaN()) {
            throw new IllegalArgumentException("The number must not be NaN");
        }
        this.min = n;
        this.max = n;
    }
    
    public NumberRange(final Number n, final Number n2) {
        this.hashCode = 0;
        this.toString = null;
        if (n == null || n2 == null) {
            throw new IllegalArgumentException("The numbers must not be null");
        }
        if (n.getClass() != n2.getClass()) {
            throw new IllegalArgumentException("The numbers must be of the same type");
        }
        if (!(n instanceof Comparable)) {
            throw new IllegalArgumentException("The numbers must implement Comparable");
        }
        if (n instanceof Double) {
            if (((Double)n).isNaN() || ((Double)n2).isNaN()) {
                throw new IllegalArgumentException("The number must not be NaN");
            }
        }
        else if (n instanceof Float && (((Float)n).isNaN() || ((Float)n2).isNaN())) {
            throw new IllegalArgumentException("The number must not be NaN");
        }
        final int compareTo = ((Comparable)n).compareTo(n2);
        if (compareTo == 0) {
            this.min = n;
            this.max = n;
        }
        else if (compareTo > 0) {
            this.min = n2;
            this.max = n;
        }
        else {
            this.min = n;
            this.max = n2;
        }
    }
    
    public Number getMinimumNumber() {
        return this.min;
    }
    
    public Number getMaximumNumber() {
        return this.max;
    }
    
    public boolean containsNumber(final Number n) {
        if (n == null) {
            return false;
        }
        if (n.getClass() != this.min.getClass()) {
            throw new IllegalArgumentException("The number must be of the same type as the range numbers");
        }
        final int compareTo = ((Comparable)this.min).compareTo(n);
        final int compareTo2 = ((Comparable)this.max).compareTo(n);
        return compareTo <= 0 && compareTo2 >= 0;
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
        if (this.hashCode == 0) {
            this.hashCode = 17;
            this.hashCode = 37 * this.hashCode + this.getClass().hashCode();
            this.hashCode = 37 * this.hashCode + this.min.hashCode();
            this.hashCode = 37 * this.hashCode + this.max.hashCode();
        }
        return this.hashCode;
    }
    
    public String toString() {
        if (this.toString == null) {
            final StrBuilder strBuilder = new StrBuilder(32);
            strBuilder.append("Range[");
            strBuilder.append(this.min);
            strBuilder.append(',');
            strBuilder.append(this.max);
            strBuilder.append(']');
            this.toString = strBuilder.toString();
        }
        return this.toString;
    }
}
