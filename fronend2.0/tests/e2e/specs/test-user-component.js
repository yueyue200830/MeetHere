
module.exports = {
  'test user navigator without logging in': browser => {
    browser
      .init(browser.launchUrl + 'user/main')
      .assert.urlEquals(browser.launchUrl + 'user/main')
      .click('.el-menu-item:nth-child(2)')
      .assert.urlEquals(browser.launchUrl + 'user/revenue')
      .click('.el-menu-item:nth-child(3)')
      .assert.urlEquals(browser.launchUrl + 'user/booking')
      .click('.el-menu-item:nth-child(4)')
      .assert.urlEquals(browser.launchUrl + 'user/order')
      .click('.el-menu-item:nth-child(5)')
      .waitForElementVisible('.el-message')
      .waitForElementNotPresent('.el-message')
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
      .click('.el-menu-item:nth-child(2)')
      .assert.urlEquals(browser.launchUrl + 'user/revenue')
      .click('.el-menu-item:nth-child(3)')
      .assert.urlEquals(browser.launchUrl + 'user/booking')
      .click('.el-menu-item:nth-child(4)')
      .assert.urlEquals(browser.launchUrl + 'user/order')
      .click('.el-menu-item:nth-child(5)')
      .assert.urlEquals(browser.launchUrl + 'user/news')
      .click('.el-menu-item:nth-child(6)')
      .assert.urlEquals(browser.launchUrl + 'user/message')
      .click('.el-menu-item:nth-child(1)')
      .assert.urlEquals(browser.launchUrl + 'user/main')
      .moveToElement('.el-submenu__title', null, null)
      .waitForElementVisible('.el-menu--popup > .el-menu-item:nth-child(1)')
      .click('.el-menu--popup > .el-menu-item:nth-child(1)')
      .assert.urlEquals(browser.launchUrl + 'user/info')
      .pause(500)
      .moveToElement('.el-submenu__title', null, null)
      .waitForElementVisible('.el-menu--popup > .el-menu-item:nth-child(2)')
      .click('.el-menu--popup > .el-menu-item:nth-child(2)')
      .assert.urlEquals(browser.launchUrl + 'user/myMessage')
      .pause(500)
      .moveToElement('.el-submenu__title', null, null)
      .waitForElementVisible('.el-menu--popup > .el-menu-item:nth-child(3)')
      .click('.el-menu--popup > .el-menu-item:nth-child(3)')
      .assert.urlEquals(browser.launchUrl + 'user/main')
      .end()
  },

  'test log out': browser => {
    browser
      .userLogin()
      .moveToElement('.el-submenu__title', null, null)
      .waitForElementVisible('.el-menu--popup > .el-menu-item:nth-child(3)')
      .click('.el-menu--popup > .el-menu-item:nth-child(3)')
      .assert.urlEquals(browser.launchUrl + 'user/main')
      .waitForElementVisible('#app')
      .assert.containsText('.el-header', '登录')
      .end()
  }
}
