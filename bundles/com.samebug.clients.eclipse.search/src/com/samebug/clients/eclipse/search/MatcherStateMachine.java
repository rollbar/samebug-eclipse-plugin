package com.samebug.clients.eclipse.search;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.EvictingQueue;

abstract class MatcherStateMachine {

	public static final int MAX_MESSAGE_LINES = 10;

	protected abstract void stackTraceFound(final List<String> stackTraceLines);

	private State state;
	private final ArrayList<String> lines;
	private final EvictingQueue<String> messageBuffer;

	MatcherStateMachine() {
		this.state = State.WaitingForExceptionStart;
		this.lines = new ArrayList<String>();
		this.messageBuffer = EvictingQueue.create(MAX_MESSAGE_LINES);
	}

	void step(String line) {
		LineType lineType = LineType.match(line);
		switch (state) {
		case WaitingForExceptionStart:
			switch (lineType) {
			case MessageType:
				messageBuffer.add(line);
				break;
			case MoreType:
			case StackFrameType:
				lines.addAll(messageBuffer);
				messageBuffer.clear();
				lines.add(line);
				state = State.StackTraceStarted;
				break;
			case CausedByType:
				lines.add(line);
				state = State.CausedBy;
				break;
			default:
				throw new IllegalStateException("All states should be covered explicitly");
			}
		break;
		case StackTraceStarted:
			switch(lineType) {
			case MessageType:
				stackTraceFound(lines);
				lines.clear();
				messageBuffer.clear();
				state = State.WaitingForExceptionStart;
				messageBuffer.add(line);
				break;
			case MoreType:
			case StackFrameType:
				lines.add(line);
				break;
			case CausedByType:
				lines.add(line);
				state = State.CausedBy;
				break;
			default:
				throw new IllegalStateException("All states should be covered explicitly");
			}
		break;
		case CausedBy:
			switch(lineType) {
			case MessageType:
				if(messageBuffer.remainingCapacity()==0) {
					lines.clear();
					state=State.WaitingForExceptionStart;
				}else {
					messageBuffer.add(line);
				}
				break;
			case MoreType:
			case StackFrameType:
				lines.addAll(messageBuffer);
				messageBuffer.clear();
				lines.add(line);
				state = State.StackTraceStarted;
				break;
			case CausedByType:
				lines.addAll(messageBuffer);
				messageBuffer.clear();
				lines.add(line);
				break;
			default:
				throw new IllegalStateException("All states should be covered explicitly");
			}
			break;
		default:
			throw new IllegalStateException("All states should be covered explicitly");
		}
	}
	
	void stop() {
		switch (state) {
			case StackTraceStarted:
				stackTraceFound(lines);
			default:
		}
		messageBuffer.clear();
		lines.clear();
		state=State.WaitingForExceptionStart;
	}
}
