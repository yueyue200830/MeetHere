// Generated by Selenium IDE
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("添加场馆成功")
public class AddVenueSuccessTest extends BaseTest{

  @Test
  @Description("添加场馆成功")
  public void addVenueSuccess() {
    this.logIn();
    driver.findElement(By.cssSelector(".is-active > span")).click();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(4) > span")).click();
    driver.findElement(By.cssSelector(".table-title-button span")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".table-title-button span"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).sendKeys("TestAdd");
    driver.findElement(By.cssSelector(".el-col:nth-child(2) .el-input__inner")).sendKeys("120");
    driver.findElement(By.cssSelector(".el-row:nth-child(2) .el-input__inner")).sendKeys("3");
    driver.findElement(By.cssSelector(".el-textarea__inner")).sendKeys("This is a test");
    driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary > span")).click();
    WebDriverWait wait = new WebDriverWait(driver,10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p")));
    assertThat(driver.findElement(By.cssSelector("p")).getText(), is("添加成功"));
  }
}
