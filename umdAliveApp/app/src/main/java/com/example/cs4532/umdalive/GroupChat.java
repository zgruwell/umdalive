package com.example.cs4532.umdalive;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.example.cs4532.umdalive.fragments.create.CreateClubFrag;


public class GroupChat extends AppCompatActivity {

    private FirebaseListAdapter<ChatMessage> adapter;
    private String clubID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_chat_layout);
        FirebaseApp.initializeApp(this);

        Bundle b = getIntent().getExtras();
        if(b != null)
            clubID = b.getString("key");

        displayGroupChatMessages();

        Button sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageInput = (EditText) findViewById(R.id.messageInput);
                FirebaseDatabase.getInstance().getReference().child(clubID).push().setValue((ChatMessage) new ChatMessage(messageInput.getText().toString(),
                        UserSingleton.getInstance().getName(), UserSingleton.getInstance().getProfileUrl()));

                messageInput.setText("");
            }
        });
    }

    private void displayGroupChatMessages() {

        ListView displayOfAllMessages = (ListView) findViewById(R.id.chatDisplayBox);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference().child(clubID).getRef()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);
                ImageView messageImage = (ImageView) v.findViewById(R.id.message_image);


                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
            };
            displayOfAllMessages.setAdapter(adapter);

            }
            public GroupChat() {

            }

        }


