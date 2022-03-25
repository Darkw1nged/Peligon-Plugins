package net.peligon.PeligonPrison.struts;

public class Rank {

    private String name;
    private double cost;
    private boolean isDefault;

    public Rank(String name, double cost, boolean isDefault) {
        this.name = name;
        this.cost = cost;
        this.isDefault = isDefault;
    }

    public Rank(String name, double cost) {
        this.name = name;
        this.cost = cost;
        isDefault = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean defaultRank) {
        isDefault = defaultRank;
    }
}
