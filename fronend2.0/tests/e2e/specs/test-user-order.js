
module.exports = {
  'load user booking page': browser => {
    browser
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .assert.containsText('.order-title', '我的订单')
      .assert.elementPresent('footer')
      .end()
  },

  'should have error alert': browser => {
    browser
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .pause(1000)
      .assert.containsText('.el-message__content', '您未登录')

    browser.expect.elements('tr').count.to.equal(1)

    browser.end()
  },

  'should load order': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')

    browser.expect.elements('tr').count.to.not.equal(1)

    browser.end()
  },

  'change phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .pause(1000)
      .click('.el-table__row:nth-child(1) .el-button--default')
      .waitForElementVisible('.el-dialog')
      .setValue('input[type=newNumber]', '13265498569')
      .click('.el-dialog__footer:nth-child(3) .el-button--primary')
      .waitForElementVisible('.el-message')
      .assert.containsText('.el-message', '修改成功')
      .assert.containsText('.el-table__row:nth-child(1) > .el-table_1_column_7', '13265498569')
      .end()
  },

  'enter empty phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .pause(1000)
      .click('.el-table__row:nth-child(1) .el-button--default')
      .waitForElementVisible('.el-dialog')
      .click('.el-dialog__footer:nth-child(3) .el-button--primary')
      .pause(2000)
      .assert.containsText('.el-form-item__error', '手机号不可为空')
      .waitForElementNotPresent('.el-message')
      .assert.visible('.el-dialog__wrapper:nth-child(4) .el-dialog__header')
      .click('.el-dialog__wrapper:nth-child(4) .el-dialog__close')
      .assert.not.visible('.el-dialog__wrapper:nth-child(4) .el-dialog__header')
      .end()
  },

  'click cancel button when change phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .click('.el-table__row:nth-child(1) .el-button--default')
      .pause(1000)
      .setValue('input[type=newNumber]', '98765432112')
      .click('.el-dialog__footer:nth-child(3) .button-cancel')
      .pause(2000)
      .waitForElementNotVisible('.el-dialog__wrapper:nth-child(4) .el-dialog__header')
      .click('.el-table__row:nth-child(1) .el-button--default')
      .assert.not.value('input[type=newNumber]', '98765432112')
      .end()
  },

  'enter and delete phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .click('.el-table__row:nth-child(1) .el-button--default')
      .waitForElementVisible('.el-dialog__wrapper:nth-child(4) .el-dialog__header')
      .setValue('input[type=newNumber]', '13123456789')
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .keys(browser.Keys.BACK_SPACE)
      .assert.containsText('.el-form-item__error', '手机号不可为空')
      .end()
  },

  'change short phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .click('.el-table__row:nth-child(1) .el-button--default')
      .pause(1000)
      .setValue('input[type=newNumber]', '1326')
      .click('.el-dialog__footer:nth-child(3) .el-button--primary')
      .assert.containsText('.el-form-item__error', '请输入11位手机号')
      .end()
  },

  'change long phone number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .click('.el-table__row:nth-child(1) .el-button--default')
      .pause(1000)
      .setValue('input[type=newNumber]', '132612345678')
      .click('.el-dialog__footer:nth-child(3) .el-button--primary')
      .assert.containsText('.el-form-item__error', '请输入11位手机号')
      .end()
  },

  'change small long number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .click('.el-table__row:nth-child(1) .el-button--default')
      .pause(1000)
      .setValue('input[type=newNumber]', '12999999999')
      .click('.el-dialog__footer:nth-child(3) .el-button--primary')
      .assert.containsText('.el-form-item__error', '请输入11位手机号')
      .end()
  },

  'change large long number': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .click('.el-table__row:nth-child(1) .el-button--default')
      .pause(1000)
      .setValue('input[type=newNumber]', '14000000000')
      .click('.el-dialog__footer:nth-child(3) .el-button--primary')
      .assert.containsText('.el-form-item__error', '请输入11位手机号')
      .end()
  },

  'delete order': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .click('.el-table__row:nth-child(1) .button-delete')
      .waitForElementVisible('.el-dialog__wrapper:nth-child(3) .el-dialog__header')
      .click('.el-dialog__footer:nth-child(3) .el-button--primary')
      .waitForElementNotVisible('.el-dialog__wrapper:nth-child(3) .el-dialog__header')
      .waitForElementPresent('.el-message')
      .assert.containsText('.el-message', '删除订单成功')
      .end()
  },

  'cancel delete order': browser => {
    browser
      .userLogin()
      .url(browser.launchUrl + 'user/order')
      .waitForElementVisible('#app')
      .click('.el-table__row:nth-child(1) .button-delete')
      .waitForElementVisible('.el-dialog__wrapper:nth-child(3) .el-dialog__header')
      .click('.el-dialog__footer:nth-child(3) .button-cancel')
      .waitForElementNotVisible('.el-dialog__wrapper:nth-child(3) .el-dialog__header')
      .waitForElementNotPresent('.el-message')
      .end()
  }

}
