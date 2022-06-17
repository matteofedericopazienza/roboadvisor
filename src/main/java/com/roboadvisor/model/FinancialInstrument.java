package com.roboadvisor.model;


public class FinancialInstrument {
    public String Name;
    public Double YTDPerformance;
    public Double YPerformance;
    public Double YYPerformance;
    public String YYYPerformance;
    public Double STDev;

    public Double getSTDev() {
        return STDev;
    }

    public void setSTDev(Double STDev) {
        this.STDev = STDev;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getYTDPerformance() {
        return YTDPerformance;
    }

    public void setYTDPerformance(Double YTDPerformance) {
        this.YTDPerformance = YTDPerformance;
    }

    public Double getYPerformance() {
        return YPerformance;
    }

    public void setYPerformance(Double YPerformance) {
        this.YPerformance = YPerformance;
    }

    public Double getYYPerformance() {
        return YYPerformance;
    }

    public void setYYPerformance(Double YYPerformance) {
        this.YYPerformance = YYPerformance;
    }

    public String getYYYPerformance() {
        return YYYPerformance;
    }

    public void setYYYPerformance(String YYYPerformance) {
        this.YYYPerformance = YYYPerformance;
    }
}
