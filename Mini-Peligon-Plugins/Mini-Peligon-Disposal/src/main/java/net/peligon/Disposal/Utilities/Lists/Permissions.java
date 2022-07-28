package net.peligon.Disposal.Utilities.Lists;

public enum Permissions {

    // list of permissions
    global_permission("Peligon.Disposal.*"),
    use_permission("Peligon.Disposal.use"),
    create_sign_permission("Peligon.Disposal.createSign"),
    use_sign_permission("Peligon.Disposal.useSign");

    // Receiving the permission string from the enum
    private final String permission;
    Permissions(String permission) { this.permission = permission; }
    public String getPermission() { return permission; }



}
