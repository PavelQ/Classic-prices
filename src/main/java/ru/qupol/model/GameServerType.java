package ru.qupol.model;

public enum GameServerType {
    PVE,
    PVP,
    RP,
    PVP_RP;

    static GameServerType getType(String stringValue) {
        switch (stringValue) {
            case "PvP":
                return PVP;
            case "PvE":
                return PVE;
            case "RP":
                return RP;
            case "PvP RP":
                return PVP_RP;
            default:
                throw new IllegalArgumentException("incorrect value:" + stringValue);
        }
    }
}
