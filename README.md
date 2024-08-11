<h1  align="center" style="border-bottom: none">
    <b>
        ThingWorx Extension Uploader, an Eclipse Plugin
    </b>
</h1>
    <h4 align="center">An open-source tool to auto upgrade, build and upload ThingWorx extension from Eclipse IDE</h4>
<br/>
<p align="center">
<img src="https://sonarcloud.io/api/project_badges/measure?project=WGLabz_kafka-explorer&metric=code_smells" />
<img src="https://sonarcloud.io/api/project_badges/measure?project=WGLabz_kafka-explorer&metric=sqale_rating" />
<img src="https://sonarcloud.io/api/project_badges/measure?project=WGLabz_kafka-explorer&metric=bugs" />
</p>
<br/>
<p align="center"><img src=".assets/icon.png" alt="Kafka Explorer" width="300px" height="200px"/></p>
<br/>
<br/>

## How to use ?
<br/>

Downlod the .jar file frm [release page](https://github.com/WGLabz/ThingWorxExtensionUploader/releases) and put it into dropins folder of your eclipse IDE

<p align="center"><img src=".assets/1.png" alt="Kafka Explorer" width="400" /></p>

Now restart the Eclipse IDE, Once restarted you will see a new icon in the toolbar of your IDE,

<p align="center"><img src=".assets/2.png" alt="Kafka Explorer" width="400" /></p>

You can now use the plugin, on click of this the following dialog would appear,
<p align="center"><img src=".assets/3.png" alt="Kafka Explorer" width="400" /></p>

In the dialog, select the extension project, set the serevr address, and add an appKey with Admin level access, don not forget to click on Update TWX details. That would store the details for future use.


> At this point make sure you have compiled your extension outside of this plugin atleast once and refreshed the project content. (Its a known bug, am working on), otherwisre you will get following error,
<p align="center"><img src=".assets/4.png" alt="Kafka Explorer" width="400" /></p>



Now when you click ok, if verything goes well, you will egt a sucessfull message and the extension woudl be deployed to the server.
<p align="center"><img src=".assets/5.png" alt="Kafka Explorer" width="400" /></p>


## How to build ?


to be continued.....


## License

Distributed under the AGPLv3 License. See `LICENSE.md` for more information.

<br/>