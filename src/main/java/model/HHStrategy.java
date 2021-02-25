package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy{

    private static final String URL_FORMAT = "https://hh.ru/search/vacancy?text=java+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> allVacancies = new ArrayList<>();

        int page = 0;
        try {
            do {
                Document doc = getDocument(searchString, page);

                Elements vacanciesHtmlList = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");

                if (vacanciesHtmlList.isEmpty()) break;

                for (Element element : vacanciesHtmlList) {
                    Elements links = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title");
                    Elements locations = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address");
                    Elements companyName = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer");
                    Elements salary = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation");

                    Vacancy vacancy = new Vacancy();
                    vacancy.setSiteName("hh.ru");
                    vacancy.setTitle(links.get(0).text());
                    vacancy.setUrl(links.get(0).attr("href"));
                    vacancy.setCity(locations.get(0).text());
                    vacancy.setCompanyName(companyName.get(0).text());
                    vacancy.setSalary(salary.size() > 0 ? salary.get(0).text() : "");

                    allVacancies.add(vacancy);
                }

                page++;
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allVacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.hh.ru")
                .get();
    }
}

