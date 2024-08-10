package thingworxextensionuploader.builder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlVersionUpdater {

    private static MessageConsole console = new MessageConsole("XML Version Updater", null);

    public static String updateVersionInXmlFile(IFile xmlFile) {
    	String updatedVersion = "";
        MessageConsoleStream out = console.newMessageStream();
        try {
            // Get the full path of the file
            IPath path = xmlFile.getLocation();
            File file = path.toFile();

            // Create DocumentBuilderFactory and DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse XML file into Document
            Document document = builder.parse(new FileInputStream(file));
            document.getDocumentElement().normalize();

            // Get the ExtensionPackage tag
            NodeList nodeList = document.getElementsByTagName("ExtensionPackage");
            if (nodeList.getLength() == 0) {
                out.println("ExtensionPackage tag not found in the XML.");
                return "";
            }

            Element packageElement = (Element) nodeList.item(0);

            // Get the packageVersion attribute
            String versionText = packageElement.getAttribute("packageVersion");
            if (versionText == null || versionText.isEmpty()) {
                out.println("packageVersion attribute not found or is empty.");
                return "";
            }

            // Extract the version numbers
            String[] versionParts = versionText.split("\\.");
            if (versionParts.length != 3) {
                out.println("Version format is incorrect. Expected format: x.y.z");
                return "";
            }

            // Increment the patch number
            int major = Integer.parseInt(versionParts[0]);
            int minor = Integer.parseInt(versionParts[1]);
            int patch = Integer.parseInt(versionParts[2]) + 1;

            // Create the new version string
            String newVersion = major + "." + minor + "." + patch;
            packageElement.setAttribute("packageVersion", newVersion);
            updatedVersion = newVersion;
            // Print the updated version number
            out.println("Updated version number: " + newVersion);

            // Save the changes back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(file));
            transformer.transform(source, result);
        } catch (Exception e) {
            out.println("Error updating version: " + e.getMessage());
            e.printStackTrace(new PrintStream(out));
        }
		return updatedVersion;
    }

    public static MessageConsole getConsole() {
        return console;
    }

    public static void activateConsole() {
        org.eclipse.ui.console.ConsolePlugin.getDefault().getConsoleManager().addConsoles(new org.eclipse.ui.console.IConsole[]{console});
    }
}