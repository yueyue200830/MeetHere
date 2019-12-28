
module.exports = {
  'test user navigator without logging in': browser => {
    browser
      .init(browser.launchUrl + "user/main")
      .assert.urlEquals(browser.launchUrl + 'user/main')
      .click('.el-menu-item:nth-child(2)')
      .assert.urlEquals(browser.launchUrl + 'user/revenue')
      .click('.el-menu-item:nth-child(3)')
      .assert.urlEquals(browser.launchUrl + 'user/booking')
      .click('.el-menu-item:nth-child(4)')
      .assert.urlEquals(browser.launchUrl + 'user/order')
      .click('.el-menu-item:nth-child(5)')
      .pause(3000) // Wait for the message disappear
      .assert.urlEquals(browser.launchUrl + 'user/news')
      .click('.el-menu-item:nth-child(6)')
      .assert.urlEquals(browser.launchUrl + 'user/message')
      .click('.el-menu-item:nth-child(1)')
      .assert.urlEquals(browser.launchUrl + 'user/main')
      .click('.el-menu-item:nth-child(7)')
      .assert.urlEquals(browser.launchUrl + 'user/login')
      .end()
  },

  'test user navigator with logging in': browser => {
    browser
      .userLogin()
      .end()
  }
}
