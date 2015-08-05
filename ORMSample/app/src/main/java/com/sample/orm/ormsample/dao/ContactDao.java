package com.sample.orm.ormsample.dao;

import android.content.Context;

import com.sample.orm.ormsample.model.Contact;

/**
 * Created by Thanh Nguyen on 8/4/2015.
 */
public class ContactDao extends DataDao<Contact, Long> {
    public ContactDao(Context context) {
        super(context, Contact.class);
    }
}
