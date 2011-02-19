package com.ctg.easypolling.chart.impl;

import com.ctg.easypolling.chart.ChartProvider;
import models.Answer;
import models.Poll;
import models.Proposition;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: alexis
 * Date: 16/02/11
 * Time: 02:57
 * To change this template use File | Settings | File Templates.
 */
public class GoogleChartProvider implements ChartProvider
{
    /** create an url representing an image that show the results of the given poll
     *  @param poll a Poll
     *  @return an URL
     */
    public URL createChart(Poll poll)
    {
        URL url = null;

        if ( poll != null )
        {
            StringBuilder urlBuilder = new StringBuilder();

            /* process poll data */
            Collection<Answer> answers = Answer.findAnswersOfPoll(poll);

            if( answers != null && ! answers.isEmpty() )
            {
                /* map containing Proposition as key and count of answer in value */
                Map<Proposition, Integer> answerPerPropositions = new HashMap<Proposition, Integer>();

                Iterator<Answer> it = answers.iterator();

                while(it.hasNext())
                {
                    Answer currentAnswer = it.next();

                    if ( currentAnswer != null )
                    {
                        Integer currentCount = answerPerPropositions.get(currentAnswer.proposition);

                        answerPerPropositions.put(currentAnswer.proposition, (currentCount == null ? 1 : currentCount.intValue() + 1));
                    }
                }

                urlBuilder.append("https://chart.googleapis.com/chart?");

                urlBuilder.append("cht=p3");//type 3D pie chart
                urlBuilder.append("&");
                urlBuilder.append("chs=500x250");//size
                urlBuilder.append("&");
                urlBuilder.append("chf=bg,s,65432100");//background
                urlBuilder.append("&");


                /** give the results */
                StringBuilder data   = new StringBuilder();
                StringBuilder labels = new StringBuilder();

                Iterator<Map.Entry<Proposition, Integer>> entries = answerPerPropositions.entrySet().iterator();

                while(entries.hasNext())
                {
                    Map.Entry<Proposition, Integer> currentEntry = entries.next();

                    data.append(currentEntry.getValue() == null ? "0" : Integer.toString(currentEntry.getValue()));
                    labels.append(currentEntry.getKey().value);

                    if ( entries.hasNext() )
                    {
                        data.append(",");
                        labels.append("|");
                    }
                }

                urlBuilder.append("chd=t:" + data.toString());
                urlBuilder.append("&");
                urlBuilder.append("chl=" + labels.toString());

                try
                {
                    url = new URL(urlBuilder.toString());
                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        return url;
    }

}
