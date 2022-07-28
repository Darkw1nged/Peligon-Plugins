package net.peligon.AutoPickup.Utilities.Lists;

public enum Permissions {

    // list of permissions
    use_permission("Peligon.AutoSmelt.use");

    // Receiving the permission string from the enum
    private final String permission;
    Permissions(String permission) { this.permission = permission; }
    public String getPermission() { return permission; }



}
