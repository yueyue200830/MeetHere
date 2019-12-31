// Generated by Selenium IDE

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("改变表格页码")
public class ChangeTablePageTest extends BaseTest{

  @Test
  @Step("下一页，上一页，输入页码跳转")
  public void changeTablePage() {
    this.logIn();
    assertThat(driver.findElement(By.cssSelector(".active")).getText(), is("1"));
    driver.findElement(By.cssSelector(".number:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".active")).getText(), is("2"));
    driver.findElement(By.cssSelector(".el-icon-arrow-left")).click();
    assertThat(driver.findElement(By.cssSelector(".active")).getText(), is("1"));
    driver.findElement(By.cssSelector(".el-icon-arrow-right")).click();
    assertThat(driver.findElement(By.cssSelector(".active")).getText(), is("2"));
    {
      WebElement element = driver.findElement(By.cssSelector(".el-pagination__editor > .el-input__inner"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).clickAndHold().perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".el-pagination__editor > .el-input__inner"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.cssSelector(".el-pagination__editor > .el-input__inner"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).release().perform();
    }

  }
  @Test
  public void jumpTablePage(){
    this.logIn();
    driver.findElement(By.cssSelector(".el-pagination__editor > .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-pagination__editor > .el-input__inner")).sendKeys("2");
    driver.findElement(By.cssSelector(".el-pagination__editor > .el-input__inner")).sendKeys(Keys.ENTER);
    assertThat(driver.findElement(By.cssSelector(".active")).getText(), is("2"));
  }

}
