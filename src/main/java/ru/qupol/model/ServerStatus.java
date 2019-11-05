package ru.qupol.model;

public class ServerStatus {
    String serverName;
    GameServerType serverType;
    String population;
    String serverLocation;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public GameServerType getServerType() {
        return serverType;
    }

    public void setServerType(GameServerType serverType) {
        this.serverType = serverType;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getServerLocation() {
        return serverLocation;
    }

    public void setServerLocation(String serverLocation) {
        this.serverLocation = serverLocation;
    }
}
