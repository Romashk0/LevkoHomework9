package com.levko.roma.levkohomework9.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.levko.roma.levkohomework9.Account;
import com.levko.roma.levkohomework9.Constants;

import java.util.HashMap;

/**
 * Created by roma on 14.03.16.
 */
public class SaveSpecimenFragment extends Fragment {

    private HashMap<String, Account> dataBase;
    private Account current;

    public Account getCurrent() {
        return current;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else {
            dataBase = new HashMap<>();
        }
        setRetainInstance(true);
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.DATA_KEY, dataBase);
    }

    @SuppressWarnings("unchecked")
    private void restoreState(Bundle savedInstanceState) {
        try {
            dataBase = (HashMap<String, Account>) savedInstanceState.getSerializable(Constants.DATA_KEY);
        } catch (ClassCastException e) {
            Log.e("Homework", e.toString());
        }
    }

    public void addUser(Account account) {
        dataBase.put(account.getLogin(), account);
        current = account;
    }

    public boolean isUserRegistered(Account account) {
        return dataBase.containsKey(account.getLogin());
    }

    public boolean isUserRegistered(String login, String password) {
        return dataBase.containsKey(login) && dataBase.get(login).getPassword().equals(password);
    }
}