package net.peligon.InstantRespawn.Utilities.Lists;

public enum Permissions {

    // list of permissions
    respawn_permission("Peligon.InstantRespawn.respawn");

    // Receiving the permission string from the enum
    private final String permission;
    Permissions(String permission) { this.permission = permission; }
    public String getPermission() { return permission; }



}
