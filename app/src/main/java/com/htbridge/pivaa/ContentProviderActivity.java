package com.htbridge.pivaa;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.htbridge.pivaa.handlers.MenuHandler;

public class ContentProviderActivity extends AppCompatActivity {
    private WebView myWebView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(ContentProviderActivity.this, item.getItemId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button mContentProviderButtonView = (Button) findViewById(R.id.button_content_provider);
        mContentProviderButtonView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText mQueryContentProviderView = (EditText) findViewById(R.id.url_content_provider);
                String query = mQueryContentProviderView.getText().toString();;

                String url = "content://com.htbridge.pivaa/" + query;
                Uri uri = Uri.parse(url);

                try {
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                    StringBuilder sb = new StringBuilder();
                    if (cursor != null) cursor.moveToFirst();

                    while (!cursor.isAfterLast()) {
                        String e = DatabaseUtils.dumpCurrentRowToString(cursor);

                        Log.d("htbridge", e);
                        sb.append(e).append("\n");

                        cursor.moveToNext();
                    }

                    //Log.i("htbridge", sb.toString());

                    TextView mOutputContentProviderView = (TextView) findViewById(R.id.output_content_provider);
                    mOutputContentProviderView.setText(sb.toString(), TextView.BufferType.EDITABLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });
    }

}
