package com.ismcenter.evataxiapp.Modelos.Request;

public class RequestInstaPago {

    public String KeyId;
    public String PublicKeyId;
    public String StatusId;
    public String Amount;
    public String Description;
    public String CardHolder;
    public String CardHolderID;
    public String CardNumber;
    public String CVC;
    public String ExpirationDate;
    public String IP;
    public String OrderNumber;
    public String Address;
    public String City;
    public String ZipCode;
    public String State;
    public String person_id;


    public RequestInstaPago(String keyId, String publicKeyId, String statusId, String amount, String description, String cardHolder, String cardHolderID, String cardNumber, String CVC, String expirationDate, String IP, String orderNumber, String address, String city, String zipCode, String state, String person_id, String personId) {
        KeyId = keyId;
        PublicKeyId = publicKeyId;
        StatusId = statusId;
        Amount = amount;
        Description = description;
        CardHolder = cardHolder;
        CardHolderID = cardHolderID;
        CardNumber = cardNumber;
        this.CVC = CVC;
        ExpirationDate = expirationDate;
        this.IP = IP;
        OrderNumber = orderNumber;
        Address = address;
        City = city;
        ZipCode = zipCode;
        State = state;
        this.person_id = person_id;
    }

    public String getKeyId() {
        return KeyId;
    }

    public void setKeyId(String keyId) {
        KeyId = keyId;
    }

    public String getPublicKeyId() {
        return PublicKeyId;
    }

    public void setPublicKeyId(String publicKeyId) {
        PublicKeyId = publicKeyId;
    }

    public String getStatusId() {
        return StatusId;
    }

    public void setStatusId(String statusId) {
        StatusId = statusId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCardHolder() {
        return CardHolder;
    }

    public void setCardHolder(String cardHolder) {
        CardHolder = cardHolder;
    }

    public String getCardHolderID() {
        return CardHolderID;
    }

    public void setCardHolderID(String cardHolderID) {
        CardHolderID = cardHolderID;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getCVC() {
        return CVC;
    }

    public void setCVC(String CVC) {
        this.CVC = CVC;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }
}
