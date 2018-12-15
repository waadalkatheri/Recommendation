package com.example.recommendation;

import android.content.Intent;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.Handler;


public class RecomendaionActivity extends AppCompatActivity {
    private ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    protected static ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Park> listOfParks = new ArrayList<>();
    ArrayList<Park> displayList = new ArrayList<>();

    protected static ArrayList<Facilitiy> AvaliableFacilities = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private String Name;
    final static String user_id="U003";
    // protected static String [] visitedParks;
    protected  String visitedParkID;
    // String [] visitedBEFORE;
    protected int historyRecords;
    SearchView searchChanged;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference databaseReference;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Parks");
        listView = (ListView) findViewById(R.id.lists);
        searchChanged= (SearchView) findViewById(R.id.searchView);
        //searchView=(SearchView) findViewById(R.id.searchView);

        getVisitedParksCount (); //Get the number of records of the user history.
        //set the visited parks array with the parks IDs.




        databaseReference.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {

                getListOfParks(dataSnapshot); //Get the list of all parks in the database with their facilities list

                if (historyRecords<4){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("Users").child(String.valueOf(user_id)).addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String PreferredFacilities = String.valueOf(dataSnapshot.child("Interests").getValue());
                            String[] DesiredFacilities = PreferredFacilities.split(" ");
                            for (int i = 0; i < DesiredFacilities.length; i++) {
                                Facilitiy temproaryFacility=new Facilitiy(DesiredFacilities[i]);
                                AvaliableFacilities.add(temproaryFacility);
                            }
                            Log.d ("NOTE","Entered if Statement");

                            //getUserIntrests();
                            Park.setPreferencesForProfile(listOfParks, AvaliableFacilities);
                            Log.d ("NOTE","Preferences Successfully Set");
                            Collections.sort(listOfParks);
                            ArrayList<String> parks = getSortedList(listOfParks);
                            Log.d ("NOTE","Parks Successfully Sorted");

                            //arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, parks);
                            //   listView.setAdapter(arrayAdapter);
                            displayList=listOfParks;
                            adapter=new CustomAdapter(RecomendaionActivity.this,listOfParks, displayList);
                            listView.setAdapter(adapter);
                            //listView.setAdapter(new CustomAdapter(MainActivity.this, listOfParks));
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("UserHistory").child(String.valueOf(user_id)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot UdataSnapshot) {
                            for (DataSnapshot childSnapshot : UdataSnapshot.getChildren()) {
                                // for (int k=1;k<=historyRecords;k++) {
                                String ParkID = String.valueOf(childSnapshot.child("ParkID").getValue());
                                Log.d("GOT_SOMETHING", "FOUND ID= " + ParkID);
                                String currentParkID = String.valueOf(dataSnapshot.child("ParkID").getValue());


                                if (currentParkID.equals(ParkID)) {
                                    //Get the park facilities and store it in a string.
                                    String Facilities = String.valueOf(dataSnapshot.child("Facilities").getValue());
                                    //Split the Facilities' string to get each indivisual item in the list and store it in an array.
                                    String[] items = Facilities.split(" ");
                                    for (int i = 0; i < items.length; i++) {
                                        // Log.d("GOT_A_FACILITY", "FOUND facility= " + items[0]);

                                        //Check if the facility already exists in the list.
                                        boolean alreadyExists = Facilitiy.AlreadyExists(items[i], AvaliableFacilities);
                                        if (alreadyExists == true) {
                                            int Tempo = Facilitiy.tempFacility.getFacilityCounter();

                                            Log.d("COUNTER", items[i] + " Equals " + Tempo);
                                            // break;
                                        } else {
                                            Facilitiy temproaryFacility = new Facilitiy(items[i]);


                                            AvaliableFacilities.add(temproaryFacility);
                                            Log.d("AVALIABLE_FACILITIES", "Added a new object " + AvaliableFacilities.size());
                                            Log.d("ELSE CONDITION", "A new item Added: " + items[i]);
                                            //arrayList.add(items[i]);
                                        }

                                    }

                                    break;
                                    // String name = String.valueOf(dataSnapshot.child("ParkName").getValue());
                                } //End of if statement

                            }

                            Log.d("AFTER_MAIN","some text");
                            // perform set on query text listener event



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                }

            } //End of onChildAdded.


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        }); //End of ChildEventListener.

     //   searchChanged.onActionViewExpanded();
     //   searchChanged.setIconified(true);

        searchChanged.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                Log.d("DONE_ADDING","List Updated");
                //displayList=adapter.getSearchResult();
                // adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                return false;

            }
        });
    } // End of OnCreate Method.


    public void getListOfParks(DataSnapshot dataSnapshot)  {
        String Name= String.valueOf(dataSnapshot.child("ParkName").getValue());
        String Facilities= String.valueOf(dataSnapshot.child("Facilities").getValue());
        String SRating= String.valueOf(dataSnapshot.child("OverallRating").getValue());
        float Rating=Float.valueOf(SRating);
        String[] ParkFacilities= Facilities.split(" ");


        // String [] dates=Park.getEventDays(strDate,endDate,duration);
        String event=String.valueOf(dataSnapshot.child("EventID").getValue());
        Boolean eventStatus=event.contains("E");
        Park tempPark=new Park(Name,ParkFacilities,Rating);

        if(eventStatus){
            String eventStartDate=String.valueOf(dataSnapshot.child("EventStartDate").getValue());
            String eventEndDate=String.valueOf(dataSnapshot.child("EventEndDate").getValue());
            int duration=Integer.valueOf(String.valueOf(dataSnapshot.child("EventDuration").getValue()));
            tempPark.setEventAvaliable(eventStatus);
            tempPark.setEventDays(tempPark.getEventDays(eventStartDate,eventEndDate, duration));

        }
        Log.d("ALERT", "Event status is "+eventStatus);

        listOfParks.add(tempPark);
        Log.d("ALERT", "Park Added with name "+Name);
    } //Get the list of parks

    public ArrayList<String> getSortedList(ArrayList <Park> list){
        ArrayList<String> ParksList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ParksList.add(list.get(i).getName()+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t "+" التقييم "+list.get(i).getRating()+"\nتبعد عنك:");
            Log.d("SORTED", "\nPark Name= "+list.get(i).getName());

        }
        return ParksList;
    } //Get sorted arraylist of string trype to display

    //Get the number of parks that the user have visited , to use it as the length of the array in order to loop through all the visited parks.
    public void getVisitedParksCount (){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("UserHistory").child(String.valueOf(user_id)).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot PdataSnapshot) {
                String counter=String.valueOf(PdataSnapshot.getChildrenCount());
                historyRecords=Integer.valueOf(counter);

                Log.d("GOT_SOMETHING", "FOUND COUNTER= " + historyRecords);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getUserIntrests(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("Users").child(String.valueOf(user_id)).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String PreferredFacilities = String.valueOf(dataSnapshot.child("Interests").getValue());
                String[] DesiredFacilities = PreferredFacilities.split(" ");
                for (int i = 0; i < DesiredFacilities.length; i++) {
                    Facilitiy temproaryFacility=new Facilitiy(DesiredFacilities[i]);
                    AvaliableFacilities.add(temproaryFacility);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
