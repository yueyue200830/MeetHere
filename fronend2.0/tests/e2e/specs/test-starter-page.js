
module.exports = {
  'starter page should jump to user/main page': browser => {
    browser
      .init()
      .pause(1000)
      .assert.urlEquals(browser.launchUrl + 'user/main')
      .assert.elementPresent('#user')
      .assert.elementPresent('.el-header')
      .assert.containsText('.main-title-text', '场馆')
      .end()
  }
}
