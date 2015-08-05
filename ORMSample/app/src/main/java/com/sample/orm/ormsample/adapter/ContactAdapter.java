package com.sample.orm.ormsample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.orm.ormsample.R;
import com.sample.orm.ormsample.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Thanh Nguyen on 8/4/2015.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.CustomerViewHolder> {
    private List<Contact> contacts;
    private Context context;

    public ContactAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomerViewHolder holder = (CustomerViewHolder) view.getTag();
            int position = holder.getAdapterPosition();

            Contact item = contacts.get(position);
            Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orm_item_contact_activity, null);
        CustomerViewHolder viewHolder = new CustomerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder customerViewHolder, int i) {
        Contact contact = contacts.get(i);

        Picasso.with(context).load(contact.getImage())
                .error(R.drawable.ic_launcher)
                .placeholder(R.drawable.ic_launcher)
                .into(customerViewHolder.image);

        customerViewHolder.name.setText(contact.getName());
        customerViewHolder.rltItem.setTag(customerViewHolder);

        customerViewHolder.rltItem.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return (null != contacts ? contacts.size() : 0);
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView name;
        protected RelativeLayout rltItem;

        public CustomerViewHolder(View view) {
            super(view);
            rltItem = (RelativeLayout) view.findViewById(R.id.orm_item_contactActivity_rltItem);
            image = (ImageView) view.findViewById(R.id.orm_item_contactActivity_image);
            name = (TextView) view.findViewById(R.id.orm_item_contactActivity_name);
        }
    }
}
