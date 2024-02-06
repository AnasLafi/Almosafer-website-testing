package com.qa.test;

import com.qa.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.lang.model.element.Element;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class HomePageTest extends BaseClass {
    private WebDriver driver;

    @BeforeTest
    public void testSetup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.get(URL);
        WebElement selectYourCountryPopup = driver.findElement(By.cssSelector(".sc-iBmynh.izXFRL"));
        if (selectYourCountryPopup.isDisplayed()) {
            WebElement sarCurrency = driver.findElement(By.className("cta__saudi"));
            sarCurrency.click();
        }


    }

    @Test(priority = 1)
    public void checkIfDefaultCurrencyIsSAR() {
        String actualCurrency = driver.findElement(By.cssSelector(".sc-dRFtgE.fPnvOO")).getText();
        String expectedCurrency = "SAR";
        Assert.assertEquals(actualCurrency,expectedCurrency);
    }

    @Test(priority = 2)
    public void CheckTheDefaultLanguageIsEnglish(){
        String actualLanguage = driver.findElement(By.tagName("html")).getAttribute("lang").toLowerCase();
        String expectedLanguage="en";
        Assert.assertEquals(actualLanguage,expectedLanguage);
    }
    @Test(priority = 3)
    public void checkIfContactNumbersAreCorrect(){
        String actualContactNumber = driver.findElement(By.tagName("strong")).getText();
        String expectedContactNumber = "+966554400000";
        Assert.assertEquals(actualContactNumber,expectedContactNumber);

    }

    @Test(priority = 4)
    public void verifyIfQitafLogoIsDisplayedInFooter(){
        WebElement footer=driver.findElement(By.tagName("footer"));
        WebElement qitafLogo = footer.findElement(By.cssSelector(".sc-bdVaJa.bxRSiR.sc-ekulBa.eYboXF"));

        boolean actualResult=qitafLogo.isDisplayed();
        Assert.assertEquals(actualResult,true);

    }
    @Test(priority = 5)
    public void checkIfHotelSearchTabIsNotSelectedByDefault(){
        String actual=driver.findElement(By.id("uncontrolled-tab-example-tab-hotels")).getAttribute("aria-selected");
        String expected="false";

        Assert.assertEquals(actual,expected);
    }
    @Test(priority = 6)
    public void checkIfFlightDepartureDateByDefaultEqualTodayPlusOneDay(){
        LocalDate today=LocalDate.now();
        int expectedDepartureDay = today.plusDays(1).getDayOfMonth();
        WebElement departureDateElement=driver.findElement(By.cssSelector(".sc-iHhHRJ.sc-kqlzXE.blwiEW"));
        int actualDepartureDay = Integer.parseInt(departureDateElement.findElement(By.cssSelector(".sc-cPuPxo.LiroG")).getText());
        Assert.assertEquals(actualDepartureDay,expectedDepartureDay);


    }
    @Test(priority = 7)
    public void checkIfFlightReturnDateByDefaultEqualTodayPlusTwoDays(){
        LocalDate today=LocalDate.now();
        int expectedReturnDay = today.plusDays(2).getDayOfMonth();
        WebElement returnDayElement=driver.findElement(By.cssSelector(".sc-iHhHRJ.sc-OxbzP.edzUwL"));
        int actualReturnDay = Integer.parseInt(returnDayElement.findElement(By.cssSelector(".sc-cPuPxo.LiroG")).getText());
    }
    @Test(priority = 8)
    public void randomMethodToChangeTheLanguage(){


        int randomIndexForTheWebsiteLanguage = rand.nextInt(URL_EN_AR_LANG.length);

        driver.get(URL_EN_AR_LANG[randomIndexForTheWebsiteLanguage]);

        if (driver.getCurrentUrl().contains("ar")) {
            String ExpectedLang = "ar";
            String ActualLang = driver.findElement(By.tagName("html")).getAttribute("lang");

            Assert.assertEquals(ActualLang, ExpectedLang);
        } else {
            String ExpectedLang = "en";

            String ActualLang = driver.findElement(By.tagName("html")).getAttribute("lang");

            Assert.assertEquals(ActualLang, ExpectedLang);

        }

    }
    @Test(priority = 9)
    public void switchToHotelSearchTab(){
        WebElement hotelTab=driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
        hotelTab.click();
        WebElement locationField=driver.findElement(By.cssSelector(".phbroq-2.cerrLM.AutoComplete__Input"));
        hotelTab.click();
        String[]citiesInEnglish={"Dubai","Jaddah","Riyadh"};
        String[]citiesInArabic={"دبي","جدة","الرياض"};
        String currentWebsiteLanguage = driver.findElement(By.tagName("html")).getAttribute("lang").toLowerCase();

        if (currentWebsiteLanguage.equals("en")){
            locationField.sendKeys(citiesInEnglish[rand.nextInt(citiesInEnglish.length)]);
        }
        else {
            locationField.sendKeys(citiesInArabic[rand.nextInt(citiesInArabic.length)]);
        }
        WebElement listOfLocations=driver.findElement(By.className("UzzIN"));
        if (listOfLocations.isDisplayed()){
        WebElement firstLocationInList = listOfLocations.findElements(By.tagName("li")).get(1);
        firstLocationInList.click();

        }
    }
    @Test(priority = 10)
    public void randomlySelectOneOfTheFirstTwoReservationOptions(){
        Select selector=new Select(driver.findElement(By.cssSelector(".tln3e3-1.eFsRGb")));
//        selector.getOptions().forEach(webElement -> System.out.println(webElement.getText()));
        List<WebElement> options = selector.getOptions();
        int randomOption = rand.nextInt(options.size() - 1);
        selector.selectByIndex(randomOption);

        driver.findElement(By.className("js-HotelSearchBox__SearchButton")).click();

    }
    @Test(priority = 11)
    public void verifyIfSearchHotelsResultPageFullyLoaded() throws InterruptedException {
        Thread.sleep(10_000);
            String searchResultMessage =driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/section/span")).getText();
            if (driver.getCurrentUrl().contains("ar")){
                boolean actualResult=searchResultMessage.contains("وجدنا");
                Assert.assertEquals(actualResult,true);
            }
            else {
                boolean actualResult = searchResultMessage.contains("found");
                Assert.assertEquals(actualResult, true);

            }

    }
    @Test(priority = 12)

    public void sortTheItemsBasedOnThePriceFromLowerToHigher() throws InterruptedException {
        Thread.sleep(5000);

        WebElement lowestPriceButton = driver.findElement(By.cssSelector(".sc-csuNZv.jyUtIz"));
        lowestPriceButton.click();
        WebElement hotelCardSection = driver.findElement(By.cssSelector(".sc-htpNat.KtFsv.col-9"));
        List<WebElement> priceValueForAllItems = hotelCardSection.findElements(By.className("Price__Value"));
        int theLowestPrice = Integer.parseInt(priceValueForAllItems.get(0).getText());
        int theHighestPrice = Integer.parseInt(priceValueForAllItems.get(priceValueForAllItems.size() - 1).getText());
        System.out.println("lowest " + theLowestPrice);
        System.out.println("highest " + theHighestPrice);
        Assert.assertEquals(theHighestPrice>theLowestPrice,true);

    }
    @AfterTest
    public  void afterTest(){
        if (driver!=null){
            driver.quit();
        }
    }
}
