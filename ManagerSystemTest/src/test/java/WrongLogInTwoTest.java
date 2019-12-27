// Generated by Selenium IDE
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public class WrongLogInTwoTest extends BaseTest{

  @Test
  public void wrongLogInTwo() {
    driver.get("http://localhost:8080/user/main");
    driver.manage().window().setSize(new Dimension(864, 755));
    driver.findElement(By.cssSelector(".el-menu-item:nth-child(7)")).click();
    driver.findElement(By.cssSelector(".el-button--text > span")).click();
    driver.findElement(By.cssSelector(".el-button > span")).click();
    assertThat(driver.findElement(By.cssSelector(".el-message__content")).getText(), is("用户名和密码不能为空！"));
  }
}
