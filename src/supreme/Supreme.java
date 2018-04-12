
package supreme;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class Supreme {
    static String size, name, email, tel, address, addy2, zip, city, state, typeOfItem, ccNumber, ccMonth, ccYear, cvv;
    static String keyword, color;
    public static void main(String[] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation")); 
        
        //For the line below, change to your own path for ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:/Users/jinth/Desktop/flights/chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);
        
        typeOfItem = "accessories";
        //Opens to Supreme's website.
        openLink(typeOfItem, driver);
        int time = Integer.parseInt((new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).substring(9));
        while(time < 105957){
            //This will cause program to wait until 10:59:57 AM
            TimeUnit.SECONDS.sleep(1);
            time = Integer.parseInt((new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).substring(9));
        }
        //Now refresh until i can find the keyword
        boolean itemFound = false;
        int itemLocation = 0;
        while(itemFound == false){
            List<WebElement> items = driver.findElements(By.tagName("article"));
            for(int i = 0; i < items.size(); i++){
                WebElement item = driver.findElement(By.xpath("//*[@id=\"container\"]/article[" + i + "]/div/h1/a"));
                //need to get access to a US site to get the right xpath.
                String itemDesc = item.getAttribute("innerHTML");
                //Have item description. Need to compare it to keyword.
                if(itemDesc.contains(keyword)){
                    //Found item
                    itemFound = true;
                    itemLocation = i;
                    break;
                }     
            }
            driver.navigate().refresh();
        }
        //Have the location of the item.
        while(true){
            WebElement item = driver.findElement(By.xpath("//*[@id=\"container\"]/article[" + itemLocation + "]/div/p/a"));
            String itemColor = item.getAttribute("innerHTML").toLowerCase();
            if(itemColor.contains(color.toLowerCase())){
                //found the element that the browser needs to click
                item.click();
                break;
            }
            //not the element. need to progress onto the next element
            itemLocation++;      
        }
        //By now, we should be at the actual item page.
        //Need to add the proper size of the item to cart
        addSizeToCart(size, driver);
        //Size should have been added to cart by now. Progressing onto checkout page
        driver.navigate().to("https://www.supremenewyork.com/checkout");
        fillInfo(driver);
        
        
        
    }
    public static void fillInfo(WebDriver driver){
        WebElement nameBox = driver.findElement(By.id("order_billing_name"));
        //CC Number and telephone needs to be copy and pasted into their boxes otherwise the digit order gets messed up
        WebElement emailBox = driver.findElement(By.id("order_email"));
        WebElement ccNumberBox = driver.findElement(By.id("nnaerb"));
        WebElement addressBox = driver.findElement(By.id("bo"));
        addressBox.sendKeys(ccNumber);
        addressBox.sendKeys(Keys.chord(Keys.CONTROL,"a"));
        addressBox.sendKeys(Keys.chord(Keys.CONTROL,"c"));
        ccNumberBox.sendKeys(Keys.chord(Keys.CONTROL,"v"));
        
        
        emailBox.sendKeys(tel);
        emailBox.sendKeys(Keys.chord(Keys.CONTROL,"a"));
        emailBox.sendKeys(Keys.chord(Keys.CONTROL,"c"));
        emailBox.sendKeys(Keys.TAB, Keys.chord(Keys.CONTROL,"v"));

        nameBox.sendKeys(name, Keys.TAB, email, Keys.TAB, Keys.TAB, address, Keys.TAB, addy2, Keys.TAB, addy2, Keys.TAB, zip, Keys.TAB);
        ccNumberBox.sendKeys(ccNumber);
        Select ccMonthDropdown = new Select(driver.findElement(By.id("credit_card_month")));
        ccMonthDropdown.selectByVisibleText(ccMonth);
        Select ccYearDropdown = new Select(driver.findElement(By.id("credit_card_year")));
        ccYearDropdown.selectByVisibleText(ccYear);
        WebElement cvvBox = driver.findElement(By.id("orcer"));
        cvvBox.sendKeys(cvv, Keys.TAB, Keys.SPACE);
        cvvBox.sendKeys(Keys.ENTER);
        
    }
    public static void addSizeToCart(String size, WebDriver driver){
        Select dropdown = new Select(driver.findElement(By.id("size")));
        dropdown.selectByVisibleText(size);
        driver.findElement(By.name("commit")).click();
        
    }
    public static void openLink(String typeOfItem, WebDriver driver){
        switch(typeOfItem){
            case "jackets":   
                driver.navigate().to("http://www.supremenewyork.com/shop/all/jackets");
                break;
            case "t-shirts":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/t-shirts");
                break;
            case "tops/sweaters":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/tops_sweaters");
                break;
            case "sweatshirts":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/sweatshirts");
                break;
            case "pants":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/pants");
                break;
            case "hats":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/hats");
                break;
            case "bags":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/bags");
                break;
            case "accessories":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/accessories");
                break;
            case "shoes":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/shoes");
                break;
            case "skate":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/skate");
                break;
            case "shorts":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/shorts");
                break;
            case "shirts":
                driver.navigate().to("http://www.supremenewyork.com/shop/all/shirts");
            default: print("Not a valid option.");
        }

    }
    public static void print(String string){
        System.out.println(string);
    }
    
}
