package com.levko.roma.levkohomework9;

import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.levko.roma.levkohomework9.Fragments.LoginFragment;
import com.levko.roma.levkohomework9.Fragments.RegisterFragment;
import com.levko.roma.levkohomework9.Fragments.RegistrationDialogFragment;
import com.levko.roma.levkohomework9.Fragments.RetainInstanceFragment;
import com.levko.roma.levkohomework9.Interfaces.LoginEventHandler;
import com.levko.roma.levkohomework9.Interfaces.RegisterEventHandler;


public class MainActivity extends AppCompatActivity implements LoginEventHandler, RegisterEventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            commitHeadlessFragment();
            commitLoginFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void commitLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, loginFragment, "login")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void commitRegisterFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, registerFragment, "register")
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void commitHeadlessFragment() {
        RetainInstanceFragment dataFragment = new RetainInstanceFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(dataFragment, "headless")
                .commit();
    }

    @Override
    public void onBtnRegistered() {
        commitRegisterFragment();
    }

    @Override
    public void onBtnLogined(String login, String password) {
        if (!getDataFragment().isUserRegistered(login, password))
            new AlertDialog
                    .Builder(this)
                    .setTitle(R.string.warning_login_fail_title)
                    .setMessage(R.string.warning_login_fail_message)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        else {
            Account account = getDataFragment().getCurrent();

            String welcome = getText(R.string.login_success_message).toString();
            welcome += account.getGender().equals("male") ? " Mr. " : " Mrs. ";
            welcome += account.getFirstName() + " " + account.getLastName();

            new AlertDialog
                    .Builder(this)
                    .setTitle(R.string.login_success_title)
                    .setMessage(welcome)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    public RetainInstanceFragment getDataFragment() {
        return (RetainInstanceFragment) getSupportFragmentManager().findFragmentByTag("headless");
    }

    @Override
    public void onBtnConfirmPressed(Account account) {
        Bundle dialogArgs = new Bundle();
        RegistrationDialogFragment dialogFragment = new RegistrationDialogFragment();

        if (getDataFragment().isUserRegistered(account)) {

            dialogArgs.putString(Constants.MESSAGE_ID,
                    account.getFirstName() + " " + account.getLastName() + " \nalready exists");
            dialogFragment.setArguments(dialogArgs);
            dialogFragment.show(getFragmentManager(), "dialogFragment");

        } else {

            getDataFragment().addUser(account);

            dialogArgs.putString(Constants.MESSAGE_ID,
                    account.getFirstName() + " " + account.getLastName() + " \nregistered");
            dialogFragment.setArguments(dialogArgs);
            dialogFragment.show(getFragmentManager(), "dialogFragment");

            onBackPressed();

        }
    }

}
