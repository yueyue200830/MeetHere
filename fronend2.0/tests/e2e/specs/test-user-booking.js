
module.exports = {
  'load user booking page': browser => {
    browser
      .url(browser.launchUrl + "user/booking")
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '预约')
      .assert.elementPresent('footer')
      .waitForElementVisible('.el-button:nth-child(3)')
      .end()
  },

  'search booking by name': browser => {
    browser
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('.el-select .el-input__inner')
      .useXpath()
      .waitForElementVisible('//div/div/ul/li[2]')
      .click('//div/div/ul/li[2]')
      .click('//button[contains(.,\'搜索\')]')
      .useCss()
      .pause(1000)
      .waitForElementVisible('tr:nth-child(2) > .table-td:nth-child(2) .el-button')
      .end()
  },

  'search booking by date': browser => {
    browser
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('.el-date-editor > .el-input__inner')
      .pause(1000)
      // todo select date
      .end()
  },

  'should not allowed to book': browser => {
    browser
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('tr:nth-child(14) > .table-td:nth-child(2) .el-button')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '您未登录，请登录后预约！')
      .end()
  },

  'book a revenue with empty number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('tr:nth-child(14) > .table-td:nth-child(2) .el-button')
      .waitForElementVisible('.el-dialog')
      .click('.el-button:nth-child(2)')
      .assert.containsText('.el-form-item__error', '手机号不可为空')
      .end()
  },

  'enter short phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('tr:nth-child(14) > .table-td:nth-child(2) .el-button')
      .waitForElementVisible('.el-dialog')
      .setValue('input[type=phoneNumber]', '1325')
      .click('.el-button:nth-child(2)')
      .assert.containsText('.el-form-item__error', '请输入11位手机号')
      .end()
  },

  'enter long phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('tr:nth-child(14) > .table-td:nth-child(2) .el-button')
      .waitForElementVisible('.el-dialog')
      .setValue('input[type=phoneNumber]', '1456321569879')
      .click('.el-button:nth-child(2)')
      .assert.containsText('.el-form-item__error', '请输入11位手机号')
      .end()
  },

  'enter small phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('tr:nth-child(14) > .table-td:nth-child(2) .el-button')
      .waitForElementVisible('.el-dialog')
      .setValue('input[type=phoneNumber]', '12345678912')
      .click('.el-button:nth-child(2)')
      .assert.containsText('.el-form-item__error', '请输入11位手机号')
      .end()
  },

  'enter large phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('tr:nth-child(14) > .table-td:nth-child(2) .el-button')
      .waitForElementVisible('.el-dialog')
      .setValue('input[type=phoneNumber]', '14000000000')
      .click('.el-button:nth-child(2)')
      .assert.containsText('.el-form-item__error', '请输入11位手机号')
      .end()
  },

  'book a revenue successfully': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/booking')
      .waitForElementVisible('#app')
      .click('tr:nth-child(14) > .table-td:nth-child(2) .el-button')
      .waitForElementVisible('.el-dialog')
      .setValue('input[type=phoneNumber]', '13265498564')
      .click('.el-button:nth-child(2)')
      .pause(1000)
      .assert.urlEquals(browser.launchUrl + 'user/order')
      .end()
  }
}
