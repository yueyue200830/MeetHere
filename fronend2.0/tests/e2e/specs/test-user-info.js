
module.exports = {
  'load user info page': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '个人信息')
      .assert.value('input[type=name]', 'apple')
      .assert.elementPresent('footer')
      .end()
  },

  'should jump to login page': browser => {
    browser
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .assert.urlEquals(browser.launchUrl + 'user/login')
      .end()
  },

  'change avatar': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .click('.avatar-upload')
      .waitForElementVisible('.vicp-wrap')
      // .click('.vicp-hint')
      // .pause(1000)
      // .setValue('input:nth-child(4)', 'C:\\zjy\\工藤新一.jpeg')
      // // todo: solve choose the file path.
      // .pause(2000)
      .end()
  },

  'change user name': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .clearValue('input[type=name]')
      .setValue('input[type=name]', 'apple1')
      .click('.el-button--primary:nth-child(2)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .assert.containsText('.el-header', 'apple1')
      .waitForElementNotVisible('.el-message')
      // change user name back
      .clearValue('input[type=name]')
      .setValue('input[type=name]', 'apple')
      .click('.el-button--primary:nth-child(2)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .assert.containsText('.el-header', 'apple')
      .end()
  },

  'input invalid username': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .clearValue('input[type=name]')
      .setValue('input[type=name]', 'admin')
      .click('.el-button--primary:nth-child(2)')
      .waitForElementVisible('.el-form-item:nth-child(1) .el-form-item__error')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '用户名已存在')
      .end()
  },

  'press button without changing user name': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .click('.el-button--primary:nth-child(2)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '新用户名与原用户名相同，请更换用户名再提交！')
      .end()
  },
  // todo: change invalid user name

  'change password': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456')
      .setValue('.el-form-item:nth-child(4) .el-input__inner', '123456')
      .click('.el-button:nth-child(1)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .pause(3000)
      // set password back to original one.
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123')
      .setValue('.el-form-item:nth-child(4) .el-input__inner', '123')
      .click('.el-button:nth-child(1)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .end()
  },

  'empty password enter': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .click('.el-button:nth-child(1)')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '请输入密码')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '请输入密码')
      .assert.containsText('.el-form-item:nth-child(4) .el-form-item__error', '请再次输入密码')
      .end()
  },

  'different new password': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .click('.el-button:nth-child(1)')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123')
      .setValue('.el-form-item:nth-child(4) .el-input__inner', '321')
      .click('.el-form-item:nth-child(3) .el-input__inner')
      .assert.containsText('.el-form-item:nth-child(4) .el-form-item__error', '两次输入密码不一致!')
      .end()
  },

  'wrong password': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .click('.el-button:nth-child(1)')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '321')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456')
      .setValue('.el-form-item:nth-child(4) .el-input__inner', '123456')
      .click('.el-button:nth-child(1)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '密码验证错误！')
      .end()
  }
  // todo: add invalid password
}
