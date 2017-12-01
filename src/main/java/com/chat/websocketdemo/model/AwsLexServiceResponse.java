package com.chat.websocketdemo.model;

import java.util.Map;

public class AwsLexServiceResponse {

    private String dialogState;
    private String intentName;
    private String message;
    private Map<String, String> sessionAttributes;
    private Map<String, String> slots;
    private String slotToElicit;

    public String getDialogState() {
        return dialogState;
    }

    public void setDialogState(String dialogState) {
        this.dialogState = dialogState;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(Map<String, String> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public Map<String, String> getSlots() {
        return slots;
    }

    public void setSlots(Map<String, String> slots) {
        this.slots = slots;
    }

    public String getSlotToElicit() {
        return slotToElicit;
    }

    public void setSlotToElicit(String slotToElicit) {
        this.slotToElicit = slotToElicit;
    }

    @Override
    public String toString() {
        return "AwsLexServiceResponse [dialogState=" + dialogState + ", intentName=" + intentName + ", message="
                + message + ", sessionAttributes=" + sessionAttributes + ", slots=" + slots + ", slotToElicit="
                + slotToElicit + "]";
    }

}
