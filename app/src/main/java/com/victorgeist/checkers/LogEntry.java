package com.victorgeist.checkers;

public class LogEntry {
    // temporary design
    private String mEntryText;

    public LogEntry() {
        mEntryText = "default";
    }

    public String getEntryText () {
        return mEntryText;
    }

    public void setEntryText(String text) {
        mEntryText = text;
    }
}