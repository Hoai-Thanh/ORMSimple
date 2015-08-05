package com.sample.orm.ormsample.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.sample.orm.ormsample.R;
import com.sample.orm.ormsample.adapter.ContactAdapter;
import com.sample.orm.ormsample.model.Contact;
import com.sample.orm.ormsample.service.ContactService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ContactsActivity extends Activity {

    private List<Contact> contacts;
    private RecyclerView rcrView;
    private ContactAdapter adapter;
    private ProgressBar pgbLoading;

    private OkHttpClient client;
    private ContactService contactService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orm_list_contact_activity);
        initView();
        fillData();
    }

    private void initView() {
        rcrView = (RecyclerView) findViewById(R.id.orm_contactActivity_rcrContact);
        rcrView.setLayoutManager(new LinearLayoutManager(this));
        pgbLoading = (ProgressBar) findViewById(R.id.orm_contactActivity_pgbContact);
        pgbLoading.setVisibility(View.GONE);
        contacts = new ArrayList<Contact>();
    }

    private void fillData() {
        contactService = new ContactService(ContactsActivity.this);
        if (contactService.getContacts() != null && contactService.getContacts().size() != 0) {
            adapter = new ContactAdapter(contactService.getContacts(), ContactsActivity.this);
            rcrView.setAdapter(adapter);
        } else {
            GetContactTask getContactTask = new GetContactTask();
            getContactTask.execute();
        }
    }

    private class GetContactTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String result = null;
            try {
                result = run("http://dev.tapptic.com/test/json.php");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(result)) {
                try {
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String image = object.getString("image");
                        String name = object.getString("name");
                        Contact contact = new Contact();
                        contact.setImage(image);
                        contact.setName(name);
                        contacts.add(contact);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ContactAdapter(contacts, ContactsActivity.this);
            rcrView.setAdapter(adapter);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    contactService = new ContactService(ContactsActivity.this);
                    contactService.saveContacts(contacts);
                }
            }).start();
        }
    }

    private String run(String url) throws IOException {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
