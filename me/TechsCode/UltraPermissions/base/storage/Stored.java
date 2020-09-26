

package me.TechsCode.EnderPermissions.base.storage;

import java.util.Objects;
import java.util.Optional;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import me.TechsCode.EnderPermissions.base.misc.Getter;

public class Stored<T extends Storable>
{
    private String key;
    private Getter<Storage<T>> storageGetter;
    private T cache;
    
    public static <T extends Storable> Stored<T> empty() {
        return new Stored<T>((JsonElement)JsonNull.INSTANCE, null);
    }
    
    public Stored(final String key, final Getter<Storage<T>> storageGetter) {
        this.key = key;
        this.storageGetter = storageGetter;
    }
    
    public Stored(final JsonElement jsonElement, final Getter<Storage<T>> storageGetter) {
        this.key = ((jsonElement == null || jsonElement.isJsonNull()) ? null : jsonElement.getAsString());
        this.storageGetter = storageGetter;
    }
    
    public Optional<T> get() {
        if (this.cache != null) {
            if (this.cache.isLinkedToStorage()) {
                return Optional.of(this.cache);
            }
            this.cache = null;
        }
        if (this.key == null) {
            return Optional.empty();
        }
        if (this.storageGetter == null) {
            return Optional.empty();
        }
        final Storage<T> storage = this.storageGetter.get();
        if (storage == null) {
            return Optional.empty();
        }
        final Optional<T> first = storage.get().stream().filter(storable -> storable.getKey().equals(this.key)).findFirst();
        first.ifPresent(cache -> this.cache = (T)cache);
        return first;
    }
    
    public boolean isPresent() {
        return this.get().isPresent();
    }
    
    public String getKey() {
        return this.key;
    }
    
    public T orElse(final T other) {
        return this.get().orElse(other);
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && Objects.equals(this.key, ((Stored)o).key));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.key);
    }
}
