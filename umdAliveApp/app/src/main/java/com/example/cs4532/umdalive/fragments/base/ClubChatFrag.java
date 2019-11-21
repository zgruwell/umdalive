package com.example.cs4532.umdalive.fragments.base;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;
import com.example.cs4532.umdalive.UserSingleton;
import com.example.cs4532.umdalive.fragments.create.CreateEventFrag;
import com.example.cs4532.umdalive.fragments.edit.EditClubFrag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClubChatFrag extends Fragment {
    //View
    View view;

    //Layout Components
    private Button sendButton;
    private EditText editText;
    private ListView listView;

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
        view = inflater.inflate(R.layout.club_chat_layout, container, false);


        //Get Layout Components
        getLayoutComponents();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
        sendButton = (Button) view.findViewById(R.id.sendButton);
        editText = (EditText) view.findViewById(R.id.messageInput);
        listView = (ListView) view.findViewById(R.id.chatDisplayBox);
    }

    /**
     * {"name":"",
     * "description":"",
     * "members":{
     * "admin":{"name":"","userID":""},
     * "regular":[]
     * },
     * "events":[]}
     */

    /**
     * Adds the club information to the page depending on which club was clicked. Inside there are several onClicks relevant to different items in lists being
     * clicked within the club layout.
     * @param res The response from the database
     * @throws JSONException Error in JSON processing
     * @see JSONException
     */
    private void updateUI(JSONObject res) throws JSONException{
        view.setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.PageLoading).setVisibility(View.GONE);


    }




}