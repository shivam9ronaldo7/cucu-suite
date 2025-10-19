package com.cucu.test.utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class EmailGenerator {

    public static void main(String[] args) throws IOException {
        File input = new File(System.getProperty("user.dir") + "/target/cluecumber/pages/feature-summary.html");
        Document doc = Jsoup.parse(input, "UTF-8", "file:///"
                + System.getProperty("user.dir") + "/target/cluecumber/pages/feature-summary.html");
        Elements trElements = doc.select("table#feature_summary > tbody > tr");
        AtomicReference<String> html = new AtomicReference<>("");
        trElements.forEach(trElement -> {
            String text = String.format(
                    "<tr>" +
                            "<td>%s</td>" +
                            "<td style='text-align: center;'>%s</td>" +
                            "<td style='text-align: center;'>%s</td>" +
                            "<td style='text-align: center;'>%s</td>" +
                    "<tr>",
                    trElement.child(0).text(), trElement.child(2).text(),
                    trElement.child(3).text(), trElement.child(1).text());
            html.set(html + text);
        });
        System.out.println("Table:");
        html.set(String.format("" +
                "<table>" +
                    "<thead>" +
                        "<tr>" +
                            "<th>Feature</th>" +
                            "<th>Pass</th>" +
                            "<th>Fail</th>" +
                            "<th>Total</th>" +
                        "</tr>" +
                    "</thead>" +
                "</thead>", html));
        System.out.println(html);
        System.out.println("File Path: " + System.getProperty("user.dir" + "/target/feature-summary.txt"));
        FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "/target/feature-summary.txt");
        myWriter.write(html.get());
        myWriter.close();
    }

}
