# Java Azure Function Samples

This repo serves only as a set of samples for Java Azure Functions since some of the information is difficult to find.

You should start by reading this:
https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-first-java-maven 

## Versions

At the time this was written, the following were the latest versions of all dependencies:

* Azure Functions Core Tools - 2.0.1-beta.24
  * https://www.npmjs.com/package/azure-functions-core-tools
  * npm install -g azure-functions-core-tools@core
  * npm shows the latest version at 2.0.1-beta1.15, but that is inaccurate

* Azure Functions Maven Plugin - 0.1.10
  * https://github.com/Microsoft/azure-maven-plugins/tree/master/azure-functions-maven-plugin 

* Azure Functions Java Worker - v1.0.0-beta-3
  * https://github.com/Azure/azure-functions-java-worker/tree/master 
  * This version should be deployed as part of the Core Tools

## POM

If you follow the above guide for creating a new Azure Function stub, it will give you a POM file, but you need to add some additional things:

This dependency should be in the file, but note that even though the latest version of the worker is version beta-3, the dependency in the POM file cannot be higher than beta-2.

```xml
<dependency>
    <groupId>com.microsoft.azure</groupId>
    <artifactId>azure-functions-java-core</artifactId>
    <version>[1.0.0-beta-2,1.0.0)</version>
</dependency>
```

You need to add the following project/properties/functionLib so that the lib folder is considered for dependencies.

```xml
<functionLib>${project.build.directory}/azure-functions/${functionAppName}/lib</functionLib>
```

You need to add the following project/build/plugins/plugin so that the dependencies are copied into a lib folder.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>3.0.2</version>
    <executions>
        <execution>
            <id>copy-dependencies</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>copy-dependencies</goal>
            </goals>
            <configuration>
                <outputDirectory>${functionLib}</outputDirectory>
                <overWriteReleases>false</overWriteReleases>
                <overWriteSnapshots>false</overWriteSnapshots>
                <overWriteIfNewer>true</overWriteIfNewer>
            </configuration>
        </execution>
    </executions>
</plugin>

```

## Using the Maven plugin

Clean, Build, Package

```bash
mvn clean package azure-functions:package
```

Run

```bash
mvn azure-functions:run
```

Deploy

```bash
mvn azure-functions:deploy
```

## Replacing a Worker

If you need to run a Java Worker from the dev branch...

```bash
git clone -b dev https://github.com/Azure/azure-functions-java-worker && cd azure-functions-java-worker
mvn clean package

CORE_TOOLS_PATH=/usr/local/lib/node_modules/azure-functions-core-tools/bin/workers/java/

sudo npm install -g azure-functions-core-tools@core --unsafe-perm true
sudo mv ${CORE_TOOLS_PATH}/azure-functions-java-worker.java ${CORE_TOOLS_PATH/azure-functions-java-worker.java.current

sudo cp <directory of azure functions java worker clone>/azure-functions-java-worker/target/azure-functions-java-worker-1.0.0-beta-2.jar ${CORE_TOOLS_PATH}/azure-functions-java-worker.jar
```