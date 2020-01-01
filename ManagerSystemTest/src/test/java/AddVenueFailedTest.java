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

@Feature("添加场馆失败")
public class AddVenueFailedTest extends BaseTest{

  @Test
  @Description("输入内容为空，弹出信息：输入格式不正确！")
  public void addVenueFailedOne() {
    //输入为空
    this.logIn();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".table-title-button span")).click();
    driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    assertThat(driver.findElement(By.cssSelector(".el-message")).getText(), is("输入格式不正确！"));
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).click();
    driver.findElement(By.cssSelector("div > .el-dialog__wrapper:nth-child(3) .el-dialog__close")).click();
  }

  @Test
  @Description("场馆价格输入非数字，弹出信息：输入格式不正确！")
  public void addVenueFailedTwo() {
    //场馆价格输入非数字
    this.logIn();
    driver.findElement(By.cssSelector(".is-active")).click();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".table-title-button span")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).sendKeys("venue");
    driver.findElement(By.cssSelector(".el-col:nth-child(2) .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-col:nth-child(2) .el-input__inner")).sendKeys("adasd");
    driver.findElement(By.cssSelector(".el-row:nth-child(2) .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(2) .el-input__inner")).sendKeys("2");
    driver.findElement(By.cssSelector(".el-textarea__inner")).click();
    driver.findElement(By.cssSelector(".el-textarea__inner")).sendKeys("asfas");
    driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary > span")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary > span"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    assertThat(driver.findElement(By.cssSelector(".el-message")).getText(), is("输入格式不正确！"));
    driver.findElement(By.cssSelector("div > .el-dialog__wrapper:nth-child(3) .el-dialog__close")).click();
  }

  @Test
  @Description("场馆房间数量输入非数字，弹出信息：输入格式不正确！")
  public void addVenueFailedThree() {
    this.logIn();
    driver.findElement(By.cssSelector(".is-active")).click();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".table-title-button span")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).sendKeys("asd");
    driver.findElement(By.cssSelector(".el-col:nth-child(2) .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-col:nth-child(2) .el-input__inner")).sendKeys("120");
    driver.findElement(By.cssSelector(".el-row:nth-child(2) .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(2) .el-input__inner")).sendKeys("asf");
    driver.findElement(By.cssSelector(".el-textarea__inner")).click();
    driver.findElement(By.cssSelector(".el-textarea__inner")).sendKeys("asd");
    driver.findElement(By.cssSelector(".el-row:nth-child(3) > .el-col")).click();
    driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    assertThat(driver.findElement(By.cssSelector(".el-message")).getText(), is("输入格式不正确！"));
    driver.findElement(By.cssSelector("div > .el-dialog__wrapper:nth-child(3) .el-dialog__close")).click();
  }

  @Test
  @Description("输入字符串过长，弹出信息：添加失败")
  public void addVenueFailedFour() {
    this.logIn();
    String title = BaseTest.generateString(60);
    driver.findElement(By.cssSelector(".is-active")).click();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(4)")).click();
    driver.findElement(By.cssSelector(".table-title-button span")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(1) > .el-col:nth-child(1) > .el-form-item .el-input__inner")).sendKeys(title);
    driver.findElement(By.cssSelector(".el-col:nth-child(2) .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-col:nth-child(2) .el-input__inner")).sendKeys("120");
    driver.findElement(By.cssSelector(".el-row:nth-child(2) .el-input__inner")).click();
    driver.findElement(By.cssSelector(".el-row:nth-child(2) .el-input__inner")).sendKeys("2");
    driver.findElement(By.cssSelector(".el-textarea__inner")).click();
    driver.findElement(By.cssSelector(".el-textarea__inner")).sendKeys("asd");
    driver.findElement(By.cssSelector(".el-row:nth-child(3) > .el-col")).click();
    driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    assertThat(driver.findElement(By.cssSelector(".el-message")).getText(), is("添加失败"));
  }
}
