package com.example.harjoitustyo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class VerificationCodeDialog extends AppCompatDialogFragment {
    private TextView verificationCode;
    private EditText enterVerificationCode;
    private VerificationCodeDialogListener listener;
    Bank bank = Bank.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_verification_dialog, null);

        builder.setView(view)
                .setTitle("Varmistuskoodi")
                .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String code = enterVerificationCode.getText().toString();
                        if (code.equals(verificationCode.getText().toString())) {
                            listener.confirmedCode(1);
                        } else {
                            listener.confirmedCode(0);
                        }
                    }
                });
        verificationCode = view.findViewById(R.id.verificationCodeLine);
        enterVerificationCode = view.findViewById(R.id.enterVerificationCode);
        verificationCode.setText(bank.generateRandomLogInCode());

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (VerificationCodeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement VerificationCodeDialogListener");
        }
    }

    public interface VerificationCodeDialogListener {
        void confirmedCode(int code);
    }
}
