package com.levko.roma.levkohomework9.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.levko.roma.levkohomework9.Interfaces.LoginEventHandler;
import com.levko.roma.levkohomework9.R;
import com.levko.roma.levkohomework9.Account;

/**
 * Created by roma on 14.03.16.
 */
public class LoginFragment extends Fragment {

    private TextView mTextWelcome;
    private EditText mTextLogin;
    private EditText mTextPassword;
    private TextView mBtnRegister;
    private Button mBtnLogin;

    private LoginEventHandler mLoginEventHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_card, container, false);

        mTextWelcome = (TextView) view.findViewById(R.id.text_hello_LC);
        mTextLogin = (EditText) view.findViewById(R.id.text_login_LC);
        mTextPassword = (EditText) view.findViewById(R.id.text_password_LC);
        mBtnRegister = (TextView) view.findViewById(R.id.btn_register_LC);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login_LC);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginEventHandler.onBtnRegistered();
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginEventHandler.onBtnLogined(
                        mTextLogin.getText().toString(),
                        mTextPassword.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getDataFragment().getCurrent() != null) {
            Account account = getDataFragment().getCurrent();

            mTextLogin.setText(account.getLogin());
            mTextPassword.setText(account.getPassword());

            String welcome = getText(R.string.hello).toString();
            welcome += account.getGender().equals("male") ? " Mr. " : " Mrs. ";
            welcome += account.getFirstName() + " " + account.getLastName();
            mTextWelcome.setText(welcome);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLoginEventHandler = (LoginEventHandler) context;
    }

    public RetainInstanceFragment getDataFragment() {
        return (RetainInstanceFragment) getFragmentManager().findFragmentByTag("headless");
    }

}