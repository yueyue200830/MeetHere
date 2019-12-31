
module.exports = {
  'load user message page': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '我的留言')
      .waitForElementVisible('.main-card:nth-child(2)')
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
      .pause(1000)
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
      .pause(2000)
      .assert.containsText('.main-card:nth-child(2) .title', '修改留言-系统测试')
      .assert.containsText('.main-card:nth-child(2) .text', '修改留言内容-系统测试')
      .end()
  },

  'edit message with long input ': browser => {
    let message = ''
    for (let i = 0; i  < 60; i++) {
      message += 'a'
    }
    let content = ''
    for (let i = 0; i  < 110; i++) {
      content += '0123456789'
    }
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .waitForElementVisible('.main-card:nth-child(2)')
      .pause(1000)
      .click('.main-card:nth-child(3) .edit-message > .el-link--inner')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__title', '修改留言')
      .clearValue('.el-input__inner')
      .setValue('.el-input__inner', '修改留言-系统测试')
      .setValue('.el-input__inner', message)
      .clearValue('.el-textarea__inner')
      .setValue('.el-textarea__inner', '修改留言内容-系统测试')
      .setValue('.el-textarea__inner', content)
      .click('.el-button:nth-child(2)')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .end()
  },

  'empty input': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .waitForElementVisible('.main-card:nth-child(2)')
      .pause(1000)
      .click('.main-card:nth-child(3) .edit-message > .el-link--inner')
      .waitForElementVisible('.el-dialog')
      .clearValue('.el-input__inner')
      .setValue('.el-input__inner', '修改留言-系统测试')
      .click('.el-input__inner')
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .clearValue('.el-textarea__inner')
      .setValue('.el-textarea__inner', '系统测试')
      .click('.el-textarea__inner')
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .click('.el-button:nth-child(2)')
      .waitForElementVisible('.el-form-item:nth-child(1) .el-form-item__error')
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '标题不可为空')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '内容不可为空')
      .end()
  },

  'close edit form': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .waitForElementVisible('.main-card:nth-child(3)')
      .pause(1000)
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
      .pause(1000)
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
  },

  'delete message': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/myMessage')
      .waitForElementVisible('#app')
      .waitForElementVisible('.main-card:nth-child(3)')
      .pause(1000)
      .click('.main-card:nth-child(2) .delete-message > .el-link--inner')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '删除成功')
      .end()
  }
}
