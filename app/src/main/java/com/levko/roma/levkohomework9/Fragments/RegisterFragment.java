package com.levko.roma.levkohomework9.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.levko.roma.levkohomework9.Account;
import com.levko.roma.levkohomework9.Interfaces.RegisterEventHandler;
import com.levko.roma.levkohomework9.R;

/**
 * Created by roma on 14.03.16.
 */
public class RegisterFragment extends Fragment {

    private EditText mTextLogin;
    private EditText mTextPassword;
    private EditText mTextFirstName;
    private EditText mTextLastName;
    private RadioGroup mRadioGender;
    private Button mBtnRegister;

    private RegisterEventHandler mRegisterEventHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_card, container, false);

        mTextLogin = (EditText) view.findViewById(R.id.text_login_RC);
        mTextPassword = (EditText) view.findViewById(R.id.text_password_RC);
        mTextFirstName = (EditText) view.findViewById(R.id.text_first_name_RC);
        mTextLastName = (EditText) view.findViewById(R.id.text_last_name_RC);
        mRadioGender = (RadioGroup) view.findViewById(R.id.radio_group_gender);
        mBtnRegister = (Button) view.findViewById(R.id.btn_register_RC);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmptyFields()) {
                    String gender = null;

                    switch (mRadioGender.getCheckedRadioButtonId()) {
                        case R.id.radio_male_RC:
                            gender = "male";
                            break;
                        case R.id.radio_female_RC:
                            gender = "female";
                            break;
                    }

                    Account account = new Account(
                            mTextLogin.getText().toString(),
                            mTextPassword.getText().toString(),
                            mTextFirstName.getText().toString(),
                            mTextLastName.getText().toString(),
                            gender);

                    mRegisterEventHandler.onBtnConfirmPressed(account);
                } else
                    Toast.makeText(getContext(), R.string.toast_fields_incomplete, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRegisterEventHandler = (RegisterEventHandler) context;
    }

    private boolean isEmptyFields() {
        return TextUtils.isEmpty(mTextLogin.getText())
                && TextUtils.isEmpty(mTextPassword.getText())
                && TextUtils.isEmpty(mTextFirstName.getText())
                && TextUtils.isEmpty(mTextLastName.getText());
    }

}
