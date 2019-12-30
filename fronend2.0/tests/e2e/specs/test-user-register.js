
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

  'register': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'test-user')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456')
      .click('.el-button--primary')
      .assert.urlEquals(browser.launchUrl + 'user/login')
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
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '用户名已存在')
      .end()
  },

  'different password': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'test-user')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '654321')
      .click('.el-button--primary')
      .assert.containsText('.el-form-item:nth-child(3) .el-form-item__error', '两次输入密码不一致!')
      .end()
  },

  // todo: test invalid user name and password

  'test reset button': browser => {
    browser
      .url(browser.launchUrl + 'user/register')
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'test-user')
      .setValue('.el-form-item:nth-child(2) .el-input__inner', '123456')
      .setValue('.el-form-item:nth-child(3) .el-input__inner', '123456')
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

