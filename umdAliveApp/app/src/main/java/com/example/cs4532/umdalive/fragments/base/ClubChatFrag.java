package com.example.cs4532.umdalive.fragments.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cs4532.umdalive.MainActivity;
import com.example.cs4532.umdalive.R;
import com.example.cs4532.umdalive.RestSingleton;
import com.example.cs4532.umdalive.UserSingleton;
import com.google.android.material.textfield.TextInputLayout;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.lang.String;

public class ClubChatFrag extends Activity {
    View view;
    JSONArray chatArray = new JSONArray();
    Button send;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_chat_layout);

        send = view.findViewById(R.id.enterButton);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    enterMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                };
            }
        });
    }

    private void enterMessage() throws IOException, SAXException, JSONException {
        TextInputLayout messageInput = view.findViewById(R.id.messageInput);
        String message = messageInput.getEditText().getText().toString();

        JSONObject newMessageData = new JSONObject();
        try {
            newMessageData.put("name", UserSingleton.getInstance().getName());
            newMessageData.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, RestSingleton.getInstance(view.getContext()).getUrl() + "sendMessage", newMessageData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error connecting", String.valueOf(error));
            }
        });

        RestSingleton.getInstance(view.getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void updateChat() throws JSONException {
        TextView chatWindow = view.findViewById(R.id.chatTextView);
        for(int i=0; i<chatArray.length(); i++) {
            chatWindow.append(chatArray.getJSONObject(i).toString());
        }
    }
}
