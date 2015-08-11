package com.sample.orm.ormsample.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ContactsFragment extends BaseFragment {

    private List<Contact> contacts;
    private RecyclerView rcrView;
    private ContactAdapter adapter;
    private ProgressBar pgbLoading;

    private OkHttpClient client;
    private ContactService contactService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orm_list_contact_fragment, container, false);
        initView(view);
        fillData();
        return view;
    }

    private void initView(View v) {
        rcrView = (RecyclerView) v.findViewById(R.id.orm_contactActivity_rcrContact);
        rcrView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pgbLoading = (ProgressBar) v.findViewById(R.id.orm_contactActivity_pgbContact);
        pgbLoading.setVisibility(View.GONE);
        contacts = new ArrayList<Contact>();
    }

    private void fillData() {
        contactService = new ContactService(getActivity());
        if (contactService.getContacts() != null && contactService.getContacts().size() != 0) {
            adapter = new ContactAdapter(contactService.getContacts(), getActivity());
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
            adapter = new ContactAdapter(contacts, getActivity());
            rcrView.setAdapter(adapter);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    contactService = new ContactService(getActivity());
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
