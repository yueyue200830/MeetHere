// Generated by Selenium IDE
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("查看新闻图片成功")
public class ViewPictureSuccessTest extends BaseTest{
  @Test
  @Description("测试的是第八条新闻，该新闻必须有图片。弹出弹窗显示图片。")
  public void viewPictureSuccess() {
    this.logIn();
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(5)")).click();
    WebDriverWait wait = new WebDriverWait(driver,10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".el-table__row:nth-child(8) .el-button:nth-child(2) > span")));
    driver.findElement(By.cssSelector(".el-table__row:nth-child(8) .el-button:nth-child(2) > span")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".el-table__row:nth-child(8) .el-button:nth-child(2) > span"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    js.executeScript("window.scrollTo(0,0)");
    {
      wait = new WebDriverWait(driver, 2);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div > .el-dialog__wrapper:nth-child(3) .el-dialog__title")));
    }
    //todo 第八条新闻必须有图片
    List<WebElement> elements = driver.findElements(By.cssSelector(".el-message"));
    if(elements.size() > 0){
      System.out.println("测试的新闻是第八条，所以第八条新闻要有图片");
    }else{
      assertThat(driver.findElement(By.cssSelector("div > .el-dialog__wrapper:nth-child(3) .el-dialog__title")).getText(), is("新闻图片"));
      driver.findElement(By.cssSelector("div > .el-dialog__wrapper:nth-child(3) .el-dialog__close")).click();
    }
  }
}
