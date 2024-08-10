package thingworxextensionuploader.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import thingworxextensionuploader.builder.FileUploader;
import thingworxextensionuploader.builder.XmlVersionUpdater;
import thingworxextensionuploader.builder.builder;
import thingworxextensionuploader.dialogs.ProjectListDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Shell;

public class MainHandler extends AbstractHandler {
	String newVersionOfExtension;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ProjectListDialog dialog = new ProjectListDialog(window.getShell());
		dialog.open();

		if (dialog.getReturnCode() == TitleAreaDialog.OK) {
			String selectedProjectName = dialog.getSelectedProjectName();
			System.out.println("Selected Project: " + selectedProjectName);
			// Perform additional actions with the selected project name
			updateXmlVersion(window.getShell(), selectedProjectName);
			new builder().buildProject(selectedProjectName);
			uploadToServer(window.getShell(), selectedProjectName);
		}
		return null;
	}

	private void updateXmlVersion(Shell shell, String selectedProjectName) {
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(selectedProjectName);
			if (project != null && project.isAccessible()) {
				IFile xmlFile = project.getFile("configfiles/metadata.xml");
				if (xmlFile.exists()) {
					newVersionOfExtension = XmlVersionUpdater.updateVersionInXmlFile(xmlFile);
				} else {
					MessageDialog.openError(shell, "Error", "The XML file does not exist in the selected project.");
				}
			} else {
				MessageDialog.openError(shell, "Error", "The selected project is not accessible.");
			}
		} catch (Exception e) {
			MessageDialog.openError(shell, "Error", "An error occurred while accessing the project: " + e.getMessage());
		}
	}

	private void uploadToServer(Shell shell, String selectedProjectName) {
		System.out.println("Uploading file to server.");
		try {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(selectedProjectName);
			if (project != null && project.isAccessible()) {
				IFile zipFile = project.getFile("build/distributions/" + selectedProjectName + ".zip");
				if (zipFile.exists()) {
					FileUploader.uploadZipFile(zipFile, shell, newVersionOfExtension);
				} else {
					MessageDialog.openError(shell, "Error",
							"The zip file " + ("build/distributions/" + selectedProjectName + ".zip")
									+ " does not exist in the selected project.");
				}
			} else {
				MessageDialog.openError(shell, "Error", "The selected project is not accessible.");
			}
		} catch (Exception e) {
			MessageDialog.openError(shell, "Error", "An error occurred while accessing the project: " + e.getMessage());
		}
	}
}