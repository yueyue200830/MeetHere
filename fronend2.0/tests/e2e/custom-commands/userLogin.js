
module.exports = {
  command: async function () {
    this.init(this.launchUrl + "user/login")
    this.waitForElementVisible('#app')
    this.setValue('input[type=name]', 'apple')
    this.setValue('input[type=password]', '123')
    this.click('.login-button-submit')
    this.assert.urlEquals(this.launchUrl + 'user/main')
    this.assert.containsText('.el-submenu__title', 'apple')
  }
}
