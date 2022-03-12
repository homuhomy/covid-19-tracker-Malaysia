package io.homuhomy.covidtracker.Service;

import io.homuhomy.covidtracker.Service.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


//@service & @postconstruct

//when u construct instance of CovidDataService, after it's done, just execute postconstruct method
@Service
public class CovidDataService {

    private static String VirusDataUrl = "https://raw.githubusercontent.com/MoH-Malaysia/covid19-public/main/epidemic/cases_state.csv";

    private List<LocationStats> allStats = new ArrayList<>();
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *") //update every day
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VirusDataUrl))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(httpResponse.body());

        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("state"));
            locationStat.setCases_new(Integer.parseInt(record.get(record.size()-1)));
            System.out.println(locationStat);
            newStats.add(locationStat);
        }
        this.allStats = newStats;

    }
}
