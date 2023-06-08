package com.example.api_client;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ApiClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        GetImage();
    }


    //RETURNS THE LOCAL TIME
    public static void GetTime()throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/api/time")).GET().build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String apiResponse = response.body();
        System.out.println(apiResponse);
    }

    //RETURNS THE LOCAL TIME AND CLIENT IP IN JSON
    public static void GetTimeAndIP()throws IOException{
        String url = "http://localhost:8080/api/time_ip";

        URL apiURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();
            System.out.println("API Response: " + response.toString());
        } else {
            System.out.println("Error: Failed to get API response. Response code: " + responseCode);
        }

        connection.disconnect();
    }
    public static void GetImage()throws IOException{
        String imageUrl = "http://localhost:8080/api/image";
        String savePath = "src/main/resources/static/image.jpg"; // Provide the path where you want to save the image

        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(savePath);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("Image downloaded successfully.");
        } else {
            System.out.println("Error: Failed to download image. Response code: " + responseCode);
        }

        connection.disconnect();
    }
    }


