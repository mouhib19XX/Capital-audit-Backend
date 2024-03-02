package com.example.capitalauditbackend.Controllers;

import com.example.capitalauditbackend.DOA.DatabaseConnector;
import com.example.capitalauditbackend.Utilities.Authentication;
import com.example.capitalauditbackend.model.PaymentData;
import com.example.capitalauditbackend.model.user;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;

public class GetPaymentDataController {


    private final Gson gson = new Gson();
    @GetMapping("/postPaymentData")
    public ResponseEntity<String> login(@RequestBody String jsonString) {
        return getPaymentDataHandler(jsonString);
    }

    private ResponseEntity<String> getPaymentDataHandler(String jsonString)
    {
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        String token = jsonObject.get("token").getAsString();
        Claims claim = Authentication.decodeToken(token);
        if(Authentication.tokenAuthenticator(claim))
        {
            DatabaseConnector db = new DatabaseConnector();
            int user_id = user.getUser_id();
            try
            {
                List<PaymentData> paymentDataList = new ArrayList<>();
                paymentDataList = db.getPaymentData(user_id);
                return response(0, "Success.", paymentDataList);
            }
            catch (Exception e)
            {
                return response(1, "Error when trying to fetch data", null);

            }
        }
        else
        {
            return response(1, "Authentication failed. Try to sign in again.", null);
        }
    }

    private ResponseEntity<String> response(int result, String message, List<PaymentData> paymentDataList)
    {
        if (result == 0)
        {
            return ResponseEntity.ok().body(gson.toJson(paymentDataList));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
    }

}
