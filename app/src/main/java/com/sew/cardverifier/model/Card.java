package com.sew.cardverifier.model;

public class Card {
    private int cardImageID;
    private String cardCompany;

    public Card(int cardImageID, String cardCompany) {
        this.cardImageID = cardImageID;
        this.cardCompany = cardCompany;
    }

    public int getCardImageID() {
        return cardImageID;
    }

    public void setCardImageID(int cardImageID) {
        this.cardImageID = cardImageID;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
    }
}
