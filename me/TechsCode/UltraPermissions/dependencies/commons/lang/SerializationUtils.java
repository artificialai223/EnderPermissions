

package me.TechsCode.EnderPermissions.dependencies.commons.lang;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SerializationUtils
{
    public static Object clone(final Serializable s) {
        return deserialize(serialize(s));
    }
    
    public static void serialize(final Serializable obj, final OutputStream out) {
        if (out == null) {
            throw new IllegalArgumentException("The OutputStream must not be null");
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(obj);
        }
        catch (IOException ex) {
            throw new SerializationException(ex);
        }
        finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    
    public static byte[] serialize(final Serializable s) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        serialize(s, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    public static Object deserialize(final InputStream in) {
        if (in == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(in);
            return objectInputStream.readObject();
        }
        catch (ClassNotFoundException ex) {
            throw new SerializationException(ex);
        }
        catch (IOException ex2) {
            throw new SerializationException(ex2);
        }
        finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            }
            catch (IOException ex3) {}
        }
    }
    
    public static Object deserialize(final byte[] buf) {
        if (buf == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        return deserialize(new ByteArrayInputStream(buf));
    }
}
