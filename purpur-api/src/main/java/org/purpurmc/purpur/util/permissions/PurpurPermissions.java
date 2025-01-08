package org.purpurmc.purpur.util.permissions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.permissions.DefaultPermissions;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class PurpurPermissions {
    private static final String ROOT = "purpur";
    private static final String PREFIX = ROOT + ".";
    private static final Set<String> mobs = new HashSet<>();

    static {
        for (EntityType mob : EntityType.values()) {
            Class<? extends Entity> clazz = mob.getEntityClass();
            if (clazz != null && Mob.class.isAssignableFrom(clazz)) {
                mobs.add(mob.getName());
            }
        }
    }

    @NotNull
    public static Permission registerPermissions() {
        Permission purpur = DefaultPermissions.registerPermission(ROOT, "Gives the user the ability to use all Purpur utilities and commands", PermissionDefault.FALSE);

        DefaultPermissions.registerPermission(PREFIX + "debug.f3n", "Allows the user to use F3+N keybind to swap gamemodes", PermissionDefault.FALSE, purpur);

        Permission book = DefaultPermissions.registerPermission(PREFIX + "book", "Allows the user to use color codes on books", PermissionDefault.FALSE, purpur);
        DefaultPermissions.registerPermission(PREFIX + "book.color.edit", "Allows the user to use color codes on books when editing", PermissionDefault.FALSE, book);
        DefaultPermissions.registerPermission(PREFIX + "book.color.sign", "Allows the user to use color codes on books when signing", PermissionDefault.FALSE, book);
        book.recalculatePermissibles();

        Permission ride = DefaultPermissions.registerPermission("allow.ride", "Allows the user to ride all mobs", PermissionDefault.FALSE, purpur);
        for (String mob : mobs) {
            DefaultPermissions.registerPermission("allow.ride." + mob, "Allows the user to ride " + mob, PermissionDefault.FALSE, ride);
        }
        ride.recalculatePermissibles();

        Permission special = DefaultPermissions.registerPermission("allow.special", "Allows the user to use all mobs special abilities", PermissionDefault.FALSE, purpur);
        for (String mob : mobs) {
            DefaultPermissions.registerPermission("allow.special." + mob, "Allows the user to use " + mob + " special ability", PermissionDefault.FALSE, special);
        }
        special.recalculatePermissibles();

        Permission powered = DefaultPermissions.registerPermission("allow.powered", "Allows the user to toggle all mobs powered state", PermissionDefault.FALSE, purpur);
        DefaultPermissions.registerPermission("allow.powered.creeper", "Allows the user to toggle creeper powered state", PermissionDefault.FALSE, powered);
        powered.recalculatePermissibles();

        purpur.recalculatePermissibles();
        return purpur;
    }
}
