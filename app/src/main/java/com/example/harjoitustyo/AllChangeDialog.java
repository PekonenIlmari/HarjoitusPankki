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

/*public class PhoneChangeDialog extends AppCompatDialogFragment {
    private EditText newPhone;
    private NewPhoneChangeDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_phone_change_dialog, null);

        builder.setView(view)
                .setTitle("Vaihda puhelinnumero")
                .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                })
                .setPositiveButton("Vaihda", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (newPhone.getText().toString().length() > 3) {
                            String phoneNum = newPhone.getText().toString();
                            listener.changedPhoneNumber(phoneNum);
                        } else {
                            listener.changedPhoneNumber("ERROR");
                        }
                    }
                });
        newPhone = view.findViewById(R.id.newPhone);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewPhoneChangeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement NewPhoneChangeDialogListener");
        }
    }

    public interface NewPhoneChangeDialogListener {
        void changedPhoneNumber(String phoneNum);
    }
}*/

public class AllChangeDialog extends AppCompatDialogFragment {
    private EditText newPhone;

    private EditText newPassword, newPasswordCheck;

    private EditText newAddress;

    private TextView verificationCode;
    private EditText enterVerificationCode;

    private NewAllChangeDialogListener listener;

    Bank bank = Bank.getInstance();
    PasswordChecker pc = PasswordChecker.getInstance();

    int type;

    public static AllChangeDialog newInstance(int num) {
        AllChangeDialog acd = new AllChangeDialog();

        Bundle args = new Bundle();
        args.putInt("num", num);
        acd.setArguments(args);

        return acd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        type = getArguments().getInt("num");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        if (type == 1) {
            View view = inflater.inflate(R.layout.layout_phone_change_dialog, null);

            builder.setView(view)
                    .setTitle("Vaihda puhelinnumero")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Vaihda", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (newPhone.getText().toString().length() > 3) {
                                String phoneNum = newPhone.getText().toString();
                                listener.changedPhoneNumber(phoneNum);
                            } else {
                                listener.changedPhoneNumber("ERROR");
                            }
                        }
                    });
            newPhone = view.findViewById(R.id.newPhone);
        } else if (type == 2) {
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
        } else if (type == 3) {
            View view = inflater.inflate(R.layout.layout_address_change_dialog, null);

            builder.setView(view)
                    .setTitle("Vaihda osoite")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Vaihda", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (newAddress.getText().toString().length() > 5) {
                                String address = newAddress.getText().toString();
                                listener.changedAddress(address);
                            } else {
                                listener.changedAddress("ERROR");
                            }
                        }
                    });
            newAddress = view.findViewById(R.id.newAddress);
        } else if (type == 4) {
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
        }
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewAllChangeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement NewAllChangeDialogListener");
        }
    }

    public interface NewAllChangeDialogListener {
        void changedPhoneNumber(String phoneNum);
        void changedPassword(String password);
        void changedAddress(String address);
        void confirmedCode(int code);
    }
}
