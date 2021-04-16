package com.huanhe_tech.cli.DAO;

public class SymbolsList {
    private int id;
    private int conid;

    public SymbolsList() {
    }

    public SymbolsList(int id, int conid) {
        this.id = id;
        this.conid = conid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConid() {
        return conid;
    }

    public void setConid(int conid) {
        this.conid = conid;
    }

    @Override
    public String toString() {
        return "symbolsList{" +
                "id=" + id +
                ", conid='" + conid + '\'' +
                '}';
    }
}
