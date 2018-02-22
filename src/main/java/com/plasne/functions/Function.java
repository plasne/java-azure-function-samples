package com.plasne.functions;

import java.util.*;
import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.serverless.functions.*;

public class Function {

    @FunctionName("produce")
    public static String produce(
        @TimerTrigger(name = "schedule", schedule = "0 */1 * * * *") String timerInfo,
	@BlobInput(name = "blob", connection = "AzureWebJobsStorage", dataType = "binary", path = "input/config.json") byte[] myblob,
        @QueueOutput(name = "message", queueName = "myqueue", connection = "AzureWebJobsStorage") OutputBinding<String> queue,
        final ExecutionContext context) {

	// read message
	String value = new String(myblob);

	// queue a message
	String message = value;
	queue.setValue(message);

	// return
	context.getLogger().info("queued: " + message); 
	return "queued: " + message;
    }

    @FunctionName("consume")
    public static String consume(
        @QueueTrigger(name = "message", queueName = "myqueue", connection = "AzureWebJobsStorage") String message,
        final ExecutionContext context) {

	// return message
	context.getLogger().info("consumed: " + message);
	return "consumed: " + message;

    }

}
