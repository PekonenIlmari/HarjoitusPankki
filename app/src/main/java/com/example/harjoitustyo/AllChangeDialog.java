package com.example.harjoitustyo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AllChangeDialog extends AppCompatDialogFragment {
    private EditText newPhone;

    private EditText newPassword, newPasswordCheck;

    private EditText newAddress;

    private TextView verificationCode;
    private EditText enterVerificationCode;

    private EditText newUsername;

    private TextView confirmationText;

    private EditText addAmount;

    private EditText paylimit;

    private EditText takeLimit;

    private EditText takeAmount;
    private Button regionButton;

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
            View view = inflater.inflate(R.layout.layout_oneline_change_dialog, null);

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
            newPhone = view.findViewById(R.id.newLine);
            newPhone.setHint("Syötä uusi puhelinnumero");
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
            View view = inflater.inflate(R.layout.layout_oneline_change_dialog, null);

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
            newAddress = view.findViewById(R.id.newLine);
            newAddress.setHint("Syötä uusi osoite");
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
        } else if (type == 5) {
            View view = inflater.inflate(R.layout.layout_oneline_change_dialog, null);

            builder.setView(view)
                    .setTitle("Vaihda käyttäjänimi")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Vaihda", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (newUsername.getText().toString().length() > 3) {
                                String username = newUsername.getText().toString();
                                listener.changedUsername(username);
                            } else {
                                listener.changedUsername("ERROR");
                            }
                        }
                    });
            newUsername = view.findViewById(R.id.newLine);
            newUsername.setHint("Syötä uusi käyttäjätunnus");
        } else if (type == 6) {
            View view = inflater.inflate(R.layout.layout_confirmation_dialog, null);

            builder.setView(view)
                    .setTitle("Tilin poisto")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Jatka", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.confirmedCode(1);
                        }
                    });
            confirmationText = view.findViewById(R.id.confirmationTextLine);
            confirmationText.setText("Oletko varma että haluat poistaa kyseisen tilin? Menetät tilillä olevat rahat jos et siirrä niitä " +
                    "ensin toiselle tilille.");
        } else if (type == 7) {
            View view = inflater.inflate(R.layout.layout_oneline_change_dialog, null);

            builder.setView(view)
                    .setTitle("Lisää rahaa tilille")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Lisää", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String tempAmount = addAmount.getText().toString();
                            float a = Float.parseFloat(tempAmount);
                            if (a > 0) {
                                listener.addedAmount(a);
                            } else {
                                listener.addedAmount(-1);
                            }
                        }
                    });
            addAmount = view.findViewById(R.id.newLine);
            addAmount.setHint("Syötä tilille lisättävä euromäärä");
        } else if (type == 8) {
            View view = inflater.inflate(R.layout.layout_oneline_change_dialog, null);

            builder.setView(view)
                    .setTitle("Muuta kortin maksurajaa")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Muuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String tempLimit = paylimit.getText().toString();
                            int a = Integer.parseInt(tempLimit);
                            if (a > 0) {
                                listener.changedPayLimit(a);
                            } else {
                                listener.changedPayLimit(-1);
                            }
                        }
                    });
            paylimit = view.findViewById(R.id.newLine);
            paylimit.setHint("Syötä Kortin uusi maksuraja kokonaisissa euroissa");
        } else if (type == 9) {
            View view = inflater.inflate(R.layout.layout_confirmation_dialog, null);

            builder.setView(view)
                    .setTitle("Kortin poisto")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Jatka", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listener.confirmedCode(1);
                        }
                    });
            confirmationText = view.findViewById(R.id.confirmationTextLine);
            confirmationText.setText("Oletko varma että haluat poistaa kyseisen kortin?");
        } else if (type == 10) {
            View view = inflater.inflate(R.layout.layout_oneline_change_dialog, null);

            builder.setView(view)
                    .setTitle("Muuta kortin nostorajaa")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Muuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String tempLimit = takeLimit.getText().toString();
                            int a = Integer.parseInt(tempLimit);
                            if (a > 0) {
                                listener.changedTakeLimit(a);
                            } else {
                                listener.changedTakeLimit(-1);
                            }
                        }
                    });
            takeLimit = view.findViewById(R.id.newLine);
            takeLimit.setHint("Syötä Kortin uusi nostoraja kokonaisissa euroissa");
        } else if (type == 11) {
            View view = inflater.inflate(R.layout.layout_take_money_dialog, null);

            builder.setView(view)
                    .setTitle("Nosta rahaa tililtä")
                    .setNegativeButton("Peruuta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                        }
                    })
                    .setPositiveButton("Nosta", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int tempRegion = 0;
                            if (regionButton.getText().equals("Kotimaa")) {
                                tempRegion = 1;
                            } else if (regionButton.getText().equals("Ulkomaa")) {
                                tempRegion = 2;
                            }
                            String tempAmount = takeAmount.getText().toString();
                            float a = Float.parseFloat(tempAmount);
                            if (a > 0) {
                                listener.takenAmount(a, tempRegion);
                            } else {
                                listener.takenAmount(-1, tempRegion);
                            }
                        }
                    });
            takeAmount = view.findViewById(R.id.newLine);
            regionButton = view.findViewById(R.id.regionButtonDialog);
            takeAmount.setHint("Syötä tililta nostettava euromäärä");

            regionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (regionButton.getText().equals("Kotimaa")) {
                        regionButton.setText("Ulkomaa");
                    } else if (regionButton.getText().equals("Ulkomaa")) {
                        regionButton.setText("Kotimaa");
                    }
                }
            });
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

        void changedUsername(String username);

        void addedAmount(float amount);

        void changedPayLimit(int paylimit);

        void changedTakeLimit(int takelimit);

        void takenAmount(float amount, int region);
    }
}
