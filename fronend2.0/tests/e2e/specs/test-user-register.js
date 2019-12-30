
module.exports = {
  // 'load register page': browser => {
  //   browser
  //     .url(browser.launchUrl + 'user/register')
  //     .waitForElementVisible('#app')
  //     .assert.containsText('.user-title', '用户注册')
  //     .assert.elementPresent('footer')
  //     .end()
  // },

  'register': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'use')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456')
      .click('.el-button--primary')
      .pause(1000)
      .end()
  }
}
