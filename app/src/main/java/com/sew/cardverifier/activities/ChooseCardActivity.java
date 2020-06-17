package com.sew.cardverifier.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sew.cardverifier.R;
import com.sew.cardverifier.model.Card;

import java.util.ArrayList;


public class ChooseCardActivity extends BaseActivity {

    private TextInputEditText tieCardNumberValue;
    private TextInputLayout tilCardNumberLabel;

    private ConstraintLayout clItemContainerVisa, clItemContainerMasterCard, clItemContainerAmericanExpress, clItemContainerDiscover;
    private AppCompatCheckBox cbCardOptionVisa, cbCardOptionMasterCard, cbCardOptionAmericanExpress, cbCardOptionDiscover;
    private TextView tvCardCompanyVisa, tvCardCompanyMasterCard, tvCardCompanyAmericanExpress, tvCardCompanyDiscover;

    private AlertDialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_card);

        clItemContainerVisa = findViewById(R.id.clItemContainerVisa);
        clItemContainerMasterCard = findViewById(R.id.clItemContainerMasterCard);
        clItemContainerAmericanExpress = findViewById(R.id.clItemContainerAmericanExpress);
        clItemContainerDiscover = findViewById(R.id.clItemContainerDiscover);

        cbCardOptionVisa = findViewById(R.id.cbCardOptionVisa);
        cbCardOptionMasterCard = findViewById(R.id.cbCardOptionMasterCard);
        cbCardOptionAmericanExpress = findViewById(R.id.cbCardOptionAmericanExpress);
        cbCardOptionDiscover = findViewById(R.id.cbCardOptionDiscover);

        tvCardCompanyVisa = findViewById(R.id.tvCardCompanyVisa);
        tvCardCompanyMasterCard = findViewById(R.id.tvCardCompanyMasterCard);
        tvCardCompanyAmericanExpress = findViewById(R.id.tvCardCompanyAmericanExpress);
        tvCardCompanyDiscover = findViewById(R.id.tvCardCompanyDiscover);

        clItemContainerVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                updateUI(v);
//                updateUICard();
            }
        });

        clItemContainerMasterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                updateUI(v);
//                updateUICard();
            }
        });

        clItemContainerAmericanExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                updateUI(v);
//                updateUICard();
            }
        });

        clItemContainerDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                updateUI(v);
//                updateUICard();
            }
        });

        cbCardOptionVisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cbCardOptionVisa.setChecked(cbCardOptionVisa.isChecked());
                updateUI(v);
//                updateUICheckBox();
            }
        });

        cbCardOptionMasterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbCardOptionMasterCard.setChecked(cbCardOptionMasterCard.isChecked());
                updateUI(v);
//                updateUICheckBox();
            }
        });

        cbCardOptionAmericanExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbCardOptionAmericanExpress.setChecked(cbCardOptionAmericanExpress.isChecked());
                updateUI(v);
//                updateUICheckBox();
            }
        });

        cbCardOptionDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbCardOptionDiscover.setChecked(cbCardOptionDiscover.isChecked());
                updateUI(v);
//                updateUICheckBox();
            }
        });

        tilCardNumberLabel = findViewById(R.id.tilCardNumberLabel);
        tieCardNumberValue = findViewById(R.id.tieCardNumberValue);
        tieCardNumberValue.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }

                if (s.length() >= 4) {
                    checkForPossibleCards(s.toString().substring(0, 4));
                } else {
                    tilCardNumberLabel.setHelperText("");
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        findViewById(R.id.imgBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        Card card = new Card(112, "VISA");
//        ArrayList<Card> cards = new ArrayList<>();
//        ArrayList<Card> cards_1 = new ArrayList<>();
//        cards.add(card);
//        cards_1.add(card);
//        cards.get(0).setCardCompany("MasterCard");
//        doSomething(cards);
        /*
        print card company name from card.
        print card company name from first index of cards arraylist.
        print card company name from first index of card_1 arraylist.
         */
    }

//    private void doSomething(ArrayList<Card> cards) {
//        cards.add(new Card(0, "MASTER"));
//        cards = new ArrayList<>();
//        cards.add(new Card(90, "Discover"));
//
//    }

    private void checkForPossibleCards(String inputCardNumbers) {
        if (clItemContainerVisa.isSelected() && matchForVisa(inputCardNumbers)) {
            // set visa
            tilCardNumberLabel.setHelperText(VISA_HELPER_TEXT);
            return;
        } else if (clItemContainerAmericanExpress.isSelected() && matchForAmex(inputCardNumbers)) {
            // set amex
            tilCardNumberLabel.setHelperText(AMEX_HELPER_TEXT);
            return;
        } else if (clItemContainerMasterCard.isSelected() && matchForMasterCard(inputCardNumbers)) {
            // set master card
            tilCardNumberLabel.setHelperText(MASTERCARD_HELPER_TEXT);
            return;
        } else if (clItemContainerDiscover.isSelected() && matchForDiscover(inputCardNumbers)) {
            // set discover
            tilCardNumberLabel.setHelperText(DISCOVER_HELPER_TEXT);
            return;
        }

        showAlertDialogbox(DIALOGBOX_WRONG_CARD_HEADING, DIALOGBOX_WRONG_CARD_MESSAGE);
        // show dialog box
    }

    private void showAlertDialogbox(String title, String message) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(ChooseCardActivity.this)
                    .setCancelable(false)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tieCardNumberValue.setText("");
                            alertDialog = null;
                        }
                    })
                    .show();
        }
    }

    public void updateUI(View v){
        if(v instanceof AppCompatCheckBox){
            updateUICheckBox();
        }else if(v instanceof ConstraintLayout){
            updateUICard();
        }
    }

    private void updateUICheckBox() {
        if (cbCardOptionVisa.isChecked()) {
            cbCardOptionVisa.setChecked(true);
            cbCardOptionVisa.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            clItemContainerVisa.setSelected(true);
            tvCardCompanyVisa.setTextColor(getColor(R.color.white));
        } else {
            cbCardOptionVisa.setChecked(false);
            cbCardOptionVisa.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            clItemContainerVisa.setSelected(false);
            tvCardCompanyVisa.setTextColor(getColor(R.color.black));
        }

        if (cbCardOptionMasterCard.isChecked()) {
            cbCardOptionMasterCard.setChecked(true);
            cbCardOptionMasterCard.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            clItemContainerMasterCard.setSelected(true);
            tvCardCompanyMasterCard.setTextColor(getColor(R.color.white));
        } else {
            cbCardOptionMasterCard.setChecked(false);
            cbCardOptionMasterCard.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            clItemContainerMasterCard.setSelected(false);
            tvCardCompanyMasterCard.setTextColor(getColor(R.color.black));
        }

        if (cbCardOptionAmericanExpress.isChecked()) {
            cbCardOptionAmericanExpress.setChecked(true);
            cbCardOptionAmericanExpress.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            clItemContainerAmericanExpress.setSelected(true);
            tvCardCompanyAmericanExpress.setTextColor(getColor(R.color.white));
        } else {
            cbCardOptionAmericanExpress.setChecked(false);
            cbCardOptionAmericanExpress.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            clItemContainerAmericanExpress.setSelected(false);
            tvCardCompanyAmericanExpress.setTextColor(getColor(R.color.black));
        }

        if (cbCardOptionDiscover.isChecked()) {
            cbCardOptionDiscover.setChecked(true);
            cbCardOptionDiscover.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            clItemContainerDiscover.setSelected(true);
            tvCardCompanyDiscover.setTextColor(getColor(R.color.white));
        } else {
            cbCardOptionDiscover.setChecked(false);
            cbCardOptionDiscover.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            clItemContainerDiscover.setSelected(false);
            tvCardCompanyDiscover.setTextColor(getColor(R.color.black));
        }

    }

    private void updateUICard() {
        if (clItemContainerVisa.isSelected()) {
            cbCardOptionVisa.setChecked(true);
            cbCardOptionVisa.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            clItemContainerVisa.setSelected(true);
            tvCardCompanyVisa.setTextColor(getColor(R.color.white));
        } else {
            cbCardOptionVisa.setChecked(false);
            cbCardOptionVisa.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            clItemContainerVisa.setSelected(false);
            tvCardCompanyVisa.setTextColor(getColor(R.color.black));
        }

        if (clItemContainerMasterCard.isSelected()) {
            cbCardOptionMasterCard.setChecked(true);
            cbCardOptionMasterCard.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            clItemContainerMasterCard.setSelected(true);
            tvCardCompanyMasterCard.setTextColor(getColor(R.color.white));
        } else {
            cbCardOptionMasterCard.setChecked(false);
            cbCardOptionMasterCard.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            clItemContainerMasterCard.setSelected(false);
            tvCardCompanyMasterCard.setTextColor(getColor(R.color.black));
        }

        if (clItemContainerAmericanExpress.isSelected()) {
            cbCardOptionAmericanExpress.setChecked(true);
            cbCardOptionAmericanExpress.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            clItemContainerAmericanExpress.setSelected(true);
            tvCardCompanyAmericanExpress.setTextColor(getColor(R.color.white));
        } else {
            cbCardOptionAmericanExpress.setChecked(false);
            cbCardOptionAmericanExpress.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            clItemContainerAmericanExpress.setSelected(false);
            tvCardCompanyAmericanExpress.setTextColor(getColor(R.color.black));
        }

        if (clItemContainerDiscover.isSelected()) {
            cbCardOptionDiscover.setChecked(true);
            cbCardOptionDiscover.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white)));
            clItemContainerDiscover.setSelected(true);
            tvCardCompanyDiscover.setTextColor(getColor(R.color.white));
        } else {
            cbCardOptionDiscover.setChecked(false);
            cbCardOptionDiscover.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
            clItemContainerDiscover.setSelected(false);
            tvCardCompanyDiscover.setTextColor(getColor(R.color.black));
        }
    }
}
