package com.example.cs4532.umdalive.fragments.base;

import android.app.Activity;
import android.os.Bundle;

import com.example.cs4532.umdalive.MainActivity;
import com.example.cs4532.umdalive.R;
import com.google.android.material.textfield.TextInputLayout;

import android.view.View;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_chat_layout);
    }

    private void enterMessage() throws IOException, SAXException, JSONException {
        TextInputLayout messageInput = view.findViewById(R.id.messageInput);
        String message = messageInput.getEditText().getText().toString();

        JSONObject JSONmessage = new JSONObject(message);
        chatArray.put(JSONmessage);

        updateChat();
    }

    private void updateChat() throws JSONException {
        TextView chatWindow = view.findViewById(R.id.chatTextView);
        for(int i=0; i<chatArray.length(); i++) {
            chatWindow.append(chatArray.getJSONObject(i).toString());
        }
    }
}
