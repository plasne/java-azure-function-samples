# Java Azure Function Samples

This repo serves only as a set of samples for Java Azure Functions since some of the information is difficult to find.

## Produce / Consume

The sample includes 2 Functions that do the following:

* Produce
  * Run every 1 minute
  * Read a text file from Azure Blob Storage
  * Enqueue the contents of the file to an Azure Storage Queue

As an alternative, you can also get the Blob as a byte array:

```java
@BlobInput(name = "blob", connection = "AzureWebJobsStorage", dataType = "binary", path = "input/config.json") byte[] myblob,
```

According to the enumeration you could get a stream as well, but I have not got that to work.

* Consume
  * Runs whenever a new message is found in the Azure Storage Queue
  * Writes the message to the logger

## Configuration

The "local.settings.json" file must be modified such that "AzureWebJobsStorage" contains a connection string to a Blob Storage Account.
