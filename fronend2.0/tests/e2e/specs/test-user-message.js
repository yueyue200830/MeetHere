
module.exports = {
  'load user message page': browser => {
    browser
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .assert.containsText('.user-title', '留言')
      .assert.elementVisible('footer')
      .assert.elementVisible('.refresh')
      .assert.elementVisible('.add-comment')
      .assert.elementVisible('.el-button--primary:nth-child(1)')
      .assert.elementVisible('.main-card:nth-child(4)')
      .end()
  },

  'press refresh button': browser => {
    browser
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .click('.refresh')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '刷新成功')
      .end()
  },

  'press button': browser => {
    browser
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .click('.el-button--primary:nth-child(1)')
      .waitForElementVisible('.main-card:nth-child(14)')
      .end()
  },

  'should not be allowed to add message': browser => {
    browser
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .click('.add-comment')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '您未登录，不可留言')
      .end()
  },

  'test form close button': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .click('.add-comment')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__header', '添加留言')
      .click('.el-dialog__close')
      .waitForElementNotVisible('.el-dialog')
      .end()
  },

  'test form cancel button': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .click('.add-comment')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__header', '添加留言')
      .setValue('input[type=title]', '取消按钮测试')
      .setValue('.el-textarea__inner', '添加留言内容-系统测试')
      .click('.button-cancel')
      .waitForElementNotVisible('.el-dialog')
      .click('.add-comment')
      .waitForElementVisible('.el-dialog')
      .assert.value('input[type=title]', '')
      .assert.value('.el-textarea__inner', '')
      .end()
  },

  'add message successfully': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .click('.add-comment')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__header', '添加留言')
      .setValue('input[type=title]', '添加留言测试')
      .setValue('.el-textarea__inner', '添加留言内容-系统测试')
      .click('.dialog-footer > .el-button--primary')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '添加留言成功')
      .end()
  },

  'add empty message': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .click('.add-comment')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__header', '添加留言')
      .click('.dialog-footer > .el-button--primary')
      .pause(1000)
      .assert.containsText('.el-form-item:nth-child(1) .el-form-item__error', '标题不可为空')
      .assert.containsText('.el-form-item:nth-child(2) .el-form-item__error', '内容不可为空')
      .end()
  },

  // This should work with backend verification.
  'add long message': browser => {
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
      .url(browser.launchUrl + 'user/message')
      .waitForElementVisible('#app')
      .click('.add-comment')
      .waitForElementVisible('.el-dialog')
      .assert.containsText('.el-dialog__header', '添加留言')
      .setValue('input[type=title]', message)
      .setValue('.el-textarea__inner', content)
      .click('.dialog-footer > .el-button--primary')
      .waitForElementNotVisible('.el-dialog')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '添加留言成功')
      .end()
  }
}
