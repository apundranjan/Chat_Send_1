package com.example.dellpc.yef_chat;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity  {
    private static final String TAG = "ChatActivity";
    private ChatArrayAdapter adp;
    private ListView list;
    private EditText chatText;
    private Button send;
    Intent intent;
    private boolean side = false;


    // for speech//

    protected static final int SPEECH = 1;
    private ImageButton speak;
    private TextView txt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_main);
        final Button send = findViewById(R.id.btn);
        final ListView list = findViewById(R.id.listview);
        adp = new ChatArrayAdapter(getApplicationContext(), R.layout.chat);
        list.setAdapter(adp);
        EditText chatText = findViewById(R.id.chat_text);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode ==
                        KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            public void OnChanged() {
                super.onChanged();
                list.setSelection(adp.getCount() - 1);
            }
        });

        //for speech
        final TextView txt = findViewById(R.id.txtText);
        final ImageButton speak = findViewById(R.id.speakbtn);

        speak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub//


                Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-us");


                try {

                    startActivityForResult(in, SPEECH);

                    txt.setText("");

                } catch (ActivityNotFoundException e) {


                    Toast t = Toast.makeText(getApplicationContext(), "your device not support Speech to Text", Toast.LENGTH_SHORT);

                    t.show();


                }
            }
        });

        //speech ends

    }

    private boolean sendChatMessage() {
        adp.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");
        side = !side;
        return true;
    }

    //for speech

    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub//
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case SPEECH: {


                if (requestCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txt.setText(text.get(0));
                }


                break;

            }


        }


    }

    //speech ends


}

// for login and register

