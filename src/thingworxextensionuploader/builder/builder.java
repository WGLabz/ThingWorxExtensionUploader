package thingworxextensionuploader.builder;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

import java.io.File;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.IConsole;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class builder{
	private MessageConsole console;
	
	public void buildProject(String projectName) {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject project = workspace.getRoot().getProject(projectName);

        if (!project.exists()) {
            MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Project does not exist.");
            return;
        }

        // Path to the Ant build script
        File buildFile = new File(project.getLocation().toOSString(), "build-extension.xml");

        if (!buildFile.exists()) {
            MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Build file does not exist.");
            return;
        }

        Project antProject = new Project();
        antProject.setUserProperty("ant.file", buildFile.getAbsolutePath());

        try {
            project.open(new NullProgressMonitor());
        } catch (CoreException e) {
            e.printStackTrace();
            return;
        }

        antProject.init();
        ProjectHelper.configureProject(antProject, buildFile);
        
        // Setup the console
        setupConsole();

        // Create a custom logger to redirect Ant output to the Eclipse console
        antProject.addBuildListener(new AntBuildLogger(console));
        // Optional: Use Ant's command-line interface to run the build
        antProject.executeTarget("build");
        
        System.out.println("Build completed");
        // Alternatively, you can run the build directly
        // antProject.executeTarget("your-target-name");
    }
	
	private void setupConsole() {
        console = findConsole("Ant Build Console");
        console.activate();
    }

    private MessageConsole findConsole(String name) {
        // Find existing consoles
        IConsole[] existingConsoles = ConsolePlugin.getDefault().getConsoleManager().getConsoles();
        for (IConsole console : existingConsoles) {
            if (console instanceof MessageConsole && console.getName().equals(name)) {
                return (MessageConsole) console;
            }
        }

        // Create a new console if not found
        MessageConsole newConsole = new MessageConsole(name, null);
        ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{newConsole});
        return newConsole;
    }
}