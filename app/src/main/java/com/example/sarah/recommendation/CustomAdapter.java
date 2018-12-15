package com.example.recommendation;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Oclemy for ProgrammingWizards TV Channel and http://www.camposha.info.
 - Our adapter class.
 - Derives from android.widget.BaseAdapter.
 - Here we: inflate our model xml layout to viewitems and recycle it, bind data to these viewitems.
 - The data we bind is passed to us via constructor.
 - Apart from the data being passed us, we are also passed a Context object that will help us getSystemService that we  need for
 our inflation of model layout.
 - Being that we derive from BaseAdapter, we override getCount() which returns total count of our data, getItem() which returns
 each data object,getItemId() which returns the object's id, and getView() to return us its view().
 */
public class CustomAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Park> listOfParks;
    private Context c;
    protected  ArrayList <Park> DisplayList;

    public CustomAdapter(Context c,ArrayList<Park> parks,ArrayList<Park>DisplayList) {
        this.listOfParks= parks;
        this.c = c;
        this.DisplayList=DisplayList;
    }
    @Override
    public int getCount() {
        return DisplayList.size();
    }
    @Override
    public Object getItem(int i) {
        return DisplayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    /*
    INFLATE XML LAYOUT TO VIEW
     */
    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
        }

        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);
        //ImageView img= (ImageView) view.findViewById(R.id.spacecraftImage);
        SimpleRatingBar ratingBar= (SimpleRatingBar) view.findViewById(R.id.ratingBarID);
        ImageView eventStatus=(ImageView)view.findViewById(R.id.eventStatus);

        final Park s= (Park) this.getItem(pos);

        nameTxt.setText(s.getName());
        ratingBar.setRating(s.getRating());
        ratingBar.setIndicator(true);



        if (s.getEventAvaliable()==true){
            String [] eventDays=s.getEventDays();
            String TDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Log.d("ARE_YOU_ALLOWED","Hello this is "+TDate);
            for(int i=0;i<eventDays.length;i++){
                if (eventDays[i].equals(TDate)){
                    eventStatus.setVisibility(View.VISIBLE);
                    break;
                }
                else{
                    eventStatus.setVisibility(View.INVISIBLE);

                }
            }

        }
        else{
            eventStatus.setVisibility(View.INVISIBLE);
        }

        // img.setImageResource(s.getImage());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, s.getName()+ " Rating : "+String.valueOf(s.getRating()), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
/*
    public void filter(String charText) {
        ArrayList<Park> Result=listOfParks;
        charText = charText.toLowerCase(Locale.getDefault());
        Result.clear();
        if (charText.length() == 0) {
            Log.d("ENTERED_IF_STATEMENT","Text");
            Result.addAll(listOfParks);
        } else {
            for (Park wp : listOfParks) {
                Log.d("ENTERED_for_STATEMENT","Text");

                if (wp.getName().contains(charText)) {
                    Result.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
*/
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Park> cloneList=listOfParks;


                // If the constraint (search string/pattern) is null
                // or its length is 0, i.e., its empty then
                // we just set the `values` property to the
                // original contacts list which contains all of them
                if (constraint == null || constraint.length() == 0) {
                    results.values =   cloneList;
                    results.count = cloneList.size();
                //Parkresults=listOfParks;
                }
                else {
                    // Some search copnstraint has been passed
                    // so let's filter accordingly
                    //ArrayList<String> filteredTitle = new ArrayList<String>();
                    ArrayList<Park> Parkresults = new ArrayList<Park>();
                   // DisplayList.clear();
                    // We'll go through all the title and see
                    // if they contain the supplied string
                    for (Park c : cloneList) {
                        Log.d("FOR_LOOP","Entered");
                        if (c.getName().contains( constraint.toString() )) {
                            Log.d("FOUND_SOMETHING","Park Name "+c.getName());
                            // if `contains` == true then add it
                            // to our filtered list
                            Parkresults.add(c);
                        }
                    }

                    // Finally set the filtered values and size/count
                    results.values = Parkresults;
                    results.count = Parkresults.size();
                }

                // Return our FilterResults object
               // setSearchResult(Parkresults);
              //  listOfParks.clear();
                return results;


            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                DisplayList = (ArrayList<Park>) results.values;
                //setSearchResult(searchResult);
                //     for (Park list: mList){
               //     searchResult.add(list); //Log.i("filtro", "Results: " +list); } notifyDataSetChanged(); }
                //}
               // setSearchResult(searchResult);
                notifyDataSetChanged();
            }
        };
    }
    public void setSearchResult (ArrayList<Park> list){
       // searchResult=list;
    }
   // public ArrayList<Park> getSearchResult(){
      //  return searchResult;
    //}



}
