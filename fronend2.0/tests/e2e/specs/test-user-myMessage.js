
module.exports = {
  'load user message page': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '我的留言')
      .end()
  },

  'should jump to login': browser => {
    browser
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .assert.urlEquals(browser.launchUrl + 'user/login')
      .end()
  },

  'edit message': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .waitForElementVisible('.main-card:nth-child(2)')
      .click('.main-card:nth-child(3) .edit-message > .el-link--inner')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__title', '修改留言')
      .clearValue('.el-input__inner')
      .setValue('.el-input__inner', '修改留言-系统测试')
      .clearValue('.el-textarea__inner')
      .setValue('.el-textarea__inner', '修改留言内容-系统测试')
      .click('.el-button:nth-child(2)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .pause(1000)
      .assert.containsText('.main-card:nth-child(2) .title', '修改留言-系统测试')
      .assert.containsText('.main-card:nth-child(2) .text', '修改留言内容-系统测试')
      .end()
  },

  // todo: enter empty

  'close eidt form': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .waitForElementVisible('.main-card:nth-child(3)')
      .click('.main-card:nth-child(3) .edit-message > .el-link--inner')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__title', '修改留言')
      .click('.el-dialog__close')
      .waitForElementNotVisible('.el-dialog')
      .end()
    },

  'press cancel button': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .waitForElementVisible('.main-card:nth-child(3)')
      .click('.main-card:nth-child(3) .edit-message > .el-link--inner')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__title', '修改留言')
      .clearValue('.el-input__inner')
      .setValue('.el-input__inner', '修改留言-系统测试-草稿')
      .clearValue('.el-textarea__inner')
      .setValue('.el-textarea__inner', '修改留言内容-系统测试-草稿')
      .click('.button-cancel')
      .waitForElementNotVisible('.el-dialog')
      .click('.main-card:nth-child(3) .edit-message > .el-link--inner')
      .waitForElementVisible('.el-dialog')
      .assert.not.containsText('.el-input__inner', '修改留言-系统测试-草稿')
      .assert.not.containsText('.el-textarea__inner', '修改留言内容-系统测试-草稿')
      .end()
  }
}
