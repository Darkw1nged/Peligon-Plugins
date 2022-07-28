package net.peligon.BlockedCommands.Utilities.Lists;

public enum Permissions {

    // list of permissions
    bypass_permission("Peligon.BlockedCommands.bypass");

    // Receiving the permission string from the enum
    private final String permission;
    Permissions(String permission) { this.permission = permission; }
    public String getPermission() { return permission; }



}
