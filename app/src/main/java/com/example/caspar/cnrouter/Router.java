package com.example.caspar.cnrouter;

import android.media.audiofx.Visualizer;

import java.util.ArrayList;

/**
 * Created by Caspar on 28.11.2017.
 */

public class Router {
    private String IPaddress;
    private ArrayList<LocalRow> RTable = new ArrayList<LocalRow>();
    private ArrayList<RemoteRow> RRTable = new ArrayList<RemoteRow>();

    public Router(String IPaddress) {
        this.IPaddress = IPaddress;
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public void setIPaddress(String IPaddress) {
        this.IPaddress = IPaddress;
    }

    public ArrayList<LocalRow> getRTable() {
        return RTable;
    }

    public void setRTable(ArrayList<LocalRow> RTable) {
        this.RTable = RTable;
    }

    public ArrayList<RemoteRow> getRRTable() {
        return RRTable;
    }

    public void setRRTable(ArrayList<RemoteRow> RRTable) {
        this.RRTable = RRTable;
    }

    public void addStaticRow(String na){
        LocalRow l = new LocalRow(na);
        RTable.add(l);
    }
    public void addDynRow(String na,String nh,int dist){
        RemoteRow r = new RemoteRow(na,nh,dist);
        RRTable.add(r);
    }


}
