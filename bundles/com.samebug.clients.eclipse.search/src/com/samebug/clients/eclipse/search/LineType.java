package com.samebug.clients.eclipse.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LineType {
	StackFrameType(Regexes.PossiblyFrameRegex),
	CausedByType(Regexes.CausedByRegex),
	MoreType(Regexes.CommonFramesRegex),
	MessageType(Pattern.compile(".*"));
	
	private final Pattern pattern;
	
	LineType(Pattern regex){
		this.pattern=regex;
	}
	
	public static LineType match(String line) {
		for (LineType lineType : values()) {
			Matcher matcher = lineType.pattern.matcher(line);
			if(matcher.find())
				return lineType;
		}
		throw new IllegalStateException("Message line type should match any input!");
	}
}

final class Regexes{
    static final Pattern SpaceRegex = Pattern.compile("[ \\t\\x0B\\xA0]");
    static final Pattern IdentifierRegex = Pattern.compile("(?:\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)");
    static final Pattern ExceptionClassNameRegex = Pattern.compile("(?:[A-Z]\\p{javaJavaIdentifierPart}*)");
    static final Pattern ExceptionTypeRegex = Pattern.compile(String.format("((?:%s\\.)+%s)", IdentifierRegex, ExceptionClassNameRegex));
    static final Pattern CausedByRegex = Pattern.compile(String.format("(Caused [bB]y:)\\s+%s", ExceptionTypeRegex));
    static final Pattern CommonFramesRegex = Pattern.compile("\\.\\.\\.\\s+(\\d+)\\s+(?:more|common frames omitted)");
    static final Pattern PossiblyCallRegex = Pattern.compile("(?:[\\p{javaJavaIdentifierStart}<][\\p{javaJavaIdentifierPart}>]*)");
    static final Pattern PossiblyLocationRegex = Pattern.compile("\\(([^\\)]*)\\)");
    static final Pattern PossiblyJarRegex = Pattern.compile(String.format("(?:%s~|%s|~|)\\[([^\\]]*)\\]", SpaceRegex, SpaceRegex));
    static final Pattern PossiblyFrameRegex = Pattern.compile(String.format("at%s+((?:%s\\.)+(?:%s)?)%s(?:%s)?", SpaceRegex, IdentifierRegex, PossiblyCallRegex, PossiblyLocationRegex, PossiblyJarRegex));

    private Regexes() {}
}