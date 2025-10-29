package com.startxai;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@CrossOrigin(origins = "*") // ✅ Allow Flutter app to connect
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ApiService apiService;

    private Map<String, String> loggedUsers = new HashMap<>();

    // ✅ 1. Login endpoint
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (username.equals("admin") && password.equals("1234")) {
            loggedUsers.put(username, "ACTIVE");
            return "✅ Login successful! Welcome " + username + ".";
        } else {
            return "❌ Invalid credentials. Try again.";
        }
    }

    // ✅ 2. Start conversation
    @GetMapping("/start")
    public String startChat(@RequestParam String username) {
        if (!loggedUsers.containsKey(username)) {
            return "⚠️ Please login first!";
        }
        return "👋 Hi " + username + "! Tell me your business type (e.g., retail, farming, education, etc.)";
    }

    // ✅ 3. Process user reply & generate AI-based strategy
    @PostMapping("/respond")
    public Map<String, Object> respond(@RequestParam String username, @RequestParam String userInput) {
        if (!loggedUsers.containsKey(username)) {
            return Map.of("message", "⚠️ Please login first!");
        }

        String apiResult = apiService.processAPIs(userInput);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("username", username);
        response.put("input", userInput);
        response.put("strategy", apiResult);
        response.put("note", "🔁 Same input will generate different output each time.");
        return response;
    }

    // ✅ 4. Export strategy
    @GetMapping("/export")
    public String exportStrategy(@RequestParam String username) {
        return "📝 Strategy exported successfully for " + username + "!";
    }
}
