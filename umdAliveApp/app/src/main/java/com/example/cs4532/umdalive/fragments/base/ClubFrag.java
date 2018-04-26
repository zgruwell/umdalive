package com.example.cs4532.umdalive.fragments.base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;
import com.example.cs4532.umdalive.UserSingleton;
import com.example.cs4532.umdalive.fragments.edit.EditClubFrag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Requires argument with key of clubID to be passed into it before it is added to the frame layout
 */

/**
 * @author Josh Senst
 *
 * 4/26/2018
 *
 * Class that creates the club page
 */
public class ClubFrag extends Fragment{

    //View
    View view;

    //Layout Components
    private TextView clubName;
    private TextView clubDescription;
    private LinearLayout members;
    private LinearLayout eventsList;
    private Button LeaveJoin;
    private FloatingActionButton editClub;
    private FloatingActionButton addEvent;


    /**
     * Creates the page whenever the club page is clicked on
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view The view of the club page
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Create View
        view = inflater.inflate(R.layout.club_layout, container, false);

        //Get Layout Components
        getLayoutComponents();

        //Use Volley Singleton to Update Page UI
        RestSingleton restSingleton = RestSingleton.getInstance(view.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, restSingleton.getUrl() + "getClub/" + getArguments().getString("clubID"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            updateUI(new JSONObject(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error connecting", String.valueOf(error));
            }
        });
        restSingleton.addToRequestQueue(stringRequest);

        //Return View
        return view;
    }


    /**
     * Gets the layout components from club_layout.xml
     * @return nothing
     */
    private void getLayoutComponents() {
        clubName = (TextView) view.findViewById(R.id.ClubNameView);
        clubDescription = (TextView) view.findViewById(R.id.DescriptionView);
        members = (LinearLayout) view.findViewById(R.id.MembersLayout);
        eventsList = (LinearLayout) view.findViewById(R.id.EventsLayout);
        LeaveJoin = (Button) view.findViewById(R.id.ClubJoin);
        LeaveJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestSingleton rs = RestSingleton.getInstance(view.getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, rs.getUrl() + "getClub/" + getArguments().getString("clubID"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    updateUI(new JSONObject(response));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error connecting", String.valueOf(error));
                    }
                });
                rs.addToRequestQueue(stringRequest);
            }
        });
        editClub = (FloatingActionButton) view.findViewById(R.id.EditClub);
        editClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditClubFrag frag = new EditClubFrag();
                Bundle data = new Bundle();
                data.putString("clubID", clubName.getTag().toString());
                frag.setArguments(data);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
            }
        });
    }

    /**
     * Adds the club information to the page depending on which club was clicked. Inside there are several onClicks relevant to different items in lists being
     * clicked within the club layout.
     * @param res The response from the database
     * @throws JSONException Error in JSON processing
     * @see JSONException
     */
    private void updateUI(JSONObject res) throws JSONException{
        /**String userName = UserSingleton.getInstance().getName();
        String userID = UserSingleton.getInstance().getUserID();*/
        getActivity().findViewById(R.id.PageLoading).setVisibility(View.GONE);
        clubName.setText(res.getString("name"));
        clubName.setTag(res.getString("_id"));
        clubDescription.setText(res.getString("description"));
        JSONObject memberJson = res.getJSONObject("members");
        JSONArray regulars = memberJson.getJSONArray("regular");
        JSONObject admins = memberJson.getJSONObject("admin");
        JSONArray events = res.getJSONArray("events");
        LinearLayout hla = new LinearLayout(view.getContext());
        hla.setOrientation(LinearLayout.HORIZONTAL);
        /**if(admins.getString("userID")!=userID){
            editClub.setVisibility(View.GONE);
        }*/
        TextView memberAdmin = new TextView(view.getContext());
        TextView admin = new TextView(view.getContext());
        admin.setText("admin");
        admin.setTextSize(16);
        admin.setGravity(Gravity.RIGHT);
        admin.setWidth(members.getWidth()/2);
        memberAdmin.setText(admins.getString("name"));
        memberAdmin.setTextSize(16);
        memberAdmin.setWidth(members.getWidth()/2);
        memberAdmin.setTag(admins.getString("userID"));
        memberAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String TAG = (String) v.getTag();
                    ProfileFrag frag = new ProfileFrag();
                    Bundle data = new Bundle();
                    data.putString("userID", TAG);
                    frag.setArguments(data);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
                }
            });
        hla.addView(memberAdmin);
        hla.addView(admin);
        members.addView(hla);
        for (int i=0;i<regulars.length();i++){
            String name = regulars.getJSONObject(i).getString("name");
            TextView member = new TextView(view.getContext());
            member.setText(name);
            member.setTextSize(16);
            member.setTag(regulars.getJSONObject(i).getString("userID"));
            member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String TAG = (String) v.getTag();
                    ProfileFrag frag = new ProfileFrag();
                    Bundle data = new Bundle();
                    data.putString("userID", TAG);
                    frag.setArguments(data);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
                }
            });
            members.addView(member);
        }
        if(events != null) {
            eventsList.setVisibility(View.VISIBLE);
            for (int i = 0; i < events.length(); i++) {
                LinearLayout hl = new LinearLayout(view.getContext());
                hl.setOrientation(LinearLayout.HORIZONTAL);
                String name = events.getJSONObject(i).getString("name");
                String day = events.getJSONObject(i).getString("date");
                String id = events.getJSONObject(i).getString("eventID").toString();
                TextView event = new TextView(view.getContext());
                TextView date = new TextView(view.getContext());
                date.setText(day);
                date.setTextSize(16);
                date.setGravity(Gravity.RIGHT);
                date.setWidth(members.getWidth()/2);
                event.setText(name);
                event.setTextSize(16);
                event.setWidth(members.getWidth()/2);
                event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String TAG = (String) v.getTag();
                        EventFrag frag = new EventFrag();
                        Bundle data = new Bundle();
                        data.putString("eventID", TAG);
                        frag.setArguments(data);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,frag).commit();
                    }
                });
                event.setTag(id);
                hl.addView(event);
                hl.addView(date);
                eventsList.addView(hl);
            }
        }
    }
}