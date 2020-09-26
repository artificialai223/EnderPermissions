

package me.TechsCode.EnderPermissions.dependencies.commons.lang.text;

import java.text.ParsePosition;
import java.text.FieldPosition;
import java.text.Format;

public class CompositeFormat extends Format
{
    private static final long serialVersionUID = -4329119827877627683L;
    private final Format parser;
    private final Format formatter;
    
    public CompositeFormat(final Format parser, final Format formatter) {
        this.parser = parser;
        this.formatter = formatter;
    }
    
    public StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.formatter.format(o, sb, fieldPosition);
    }
    
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parser.parseObject(s, parsePosition);
    }
    
    public Format getParser() {
        return this.parser;
    }
    
    public Format getFormatter() {
        return this.formatter;
    }
    
    public String reformat(final String source) {
        return this.format(this.parseObject(source));
    }
}
