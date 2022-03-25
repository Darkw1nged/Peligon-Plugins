package net.peligon.PeligonPrison.struts;

import org.bukkit.GameEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Gang {

    private String name;
    private UUID owner;
    private String ownerName;
    private String description;
    private String tag;
    private List<UUID> members;
    private List<String> memberNames;
    private List<UUID> banned;
    private List<String> bannedNames;
    private boolean isPublic;

    public Gang(String name, UUID owner, String OwnerName) {
        this.name = name;
        this.owner = owner;
        this.ownerName = OwnerName;
        this.description = "";
        this.tag = "";

        List<UUID> members = new ArrayList<>();
        members.add(owner);
        this.members = members;

        List<String> membersName = new ArrayList<>();
        membersName.add(ownerName);
        this.memberNames = membersName;

        this.banned = new ArrayList<>();
        this.bannedNames = new ArrayList<>();
        this.isPublic = false;
    }


    public Gang(String name, UUID owner, String ownerName, String description, String tag, List<UUID> members, List<String> memberNames, List<UUID> banned, List<String> bannedNames, boolean isPublic) {
        this.name = name;
        this.owner = owner;
        this.ownerName = ownerName;
        this.description = description;
        this.tag = tag;
        this.members = members;
        this.memberNames = memberNames;
        this.banned = banned;
        this.bannedNames = bannedNames;
        this.isPublic = isPublic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void setMembers(List<UUID> members) {
        this.members = members;
    }

    public void addMember(UUID member) {
        members.add(member);
    }

    public void removeMember(UUID member) {
        members.remove(member);
    }

    public List<String> getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(List<String> memberNames) {
        this.memberNames = memberNames;
    }

    public void addMemberName(String memberName) {
        memberNames.add(memberName);
    }

    public void removeMemberName(String memberName) {
        memberNames.remove(memberName);
    }

    public List<UUID> getBanned() {
        return banned;
    }

    public void setBanned(List<UUID> banned) {
        this.banned = banned;
    }

    public void addBanned(UUID banned) {
        this.banned.add(banned);
    }

    public void removeBanned(UUID banned) {
        this.banned.remove(banned);
    }

    public List<String> getBannedNames() {
        return bannedNames;
    }

    public void setBannedNames(List<String> bannedNames) {
        this.bannedNames = bannedNames;
    }

    public void addBannedName(String bannedName) {
        this.bannedNames.add(bannedName);
    }

    public void removeBannedName(String bannedName) {
        this.bannedNames.remove(bannedName);
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

}
