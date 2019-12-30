
module.exports = {
  'load user news page': browser => {
    browser
      .url(browser.launchUrl + 'user/news')
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '新闻')
      .assert.elementPresent('footer')
      .assert.elementPresent('.el-collapse')
      .useXpath()
      .click('//div[2]/div/div/div/div')
      .waitForElementVisible('//div[2]/div/div[2]')
      .pause(1000)
      .click('//div[2]/div[2]/div/div/div')
      .waitForElementVisible('//div[2]/div[2]/div[2]')
      .useCss()
      .end()
  }
}
