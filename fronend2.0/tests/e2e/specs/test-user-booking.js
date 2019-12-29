
module.exports = {
  'load user booking page': browser => {
    browser
      .url(browser.launchUrl + "user/booking")
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '预约')
      .assert.containsText('.table-th:nth-child(5)', '4号场')
      .assert.containsText('tr:nth-child(2) > .table-td:nth-child(5) .el-button', '50')
      .assert.elementPresent('footer')
      .end()
  },

  'search booking by name': browser => {
    browser
      .url(browser.launchUrl + "user/booking")
      .waitForElementVisible('#app')
      .click('.el-select .el-input__inner')
      .pause(1000)
      .useXpath()
      .click('//li[contains(.,\'排球馆\')]')
      .click('//button[contains(.,\'搜索\')]')
      .pause(1000)
      .useCss()
      .assert.containsText('.table-th:nth-child(6)', '5号场')
      .assert.containsText('tr:nth-child(2) > .table-td:nth-child(6) .el-button', '40')
      .end()
  },

  'search booking by date': browser => {
    browser
      .url(browser.launchUrl + "user/booking")
      .waitForElementVisible('#app')
      .click('.el-date-editor > .el-input__inner')
      .pause(1000)
      // todo select date
      .end()
  },

  'should not allowed to book': browser => {
    browser
      .url(browser.launchUrl + "user/booking")
      .waitForElementVisible('#app')
      .click('tr:nth-child(2) > .table-td:nth-child(5) .el-button')
      .pause(1000)
      .assert.containsText('.el-message', '您未登录，请登录后预约！')
      .end()
  },

  'book a revenue with empty number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/booking")
      .waitForElementVisible('#app')
      .click('tr:nth-child(2) > .table-td:nth-child(5) .el-button')
      .pause(1000)
      .click('.el-button:nth-child(2)')
      .assert.containsText('.el-form-item__error', '手机号不可为空')
      .end()
  },

  'book a revenue successfully': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + "user/booking")
      .waitForElementVisible('#app')
      .click('tr:nth-child(2) > .table-td:nth-child(5) .el-button')
      .pause(1000)
      .setValue('input[type=phoneNumber]', '12345678912')
      .click('.el-button:nth-child(2)')
      .pause(1000)
      .assert.urlEquals(browser.launchUrl + 'user/order')
      .end()
  }

  // todo: test different phone number
}
