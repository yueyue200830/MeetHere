
module.exports = {
  'load main page': browser => {
    browser
      .url(browser.launchUrl + "/user/main")
      .waitForElementVisible('body')
      .end()
  }
}
