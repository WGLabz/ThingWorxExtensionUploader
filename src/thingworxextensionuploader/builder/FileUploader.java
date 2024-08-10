package thingworxextensionuploader.builder;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import thingworxextensionuploader.preferences.PreferenceUtils;

public class FileUploader {
	private static MessageConsole console = new MessageConsole("XML Version Updater", null);

	public static void uploadZipFile(IFile zipFile, Shell shell, String version) {
		MessageConsoleStream out = console.newMessageStream();

		// Retrieve URL, Username, and Password
		String url = PreferenceUtils.getStoredURL();
		String appKey = PreferenceUtils.getStoredAppKey();

		// Log the details
		out.println("Starting upload...");
		out.println("URL: " + url);
		out.println("File: " + zipFile.getLocation().toString());
		if (url.isBlank() || appKey.isBlank()) {
			MessageDialog.openError(shell, "Error uploading to ThingWorx Server",
					"ThingWorx server details not configured for upload, please config!!");
		}
		// Initialize HttpClient
		try (

				CloseableHttpClient httpClient = HttpClients.createDefault()) {

			// Create HTTP POST request
			HttpPost uploadFile = new HttpPost(url);
			uploadFile.setHeader("appKey", appKey);
			uploadFile.setHeader("Accept", "application/json");
			uploadFile.setHeader("X-XSRF-TOKEN", "TWX-XSRF-TOKEN-VALUE");

			// Prepare the ZIP file to be uploaded
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("file", new File(zipFile.getLocation().toFile().getAbsolutePath()),
					ContentType.APPLICATION_OCTET_STREAM, zipFile.getName());
			HttpEntity multipart = builder.build();

			uploadFile.setEntity(multipart);

			// Configure and execute the POST request
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			uploadFile.setConfig(requestConfig);

			try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
				int statusCode = response.getStatusLine().getStatusCode();
				String responseString = new String(response.getEntity().getContent().readAllBytes());
				if(statusCode == 200) {
					MessageDialog.openInformation(shell, "Extension upload status","Version: "+version+" uploaded sucessfully!!");
				}else {
					MessageDialog.openError(shell, "Extension upload failed","Code: "+statusCode+" Res: "+responseString);
				}
				// Log the response
				out.println("Response Code: " + statusCode);
				out.println("Response Body: " + responseString);
			}

		} catch (IOException e) {
			// Log any exceptions
			out.println("Upload failed: " + e.getMessage());
			MessageDialog.openError(shell, "Upload failed", e.getMessage());

			e.printStackTrace(new PrintStream(out));
		}
	}
}