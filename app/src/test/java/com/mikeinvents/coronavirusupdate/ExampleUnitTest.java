package com.mikeinvents.coronavirusupdate;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    String [] names = {
            "NCDC", "Akwa Ibom", "Anambra", "Bayelsa", "Bauchi", "Cross River", "Delta", "Enugu",
            "Ekiti", "Edo", "FCT", "Imo", "Kano", "Kaduna", "Lagos", "Niger", "Osun", "Ogun",
            "Ondo", "Oyo", "Plateau", "Rivers", "Sokoto", "Yobe", "Zamfara"
    };
    String [][] hotLines = {
            {"080097000010"}, {"08028442194","08037934966","09023330092"}, {"08145434416"},
            {"08039216821","07019304970","081511693570"},{"08023909309","08032717887","08059600898"},
            {"09036281412"},{"08033521961","08035078541","08030758179","09065031241"},
            {"117","112","08182555550"}, {"112","09062970434", "09062970435","0906297036"},
            {"08084096723","08064258163","08035835529"},{"08099936312","07080631500"},
            {"08099555577","07087110839"},{"09093995333", "09093995444"},
            {"08025088304", "08032401473", "08035871662","08037808191","08036045755"},
            {"08000267662"},{"08038246018", "0909309642", "08077213070"}, {"293","08035025692","08033908772"},
            {"08155978393","08188978392"}, {"08002684319", "07002684319","070012684319"},
            {"08095394000","0809586300"},{"07032864444","08035422711","08065486416", "08035779917"},
            {"08056109538","080318880938","08033124314"},{"07031935037","08022069567","080350774228"},
            {"08131834764","07041116027"},
            {"08035626731","08035161538","08161330774","08065408696","08105009888","08063075385"}
    };

    ArrayList<String []> list = new ArrayList<>();

    @Test
    public void addition_isCorrect() {
        assertEquals(25, hotLines.length);
    }

    @Test
    public void checkValues(){
        for(int i=0; i<names.length; i++){
            list.add(hotLines[i]);

        }
        System.out.print(Arrays.toString(list.get(0)));
       // System.out.println("This the hotline list: \n"+list);
    }
}