import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;


public class BaseTest {
    static WebDriver driver;
    static Map<String, Object> vars;
    static JavascriptExecutor js;
    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver","C:\\Program Files\\Mozilla Firefox\\geckodriver.exe");
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    public void logIn(){
        driver.get("http://localhost:8080/user/main");
        driver.manage().window().setSize(new Dimension(864, 752));
        driver.findElement(By.cssSelector(".el-menu-item:nth-child(7)")).click();
        driver.findElement(By.cssSelector(".el-button--text > span")).click();
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) .el-input__inner")).click();
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) .el-input__inner")).sendKeys("ballballtang");
        driver.findElement(By.cssSelector(".el-input--suffix > .el-input__inner")).click();
        driver.findElement(By.cssSelector(".el-input--suffix > .el-input__inner")).sendKeys("rua");
        driver.findElement(By.cssSelector(".el-icon-view")).click();
        driver.findElement(By.cssSelector(".el-button")).click();
        driver.findElement(By.cssSelector(".el-main")).click();
        driver.findElement(By.cssSelector(".el-icon-arrow-down")).click();
        driver.findElement(By.cssSelector(".el-icon-arrow-down")).click();
    }
}
