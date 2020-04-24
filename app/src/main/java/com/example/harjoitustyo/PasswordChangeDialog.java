package com.example.harjoitustyo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PasswordChangeDialog extends AppCompatDialogFragment {
    private EditText newPassword, newPasswordCheck;
    private NewPasswordDialogListener listener;
    Bank bank = Bank.getInstance();
    PasswordChecker pc = PasswordChecker.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_password_change_dialog, null);

        builder.setView(view)
                .setTitle("Vaihda salasana")
                .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                })
                .setPositiveButton("Vaihda", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (pc.checkPassword(newPassword.getText().toString(), newPasswordCheck.getText().toString()) == 1) {
                            String password = newPassword.getText().toString();
                            listener.changedPassword(password);
                        } else {
                            listener.changedPassword("ERROR");
                        }
                    }
                });
        newPassword = view.findViewById(R.id.newPassword);
        newPasswordCheck = view.findViewById(R.id.newPasswordCheck);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewPasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement VerificationCodeDialogListener");
        }
    }

    public interface NewPasswordDialogListener {
        void changedPassword(String password);
    }
}
