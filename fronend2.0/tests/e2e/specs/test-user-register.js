
module.exports = {
  'load register page': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '用户注册')
      .assert.elementPresent('.el-button--primary')
      .assert.elementPresent('.el-button--default')
      .assert.elementPresent('.el-button--text')
      .end()
  },

  'empty input': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '请输入用户名')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '请输入密码')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '请再次输入密码')
      .end()
  },

  'exist user name': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'apple')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '用户名已存在')
      .end()
  },

  'different password': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'test-user')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456z*Z')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '654321')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '两次输入密码不一致!')
      .end()
  },

  'test invalid user name': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'a')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '用户名不合法，请输入4-20个字符')
      .clearValue('input[type=name]')
      .setValue('input[type=name]', '01234567890123456789012')
      .click('.el-button--primary')
      .waitForElementNotPresent('.el-form-item:nth-child(1) .el-form-item__error')
      .assert.value('input[type=name]', '01234567890123456789')
      .end()
  },

  'test invalid password': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      // short password
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '1Aa*2')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      // long password. It will automatically decrease the length
      .clearValue('.el-form-item:nth-child(2) .el-input__inner')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '1Aa*201234567890123')
      .click('.el-button--primary')
      .waitForElementNotPresent('.el-form-item:nth-child(2) .el-form-item__error')
      .assert.value('.el-form-item:nth-child(2) .el-input__inner', '1Aa*201234567890')
      // valid one
      .clearValue('.el-form-item:nth-child(2) .el-input__inner')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '1Aa*20')
      .click('.el-button--primary')
      .waitForElementNotPresent('.el-form-item:nth-child(2) .el-form-item__error')
      // without number
      .clearValue('.el-form-item:nth-child(2) .el-input__inner')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', 'aA*zxc')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      // without lower case letter
      .clearValue('.el-form-item:nth-child(2) .el-input__inner')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', 'A*1234')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      // without upper case letter
      .clearValue('.el-form-item:nth-child(2) .el-input__inner')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', 'a*1234')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      // without special letter
      .clearValue('.el-form-item:nth-child(2) .el-input__inner')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', 'Aa1234')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '输入6-16位密码，需包含大小写字母，数字和特殊字符')
      .end()
  },

  'register': browser => {
    let t = new Date()
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'testUser' + t.getTime()%1000000)
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456aA-')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456aA-')
      .click('.el-button--primary')
      .assert.urlEquals(browser.launchUrl + 'user/login')
      .end()
  },

  'test reset button': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'test-user')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456z*Z')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456z*Z')
      .click('.el-button--default')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '请输入用户名')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '请输入密码')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '请再次输入密码')
      .end()
  },

  'test login button': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .click('.el-button--text > span')
      .assert.urlEquals(browser.launchUrl + 'user/login')
      .end()
  },

  'test headline back to main page': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .click('.head-title')
      .assert.urlEquals(browser.launchUrl + 'user/main')
      .end()
  }
}

