

package me.TechsCode.EnderPermissions.dependencies.commons.io.serialization;

import java.util.regex.Pattern;
import java.io.ObjectStreamClass;
import java.io.InvalidClassException;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.List;
import java.io.ObjectInputStream;

public class ValidatingObjectInputStream extends ObjectInputStream
{
    private final List<ClassNameMatcher> acceptMatchers;
    private final List<ClassNameMatcher> rejectMatchers;
    
    public ValidatingObjectInputStream(final InputStream in) {
        super(in);
        this.acceptMatchers = new ArrayList<ClassNameMatcher>();
        this.rejectMatchers = new ArrayList<ClassNameMatcher>();
    }
    
    private void validateClassName(final String s) {
        final Iterator<ClassNameMatcher> iterator = this.rejectMatchers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().matches(s)) {
                this.invalidClassNameFound(s);
            }
        }
        boolean b = false;
        final Iterator<ClassNameMatcher> iterator2 = this.acceptMatchers.iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().matches(s)) {
                b = true;
                break;
            }
        }
        if (!b) {
            this.invalidClassNameFound(s);
        }
    }
    
    protected void invalidClassNameFound(final String str) {
        throw new InvalidClassException("Class name not accepted: " + str);
    }
    
    @Override
    protected Class<?> resolveClass(final ObjectStreamClass desc) {
        this.validateClassName(desc.getName());
        return super.resolveClass(desc);
    }
    
    public ValidatingObjectInputStream accept(final Class<?>... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            this.acceptMatchers.add(new FullClassNameMatcher(new String[] { array[i].getName() }));
        }
        return this;
    }
    
    public ValidatingObjectInputStream reject(final Class<?>... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            this.rejectMatchers.add(new FullClassNameMatcher(new String[] { array[i].getName() }));
        }
        return this;
    }
    
    public ValidatingObjectInputStream accept(final String... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            this.acceptMatchers.add(new WildcardClassNameMatcher(array[i]));
        }
        return this;
    }
    
    public ValidatingObjectInputStream reject(final String... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            this.rejectMatchers.add(new WildcardClassNameMatcher(array[i]));
        }
        return this;
    }
    
    public ValidatingObjectInputStream accept(final Pattern pattern) {
        this.acceptMatchers.add(new RegexpClassNameMatcher(pattern));
        return this;
    }
    
    public ValidatingObjectInputStream reject(final Pattern pattern) {
        this.rejectMatchers.add(new RegexpClassNameMatcher(pattern));
        return this;
    }
    
    public ValidatingObjectInputStream accept(final ClassNameMatcher classNameMatcher) {
        this.acceptMatchers.add(classNameMatcher);
        return this;
    }
    
    public ValidatingObjectInputStream reject(final ClassNameMatcher classNameMatcher) {
        this.rejectMatchers.add(classNameMatcher);
        return this;
    }
}
