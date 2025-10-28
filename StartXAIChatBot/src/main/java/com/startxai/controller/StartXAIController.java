package com.startxai.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.*;

@RestController
@RequestMapping("/api")
public class StartXAIController {

    // 1️⃣ Chat API – main chatbot logic
    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String reply = "You said: " + message + " (StartXAIChatBot responding 💬)";
        return ResponseEntity.ok(Map.of("reply", reply));
    }

    // 2️⃣ Location Analysis API
    @PostMapping("/location-analysis")
    public ResponseEntity<Map<String, String>> locationAnalysis(@RequestBody Map<String, Double> body) {
        double lat = body.getOrDefault("latitude", 0.0);
        double lon = body.getOrDefault("longitude", 0.0);
        String location = "Unknown";
        String analysis = "Unable to analyze location.";

        if (lat == 12.9716 && lon == 77.5946) {
            location = "Bengaluru, India";
            analysis = "Tech hub region — optimized for startups and AI innovation.";
        } else if (lat == 19.0760 && lon == 72.8777) {
            location = "Mumbai, India";
            analysis = "Financial and commercial hub — perfect for business insights.";
        }

        Map<String, String> response = new HashMap<>();
        response.put("location", location);
        response.put("analysis", analysis);
        return ResponseEntity.ok(response);
    }

    // 3️⃣ Smothering Response API (Different responses for same input)
    @PostMapping("/smothering-response")
    public ResponseEntity<Map<String, String>> smothering(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        List<String> variations = List.of(
            "AI is the simulation of human intelligence by machines.",
            "Artificial Intelligence helps computers think and learn like humans.",
            "AI allows systems to mimic human reasoning and behavior."
        );
        Collections.shuffle(variations);
        Map<String, String> response = new LinkedHashMap<>();
        for (int i = 0; i < variations.size(); i++) {
            response.put("response_" + (i + 1), variations.get(i));
        }
        return ResponseEntity.ok(response);
    }

    // 4️⃣ Contextual Memory API
    private static final Map<String, List<String>> memoryDB = new HashMap<>();

    @PostMapping("/contextual-memory")
    public ResponseEntity<Map<String, String>> contextualMemory(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String message = body.get("message");

        memoryDB.putIfAbsent(userId, new ArrayList<>());
        memoryDB.get(userId).add(message);

        String lastContext = memoryDB.get(userId).size() > 1
            ? memoryDB.get(userId).get(memoryDB.get(userId).size() - 2)
            : "No previous context.";

        Map<String, String> response = new HashMap<>();
        response.put("reply", "Previous: " + lastContext + " | Current: " + message);
        return ResponseEntity.ok(response);
    }

    // 5️⃣ Intent Detection API
    @PostMapping("/intent-detection")
    public ResponseEntity<Map<String, Object>> intentDetection(@RequestBody Map<String, String> body) {
        String message = body.get("message").toLowerCase();
        String intent = "unknown";
        double confidence = 0.5;

        if (message.contains("hi") || message.contains("hello")) {
            intent = "greeting"; confidence = 0.95;
        } else if (message.contains("weather")) {
            intent = "weather_query"; confidence = 0.92;
        } else if (message.contains("time")) {
            intent = "time_query"; confidence = 0.90;
        } else if (message.contains("bye")) {
            intent = "farewell"; confidence = 0.93;
        }

        Map<String, Object> response = new HashMap<>();
        response.put("intent", intent);
        response.put("confidence", confidence);
        return ResponseEntity.ok(response);
    }
}
