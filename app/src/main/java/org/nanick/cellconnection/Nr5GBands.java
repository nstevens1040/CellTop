package org.nanick.cellconnection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Nr5GBands {
    @JsonProperty("Downlink")
    public ArrayList<Double> downlink;
    @JsonProperty("DLNRARFCN")
    public ArrayList<Double> dlNRARFCN;
    @JsonProperty("Uplink")
    public ArrayList<Double> uplink;
    @JsonProperty("UPNRARFCN")
    public ArrayList<Double> upNRARFCN;
    @JsonProperty("Band")
    public String band;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Mode")
    public String mode;
    @JsonProperty("BandWidth")
    public double bandwidth;
    @JsonProperty("DuplexSpacing")
    public int duplexSpacing;
    @JsonProperty("Geographical")
    public String geographical;
    @JsonProperty("GSM3GPP")
    public int gSM3GPP;
}