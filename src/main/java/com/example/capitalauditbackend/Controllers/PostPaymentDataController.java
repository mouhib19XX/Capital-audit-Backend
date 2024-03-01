package com.example.capitalauditbackend.Controllers;

import com.example.capitalauditbackend.DOA.DatabaseConnector;
import com.example.capitalauditbackend.Utilities.Authentication;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.crypto.Data;

public class PostPaymentDataController {
    private final Gson gson = new Gson();

    /**
     * Finish creating this, i need to make a database Connector thingy.
     *
     *
     */

    @PostMapping("/postPaymentData")
    public ResponseEntity<String> login(@RequestBody String jsonString) {
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        String token = jsonObject.get("token").getAsString();
        Claims claim = Authentication.decodeToken(token);
        if(Authentication.tokenAuthenticator(claim))
        {
            DatabaseConnector db = new DatabaseConnector();
            String data = jsonObject.get("data").getAsString();
            return response(0, "a");
        }
        else
        {
            int result = 1;
            return response(1);
        }
    }
    private void postPaymentHandler()
    {

    }

    private static ResponseEntity<String> response(int result)
    {
        if (result == 0)
        {
            ResponseEntity response;
            return response<null>;
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Token failed.");
        }
    }


}
