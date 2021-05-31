import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Run {
    WebDriver driver;
    ArrayList<String> urls = new ArrayList<String>();


    @Test
    public void execute() {
        int count = 0;
        String autoLink = "https://autogidas.lt/skelbimai/automobiliai/?f_1[0]=&f_model_14[0]=&f_215=10000&f_50=kaina_asc&page=";
        String motoLink = "https://autogidas.lt/skelbimai/motociklai/?f_1[0]=&f_model_14[0]=&f_215=15000price&f_50=kaina_asc&page=";
        while (true) {
            count++;
            System.out.println("pages read:" + count);
            driver.get(motoLink + count);


            int howManyAdds = driver.findElement(By.cssSelector("#content > div:nth-child(1) > div > main")).findElements(By.className("list-item")).size();
//iesko maziau info
            if (count > 1)
//            iesko visos info
//            if (howManyAdds < 1)
            {
                break;
            }
            getUrls();
        }
        for (int i = 0; i < urls.size(); i++) {
            driver.get(urls.get(i));
            System.out.println(urls.get(i));
            getData();
        }
    }

    //    nurenka ir atspausdina duomenis
    public void getData() {
        String name = driver.findElement(By.cssSelector("#title > div > div.left > div.title-container > h1")).getText();
        String price = driver.findElement(By.cssSelector("#content > main > article > div.container.last > div > div.content-left > div.params-block-price > div > span.data-value")).getText();
        String number = "null";
        try {
            number = driver.findElement(By.cssSelector("#content > main > article > div.container.last > div > div.content-left > div.contacts-wrapper.private > div > div.seller-ico.seller-phones.btn-action")).getText();
        } catch (Exception e) {
            try {
                number = driver.findElement(By.cssSelector("#content > main > article > div.container.last > div > div.content-left > div.contacts-wrapper.partner > div.seller-info > div.seller-ico.seller-phones.btn-action")).getText();
            } catch (Exception ex) {
            }
        }

        System.out.println("Vehicle: " + name + "price: " + price + " phone number: " + number);
    }

    //nepasileis, nes neturi anotacijos
    public void getUrls() {
        //        paspaudzia sutinku mygtuka
//        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
//        susiranda main bloka, kur yra elementas
        WebElement mainList = driver.findElement(By.cssSelector("#content > div:nth-child(1) > div > main"));
//        tame bloke susiranda visus elentus su a href
        List<WebElement> list = mainList.findElements(By.className("list-item"));
        for (int i = 0; i < list.size(); i++) {
            //sudedam visus rastum href i masyva
            urls.add(list.get(i).findElement(By.tagName("a")).getAttribute("href"));
        }
//        patikrinimui, kokia info surado
//        System.out.println(urls.size());
//        for(int i = 0; i<urls.size();i++){
//            System.out.println(urls.get(i));
//        }
    }

    @BeforeClass
    public void beforeClass() {

        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver90.exe");
        driver = new ChromeDriver();
        driver.get("https://autogidas.lt/skelbimai/automobiliai/?f_215=10000");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void afterClass() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
        driver.quit();
    }
}
