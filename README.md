<h1 align="center" style="border-bottom: none">
    <b>ThingWorx Extension Uploader, an Eclipse Plugin</b>
</h1>
<h4 align="center">An open-source tool to auto-upgrade, build, and upload ThingWorx extensions from the Eclipse IDE</h4>
<br/>
<p align="center">
    <img src="https://sonarcloud.io/api/project_badges/measure?project=WGLabz_kafka-explorer&metric=code_smells" />
    <img src="https://sonarcloud.io/api/project_badges/measure?project=WGLabz_kafka-explorer&metric=sqale_rating" />
    <img src="https://sonarcloud.io/api/project_badges/measure?project=WGLabz_kafka-explorer&metric=bugs" />
</p>
<br/>
<p align="center"><img src=".assets/icon.png" alt="ThingWorx Extension Uploader" width="300px" height="200px"/></p>
<br/>
<br/>

## Features

> If you are using this plugin, make sure your project is set to build with Ant, not Gradle. Gradle is not supported yet. Additionally, the default build file name `build-extension.xml` should not be changed.

* The plugin automatically increments the patch version of the extension with each run. For example, if the current version is `1.0.8`, it will increment to `1.0.9`. This is essential because ThingWorx only allows the import of incremental versions of any extension. 
```If you want to update the major or minor version, need to do it manually.```
* The plugin executes the build file and generates a `.zip` distribution of the extension.
* It then uploads the extension to a configured ThingWorx server using HTTP REST APIs. An Admin-level APP Key is required for this process.

## How to Use

<br/>

Download the `.jar` file from the [release page](https://github.com/WGLabz/ThingWorxExtensionUploader/releases) and place it into the `dropins` folder of your Eclipse IDE.

<p align="center"><img src=".assets/1.png" alt="ThingWorx Extension Uploader" width="400" /></p>

Restart Eclipse IDE. Once restarted, you'll see a new icon in the toolbar of your IDE.

<p align="center"><img src=".assets/2.png" alt="ThingWorx Extension Uploader" width="400" /></p>

You can now use the plugin. When you click the icon, the following dialog will appear:

<p align="center"><img src=".assets/3.png" alt="ThingWorx Extension Uploader" width="400" /></p>

In the dialog, select the extension project, set the server address, and add an `appKey` with Admin-level access. Be sure to click "Update TWX details" to save the details for future use.

> Note: Ensure that you have compiled your extension outside of this plugin at least once and refreshed the project content. This is a known bug that I am working on. Otherwise, you will receive the following error:

<p align="center"><img src=".assets/4.png" alt="ThingWorx Extension Uploader Error" width="400" /></p>

If everything goes well when you click "OK", you will receive a success message, and the extension will be deployed to the server.

<p align="center"><img src=".assets/5.png" alt="ThingWorx Extension Uploader Success" width="400" /></p>

## How to Build

To be continued...

## License

Distributed under the AGPLv3 License. See `LICENSE.md` for more information.

<br/>
