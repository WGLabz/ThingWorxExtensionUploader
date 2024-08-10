package thingworxextensionuploader.builder;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class AntBuildLogger implements BuildListener {

    @SuppressWarnings("unused")
	private final MessageConsole console;
    private final MessageConsoleStream consoleStream;

    public AntBuildLogger(MessageConsole console) {
        this.console = console;
        this.consoleStream = console.newMessageStream();
    }

    @Override
    public void buildStarted(BuildEvent event) {
        consoleStream.println("Build started: " + event.getTarget().getName());
    }

    @Override
    public void buildFinished(BuildEvent event) {
        consoleStream.println("Build finished: " + (event.getException() != null ? "Failed" : "Succeeded"));
        if (event.getException() != null) {
        	consoleStream.println(event.getException().toString());
        }
    }

    @Override
    public void targetStarted(BuildEvent event) {
        consoleStream.println("Target started: " + event.getTarget().getName());
    }

    @Override
    public void targetFinished(BuildEvent event) {
        consoleStream.println("Target finished: " + event.getTarget().getName());
    }

    @Override
    public void taskStarted(BuildEvent event) {
        consoleStream.println("Task started: " + event.getTask().getTaskName());
    }

    @Override
    public void taskFinished(BuildEvent event) {
        consoleStream.println("Task finished: " + event.getTask().getTaskName());
    }

    @Override
    public void messageLogged(BuildEvent event) {
        consoleStream.println(event.getMessage());
    }
}