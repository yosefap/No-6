package com.example.demo.controller;


import com.example.demo.model.UserList;
import com.example.demo.service.IUserListService;
import com.example.demo.util.GeneratePdfReport;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.*;

@Controller
public class Mycontroller {
    //biki API, connect API github, Gabungun, ambil data, bikin pdf nya

    @Autowired
    private IUserListService userListService;

    @RequestMapping(value = "/api")
    @GetMapping
    public String checkAPI(){
        return "haii";
    }

    @RequestMapping(value = "/apigit")
    @GetMapping
    public void apiGit (){

        try {
            URL url = new URL("https://api.github.com/user");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int respon = conn.getResponseCode();

            if (respon != 200){
                throw new RuntimeException("HttpRespon: " + respon);

            }else {
                StringBuilder informatingString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()){
                    informatingString.append(scanner.nextLine());
                }

                scanner.close();

                System.out.println(informatingString);

                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(valueOf(informatingString));

                System.out.println(dataObject.get(0));

                JSONObject countryData = (JSONObject) dataObject.get(0);

                System.out.println(countryData.get("location_type"));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> userlistReport() {

        var NameUSer = (List<UserList>) userListService.findAll();

        ByteArrayInputStream bis = GeneratePdfReport.userlistReport(NameUSer);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=userReport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}