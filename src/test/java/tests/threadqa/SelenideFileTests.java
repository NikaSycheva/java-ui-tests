package tests.threadqa;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.XlsReader;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$x;

public class SelenideFileTests {
    @Test
    public void readPdfFileTest() throws IOException {
        File pdf = new File("src/test/resources/test.pdf");
        PDF pdfReader = new PDF(pdf);
        String pdfText = pdfReader.text;
        Assertions.assertTrue(pdfText.contains("Тестовое задание для соискателей на должность"));
    }

    @Test
    public void readPdfBrowserTest() throws IOException {
        Selenide.open("https://www.pdf995.com/samples/");
        File file= $x("//td[@data-sort='pdf.pdf']/a").download();
        PDF pdfReader = new PDF(file);
        Assertions.assertTrue(pdfReader.text.contains("The pdf995 suite of products"));
        Assertions.assertEquals("Software 995", pdfReader.author);
        Assertions.assertEquals(pdfReader.numberOfPages, 5);
    }

    @Test
    public void readXlsxFileTest() throws Exception {
        File xlsx = new File("src/test/resources/fileXLSX.xlsx");
        XlsReader xlsReader = new XlsReader(xlsx);
        String[][] data = xlsReader.getSheetData();
        String name = data[2][1];
        Assertions.assertEquals("Philip", name);
    }

    @Test
    public void readXlsxBrowserTest() throws Exception {
        Selenide.open("https://www.wisdomaxis.com/technology/software/data/for-reports/");
        File xlsx = $x("//div[@class='grid_4']/a[1]").download();
        XlsReader xlsReader = new XlsReader(xlsx);
        String[][] data = xlsReader.getSheetData();
        Assertions.assertEquals(1008, data.length-1);
    }
}
