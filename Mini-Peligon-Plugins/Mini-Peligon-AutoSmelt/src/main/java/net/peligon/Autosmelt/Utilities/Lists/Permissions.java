package net.peligon.Autosmelt.Utilities.Lists;

public enum Permissions {

    // list of permissions
    global_permission("Peligon.AutoSmelt.*"),
    use_permission("Peligon.AutoSmelt.use"),
    autopickup_permission("Peligon.AutoSmelt.pickup");

    // Receiving the permission string from the enum
    private final String permission;
    Permissions(String permission) { this.permission = permission; }
    public String getPermission() { return permission; }



}
