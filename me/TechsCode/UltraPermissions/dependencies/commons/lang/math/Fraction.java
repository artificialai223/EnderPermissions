

package me.TechsCode.EnderPermissions.dependencies.commons.lang.math;

import me.TechsCode.EnderPermissions.dependencies.commons.lang.text.StrBuilder;
import java.math.BigInteger;

public final class Fraction extends Number implements Comparable
{
    private static final long serialVersionUID = 65382027393090L;
    public static final Fraction ZERO;
    public static final Fraction ONE;
    public static final Fraction ONE_HALF;
    public static final Fraction ONE_THIRD;
    public static final Fraction TWO_THIRDS;
    public static final Fraction ONE_QUARTER;
    public static final Fraction TWO_QUARTERS;
    public static final Fraction THREE_QUARTERS;
    public static final Fraction ONE_FIFTH;
    public static final Fraction TWO_FIFTHS;
    public static final Fraction THREE_FIFTHS;
    public static final Fraction FOUR_FIFTHS;
    private final int numerator;
    private final int denominator;
    private transient int hashCode;
    private transient String toString;
    private transient String toProperString;
    
    private Fraction(final int numerator, final int denominator) {
        this.hashCode = 0;
        this.toString = null;
        this.toProperString = null;
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    public static Fraction getFraction(int n, int n2) {
        if (n2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (n2 < 0) {
            if (n == Integer.MIN_VALUE || n2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            n = -n;
            n2 = -n2;
        }
        return new Fraction(n, n2);
    }
    
    public static Fraction getFraction(final int n, final int n2, final int n3) {
        if (n3 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (n3 < 0) {
            throw new ArithmeticException("The denominator must not be negative");
        }
        if (n2 < 0) {
            throw new ArithmeticException("The numerator must not be negative");
        }
        long n4;
        if (n < 0) {
            n4 = n * (long)n3 - n2;
        }
        else {
            n4 = n * (long)n3 + n2;
        }
        if (n4 < -2147483648L || n4 > 2147483647L) {
            throw new ArithmeticException("Numerator too large to represent as an Integer.");
        }
        return new Fraction((int)n4, n3);
    }
    
    public static Fraction getReducedFraction(int n, int n2) {
        if (n2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (n == 0) {
            return Fraction.ZERO;
        }
        if (n2 == Integer.MIN_VALUE && (n & 0x1) == 0x0) {
            n /= 2;
            n2 /= 2;
        }
        if (n2 < 0) {
            if (n == Integer.MIN_VALUE || n2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            n = -n;
            n2 = -n2;
        }
        final int greatestCommonDivisor = greatestCommonDivisor(n, n2);
        n /= greatestCommonDivisor;
        n2 /= greatestCommonDivisor;
        return new Fraction(n, n2);
    }
    
    public static Fraction getFraction(double abs) {
        final int n = (abs < 0.0) ? -1 : 1;
        abs = Math.abs(abs);
        if (abs > 2.147483647E9 || Double.isNaN(abs)) {
            throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
        }
        final int n2 = (int)abs;
        abs -= n2;
        int n3 = 0;
        int n4 = 1;
        int n5 = 1;
        int n6 = 0;
        int n7 = (int)abs;
        double n8 = 1.0;
        double n9 = abs - n7;
        double abs2 = Double.MAX_VALUE;
        int n10 = 1;
        double n11;
        int n12;
        do {
            n11 = abs2;
            final int n13 = (int)(n8 / n9);
            final double n14 = n9;
            final double n15 = n8 - n13 * n9;
            final int n16 = n7 * n5 + n3;
            n12 = n7 * n6 + n4;
            abs2 = Math.abs(abs - n16 / (double)n12);
            n7 = n13;
            n8 = n14;
            n9 = n15;
            n3 = n5;
            n4 = n6;
            n5 = n16;
            n6 = n12;
            ++n10;
        } while (n11 > abs2 && n12 <= 10000 && n12 > 0 && n10 < 25);
        if (n10 == 25) {
            throw new ArithmeticException("Unable to convert double to fraction");
        }
        return getReducedFraction((n3 + n2 * n4) * n, n4);
    }
    
    public static Fraction getFraction(String substring) {
        if (substring == null) {
            throw new IllegalArgumentException("The string must not be null");
        }
        if (substring.indexOf(46) >= 0) {
            return getFraction(Double.parseDouble(substring));
        }
        final int index = substring.indexOf(32);
        if (index > 0) {
            final int int1 = Integer.parseInt(substring.substring(0, index));
            substring = substring.substring(index + 1);
            final int index2 = substring.indexOf(47);
            if (index2 < 0) {
                throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
            }
            return getFraction(int1, Integer.parseInt(substring.substring(0, index2)), Integer.parseInt(substring.substring(index2 + 1)));
        }
        else {
            final int index3 = substring.indexOf(47);
            if (index3 < 0) {
                return getFraction(Integer.parseInt(substring), 1);
            }
            return getFraction(Integer.parseInt(substring.substring(0, index3)), Integer.parseInt(substring.substring(index3 + 1)));
        }
    }
    
    public int getNumerator() {
        return this.numerator;
    }
    
    public int getDenominator() {
        return this.denominator;
    }
    
    public int getProperNumerator() {
        return Math.abs(this.numerator % this.denominator);
    }
    
    public int getProperWhole() {
        return this.numerator / this.denominator;
    }
    
    public int intValue() {
        return this.numerator / this.denominator;
    }
    
    public long longValue() {
        return this.numerator / (long)this.denominator;
    }
    
    public float floatValue() {
        return this.numerator / (float)this.denominator;
    }
    
    public double doubleValue() {
        return this.numerator / (double)this.denominator;
    }
    
    public Fraction reduce() {
        if (this.numerator == 0) {
            return this.equals(Fraction.ZERO) ? this : Fraction.ZERO;
        }
        final int greatestCommonDivisor = greatestCommonDivisor(Math.abs(this.numerator), this.denominator);
        if (greatestCommonDivisor == 1) {
            return this;
        }
        return getFraction(this.numerator / greatestCommonDivisor, this.denominator / greatestCommonDivisor);
    }
    
    public Fraction invert() {
        if (this.numerator == 0) {
            throw new ArithmeticException("Unable to invert zero.");
        }
        if (this.numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: can't negate numerator");
        }
        if (this.numerator < 0) {
            return new Fraction(-this.denominator, -this.numerator);
        }
        return new Fraction(this.denominator, this.numerator);
    }
    
    public Fraction negate() {
        if (this.numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: too large to negate");
        }
        return new Fraction(-this.numerator, this.denominator);
    }
    
    public Fraction abs() {
        if (this.numerator >= 0) {
            return this;
        }
        return this.negate();
    }
    
    public Fraction pow(final int n) {
        if (n == 1) {
            return this;
        }
        if (n == 0) {
            return Fraction.ONE;
        }
        if (n < 0) {
            if (n == Integer.MIN_VALUE) {
                return this.invert().pow(2).pow(-(n / 2));
            }
            return this.invert().pow(-n);
        }
        else {
            final Fraction multiplyBy = this.multiplyBy(this);
            if (n % 2 == 0) {
                return multiplyBy.pow(n / 2);
            }
            return multiplyBy.pow(n / 2).multiplyBy(this);
        }
    }
    
    private static int greatestCommonDivisor(int a, int a2) {
        if (Math.abs(a) <= 1 || Math.abs(a2) <= 1) {
            return 1;
        }
        if (a > 0) {
            a = -a;
        }
        if (a2 > 0) {
            a2 = -a2;
        }
        int n;
        for (n = 0; (a & 0x1) == 0x0 && (a2 & 0x1) == 0x0 && n < 31; a /= 2, a2 /= 2, ++n) {}
        if (n == 31) {
            throw new ArithmeticException("overflow: gcd is 2^31");
        }
        int n2 = ((a & 0x1) == 0x1) ? a2 : (-(a / 2));
        while (true) {
            if ((n2 & 0x1) == 0x0) {
                n2 /= 2;
            }
            else {
                if (n2 > 0) {
                    a = -n2;
                }
                else {
                    a2 = n2;
                }
                n2 = (a2 - a) / 2;
                if (n2 == 0) {
                    break;
                }
                continue;
            }
        }
        return -a * (1 << n);
    }
    
    private static int mulAndCheck(final int n, final int n2) {
        final long n3 = n * (long)n2;
        if (n3 < -2147483648L || n3 > 2147483647L) {
            throw new ArithmeticException("overflow: mul");
        }
        return (int)n3;
    }
    
    private static int mulPosAndCheck(final int n, final int n2) {
        final long n3 = n * (long)n2;
        if (n3 > 2147483647L) {
            throw new ArithmeticException("overflow: mulPos");
        }
        return (int)n3;
    }
    
    private static int addAndCheck(final int n, final int n2) {
        final long n3 = n + (long)n2;
        if (n3 < -2147483648L || n3 > 2147483647L) {
            throw new ArithmeticException("overflow: add");
        }
        return (int)n3;
    }
    
    private static int subAndCheck(final int n, final int n2) {
        final long n3 = n - (long)n2;
        if (n3 < -2147483648L || n3 > 2147483647L) {
            throw new ArithmeticException("overflow: add");
        }
        return (int)n3;
    }
    
    public Fraction add(final Fraction fraction) {
        return this.addSub(fraction, true);
    }
    
    public Fraction subtract(final Fraction fraction) {
        return this.addSub(fraction, false);
    }
    
    private Fraction addSub(final Fraction fraction, final boolean b) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (this.numerator == 0) {
            return b ? fraction : fraction.negate();
        }
        if (fraction.numerator == 0) {
            return this;
        }
        final int greatestCommonDivisor = greatestCommonDivisor(this.denominator, fraction.denominator);
        if (greatestCommonDivisor == 1) {
            final int mulAndCheck = mulAndCheck(this.numerator, fraction.denominator);
            final int mulAndCheck2 = mulAndCheck(fraction.numerator, this.denominator);
            return new Fraction(b ? addAndCheck(mulAndCheck, mulAndCheck2) : subAndCheck(mulAndCheck, mulAndCheck2), mulPosAndCheck(this.denominator, fraction.denominator));
        }
        final BigInteger multiply = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf(fraction.denominator / greatestCommonDivisor));
        final BigInteger multiply2 = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(this.denominator / greatestCommonDivisor));
        final BigInteger bigInteger = b ? multiply.add(multiply2) : multiply.subtract(multiply2);
        final int intValue = bigInteger.mod(BigInteger.valueOf(greatestCommonDivisor)).intValue();
        final int n = (intValue == 0) ? greatestCommonDivisor : greatestCommonDivisor(intValue, greatestCommonDivisor);
        final BigInteger divide = bigInteger.divide(BigInteger.valueOf(n));
        if (divide.bitLength() > 31) {
            throw new ArithmeticException("overflow: numerator too large after multiply");
        }
        return new Fraction(divide.intValue(), mulPosAndCheck(this.denominator / greatestCommonDivisor, fraction.denominator / n));
    }
    
    public Fraction multiplyBy(final Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (this.numerator == 0 || fraction.numerator == 0) {
            return Fraction.ZERO;
        }
        final int greatestCommonDivisor = greatestCommonDivisor(this.numerator, fraction.denominator);
        final int greatestCommonDivisor2 = greatestCommonDivisor(fraction.numerator, this.denominator);
        return getReducedFraction(mulAndCheck(this.numerator / greatestCommonDivisor, fraction.numerator / greatestCommonDivisor2), mulPosAndCheck(this.denominator / greatestCommonDivisor2, fraction.denominator / greatestCommonDivisor));
    }
    
    public Fraction divideBy(final Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        }
        if (fraction.numerator == 0) {
            throw new ArithmeticException("The fraction to divide by must not be zero");
        }
        return this.multiplyBy(fraction.invert());
    }
    
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Fraction)) {
            return false;
        }
        final Fraction fraction = (Fraction)o;
        return this.getNumerator() == fraction.getNumerator() && this.getDenominator() == fraction.getDenominator();
    }
    
    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = 37 * (629 + this.getNumerator()) + this.getDenominator();
        }
        return this.hashCode;
    }
    
    public int compareTo(final Object o) {
        final Fraction fraction = (Fraction)o;
        if (this == fraction) {
            return 0;
        }
        if (this.numerator == fraction.numerator && this.denominator == fraction.denominator) {
            return 0;
        }
        final long n = this.numerator * (long)fraction.denominator;
        final long n2 = fraction.numerator * (long)this.denominator;
        if (n == n2) {
            return 0;
        }
        if (n < n2) {
            return -1;
        }
        return 1;
    }
    
    public String toString() {
        if (this.toString == null) {
            this.toString = new StrBuilder(32).append(this.getNumerator()).append('/').append(this.getDenominator()).toString();
        }
        return this.toString;
    }
    
    public String toProperString() {
        if (this.toProperString == null) {
            if (this.numerator == 0) {
                this.toProperString = "0";
            }
            else if (this.numerator == this.denominator) {
                this.toProperString = "1";
            }
            else if (this.numerator == -1 * this.denominator) {
                this.toProperString = "-1";
            }
            else if (((this.numerator > 0) ? (-this.numerator) : this.numerator) < -this.denominator) {
                final int properNumerator = this.getProperNumerator();
                if (properNumerator == 0) {
                    this.toProperString = Integer.toString(this.getProperWhole());
                }
                else {
                    this.toProperString = new StrBuilder(32).append(this.getProperWhole()).append(' ').append(properNumerator).append('/').append(this.getDenominator()).toString();
                }
            }
            else {
                this.toProperString = new StrBuilder(32).append(this.getNumerator()).append('/').append(this.getDenominator()).toString();
            }
        }
        return this.toProperString;
    }
    
    static {
        ZERO = new Fraction(0, 1);
        ONE = new Fraction(1, 1);
        ONE_HALF = new Fraction(1, 2);
        ONE_THIRD = new Fraction(1, 3);
        TWO_THIRDS = new Fraction(2, 3);
        ONE_QUARTER = new Fraction(1, 4);
        TWO_QUARTERS = new Fraction(2, 4);
        THREE_QUARTERS = new Fraction(3, 4);
        ONE_FIFTH = new Fraction(1, 5);
        TWO_FIFTHS = new Fraction(2, 5);
        THREE_FIFTHS = new Fraction(3, 5);
        FOUR_FIFTHS = new Fraction(4, 5);
    }
}
