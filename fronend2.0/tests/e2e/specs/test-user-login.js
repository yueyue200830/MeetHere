

module.exports = {
  'load user login page': browser => {
    browser
      .url(browser.launchUrl + "user/login")
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '用户登录')
      .assert.elementPresent('.login-button-submit')
      .assert.elementPresent('.el-button--default')
      .assert.elementPresent('.el-button--text')
      .end()
  },

  'user login successfully': browser => {
    browser
      .userLogin()
      .end()
  },

  'empty enter': browser => {
    browser
      .url(browser.launchUrl + "user/login")
      .waitForElementVisible('#app')
      .click('.login-button-submit')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '用户名不可为空')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '密码不可为空')
      .end()
  },

  'wrong password': browser => {
    browser
      .url(browser.launchUrl + "user/login")
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'apple')
      .setValue('input[type=password]', '321')
      .click('.login-button-submit')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '用户名或密码错误，请重试！')
      .end()
  },

  'wrong user name': browser => {
    browser
      .url(browser.launchUrl + "user/login")
      .waitForElementVisible('#app')
      .setValue('input[type=name]', 'asdf')
      .setValue('input[type=password]', '321')
      .click('.login-button-submit')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '用户名或密码错误，请重试！')
      .end()
  },

  'test sign up button': browser => {
    browser
      .url(browser.launchUrl + "user/login")
      .waitForElementVisible('#app')
      .waitForElementVisible('.el-button--default')
      .click('.el-button--default')
      .waitForElementVisible('#app')
      .assert.urlEquals(browser.launchUrl + 'user/register')
      .end()
  },

  'test manager login button': browser => {
    browser
      .url(browser.launchUrl + "user/login")
      .waitForElementVisible('#app')
      .waitForElementVisible('.el-button--text > span')
      .click('.el-button--text > span')
      .waitForElementVisible('#app')
      .assert.urlEquals(browser.launchUrl + 'login')
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
