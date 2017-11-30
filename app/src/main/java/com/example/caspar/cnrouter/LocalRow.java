package com.example.caspar.cnrouter;

/**
 * Created by Caspar on 28.11.2017.
 *
 * It's a class for directly-connected connections table
 */

public class LocalRow {
    private String networkAddress;

    public LocalRow(String networkAddress){
        this.networkAddress = networkAddress;
    }


    public String getNetworkAddress(){
        return this.networkAddress;
    }
    public void setNetworkAddress(String na){
        this.networkAddress = na;
    }
}
