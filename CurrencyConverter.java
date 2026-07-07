/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.currencyconverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CurrencyConverter {

    // Using a reliable free exchange rate API endpoint (ExchangeRate-API)
    private static final String API_URL = "https://open.er-api.com/v6/latest/USD";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;

        System.out.println("=== Financial Translation Engine Initiated ===");

        // The do-while loop: Keeps the engine alive for continuous routing
        do {
            try {
                // 1. THE INTAKE: Capturing request data
                System.out.print("\nEnter base currency (e.g., USD, EUR, GBP): ");
                String baseCurrency = scanner.next().toUpperCase();

                System.out.print("Enter target currency (e.g., INR, EUR, JPY): ");
                String targetCurrency = scanner.next().toUpperCase();

                System.out.print("Enter amount to convert: ");
                double rawAmount = scanner.nextDouble();

                // 2. THE SECURITY GATE: Defensive pipeline validation
                if (rawAmount < 0) {
                    System.out.println("[Error] Security Gate: Negative amounts are not allowed.");
                    continue; // Reroutes user back to start without crashing
                }

                BigDecimal amount = BigDecimal.valueOf(rawAmount);

                // 3. THE TURBOCHARGER: Fetching live API exchange rates
                System.out.println("Connecting to live pipeline...");
                BigDecimal exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);

                if (exchangeRate == null) {
                    System.out.println("[Error] Failed to fetch cross-rate routing data. Try again.");
                    continue;
                }

                // 4. THE COMBUSTION ENGINE: Precise decimal arithmetic multiplication
                BigDecimal convertedAmount = amount.multiply(exchangeRate);
                
                // Applying Banker's Rounding (HALF_EVEN) to exactly two decimal places
                convertedAmount = convertedAmount.setScale(2, RoundingMode.HALF_EVEN);

                // 5. THE EXHAUST STAGE: Formatted output polish
                System.out.println("\n--- Processing Complete ---");
                System.out.printf("Base Amount   : %,.2f %s%n", amount, baseCurrency);
                System.out.printf("Exchange Rate : %.4f%n", exchangeRate);
                System.out.printf("Converted     : %,.2f %s%n", convertedAmount, targetCurrency);
                System.out.println("---------------------------");

            } catch (InputMismatchException e) {
                // THE BUFFER TRAP: Catches strings when numbers are expected
                System.out.println("[Error] Buffer Trap: Invalid input format detected.");
                scanner.nextLine(); // Clear the broken token out of the pipe buffer
            }

            // Ask user to continue or exit
            System.out.print("\nPerform another conversion? (yes/no): ");
            String choice = scanner.next().toLowerCase();
            if (!choice.equals("yes") && !choice.equals("y")) {
                continueRunning = false;
            }

        } while (continueRunning);

        System.out.println("\nEngine shut down gracefully. Goodbye.");
        scanner.close();
    }

    /**
     * The API Connection Pipeline & JSON Parsing Logic
     */
    private static BigDecimal fetchExchangeRate(String base, String target) {
        try {
            // URL Construction & Connection Management
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Response Verification
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return null;
            }

            // Data Extraction via BufferedReader pipeline
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String json = response.toString();
            
            // Fast Cross-Rate JSON Extraction without needing external heavy frameworks
            // Locates the specific exchange currency rates block inside the payload string
            String baseKey = "\"" + base + "\":";
            String targetKey = "\"" + target + "\":";

            if (!json.contains(targetKey)) {
                System.out.println("[Error] Target currency not supported or found.");
                return null;
            }

            // If base currency isn't USD, we calculate the cross-rate dynamically via pivot math
            double usdToBaseRate = extractJsonValue(json, baseKey);
            double usdToTargetRate = extractJsonValue(json, targetKey);

            // Cross-Rate Routing logic: Target Rate / Base Rate
            double finalCrossRate = usdToTargetRate / usdToBaseRate;

            return BigDecimal.valueOf(finalCrossRate);

        } catch (Exception e) {
            System.out.println("[Pipeline Exception] " + e.getMessage());
            return null;
        }
    }

    private static double extractJsonValue(String json, String key) {
        int startIndex = json.indexOf(key) + key.length();
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1 || json.indexOf("}", startIndex) < endIndex) {
            endIndex = json.indexOf("}", startIndex);
        }
        String valueStr = json.substring(startIndex, endIndex).trim();
        return Double.parseDouble(valueStr);
    }
}