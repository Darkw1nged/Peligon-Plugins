package net.peligon.JoinLeaveMessage.Utilities.Lists;

public enum Permissions {

    // list of permissions
    global_permission("Peligon.JoinLeave.*"),
    VIP_join_permission("Peligon.JoinLeave.VIP.join"),
    VIP_leave_permission("Peligon.JoinLeave.VIP.leave");

    // Receiving the permission string from the enum
    private final String permission;
    Permissions(String permission) { this.permission = permission; }
    public String getPermission() { return permission; }



}
