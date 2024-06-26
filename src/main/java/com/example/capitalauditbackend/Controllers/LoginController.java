package com.example.capitalauditbackend.Controllers;
import com.example.capitalauditbackend.Utilities.Authentication;
import com.example.capitalauditbackend.DOA.DatabaseConnector;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    private final Gson gson = new Gson();
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String jsonString) {
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        int result = loginHandler(username, password);

        return response(result, username);
    }
    private static int loginHandler(String username, String password)
    {
        DatabaseConnector db = new DatabaseConnector();
        boolean successfulQuery = db.ExecuteLoginQuery(username, password);

        if (successfulQuery)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    private static ResponseEntity<String> response(int result, String username)
    {
        if (result == 0)
        {
            String token = Authentication.generateToken(username);
            return ResponseEntity.ok()
                    .header("Authorization", token)
                    .body("Login successful");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }
}
