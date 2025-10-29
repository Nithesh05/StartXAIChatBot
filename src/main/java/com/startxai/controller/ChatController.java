package com.startxai.chatbot.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class ChatController {

    private Map<String, Integer> userStage = new HashMap<>();
    private Map<String, Map<String, String>> userData = new HashMap<>();

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userId = "default"; // you can make this unique later
        String userMessage = request.get("message").toLowerCase();

        userStage.putIfAbsent(userId, 0);
        int stage = userStage.get(userId);

        String reply = "";

        switch (stage) {
            case 0:
                reply = "Hello! 👋 I’m StartX AI. I can help you build a smart business strategy. Can I ask you a few quick questions first?";
                userStage.put(userId, 1);
                break;

            case 1:
                reply = "Great! What type of business are you planning to start?";
                userStage.put(userId, 2);
                break;

            case 2:
                userData.putIfAbsent(userId, new HashMap<>());
                userData.get(userId).put("businessType", userMessage);
                reply = "Nice choice! Where will you open your " + userMessage + "?";
                userStage.put(userId, 3);
                break;

            case 3:
                userData.get(userId).put("location", userMessage);
                reply = "Awesome! What’s your target audience?";
                userStage.put(userId, 4);
                break;

            case 4:
                userData.get(userId).put("audience", userMessage);
                reply = "Got it! What’s your estimated budget?";
                userStage.put(userId, 5);
                break;

            case 5:
                userData.get(userId).put("budget", userMessage);
                // ✅ Combine both final messages here (no waiting stage)
                reply = "Perfect! 🎯 Based on your answers, I’ll generate a strategy suggestion for your "
                        + userData.get(userId).get("businessType") + " in "
                        + userData.get(userId).get("location") + " targeting "
                        + userData.get(userId).get("audience") + ".\n\n"
                        + "💡 Strategy Suggestion: Focus on "
                        + userData.get(userId).get("audience")
                        + " marketing and use AI-based tools for personalized offers.";
                userStage.put(userId, 7);
                break;

            default:
                reply = "You can now ask me anything about your business strategy!";
                break;
        }

        Map<String, String> response = new HashMap<>();
        response.put("reply", reply);
        return response;
    }
}
