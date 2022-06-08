package com.speech.poc;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
	// write your code here
        Transcript requestBody = new Transcript();
        requestBody.setAudio_url(Constants.audioUrl);
        requestBody.setLanguage_code(Constants.languageCode);
        Gson gson = new Gson();
        String stringPayload = gson.toJson(requestBody);

        HttpRequest httpPostRequest = HttpRequest.newBuilder()
                .uri(new URI(Constants.uri))
                .header("Authorization", Constants.apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(stringPayload))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> httpPostResponse = httpClient.send(httpPostRequest, HttpResponse.BodyHandlers.ofString());

        Transcript responseTranscript = gson.fromJson(httpPostResponse.body(), Transcript.class);

        HttpRequest httpGetRequest = HttpRequest.newBuilder()
                .uri(new URI(Constants.uri + "/" + responseTranscript.getId()))
                .header("Authorization", Constants.apiKey)
                .build();

        while(true) {
            HttpResponse<String> httpGetResponse = httpClient.send(httpGetRequest, HttpResponse.BodyHandlers.ofString());
            responseTranscript = gson.fromJson(httpGetResponse.body(), Transcript.class);
            System.out.println(responseTranscript.getStatus());
            if("completed".equals(responseTranscript.getStatus()) || "error".equals(responseTranscript.getStatus()))
                break;
            Thread.sleep(1000);
        }
        System.out.println("Transcription completed!");
        System.out.println("---");
        System.out.println(responseTranscript.getText());

    }
}
