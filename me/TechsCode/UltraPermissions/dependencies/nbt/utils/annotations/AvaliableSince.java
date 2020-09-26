

package me.TechsCode.EnderPermissions.dependencies.nbt.utils.annotations;

import me.TechsCode.EnderPermissions.dependencies.nbt.utils.MinecraftVersion;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AvaliableSince {
    MinecraftVersion version();
}
