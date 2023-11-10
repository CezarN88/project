package ro.itschool.project.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.project.models.Weather;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class WeatherServiceImpl implements  WeatherService{

    private final WeatherValidatorService weatherValidatorService;
    private final ObjectMapper objectMapper;

    public WeatherServiceImpl(WeatherValidatorService weatherValidatorService, ObjectMapper objectMapper) {
        this.weatherValidatorService = weatherValidatorService;
        this.objectMapper = objectMapper;
    }



    @Override
    public Weather getWeather(String city) throws IOException {
        weatherValidatorService.validateCity(city);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.weatherapi.com/v1/current.json?key=c95443508af34df68be161532231011&q=" + city)
                .build();
        Response response = client.newCall(request).execute();
        log.info(response.body().toString());

        String responseBody = response.body().string();
        System.out.println(responseBody);

        JsonNode jsonNode = objectMapper.readTree(responseBody);
        System.out.println(jsonNode.asText());


        String cityName = jsonNode.get("location").get("name").asText();
      //  System.out.println(cityName + "___________");
        String descriptionValue = jsonNode.get("current").get("condition").get("text").asText();
        String lastUpdatedValue = jsonNode.get("current").get("last_updated").asText();

        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime lastUpdated = LocalDateTime.parse(lastUpdatedValue,formatter);

        Weather weather = new Weather();
        weather.setCity(cityName);
        weather.setLastUpdated(lastUpdated);
        weather.setDescription(descriptionValue);

        return weather;
    }
}
