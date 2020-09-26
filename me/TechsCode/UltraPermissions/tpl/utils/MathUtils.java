

package me.TechsCode.EnderPermissions.tpl.utils;

import org.bukkit.util.Vector;

public class MathUtils
{
    public static Vector rotateAroundAxisX(final Vector vector, final double angdeg) {
        final double radians = Math.toRadians(angdeg);
        final double cos = Math.cos(radians);
        final double sin = Math.sin(radians);
        return vector.setY(vector.getY() * sin - vector.getZ() * cos).setZ(vector.getY() * cos + vector.getZ() * sin);
    }
    
    public static Vector rotateAroundAxisY(final Vector vector, final double angdeg) {
        final double radians = Math.toRadians(angdeg);
        final double cos = Math.cos(radians);
        final double sin = Math.sin(radians);
        return vector.setX(vector.getX() * sin + vector.getZ() * cos).setZ(vector.getX() * -cos + vector.getZ() * sin);
    }
    
    public static Vector rotateAroundAxisZ(final Vector vector, final double angdeg) {
        final double radians = Math.toRadians(angdeg);
        final double cos = Math.cos(radians);
        final double sin = Math.sin(radians);
        return vector.setX(vector.getX() * sin - vector.getY() * cos).setY(vector.getX() * cos + vector.getY() * sin);
    }
}
