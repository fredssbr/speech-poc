package com.speech.poc;

public class Constants {

    public static final String uri = "https://api.assemblyai.com/v2/transcript";
    public static final String apiKey = System.getenv("API_KEY");
    public static final String audioUrl = System.getenv("AUDIO_URL");
    public static final String languageCode = System.getenv("LANGUAGE_CODE");
}
