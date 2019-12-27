import _ from 'lodash';

export default {
  methods: {
    requiredInput (str, trigger = 'blur', type = 'string') {
      return { required: true, message: `请输入${str}`, trigger, type };
    },
    maxInput (max, trigger = 'blur') {
      return { max: max, message: `输入不超过${max}个字符`, trigger };
    },
    onAlertSuccess (message) {
      return this.$alert(message, '消息', {
        dangerouslyUseHTMLString: true
      });
    },
    onAlertError (message) {
      this.$message({
        message,
        type: 'error'
      });
    },
    formatGetTableData (data) {
      let totalData = [];
      let totalLength = 0;
      _.each(data, item => {
        totalData = totalData.concat(item.content);
        totalLength += item.totalElements || 0;
      });
      return {totalData, totalLength};
    }
  }
};
