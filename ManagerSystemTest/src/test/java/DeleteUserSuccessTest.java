// Generated by Selenium IDE
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DeleteUserSuccessTest extends  BaseTest{

  @Test
  public void deleteUserSuccess() {
    driver.get("http://localhost:8080/user/main");
    driver.manage().window().setSize(new Dimension(1550, 838));
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(7)")).click();
    driver.findElement(By.cssSelector(".el-button--text > span")).click();
    driver.findElement(By.cssSelector(".el-form-item:nth-child(1) .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-form-item:nth-child(1) .el-input__inner")).sendKeys("ballballtang");
    driver.findElement(By.cssSelector(".el-input--suffix > .el-input__inner")).sendKeys("rua");
    {
      WebElement element = driver.findElement(By.cssSelector(".el-button"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".el-button")).click();
    driver.findElement(By.cssSelector(".el-table__row:nth-child(1) .el-checkbox__inner")).click();
    driver.findElement(By.cssSelector(".el-button--danger > span")).click();
    //todo Unable to locate element: p
    assertThat(driver.findElement(By.cssSelector("p")).getText(), is("删除成功"));
    driver.findElement(By.cssSelector(".el-button--small > span")).click();
    driver.findElement(By.cssSelector(".el-table__row:nth-child(1) > .el-table_1_column_2 > .cell")).click();
    assertThat(driver.findElement(By.cssSelector(".el-table__row:nth-child(1) > .el-table_1_column_2 > .cell")).getText(), is("3"));
  }
}