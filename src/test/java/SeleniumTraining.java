import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SeleniumTraining {

    private WebDriver driver;
    private String baseURL = "http://the-internet.herokuapp.com/";

    @Before
    public void setUp(){
        
        System.setProperty("webdriver.chrome.driver","C:\\Users\\lazarluk\\chromedriver.exe");
        
        driver = new ChromeDriver();
        driver.get(baseURL);
        driver.manage().window().maximize();
        
    }

    @After
    public void tearDown(){
        //driver.close();
        //driver.quit();

    }
    
    @Test
    @Ignore
    public void testAddRemoveElements(){

        WebDriverWait wait = new WebDriverWait(driver,5);

        driver.navigate().to(baseURL+"add_remove_elements/");

        //Czekamy, az button :Add Element: bedzie klikalny
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.tagName("button")));

        //Stworzenie pięciu nowych przycisków
        for (int i=0;i<5;i++){
        addButton.click();
        }

        //Szukamy wszystkich stworzonych buttonow
        List<WebElement> groupOfNewButtons = driver.findElements(By.className("added-manually"));

        //Potwierdzamy liczbe stworzonych buttonow
        assertEquals(5,groupOfNewButtons.size());

        for (WebElement Button : groupOfNewButtons){
            Button.click();
        }

        List<WebElement> EmptyGroupOfNewButtons = driver.findElements(By.className("added-manually"));

        assertEquals(0,EmptyGroupOfNewButtons.size());

    }

    @Test
    @Ignore
    public void testIfImageIsBroken() throws IOException {
        //Dodajemy implicitlyWait, każde zapytanie o element bedzie czekało max 5 sekund
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.navigate().to(baseURL+"broken_images");

        //Wyszukujemy wszystkie obrazki na stronie
        List<WebElement> listOfAllImages = driver.findElements(By.tagName("img"));

        //przegladamy liste znalezionych obrazkow pod kątem takiego, ktory ma szerokosc 0 (nie ma go)
        for(WebElement image : listOfAllImages){
            //Jesli znajdzie brak obrazu najpierw zrobi zrzut ekranu calej strony a pozniej poleci assert
            if (image.getAttribute("naturalWidth").equals("0")){
                File scrFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile,new File("C:\\Users\\lazarluk\\IdeaProjects\\NewTest\\target\\screenshots\\broken_images.png"));
            }
            assertNotEquals("0",image.getAttribute("naturalWidth"));
        }

    }

    @Test
    @Ignore
    public void testTable(){

        driver.navigate().to(baseURL+"challenging_dom");

        List<WebElement> ActionColumn = driver.findElements(By.xpath("//td[7]"));

        for (WebElement cell : ActionColumn){
            assertEquals("edit delete",cell.getText());
        }

    }

    @Test
    @Ignore
    public void testHotSpot(){

        driver.navigate().to(baseURL+"context_menu");

        WebElement hotSpot = driver.findElement(By.id("hot-spot"));

        Actions builder = new Actions(driver);

        Action checkContextMenu = builder
                .contextClick(hotSpot)
                .build();

        checkContextMenu.perform();

        assertEquals("You selected a context menu",driver.switchTo().alert().getText());

        driver.switchTo().alert().accept();

    }

    //@Test
    //public void
}
