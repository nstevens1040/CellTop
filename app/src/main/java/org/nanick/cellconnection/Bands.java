package org.nanick.cellconnection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Bands{
    @JsonProperty("Downlink")
    public ArrayList<Double> downlink;
    @JsonProperty("DLEARFCN")
    public ArrayList<Double> dLEARFCN;
    @JsonProperty("Uplink")
    public ArrayList<Double> uplink;
    @JsonProperty("ULEARFCN")
    public ArrayList<Double> uLEARFCN;
    @JsonProperty("Band")
    public int band;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Mode")
    public String mode;
    @JsonProperty("Bandwidth")
    public double bandwidth;
    @JsonProperty("DuplexSpacing")
    public int duplexSpacing;
    @JsonProperty("Geographical")
    public String geographical;
    @JsonProperty("GSM3GPP")
    public int gSM3GPP;
}