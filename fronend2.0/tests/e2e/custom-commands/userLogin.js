
module.exports = {
  command: async function () {
    // Other Nightwatch commands are available via "this"
    // .init() simply calls .url() command with the value of the "launch_url" setting
    this.init(this.launchUrl + "user/login")
    this.waitForElementVisible('body')
    this.setValue('input[type=name]', 'apple')
    this.setValue('input[type=password]', '123')
    this.pause(1000)

  }
}
