package ru.qupol.model;

import java.util.Objects;

public class GameServer implements Comparable<GameServer> {
    private String name;
    private Faction faction;

    public GameServer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    @Override
    public int compareTo(GameServer o) {
        int comp = this.name.compareTo(o.name);
        if (comp == 0) {
            if (this.faction == null && o.faction == null) {
                return 0;
            } else if (this.faction != null && o.faction != null) {
                return this.faction.compareTo(o.faction);
            } else {
                if (this.faction != null)
                    return 1;
                else
                    return -1;
            }
        } else return comp;
    }

    public enum Faction {
        HORDE,
        ALIANCE
    }

    @Override
    public String toString() {
        return "GameServer{" +
                "name='" + name + '\'' +
                ", faction=" + faction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameServer that = (GameServer) o;
        return Objects.equals(name, that.name) &&
                faction == that.faction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, faction);
    }
}
