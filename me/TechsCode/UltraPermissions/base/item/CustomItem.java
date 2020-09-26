

package me.TechsCode.EnderPermissions.base.item;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import java.lang.reflect.Type;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import com.google.gson.JsonObject;
import org.bukkit.inventory.ItemFlag;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.TechsCode.EnderPermissions.base.visual.Text;
import java.lang.reflect.Field;
import com.mojang.authlib.properties.Property;
import me.TechsCode.EnderPermissions.dependencies.commons.codec.binary.Base64;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.TechsCode.EnderPermissions.tpl.SkinTexture;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import java.util.function.Consumer;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import com.google.gson.Gson;
import java.io.Serializable;

public class CustomItem implements Serializable
{
    public static Gson gson;
    private ItemStack is;
    
    public CustomItem(final Material material) {
        this(new ItemStack(material));
    }
    
    public CustomItem(final XMaterial xMaterial) {
        this(xMaterial.getAsItemStack().orElse(new ItemStack(Material.STONE)));
    }
    
    public CustomItem(final XMaterial xMaterial, final boolean b) {
        this(xMaterial.getAsItemStack().orElse(new ItemStack(Material.STONE)), b);
    }
    
    public CustomItem(final ItemStack itemStack) {
        this(itemStack, true);
    }
    
    public CustomItem(final ItemStack itemStack, final boolean b) {
        Preconditions.checkArgument(itemStack != null, (Object)"ItemStack cannot be null");
        this.is = itemStack.clone();
        if (b) {
            this.showAllAttributes(false);
        }
    }
    
    public CustomItem material(final XMaterial xMaterial) {
        xMaterial.getBukkitMaterialData().ifPresent(this.is::setData);
        return this;
    }
    
    public CustomItem material(final Material type) {
        this.is.setType(type);
        return this;
    }
    
    public CustomItem skull(final String owner) {
        if (this.is.getItemMeta() != null && this.is.getItemMeta() instanceof SkullMeta) {
            final SkullMeta itemMeta = (SkullMeta)this.is.getItemMeta();
            itemMeta.setOwner(owner);
            this.is.setItemMeta((ItemMeta)itemMeta);
        }
        return this;
    }
    
    public CustomItem setSkullTexture(final SkinTexture skinTexture) {
        if (skinTexture == null) {
            return this;
        }
        if (skinTexture.getUrl() == null || skinTexture.getUrl().equals("null")) {
            return this;
        }
        if (this.is.hasItemMeta() && this.is.getItemMeta() instanceof SkullMeta) {
            final SkullMeta skullMeta = (SkullMeta)this.is.getItemMeta();
            final GameProfile value = new GameProfile(UUID.randomUUID(), "");
            value.getProperties().put((Object)"textures", (Object)new Property("textures", new String(Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", skinTexture.getUrl()).getBytes()))));
            try {
                final Field declaredField = skullMeta.getClass().getDeclaredField("profile");
                declaredField.setAccessible(true);
                declaredField.set(skullMeta, value);
            }
            catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                final Throwable t;
                t.printStackTrace();
            }
            this.is.setItemMeta((ItemMeta)skullMeta);
        }
        return this;
    }
    
    public String getSkullOwner() {
        if (this.is.getItemMeta() != null && this.is.getItemMeta() instanceof SkullMeta) {
            return ((SkullMeta)this.is.getItemMeta()).getOwner();
        }
        return null;
    }
    
    public CustomItem name(final String s) {
        if (this.is.getItemMeta() != null) {
            final ItemMeta itemMeta = this.is.getItemMeta();
            itemMeta.setDisplayName(Text.color(s));
            this.is.setItemMeta(itemMeta);
        }
        return this;
    }
    
    public CustomItem amount(final int amount) {
        this.is.setAmount(amount);
        return this;
    }
    
    public CustomItem lore(final List<String> list) {
        this.lore(list, true);
        return this;
    }
    
    public CustomItem lore(final boolean b, final String... a) {
        this.lore(new ArrayList<String>(Arrays.asList(a)), b);
        return this;
    }
    
    public CustomItem lore(final String... a) {
        this.lore(new ArrayList<String>(Arrays.asList(a)));
        return this;
    }
    
    public CustomItem lore(final List<String> c, final boolean b) {
        if (this.is.getItemMeta() != null) {
            final ItemMeta itemMeta = this.is.getItemMeta();
            itemMeta.setLore((List)(b ? new ArrayList<String>(c) : c));
            this.is.setItemMeta(itemMeta);
        }
        return this;
    }
    
    public CustomItem addEnchantment(final Enchantment enchantment, final int n) {
        this.is.addUnsafeEnchantment(enchantment, n);
        return this;
    }
    
    public CustomItem removeEnchantment(final Enchantment enchantment) {
        this.is.removeEnchantment(enchantment);
        return this;
    }
    
    public CustomItem appendLoreBeginning(final String... a) {
        final ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(a));
        list.addAll(this.getLore());
        this.lore(list);
        return this;
    }
    
    public CustomItem appendLore(final ArrayList<String> list) {
        return this.appendLore((String[])list.toArray(new String[0]));
    }
    
    public CustomItem appendLore(final String... array) {
        this.appendLore(true, array);
        return this;
    }
    
    public CustomItem appendLore(final boolean b, final String... a) {
        final ArrayList<String> list = new ArrayList<String>(this.getLore());
        list.addAll(Arrays.asList(a));
        this.lore(list, b);
        return this;
    }
    
    public XMaterial getMaterial() {
        return XMaterial.fromItem(this.is);
    }
    
    public Material getBukkitMaterial() {
        return this.is.getType();
    }
    
    public String getName() {
        return (this.is.getItemMeta() != null) ? this.is.getItemMeta().getDisplayName() : null;
    }
    
    public int getAmount() {
        return this.is.getAmount();
    }
    
    public ArrayList<String> getLore() {
        return (this.is.getItemMeta() != null && this.is.getItemMeta().hasLore()) ? new ArrayList<String>(this.is.getItemMeta().getLore()) : new ArrayList<String>();
    }
    
    public Map<Enchantment, Integer> getEnchants() {
        return (Map<Enchantment, Integer>)this.is.getEnchantments();
    }
    
    public CustomItem setPlaceholders(final HashMap<String, String> hashMap) {
        for (final Map.Entry<String, String> entry : hashMap.entrySet()) {
            this.replace(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    public CustomItem replace(final String target, final String replacement) {
        if (this.getName() != null) {
            this.name(this.getName().replace(target, replacement));
        }
        if (this.getSkullOwner() != null) {
            this.skull(this.getSkullOwner().replace(target, replacement));
        }
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator<String> iterator = this.getLore().iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().replace(target, replacement));
        }
        this.lore(list);
        return this;
    }
    
    public CustomItem showEnchantments(final boolean b) {
        if (this.is.getItemMeta() != null) {
            final ItemMeta itemMeta = this.is.getItemMeta();
            if (b) {
                itemMeta.removeItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
            }
            else {
                itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
            }
            this.is.setItemMeta(itemMeta);
        }
        return this;
    }
    
    public CustomItem showAllAttributes(final boolean b) {
        if (this.is.getItemMeta() != null) {
            final ItemMeta itemMeta = this.is.getItemMeta();
            if (b) {
                itemMeta.removeItemFlags(ItemFlag.values());
            }
            else {
                itemMeta.addItemFlags(ItemFlag.values());
            }
            this.is.setItemMeta(itemMeta);
        }
        return this;
    }
    
    public boolean equals(final CustomItem customItem) {
        return this.serialize().equals((Object)customItem.serialize());
    }
    
    public boolean isSimilar(final CustomItem customItem, final boolean b, final boolean b2, final boolean b3) {
        if (customItem == null) {
            return false;
        }
        if (b && this.getBukkitMaterial() != customItem.getBukkitMaterial()) {
            return false;
        }
        if (b2 && this.getName() != null && customItem.getName() != null && !this.getName().equals(customItem.getName())) {
            return false;
        }
        if (b3) {
            if (this.getLore().size() != customItem.getLore().size()) {
                return false;
            }
            int index = 0;
            final Iterator<String> iterator = this.getLore().iterator();
            while (iterator.hasNext()) {
                if (!customItem.getLore().get(index).equals(iterator.next())) {
                    return false;
                }
                ++index;
            }
        }
        return true;
    }
    
    public JsonObject serialize() {
        final JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("base64", ItemSerializer.itemTo64(this.is));
        }
        catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
        return jsonObject;
    }
    
    public static CustomItem deserialize(final JsonObject jsonObject) {
        if (jsonObject.has("base64")) {
            try {
                return new CustomItem(ItemSerializer.itemFrom64(jsonObject.get("base64").getAsString()), false);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (jsonObject.toString().equals("{}")) {
            return new CustomItem(XMaterial.STONE);
        }
        final Type type = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, ConfigurationSerializable> map = null;
        try {
            map = (Map<String, ConfigurationSerializable>)CustomItem.gson.fromJson(jsonObject.getAsJsonObject("itemInfo").toString(), type);
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
        if (jsonObject.has("meta")) {
            final Map map2 = (Map)CustomItem.gson.fromJson(jsonObject.getAsJsonObject("meta").toString(), type);
            for (final Map.Entry<K, HashMap<Enchantment, Integer>> entry : map2.entrySet()) {
                if (entry.getValue() instanceof Number) {
                    entry.setValue((HashMap<Enchantment, Integer>)convertNumber(entry.getValue()));
                }
            }
            final HashMap<Enchantment, Integer> hashMap = new HashMap<Enchantment, Integer>();
            if (map2.containsKey("enchants")) {
                final String[] split = map2.get("enchants").toString().replace("{", "").replace("}", "").replace("\"", "").split(",");
                for (int length = split.length, i = 0; i < length; ++i) {
                    final String[] split2 = split[i].split("=");
                    final Enchantment byName = Enchantment.getByName(split2[0]);
                    final int int1 = Integer.parseInt(split2[1].replace(".0", ""));
                    if (byName != null) {
                        hashMap.put(byName, int1);
                    }
                }
            }
            map2.put("enchants", hashMap);
            map.put("meta", ConfigurationSerialization.deserializeObject(map2, ConfigurationSerialization.getClassByAlias("ItemMeta")));
        }
        return new CustomItem(ItemStack.deserialize((Map)map), false);
    }
    
    private static Number convertNumber(final Object o) {
        final Number n = (Number)o;
        if (n instanceof Long) {
            final Long n2 = (Long)n;
            if (n2 == n2.intValue()) {
                return n2.intValue();
            }
        }
        if (n instanceof Float) {
            final Float n3 = (Float)n;
            if (n3 == n3.intValue()) {
                return n3.intValue();
            }
        }
        if (n instanceof Double) {
            final Double n4 = (Double)n;
            if (n4 == n4.intValue()) {
                return n4.intValue();
            }
        }
        return n;
    }
    
    public CustomItem clone() {
        return new CustomItem(this.is.clone(), false);
    }
    
    public ItemStack get() {
        return this.is.clone();
    }
    
    public void setBukkitStack(final ItemStack is) {
        this.is = is;
    }
    
    static {
        CustomItem.gson = new Gson();
    }
}
