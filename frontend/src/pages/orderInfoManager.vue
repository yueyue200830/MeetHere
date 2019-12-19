<template>
  <page-main-body :title="title" :loading="loading">
    <div style="width: 90%">
      <e-charts :options="chartOption"></e-charts>
    </div>
  </page-main-body>
</template>

<script>
  import axios from 'axios';
  import ECharts from 'vue-echarts/components/ECharts';
  require('echarts/lib/chart/bar');
  const getStatistic= () => axios.post("/app/getStatistic");
  const getVenue= () => axios.post("/app/getVenueNameForChart");
  export default {
    name: "orderInfo",
    components: {ECharts},
    data () {
      return {
        loading: false,
        title: '预约订单统计信息',
        chartOption: {
          grid: {left: 30, top: 30, right: 30, bottom: 30},
          xAxis: {
            type: 'category',
            axisLine: {show: false},
            axisTick: {show: false},
            axisLabel: {color: '#7383A2'},
            data: ['足球场', '游泳馆', '篮球场', '乒乓球馆', '羽毛球馆']
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
              data: [7, 2, 20, 8, 31]
            },
          ]
        },

        Data: []
      };
    },
    methods:{

    },
    mounted(){
      getStatistic().then(data => {
        console.log(data.data);
        this.chartOption.series[0].data=data.data;
      });

      getVenue().then(data => {
        console.log(data.data);
        this.chartOption.xAxis.data = data.data;
      });
    }
  }
</script>

<style scoped>

</style>
