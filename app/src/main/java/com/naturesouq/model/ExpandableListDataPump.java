package com.naturesouq.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by SI-Andriod on 15-Oct-15.
 */
public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> home = new ArrayList<String>();

        List<String> myaccount = new ArrayList<String>();


        List<String> categories = new ArrayList<String>();


        List<String> wellness = new ArrayList<String>();
        wellness.add("Wellness");

        List<String> naturefriendly = new ArrayList<String>();
        naturefriendly.add("Organic");
        naturefriendly.add("Drinks");

        List<String> airtreatment = new ArrayList<String>();
        airtreatment.add("AirTreatment");

        List<String> innovative = new ArrayList<String>();
        innovative.add("Innovative");

        List<String> specialist = new ArrayList<String>();
        specialist.add("Specialist");

        List<String> beautycare = new ArrayList<String>();
        beautycare.add("BeautyCare");

        expandableListDetail.put("Home", home);
        expandableListDetail.put("MyAccount", myaccount);
        expandableListDetail.put("CATEGORIES", categories);
        expandableListDetail.put("Wellness", wellness);
        expandableListDetail.put("NatureFriendly", naturefriendly);
        expandableListDetail.put("Airtreatment", airtreatment);
        expandableListDetail.put("Innovative", innovative);
        expandableListDetail.put("Specialist", specialist);
        expandableListDetail.put("BeautyCare", beautycare);

        return expandableListDetail;
    }
}
