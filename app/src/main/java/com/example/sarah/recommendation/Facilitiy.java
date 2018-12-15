package com.example.recommendation;

import java.util.ArrayList;

public class Facilitiy {
    private String FacilityName;
    private int FacilityCounter;
    protected static int tempCounter;
    protected static int tempIndex;
    protected static Facilitiy tempFacility;


    public void setFacilityName(String Name){
        FacilityName=Name;
    }
    public void setFacilityCounter(int Number){
        FacilityCounter=Number;
    }
    public String getFacilityName(){
        return FacilityName;
    }
    public int getFacilityCounter(){
        return FacilityCounter;
    }
    public Facilitiy(String name){
        FacilityName=name;
        FacilityCounter=0;
    }
    public Facilitiy(){

    }
    public static boolean AlreadyExists(String Name, ArrayList<Facilitiy>List){
        boolean answer=false;
        for(Facilitiy facilitiy: List){
            if(facilitiy.getFacilityName().equals(Name)){
                tempIndex=List.indexOf(facilitiy);
                tempCounter=facilitiy.FacilityCounter;
                facilitiy.setFacilityCounter(tempCounter+1);
                tempFacility=facilitiy;
                answer=true;
                break;
            }
        }
        return answer;
    }

}
