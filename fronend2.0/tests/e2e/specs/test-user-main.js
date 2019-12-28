
module.exports = {
  'load main page': browser => {
    browser
      .url(browser.launchUrl + "user/main")
      .waitForElementVisible('#app')
      .assert.elementPresent('.el-carousel')
      .assert.elementPresent('footer')
      .assert.containsText('#venue-title', '场馆')
      .assert.containsText('#news-title', '新闻')
      .assert.elementCount('img', 8)
      .end()
  }
}
