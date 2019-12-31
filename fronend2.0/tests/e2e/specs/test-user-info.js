
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
      // change existing user name
      .clearValue('input[type=name]')
      .setValue('input[type=name]', 'admin')
      .waitForElementVisible('.el-form-item:nth-child(1) .el-form-item__error')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '用户名已存在')
      // todo: change short username (cannot bind)
      // change long username
      .clearValue('input[type=name]')
      .setValue('input[type=name]', 'a012345678901234567890123')
      .waitForElementNotVisible('.el-form-item:nth-child(1) .el-form-item__error')
      .assert.value('input[type=name]', 'a0123456789012345678')
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
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456z*Za')
      .setValue('.el-form-item:nth-child(4) .el-input__inner', '123456z*Za')
      .click('.el-button:nth-child(1)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '密码验证错误！')
      .end()
  },

  'enter invalid password': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      // short password
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '1Aa*2')
      .waitForElementVisible('.el-form-item:nth-child(3) .el-form-item__error')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      // long password. It will automatically decrease the length
      .clearValue('.el-form-item:nth-child(3) .el-input__inner')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '1Aa*201234567890123')
      .waitForElementNotPresent('.el-form-item:nth-child(3) .el-form-item__error')
      .assert.value('.el-form-item:nth-child(3) .el-input__inner', '1Aa*201234567890')
      // valid one
      .clearValue('.el-form-item:nth-child(3) .el-input__inner')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '1Aa*20')
      .waitForElementNotPresent('.el-form-item:nth-child(3) .el-form-item__error')
      // without number
      .clearValue('.el-form-item:nth-child(3) .el-input__inner')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', 'aA*zxc')
      .waitForElementVisible('.el-form-item:nth-child(3) .el-form-item__error')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      // without lower case letter
      .clearValue('.el-form-item:nth-child(3) .el-input__inner')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', 'A*1234')
      .waitForElementVisible('.el-form-item:nth-child(3) .el-form-item__error')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      // without upper case letter
      .clearValue('.el-form-item:nth-child(3) .el-input__inner')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', 'a*1234')
      .waitForElementVisible('.el-form-item:nth-child(3) .el-form-item__error')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      // without special letter
      .clearValue('.el-form-item:nth-child(3) .el-input__inner')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', 'Aa1234')
      .waitForElementVisible('.el-form-item:nth-child(3) .el-form-item__error')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      .end()
  },

  'change password': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/info")
      .waitForElementVisible('#app')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456z*Z')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456z*Za')
      .setValue('.el-form-item:nth-child(4) .el-input__inner', '123456z*Za')
      .click('.el-button:nth-child(1)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .pause(3000)
      // set password back to original one.
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456z*Za')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456z*Z')
      .setValue('.el-form-item:nth-child(4) .el-input__inner', '123456z*Z')
      .click('.el-button:nth-child(1)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .end()
  }
}
