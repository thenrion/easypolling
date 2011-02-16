package com.ctg.easypolling.chart;

import models.Poll;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: alexis
 * Date: 16/02/11
 * Time: 02:55
 * To change this template use File | Settings | File Templates.
 */
public interface ChartProvider
{
    /** create an url representing an image that show the results of the given poll
     *  @param poll a Poll
     *  @return an URL
     */
    public URL createChart(Poll poll);
}
