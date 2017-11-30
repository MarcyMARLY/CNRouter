package com.example.caspar.cnrouter;

/**
 * Created by Caspar on 28.11.2017.
 *
 * it's a class for remotely connectections table
 */

public class RemoteRow {
    private String networkAddress; // It's more like id
    private String nextHop;
    private int distance;

    public RemoteRow(String networkAddress, String nextHop, int distance)
    {
        this.networkAddress = networkAddress;
        this.nextHop = nextHop;
        this.distance = distance;
    }
    public String getNetworkAddress(){
        return this.networkAddress;
    }
    public String getNextHop(){
        return this.nextHop;
    }
    public int getDistance(){
        return this.distance;
    }
    public void setNetworkAddress(String na){
        this.networkAddress = na;
    }
    public void setNextHop(String nhop){
        this.nextHop = nhop;
    }
    public void setDistance(int d){
        this.distance = d;
    }





}
