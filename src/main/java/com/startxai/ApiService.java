package com.startxai;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class ApiService {

    private final String[] ideas = {
        "Focus on local SEO marketing",
        "Introduce referral-based discounts",
        "Use social media sentiment analysis",
        "Leverage AI to predict demand trends",
        "Expand customer feedback automation"
    };

    public String processAPIs(String input) {
        // Simulate random API data
        Random random = new Random();
        String idea = ideas[random.nextInt(ideas.length)];
        return "Based on analysis of '" + input + "', strategy suggestion: " + idea;
    }
}
