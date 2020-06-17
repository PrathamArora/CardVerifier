package com.sew.cardverifier.activities;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class BaseActivity extends AppCompatActivity {

//    public static String VISA_REGEX = "4[0-9]{12}(?:[0-9]{3})";
//    public static String MASTERCARD_REGEX = "5[1-5][0-9]{14}";
//    public static String AMEX_REGEX = "3[47][0-9]{13}";
//    public static String DISCOVER_REGEX = "6(?:011|5[0-9]{2})[0-9]{12}";

    public static String VISA_HELPER_TEXT = "Visa card";
    public static String AMEX_HELPER_TEXT = "American Express card";
    public static String MASTERCARD_HELPER_TEXT = "MasterCard";
    public static String DISCOVER_HELPER_TEXT = "Discover card";

    public static String VISA = "visa";
    public static String MASTERCARD = "mastercard";
    public static String AMEX = "amex";
    public static String DISCOVER = "discover";
    public static String FILENAME = "cc_rules.json";

    public static String DIALOGBOX_WRONG_CARD_HEADING = "Error";
    public static String DIALOGBOX_WRONG_CARD_MESSAGE = "Invalid Card Number";

    HashMap<String, JSONArray> prefixMapping = null;

    public JSONArray loadPrefixesFromAsset(String key) {

        if (prefixMapping == null) {
            prefixMapping = new HashMap<>();
            String json;
            try {
                InputStream is = getAssets().open(FILENAME);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
                JSONObject jsonObject = new JSONObject(json);

                prefixMapping.put(VISA, jsonObject.getJSONObject(VISA).getJSONArray("prefixes"));
                prefixMapping.put(MASTERCARD, jsonObject.getJSONObject(MASTERCARD).getJSONArray("prefixes"));
                prefixMapping.put(AMEX, jsonObject.getJSONObject(AMEX).getJSONArray("prefixes"));
                prefixMapping.put(DISCOVER, jsonObject.getJSONObject(DISCOVER).getJSONArray("prefixes"));

                return prefixMapping.get(key);
            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
                prefixMapping = null;
                return null;
            }
        } else {
            return prefixMapping.get(key);
        }

    }

    public boolean matchForVisa(String inputCardNumbers) {
        try {
            JSONArray prefixes = loadPrefixesFromAsset(VISA);
            for (int i = 0; i < prefixes.length(); i++) {
                if (inputCardNumbers.startsWith(prefixes.getString(i))) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean matchForAmex(String inputCardNumbers) {
        try {
            JSONArray prefixes = loadPrefixesFromAsset(AMEX);
            for (int i = 0; i < prefixes.length(); i++) {
                if (inputCardNumbers.startsWith(prefixes.getString(i))) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean matchForMasterCard(String inputCardNumbers) {
        try {
            JSONArray prefixes = loadPrefixesFromAsset(MASTERCARD);
            for (int i = 0; i < prefixes.length(); i++) {
                if (inputCardNumbers.startsWith(prefixes.getString(i))) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean matchForDiscover(String inputCardNumbers) {
        try {
            JSONArray prefixes = loadPrefixesFromAsset(DISCOVER);
            for (int i = 0; i < prefixes.length(); i++) {
                if (inputCardNumbers.startsWith(prefixes.getString(i))) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
