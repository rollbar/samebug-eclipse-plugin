# Samebug plugin for Eclipse

_Some of the Samebug users showed interest in an Eclipse plugin, so we start experimenting with it_

## Goals

The goal of the plugin is to bring the features of Samebug directly to the IDE,
to give developers a better workflow for fixing crashes. Check the [plugin for IntelliJ IDEA](https://github.com/samebug/samebug-idea-plugin),
we would like something similar for Eclipse.

The core feature of the plugin is that it monitors the output of the application run from the IDE
so when a stack trace is seen, it sends the stack trace to Samebug and retrieves the possible solutions.
When a stack trace if found in the application console, the plugin marks it with an icon on the gutter,
showing different icons depending on the quality of the solutions. When the user click on this icon,
the plugin displays the solutions.

![](intellij-plugin.png "Samebug icon on the gutter near a stack trace, showing there are tips for this stack trace")

## Restrictions

At this point this is just an experiment, we have no restrictions. Later we'll have to consider:
 - what is the earliest Eclipse version we have to support,
 - what is the earliest Java version we have to be compatible with,
 - what UI toolkit to use,
 - do we have to do extra processing/filtering on the console output (e.g. the JUnit test runner
in IntelliJ IDEA does some magic to visualize the tests better, but it makes parsing the stack traces harder).

For now, stick with:
 - latest Eclipse,
 - Java 8 source code compatibility,
 - swt for UI (keep UI elements as minimal as possible)
 - suppose the application we monitor writes to stdout.
