package mj.ghadir.tweetsplitter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String CopiedText, getCopy = "";
    ItemAdapter adapter;
    ListView lstItem;
    ArrayList<String> arrayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstItem = (ListView) findViewById(R.id.lstItem);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CopiedText = readFromClipboard();
                String[] str = CopiedText.split(" ");
                arrayText = new ArrayList<>();
                int num = 0;
                int j = 0;
                while (num < CopiedText.length()) {
                    String tmp1 = "";
                    while (tmp1.length() < 280) {
                        if (j < str.length) {
                            if ((tmp1 + str[j] + " ").length() >= 280) {
                                break;
                            } else tmp1 = tmp1 + str[j] + " ";
                            j++;
                        } else break;
                    }
                    arrayText.add(tmp1);
                    num = num + tmp1.length();
                }

                adapter = new ItemAdapter(MainActivity.this, arrayText);
                lstItem.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_me) {
            String url = "https://twitter.com/code_khoda";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }
        if (id == R.id.action_help) {
            String url = "https://twitter.com/code_khoda/status/989617384465059842";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String readFromClipboard() {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB){
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            getCopy = clipboard.getText().toString();
        }else{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if(clipboard.hasPrimaryClip()== true){
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                getCopy = item.getText().toString();
            }else{
                Toast.makeText(getApplicationContext(), "Nothing to Paste", Toast.LENGTH_SHORT).show();
            }
        }
        /*ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.hasPrimaryClip()) {
            android.content.ClipDescription description = clipboard.getPrimaryClipDescription();
            android.content.ClipData data = clipboard.getPrimaryClip();
            if (data != null && description != null && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
                getCopy = String.valueOf(data.getItemAt(0).getText());
        }*/
        return getCopy;
    }

    private String[] splitByNumber(String s, int chunkSize){
        int chunkCount = (s.length() / chunkSize) + (s.length() % chunkSize == 0 ? 0 : 1);
        String[] returnVal = new String[chunkCount];
        for(int i=0;i<chunkCount;i++){
            returnVal[i] = s.substring(i*chunkSize, Math.min((i+1)*chunkSize-1, s.length()));
        }
        return returnVal;
    }
}
