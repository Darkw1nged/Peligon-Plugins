package net.peligon.PeligonPrison.struts;

public class Prestige {

    private String name;
    private double cost;
    private double rankMultiplier;

    public Prestige(String name, double cost, double rankMultiplier) {
        this.name = name;
        this.cost = cost;
        this.rankMultiplier = rankMultiplier;
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

    public double getRankMultiplier() {
        return rankMultiplier;
    }

    public void setRankMultiplier(double rankMultiplier) {
        this.rankMultiplier = rankMultiplier;
    }

}
