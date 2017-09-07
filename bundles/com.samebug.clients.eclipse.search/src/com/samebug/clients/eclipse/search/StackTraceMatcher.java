package com.samebug.clients.eclipse.search;

import java.util.List;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class StackTraceMatcher extends MatcherStateMachine implements LogScanner {
	
    public static final int FINISH_STACKTRACE_TIMEOUT = 1000;
    private final StackTraceListener listener;
    private Timer timer;
    private final StringBuilder lineBuffer;


    public StackTraceMatcher(StackTraceListener listener) {
        super();
        this.listener = listener;
        this.timer = new Timer(FINISH_STACKTRACE_TIMEOUT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StackTraceMatcher.this.end();
            }
        });
        this.timer.setRepeats(false);
        this.lineBuffer = new StringBuilder();
    }
    
    @Override
    public void append(String text) {
        timer.stop();
        String[] lines = text.split("\\r\\n|\\r|\\n", -1);
        if (lines.length == 1) {
            lineBuffer.append(lines[0]);
        } else {
            lineBuffer.append(lines[0]);
            processLine(lineBuffer.toString());
            lineBuffer.setLength(0);
            for (int i = 1; i < lines.length - 1; ++i) {
                processLine(lines[i]);
            }
            lineBuffer.append(lines[lines.length - 1]);
        }

        timer.restart();
    }

    @Override
    public void end() {
        processLine(lineBuffer.toString());
        lineBuffer.setLength(0);
        stop();
    }
    
    public String printBuffer() {
    		return lineBuffer.toString();
    }

    @Override
    protected void stackTraceFound(final List<String> stackTraceLines) {
        StringBuilder b = new StringBuilder();
        boolean first = true;
        for (String line : stackTraceLines) {
            if (!first) b.append("\n");
            else first = false;
            b.append(line);
        }
        listener.stacktraceFound(b.toString());
    }

    private void processLine(String line) {
    		 step(line);
    }

}
