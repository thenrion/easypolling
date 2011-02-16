package com.ctg.easypolling.chart;

import com.ctg.easypolling.chart.impl.GoogleChartProvider;
import models.Poll;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: alexis
 * Date: 16/02/11
 * Time: 02:59
 * To change this template use File | Settings | File Templates.
 */
public class ChartProviderFactory
{
    /** create a new ChartProvider
     *  @return a new ChartProvider
     */
    public ChartProvider createProvider()
    {
        return new GoogleChartProvider();
    }
}
