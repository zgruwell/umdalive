package com.example.kevin.umdalive;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayClubView extends AppCompatActivity {

    private static String clubName;
    private static String description;
    private static ArrayList<String> posts;
    private static String administrator;
    private static String keywords;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_club_view);
        // ALSO, nothing the user puts in here saves at this point so thats something that definitely should be fixed.
        TextView clubnameSetText = (TextView) findViewById(R.id.display_club_name);
        TextView discriptionSetText = (TextView) findViewById(R.id.display_club_description);
        TextView keywordSetText = (TextView) findViewById(R.id.display_clubs_keyword);
        TextView administratorSetText = (TextView) findViewById(R.id.display_clubs_administator);

        clubnameSetText.setText(clubName);
        discriptionSetText.setText(description);
        keywordSetText.setText(keywords);
        administratorSetText.setText(administrator);
    }
}
