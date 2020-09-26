

package me.TechsCode.EnderPermissions.dependencies.hikari.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Arrays;
import java.util.Set;
import java.util.ArrayList;
import javassist.NotFoundException;
import java.util.Iterator;
import javassist.ClassMap;
import javassist.CtNewMethod;
import java.util.HashSet;
import javassist.CtMethod;
import javassist.CtClass;
import me.TechsCode.EnderPermissions.dependencies.hikari.pool.ProxyCallableStatement;
import java.sql.CallableStatement;
import me.TechsCode.EnderPermissions.dependencies.hikari.pool.ProxyPreparedStatement;
import java.sql.PreparedStatement;
import me.TechsCode.EnderPermissions.dependencies.hikari.pool.ProxyResultSet;
import java.sql.ResultSet;
import me.TechsCode.EnderPermissions.dependencies.hikari.pool.ProxyStatement;
import java.sql.Statement;
import me.TechsCode.EnderPermissions.dependencies.hikari.pool.ProxyConnection;
import java.sql.Connection;
import javassist.ClassPath;
import javassist.LoaderClassPath;
import javassist.ClassPool;

public final class JavassistProxyFactory
{
    private static ClassPool classPool;
    
    public static void main(final String... array) {
        (JavassistProxyFactory.classPool = new ClassPool()).importPackage("java.sql");
        JavassistProxyFactory.classPool.appendClassPath((ClassPath)new LoaderClassPath(JavassistProxyFactory.class.getClassLoader()));
        try {
            final String s = "{ try { return delegate.method($$); } catch (SQLException e) { throw checkException(e); } }";
            generateProxyClass(Connection.class, ProxyConnection.class.getName(), s);
            generateProxyClass(Statement.class, ProxyStatement.class.getName(), s);
            generateProxyClass(ResultSet.class, ProxyResultSet.class.getName(), s);
            final String s2 = "{ try { return ((cast) delegate).method($$); } catch (SQLException e) { throw checkException(e); } }";
            generateProxyClass(PreparedStatement.class, ProxyPreparedStatement.class.getName(), s2);
            generateProxyClass(CallableStatement.class, ProxyCallableStatement.class.getName(), s2);
            modifyProxyFactory();
        }
        catch (Exception cause) {
            throw new RuntimeException(cause);
        }
    }
    
    private static void modifyProxyFactory() {
        System.out.println("Generating method bodies for com.zaxxer.hikari.proxy.ProxyFactory");
        final String name = ProxyConnection.class.getPackage().getName();
        final CtClass ctClass = JavassistProxyFactory.classPool.getCtClass("me.TechsCode.EnderPermissions.dependencies.hikari.pool.ProxyFactory");
        for (final CtMethod ctMethod : ctClass.getMethods()) {
            final String name2 = ctMethod.getName();
            switch (name2) {
                case "getProxyConnection": {
                    ctMethod.setBody("{return new " + name + ".HikariProxyConnection($$);}");
                    break;
                }
                case "getProxyStatement": {
                    ctMethod.setBody("{return new " + name + ".HikariProxyStatement($$);}");
                    break;
                }
                case "getProxyPreparedStatement": {
                    ctMethod.setBody("{return new " + name + ".HikariProxyPreparedStatement($$);}");
                    break;
                }
                case "getProxyCallableStatement": {
                    ctMethod.setBody("{return new " + name + ".HikariProxyCallableStatement($$);}");
                    break;
                }
                case "getProxyResultSet": {
                    ctMethod.setBody("{return new " + name + ".HikariProxyResultSet($$);}");
                    break;
                }
            }
        }
        ctClass.writeFile("target/classes");
    }
    
    private static <T> void generateProxyClass(final Class<T> clazz, final String s, final String s2) {
        final String replaceAll = s.replaceAll("(.+)\\.(\\w+)", "$1.Hikari$2");
        final CtClass ctClass = JavassistProxyFactory.classPool.getCtClass(s);
        final CtClass class1 = JavassistProxyFactory.classPool.makeClass(replaceAll, ctClass);
        class1.setModifiers(16);
        System.out.println("Generating " + replaceAll);
        class1.setModifiers(1);
        final HashSet<String> set = new HashSet<String>();
        for (final CtMethod ctMethod : ctClass.getMethods()) {
            if ((ctMethod.getModifiers() & 0x10) == 0x10) {
                set.add(ctMethod.getName() + ctMethod.getSignature());
            }
        }
        final HashSet<String> set2 = new HashSet<String>();
        for (final Class<?> clazz2 : getAllInterfaces(clazz)) {
            final CtClass ctClass2 = JavassistProxyFactory.classPool.getCtClass(clazz2.getName());
            class1.addInterface(ctClass2);
            for (final CtMethod ctMethod2 : ctClass2.getDeclaredMethods()) {
                final String string = ctMethod2.getName() + ctMethod2.getSignature();
                if (!set.contains(string)) {
                    if (!set2.contains(string)) {
                        if (!isDefaultMethod(clazz2, ctClass2, ctMethod2)) {
                            set2.add(string);
                            final CtMethod copy = CtNewMethod.copy(ctMethod2, class1, (ClassMap)null);
                            String replace = s2;
                            if ((ctClass.getMethod(ctMethod2.getName(), ctMethod2.getSignature()).getModifiers() & 0x400) != 0x400) {
                                replace = replace.replace("((cast) ", "").replace("delegate", "super").replace("super)", "super");
                            }
                            final String replace2 = replace.replace("cast", clazz.getName());
                            String body;
                            if (isThrowsSqlException(ctMethod2)) {
                                body = replace2.replace("method", copy.getName());
                            }
                            else {
                                body = "{ return ((cast) delegate).method($$); }".replace("method", copy.getName()).replace("cast", clazz.getName());
                            }
                            if (copy.getReturnType() == CtClass.voidType) {
                                body = body.replace("return", "");
                            }
                            copy.setBody(body);
                            class1.addMethod(copy);
                        }
                    }
                }
            }
        }
        class1.getClassFile().setMajorVersion(51);
        class1.writeFile("target/classes");
    }
    
    private static boolean isThrowsSqlException(final CtMethod ctMethod) {
        try {
            final CtClass[] exceptionTypes = ctMethod.getExceptionTypes();
            for (int length = exceptionTypes.length, i = 0; i < length; ++i) {
                if (exceptionTypes[i].getSimpleName().equals("SQLException")) {
                    return true;
                }
            }
        }
        catch (NotFoundException ex) {}
        return false;
    }
    
    private static boolean isDefaultMethod(final Class<?> clazz, final CtClass ctClass, final CtMethod ctMethod) {
        final ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        final CtClass[] parameterTypes = ctMethod.getParameterTypes();
        for (int length = parameterTypes.length, i = 0; i < length; ++i) {
            list.add(toJavaClass(parameterTypes[i]));
        }
        return clazz.getDeclaredMethod(ctMethod.getName(), (Class[])list.toArray(new Class[list.size()])).toString().contains("default ");
    }
    
    private static Set<Class<?>> getAllInterfaces(final Class<?> clazz) {
        final HashSet<Object> set = new HashSet<Object>();
        for (final Class<?> clazz2 : Arrays.asList(clazz.getInterfaces())) {
            if (clazz2.getInterfaces().length > 0) {
                set.addAll(getAllInterfaces(clazz2));
            }
            set.add(clazz2);
        }
        if (clazz.getSuperclass() != null) {
            set.addAll(getAllInterfaces(clazz.getSuperclass()));
        }
        if (clazz.isInterface()) {
            set.add(clazz);
        }
        return (Set<Class<?>>)set;
    }
    
    private static Class<?> toJavaClass(final CtClass ctClass) {
        if (ctClass.getName().endsWith("[]")) {
            return Array.newInstance(toJavaClass(ctClass.getName().replace("[]", "")), 0).getClass();
        }
        return toJavaClass(ctClass.getName());
    }
    
    private static Class<?> toJavaClass(final String className) {
        switch (className) {
            case "int": {
                return Integer.TYPE;
            }
            case "long": {
                return Long.TYPE;
            }
            case "short": {
                return Short.TYPE;
            }
            case "byte": {
                return Byte.TYPE;
            }
            case "float": {
                return Float.TYPE;
            }
            case "double": {
                return Double.TYPE;
            }
            case "boolean": {
                return Boolean.TYPE;
            }
            case "char": {
                return Character.TYPE;
            }
            case "void": {
                return Void.TYPE;
            }
            default: {
                return Class.forName(className);
            }
        }
    }
}
