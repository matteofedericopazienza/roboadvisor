package com.alphanft.alphanft.utils;

import yahoofinance.Stock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CalculationUtils {

    public static ArrayList<Double> getHistoricalClose (Stock stock) throws IOException {
        return stock.getHistory().stream().map(v -> v.getClose().doubleValue()).collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<Double> calculatePercentageChange(ArrayList<Double> values){
        ArrayList<Double> pctChange = new ArrayList<>();
        for (int i = 1; i < values.size(); i++){
            pctChange.add(((values.get(i) - values.get(i-1))/values.get(i-1))*100);
        }
        return pctChange;
    }

    public static ArrayList<Double> calculateLogPriceChange(ArrayList<Double> values){
        ArrayList<Double> priceChng = new ArrayList<>();
        for (int i = 1; i < values.size(); i++){
            priceChng.add((Math.log(values.get(i) / values.get(i-1))));
        }
        return priceChng;
    }

    public static Double calculateMean(ArrayList<Double> values) {
        return values.stream().reduce(0.0, Double::sum)/values.size();
    }

    public static Double calculateVariance(ArrayList<Double> values) {
        Double mean = calculateMean(values);
        return values.stream().map(v -> Math.pow(v - mean, 2)).reduce(0.0, Double::sum) / values.size();
    }

    public static Double calculateStd(ArrayList<Double> values){
        return Math.sqrt(calculateVariance(values));
    }
}
