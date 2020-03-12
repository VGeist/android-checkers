package com.victorgeist.checkers;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class GameLog {
    private List<LogEntry> mGameLog;

    public GameLog() {
        // create defaults for display testing
        mGameLog = new ArrayList<>();
        mGameLog.add(new LogEntry());
        mGameLog.add(new LogEntry());
        mGameLog.add(new LogEntry());
    }

    public List<LogEntry> getLog() {
        return mGameLog;
    }

    public void setLog(List<LogEntry> log) {
        mGameLog = log;
    }

    public void addEntry(LogEntry entry) {
        mGameLog.add(entry);
    }
}
