package cmc.project.weather;


import java.io.IOException;
import java.util.HashMap;

import java.util.Scanner;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class WeatherCrawling {
    public HashMap<String, Elements> getWeatherInformation() throws IOException{
        final String URL = "http://www.weather.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=109";

        HashMap<String, Elements> weatherData = new HashMap<>();
        Connection conn = Jsoup.connect(URL);

        Document document = conn.get();

        Elements locations = document.select("location"); // 문서가 xml 문서 이므로 location 태그를 가진 elements 찾기

        for (Element location : locations) {
            weatherData.put(location.select("city").text(), location.select("data"));
        }

        return (weatherData);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        WeatherCrawling wc = new WeatherCrawling();

        try {
            HashMap<String, Elements> weatherInformation = wc.getWeatherInformation();

            System.out.println(weatherInformation.keySet().toString());
            System.out.print("도시 이름을 입력 해주세요 : ");
            String cityName = input.next();
            System.out.println("===============================================");
            for(Element data : weatherInformation.get(cityName)) {
                String tmEf = data.select("tmEf").text(); // 시간
                String wf = data.select("wf").text(); // 날씨
                String tmn = data.select("tmn").text(); // 최저 온도
                String tmx = data.select("tmx").text(); // 최고 온도
                String rnSt = data.select("rnSt").text(); // 강수량

                System.out.println("시간 : " + tmEf);
                System.out.println("날씨 : " + wf);
                System.out.println("최저 : " + tmn + "°C");
                System.out.println("최고 : " + tmx + "°C");
                System.out.println("강수량 : " + rnSt);

                System.out.println("===============================================");

            }


        } catch (IOException e1) {
            throw new RuntimeException(e1);
        } catch (NullPointerException e2) {
            System.out.println("찾을수 없는 위치 정보 입니다.");
        }

    }
}
