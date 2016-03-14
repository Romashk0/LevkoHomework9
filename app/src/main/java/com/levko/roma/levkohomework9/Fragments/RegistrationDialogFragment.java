package com.levko.roma.levkohomework9.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.levko.roma.levkohomework9.Constants;
import com.levko.roma.levkohomework9.R;

/**
 * Created by roma on 14.03.16.
 */
public class RegistrationDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle message = getArguments();

        if (message != null)
            return new AlertDialog
                    .Builder(getActivity())
                    .setTitle(R.string.registration_success)
                    .setMessage(message.getString(Constants.MESSAGE_ID))
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
        else return null;
    }
}
