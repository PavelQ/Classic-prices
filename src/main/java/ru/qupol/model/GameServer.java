package ru.qupol.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "GAMES_SERVERS")
public class GameServer implements Comparable<GameServer> {
    public GameServer() {
//        gameServerId = new GameServerId();
    }

    @Id
    @GeneratedValue
    private long Id;

    @Column
    private String name;
    //
    @Column
    private Faction faction;

    public GameServer(String name) {
        this.name = name;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
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
        int comp = this.getName().compareTo(o.getName());
        if (comp == 0) {
            if (this.getFaction() == null && o.getFaction() == null) {
                return 0;
            } else if (this.getFaction() != null && o.getFaction() != null) {
                return this.getFaction().compareTo(o.getFaction());
            } else {
                if (this.getFaction() != null)
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
                "name='" + getName() + '\'' +
                ", faction=" + getFaction() +
                ", id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameServer that = (GameServer) o;
        return Objects.equals(getName(), that.getName()) &&
                getFaction() == that.getFaction();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getFaction());
    }
}
