package com.example.recommendation;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Park implements Comparable {
    private String ParkName;
    //  private String ParkID;

    private int PreferenceCounter;
    private String[] ParkFacilities;
    private float Rating;
    private boolean eventAvaliable;
    private String [] eventDays;
        private static ArrayList<Park> animalNamesList = null;

    public void setEventDays(String [] list){
        eventDays=list;
    }
    public String [] getEventDays(){
        return eventDays;
    }

    public void setEventAvaliable(boolean status){
        eventAvaliable=status;
    }
    public boolean getEventAvaliable(){
        return eventAvaliable;
    }

    public void setPreferenceCounter(int counter) {
        PreferenceCounter = counter;
    }

    public int getPreferenceCounter() {
        return PreferenceCounter;
    }

    public String[] getFacilitiesList() {
        return ParkFacilities;
    }
    public float getRating(){
        return Rating;
    }


    public String getName() {
        return ParkName;
    }

    public Park(String name, String[] FacilitiesList, float Rate) {
        ParkName = name;
        // ParkID=ID;
        ParkFacilities = FacilitiesList;
        //PreferenceCounter=0;
        Rating=Rate;
    }


    public static void setPreferences(ArrayList<Park> ParksList, ArrayList<Facilitiy> FacilitiesList, int numberOfVisits) {
        int counter;
        for (Park currentPark : ParksList) {
            currentPark.setPreferenceCounter(0);
             Log.d("CURRENTPARK", "Park Name= "+currentPark.getName()+" AND COUNT= "+currentPark.getPreferenceCounter());
            String[] currentFacilities = currentPark.getFacilitiesList();
            for (Facilitiy facilitiy : FacilitiesList) {
                Log.d("CURRENT_FAV_FACILITY","Current= "+facilitiy.getFacilityName());
                if (numberOfVisits<=10){
                    counter=1;
                }
                else{
                    counter=(int) numberOfVisits/5;
                }

                for (int i = 0; i < currentFacilities.length; i++) {
                    if ((facilitiy.getFacilityName()).equals(currentFacilities[i])&facilitiy.getFacilityCounter()>=counter) {
                        Log.d("CURRENT_READ_FACILITY","Current= "+currentFacilities[i]);

                        currentPark.setPreferenceCounter(currentPark.getPreferenceCounter() + 1);
                        Log.d("COUNTER ALTERED", "Park "+currentPark.getName()+" counter now= "+currentPark.getPreferenceCounter()+" Facility "+currentFacilities[i]);

                    }


                }
            }

        }

    }





    @Override
    public int compareTo(Object o) {
        int comparePreference = ((Park) o).getPreferenceCounter();
       //Decending
        return comparePreference -this.getPreferenceCounter()  ;
    }

    public static void setPreferencesForProfile(ArrayList<Park> ParksList, ArrayList<Facilitiy> FacilitiesList) {
        int counter;
        for (Park currentPark : ParksList) {
            currentPark.setPreferenceCounter(0);
            Log.d("CURRENTPARK", "Park Name= "+currentPark.getName()+" AND COUNT= "+currentPark.getPreferenceCounter());
            String[] currentFacilities = currentPark.getFacilitiesList();
            for (Facilitiy facilitiy : FacilitiesList) {
                Log.d("CURRENT_FAV_FACILITY","Current= "+facilitiy.getFacilityName());


                for (int i = 0; i < currentFacilities.length; i++) {
                    if ((facilitiy.getFacilityName()).equals(currentFacilities[i])) {
                        Log.d("CURRENT_READ_FACILITY","Current= "+currentFacilities[i]);

                        currentPark.setPreferenceCounter(currentPark.getPreferenceCounter() + 1);
                        Log.d("COUNTER ALTERED", "Park "+currentPark.getName()+" counter now= "+currentPark.getPreferenceCounter()+" Facility "+currentFacilities[i]);

                    }


                }
            }

        }

    }

    public static String [] getEventDays(String st, String en,int duration){

        String [] eventDays=new String[duration];


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        eventDays[0]=st;
        eventDays[eventDays.length-1]=en;
        String dt=st;

        for(int i=1;i<eventDays.length-1;i++){
            try {
                c.setTime(sdf.parse(dt));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 1);  // number of days to add
            dt = sdf.format(c.getTime());
            eventDays[i]=dt;
            Log.d("DATE_FOUND", "Event Date is "+ eventDays[i]);

        }
        //String dt = "2008-01-01";  // Start date

        return eventDays;

        }

        public static ArrayList <Park> searchResults(String charText,ArrayList<Park> arraylist){
     //   Log.d("ALERT","charText IS NOW: "+charText);
            animalNamesList=arraylist;
             charText = charText.toLowerCase(Locale.getDefault());
            animalNamesList.clear();
            if (charText.length() == 0) {
                Log.d("ALERT","Now in if statement");

                animalNamesList.addAll(arraylist);
                return animalNamesList;

            } else {
                Log.d("ALERT","Now in else statement");
                for (int i=0;i<arraylist.size();i++){
                    Park temp=arraylist.get(i);
                    Log.d("ALERT","Now in for loop");

                    if ((temp.getName().contains(charText))){


                //for (Park wp : arraylist) {

                  //  if (wp.getName().contains(charText)) {
                        animalNamesList.add(temp);
                      //  Log.d("SEARCH_LIST","list now contains "+animalNamesList.get(0).getName());

                    }
                }
            }
        return animalNamesList;
        }


    }



