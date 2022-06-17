package com.roboadvisor.controller;

import com.roboadvisor.model.FinancialInstrument;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static com.alphanft.alphanft.utils.CalculationUtils.calculateStd;

@Controller
public class MainController {

    public Calendar getFirstDayOfTheYear() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, 1);
        return c;
    }

    public ArrayList<Double> getYTDCloses(Stock stock) throws IOException {
        List<HistoricalQuote> history = stock.getHistory(getFirstDayOfTheYear(), Calendar.getInstance(), Interval.DAILY).stream().filter(e -> e.getClose() != null).collect(Collectors.toList());
        return history.stream().map(val -> val.getClose().doubleValue()).collect(Collectors.toCollection(ArrayList<Double>::new));
    }

    public ArrayList<Double> get1YCloses(Stock stock) throws IOException {
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.add(Calendar.DATE, -365);
        List<HistoricalQuote> history = stock.getHistory(dateFrom, Calendar.getInstance(), Interval.DAILY);
        return history.stream().filter(e -> e.getClose() != null).map(val -> val.getClose().doubleValue()).collect(Collectors.toCollection(ArrayList<Double>::new));
    }

    public ArrayList<Double> get1YClosesBetweenYears(Stock stock, Integer yearFrom, Integer yearTo) throws IOException {
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.set(Calendar.YEAR, yearFrom);
        dateFrom.set(Calendar.DAY_OF_YEAR, 1);
        dateFrom.set(Calendar.MONTH, 1);
        Calendar dateTo = Calendar.getInstance();
        dateTo.set(Calendar.YEAR, yearTo);
        dateTo.set(Calendar.DAY_OF_YEAR, 1);
        dateTo.set(Calendar.MONTH, 1);
        List<HistoricalQuote> history = stock.getHistory(dateFrom, dateTo, Interval.DAILY);
        return history.stream().map(val -> val.getClose().doubleValue()).collect(Collectors.toCollection(ArrayList<Double>::new));
    }

    public Double getPerformancePercent(Double val1, Double val2) {
        return ((val2 - val1) / val1) * 100;
    }

    @RequestMapping("index.html")
    public String getHomePage(Model model) throws IOException {
        return "index";
    }

    public FinancialInstrument mapInstrument(Stock stock) throws IOException {
        ArrayList<Double> historicalYTDcloses = getYTDCloses(stock);
        ArrayList<Double> historical1YCloses = get1YCloses(stock);
        ArrayList<Double> historical1YClosesBetweenYears = get1YClosesBetweenYears(stock, 2020, 2021);
        FinancialInstrument inst = new FinancialInstrument();

        inst.setName(stock.getName());
        inst.setYTDPerformance(getPerformancePercent(historicalYTDcloses.get(0), historicalYTDcloses.get(historicalYTDcloses.size() - 1)));
        inst.setYPerformance(getPerformancePercent(historical1YCloses.get(0), historical1YCloses.get(historical1YCloses.size() - 1)));
        inst.setYYPerformance(getPerformancePercent(historical1YClosesBetweenYears.get(0), historical1YClosesBetweenYears.get(historical1YClosesBetweenYears.size() - 1)));
        inst.setSTDev(calculateStd(historicalYTDcloses));
        return inst;
    }

    @RequestMapping("tables.html")
    public String getTables(Model model) throws IOException {
        FinancialInstrument inst1 = mapInstrument(YahooFinance.get("XSPU.L", Interval.DAILY));
        FinancialInstrument inst2 = mapInstrument(YahooFinance.get("XDWM.DE", Interval.DAILY));
        FinancialInstrument inst3 = mapInstrument(YahooFinance.get("XDWS.L", Interval.DAILY));

        ArrayList<FinancialInstrument> insts = new ArrayList<>();

        insts.add(inst1);
        insts.add(inst2);
        insts.add(inst3);
        model.addAttribute("instrument", insts);
        return "tables";
    }

    @RequestMapping("sidebar.html")
    public String getSidebar(Model model) {
        return "sidebar";
    }

    @RequestMapping("ui-cards.html")
    public String getBlog(Model model) {
        return "ui-cards";
    }

    @RequestMapping("blog-single.html")
    public String getBlogSingle() {
        return "blog-single";
    }

    @RequestMapping("inner-page.html")
    public String getInnerPage() {
        return "inner-page";
    }

    @RequestMapping("portfolio-details.html")
    public String getPortfolioDetails() {
        return "portfolio-details";
    }

    @RequestMapping("charts-chartjs.html")
    public String getCharts() {
        return "charts-chartjs";
    }

    @RequestMapping("pages-blank.html")
    public String getBlank() {
        return "pages-blank";
    }
}
