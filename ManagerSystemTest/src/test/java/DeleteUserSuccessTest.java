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

@Feature("删除用户成功")
public class DeleteUserSuccessTest extends BaseTest {

  @Test
  @Description("删除的是第一个用户，所以第一个用户不能是ballballtang，否则其他测试无法登录")
  public void deleteUserSuccess() {
    this.logIn();
    WebDriverWait wait = new WebDriverWait(driver,10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".el-table__row:nth-child(1) .el-checkbox__inner")));

    driver.findElement(By.cssSelector(".el-table__row:nth-child(1) .el-checkbox__inner")).click();
    driver.findElement(By.cssSelector(".el-button--danger > span")).click();
    //wait for the backend
    wait = new WebDriverWait(driver,10);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p")));

    assertThat(driver.findElement(By.cssSelector("p")).getText(), is("删除成功"));

  }
}
