package com.plasne.functions;

import java.util.*;
import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.serverless.functions.*;
import com.microsoft.azure.keyvault.KeyVaultClient;
import com.microsoft.azure.keyvault.models.SecretBundle;

public class Function {

    @FunctionName("secret")
    public static HttpResponseMessage<String> secret(
        @HttpTrigger(name = "req", methods = {"get"}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
        final ExecutionContext context) {

        // get password from keyvault 
        String applicationId = "3d4b51e4-9999-9999-9999-6bd7da32e45e";
        String applicationSecret = "k...Q=";
        KeyVaultClient client = KeyVaultADALAuthenticator.getClient(applicationId, applicationSecret);
        SecretBundle bundle = client.getSecret("https://pelasne-vault.vault.azure.net/", "password1");
        System.out.println(bundle);
        String password = bundle.value();

        // query parameter
        String query = request.getQueryParameters().get("name");
        String name = request.getBody().orElse(query);

        // response
        if (name == null) {
            return request.createResponse(400, "Please pass a name on the query string or in the request body");
        } else {
            return request.createResponse(200, "Hello, " + name + ", your password is " + password);
        }

    }

}
