

package me.TechsCode.EnderPermissions.base.item;

import java.io.IOException;
import java.io.InputStream;
import org.bukkit.util.io.BukkitObjectInputStream;
import java.io.ByteArrayInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import java.io.OutputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import java.io.ByteArrayOutputStream;
import org.bukkit.inventory.ItemStack;

public class ItemSerializer
{
    public static String itemTo64(final ItemStack itemStack) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream((OutputStream)byteArrayOutputStream);
            bukkitObjectOutputStream.writeObject((Object)itemStack);
            bukkitObjectOutputStream.close();
            return Base64Coder.encodeLines(byteArrayOutputStream.toByteArray());
        }
        catch (Exception cause) {
            throw new IllegalStateException("Unable to save item stack.", cause);
        }
    }
    
    public static ItemStack itemFrom64(final String s) {
        try {
            final BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream((InputStream)new ByteArrayInputStream(Base64Coder.decodeLines(s)));
            try {
                return (ItemStack)bukkitObjectInputStream.readObject();
            }
            finally {
                bukkitObjectInputStream.close();
            }
        }
        catch (ClassNotFoundException cause) {
            throw new IOException("Unable to decode class type.", cause);
        }
    }
}
