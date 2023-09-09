package Assignment1.CSC340;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
/**
 * @author Danial Afzal Assignment 1.
 *
 * A java program that implements an API to get the currency of one to another.
 *
 * @since September, 2023. I have followed the UNCG Academic Integrity policy on
 * this assignment.
 */
public class Assignment1Application {

	public static void main(String[] args) {
        SpringApplication.run(Assignment1Application.class, args);

        currPrice();
        System.exit(0);
    }

    public static void currPrice() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Convert from(capital 3 letter country): ");
        String currencyFrom = sc.nextLine();

        System.out.print("Convert to(capital 3 letter country): ");
        String currencyTo = sc.nextLine();

        try {
            //the api
            String url = "https://open.er-api.com/v6/latest/" + currencyFrom;

            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String currencyPrice = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(currencyPrice);

            //gets currency name
            String currencyName = root.findValue("base_code").asText();
            //gets curreny value that is desired
            double price = root.findValue(currencyTo).asDouble();
            //print vals
            System.out.printf("1 " + currencyName + " = %.2f " + currencyTo, price);

        } catch (JsonProcessingException ex) {
            System.out.println("error in Currency Price");
        }
    }

}
