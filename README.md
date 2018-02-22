# Java Azure Function Samples

This repo serves only as a set of samples for Java Azure Functions since some of the information is difficult to find.

## Produce / Consume

The sample includes 2 Functions that do the following:

* Produce
  * Run every 1 minute
  * Read a text file from Azure Blob Storage
  * Enqueue the contents of the file to an Azure Storage Queue

* Consume
  * Runs whenever a new message is found in the Azure Storage Queue
  * Writes the message to the logger


## Configuration

The "local.settings.json" file must be modified such that "AzureWebJobsStorage" contains a connection string to a Blob Storage Account.