
module.exports = {
  'load revenue page': browser => {
    browser
      .url(browser.launchUrl + "user/revenue")
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '场馆')
      .assert.elementPresent('footer')
      .end()
  }
}
