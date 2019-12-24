<template>
  <page-main-body :title="title" :loading="loading">
    <div style="width: 50%;position: absolute;top:15%;left:6%">
      <el-date-picker
        v-model="value"
        type="date"
        placeholder="选择日期"
        value-format="yyyy-MM-dd"
        @change="getValue">
      </el-date-picker>
    </div>
    <div style="width: 90%; top: 200px;position: absolute">
      <e-charts :options="chartOption"></e-charts>
    </div>
  </page-main-body>
</template>

<script>
  import axios from 'axios';
  import ECharts from 'vue-echarts/components/ECharts';
  require('echarts/lib/chart/bar');
  const getStatistic= (value) => axios.get(`/app/getStatistic/${value}`);
  const getVenueNameForChart= (value) => axios.get(`/app/getVenueNameForChart/${value}`);
  export default {
    name: "orderInfo",
    components: {ECharts},
    data () {
      return {
        loading: false,
        value: '',
        title: '预约订单统计信息',
        nameData: [],
        valueData:[],
        chartOption: {
          grid: {left: 30, top: 30, right: 30, bottom: 30},
          xAxis: {
            type: 'category',
            axisLine: {show: false},
            axisTick: {show: false},
            axisLabel: {color: '#7383A2'},
            data: [],
          },
          yAxis: [
            {
              splitNumber: 5,
              scale: true,
              splitLine: {show: true, lineStyle: {color: ['#b6b6b6'], opacity: 0.1}},
              axisLine: {show: false},
              axisTick: {show: false},
              type: 'value',
              name: '',
              nameTextStyle: {
                fontFamily: 'MicrosoftYaHei',
                fontSize: 12,
                color: 'rgba(255,255,255,0.6)',
                align: 'left'
              },
              min:0,
              nameGap: 30,
              axisLabel: {formatter: '{value}', color: '#999'}
            }
          ],
          series: [
            {
              name: '订单数量',
              type: 'bar',
              stack: '总量',
              label: {normal: {show: true, position: 'top', color: 'rgba(255,255,255,0.6)'}},
              itemStyle: {
                normal: {
                  barBorderRadius: 4,
                  color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                      offset: 0, color: '#71C7C6'
                    }, {
                      offset: 1, color: '#CED5E0'
                    }]
                  }}},
              barWidth: 18,
              data: [],
            },
          ]
        },

        Data: []
      };
    },
    methods:{
      getValue(){
        console.log(this.value);
        if(this.value){
          getStatistic(this.value).then(data => {
            console.log(data.data);
            this.chartOption.series[0].data = data.data;
          });

          getVenueNameForChart(this.value).then(data => {
            //console.log(data.data);
            this.chartOption.xAxis.data = data.data;
          });
        }else{
          this.onAlertError("请选择日期");
        }

      }
    },
    mounted(){
      var nowDate = new Date();

      var mdata = {
        year: nowDate.getFullYear(),
        month: nowDate.getMonth() + 1,
        date: nowDate.getDate(),
      }
      this.value = mdata.year + '-' + mdata.month + '-' + mdata.date; //获取当天日期，默认为当天日期
      //console.log(this.value);
      getStatistic(this.value).then(data => {
        console.log(data.data);
        this.chartOption.series[0].data = data.data;
      });

      getVenueNameForChart(this.value).then(data => {
        //console.log(data.data);
        this.chartOption.xAxis.data = data.data;
      });
      //this.chartOption.series[0].data = [2,1,1];
      //this.chartOption.xAxis.data = ["足球场","篮球场","游泳馆"];
      //console.log(this.chartOption.series[0].data[1]);
    }
  }
</script>

<style scoped>

</style>
