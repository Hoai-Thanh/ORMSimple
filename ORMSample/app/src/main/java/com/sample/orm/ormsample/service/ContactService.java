package com.sample.orm.ormsample.service;

import android.content.Context;

import com.sample.orm.ormsample.dao.ContactDao;
import com.sample.orm.ormsample.model.Contact;

import java.util.List;

/**
 * Created by Thanh Nguyen on 8/4/2015.
 */
public class ContactService extends BaseService {
    private ContactDao contactDao;

    public ContactService(Context context) {
        contactDao = new ContactDao(context);
    }

    public void saveContacts(List<Contact> contacts) {
        contactDao.deleteAll();
        for (Contact contact : contacts) {
            contactDao.insert(contact);
        }
    }

    public List<Contact> getContacts() {
        return contactDao.getAll();
    }
}
