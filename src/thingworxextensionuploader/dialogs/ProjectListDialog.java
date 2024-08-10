package thingworxextensionuploader.dialogs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import thingworxextensionuploader.preferences.PreferenceUtils;

import org.eclipse.swt.widgets.Button;

public class ProjectListDialog extends TitleAreaDialog {

	private Combo projectCombo;
	private String selectedProjectName;
	private Text urlText;
	private Text appKeyText;
	private Button configureButton;

	public ProjectListDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
	    Composite container = new Composite((Composite)super.createDialogArea(parent), SWT.NONE);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

		Label label = new Label(container, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		label.setText("Project:");

		projectCombo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		projectCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		populateProjectList(projects);

		String storedProjectName = PreferenceUtils.getStoredProjectName();

		List<String> projectNames = Arrays.stream(projects).map(IProject::getName).collect(Collectors.toList());

		if (storedProjectName != null && projectNames.contains(storedProjectName)) {
			projectCombo.setText(storedProjectName);
		}

		// URL
		Label urlLabel = new Label(container, SWT.NONE);
		urlLabel.setText("URL:");
		urlLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		urlText = new Text(container, SWT.BORDER);
		urlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		urlText.setText(PreferenceUtils.getStoredURL());

		// AppKey
		Label appKeyLabel = new Label(container, SWT.NONE);
		appKeyLabel.setText("App Key(ADMIN):");
		appKeyLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

		appKeyText = new Text(container, SWT.BORDER | SWT.PASSWORD);
		appKeyText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		appKeyText.setText(PreferenceUtils.getStoredAppKey());

		// Configure Button
		configureButton = new Button(container, SWT.PUSH);
		configureButton.setText("Update TWX details");
		configureButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

		configureButton.addListener(SWT.Selection, e -> saveConfig());

		return container;
	}

	private void saveConfig() {
		PreferenceUtils.storeURL(urlText.getText());
		PreferenceUtils.storeAppKey(appKeyText.getText());
		MessageDialog.openInformation(this.getShell(), "ThingWorx server configuration", "Updated sucessfully!!");

		return;
	}

	private void populateProjectList(IProject[] projects) {

		for (IProject project : projects) {
			if (project.isOpen()) {
				projectCombo.add(project.getName());
			}
		}
	}

	@Override
	protected void okPressed() {
		selectedProjectName = projectCombo.getText();
		printProjectDetails(selectedProjectName);
		PreferenceUtils.storeSelectedProjectName(selectedProjectName);
		super.okPressed();
	}

	public String getSelectedProjectName() {
		return selectedProjectName;
	}

	private void printProjectDetails(String projectName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(projectName);

		if (project.exists()) {
			System.out.println("Project Name: " + project.getName());
		} else {
			System.out.println("Project does not exist.");
		}
	}

	@Override
	public void create() {
		super.create();
		setTitle("Configurations");
        setMessage("Configure the target ThingWorx server details and the extension project", IMessageProvider.INFORMATION);
	}
}