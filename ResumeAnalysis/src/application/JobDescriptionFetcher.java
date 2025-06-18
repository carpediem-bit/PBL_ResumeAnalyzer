package application;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JobDescriptionFetcher {
    public static String fetchSkills(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            return doc.text();
        } catch (IOException e) {
            return "Error fetching job description: " + e.getMessage();
        }
    }
}