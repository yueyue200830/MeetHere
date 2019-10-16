<template>
  <page-main-body>
    <el-row style="width:100%;height:100%">
      <el-col >
        <el-row >
          <el-card :body-style="{height:'50px',padding:'10px'}">
            <div slot="header">
              <span>时间段选择</span>
            </div>
            <el-select v-model="timeslot" placeholder="时间段选择" @click="getArea">
              <el-option v-for="item in options"
                :label="item.text"
                :key="item.value"
                :value="item.value"
                >
              </el-option>
            </el-select>
          </el-card>
        </el-row>
        <el-row>
          <el-card :body-style="{height:'500px'}">
            <div slot="header">
              <span>热力图</span>
            </div>
            <!-- <canvas class="body"> -->
                <baidu-map  class="body" @ready="map_handler" :center="{lng: 116.404, lat: 39.915}" :zoom="14" >
                    <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_LEFT"></bm-map-type>
                    <bml-heatmap :data="areaData" :max="100" :radius="20">
                    </bml-heatmap>
                </baidu-map>
            <!-- </canvas> -->
          </el-card>
        </el-row>
      </el-col>
      <!-- <el-col :span="12">
        <el-card :body-style=cardStyle>
          <div slot="header">
            <span>热力图</span>
          </div>
        </el-card>
      </el-col> -->
    </el-row>
  </page-main-body>
</template>

<script>
import _ from 'lodash';
import axios from 'axios';
import {BmlHeatmap} from 'vue-baidu-map';
// import BMap from 'BMap';
// import BMapLib from 'BMapLib';
// import BMapSymbolSHAPEPOINT from 'BMap_Symbol_SHAPE_POINT';

const getData = (slot) => axios.post('/app/api/selectArea', slot);

export default {
  components: {
    BmlHeatmap
  },
  name: 'index',
  data () {
    return {
      cardStyle: { overflow: 'auto' },
      timeslot: 8,
      map:'',
      options:[
        {value:1, text:"0:00--1:00"},
        {value:2, text:"1:00--2:00"},
        {value:3, text:"2:00--3:00"},
        {value:4, text:"3:00--4:00"},
        {value:5, text:"4:00--5:00"},
        {value:6, text:"5:00--6:00"},
        {value:7, text:"6:00--7:00"},
        {value:8, text:"7:00--8:00"},
        {value:9, text:"8:00--9:00"},
        {value:10, text:"9:00--10:00"},
        {value:11, text:"10:00--11:00"},
        {value:12, text:"11:00--12:00"},
        {value:13, text:"12:00--13:00"},
        {value:14, text:"13:00--14:00"},
        {value:15, text:"14:00--15:00"},
        {value:16, text:"15:00--16:00"},
        {value:17, text:"16:00--17:00"},
        {value:18, text:"17:00--18:00"},
        {value:19, text:"18:00--19:00"},
        {value:20, text:"19:00--20:00"},
        {value:21, text:"20:00--21:00"},
        {value:22, text:"21:00--22:00"},
        {value:23, text:"22:00--23:00"},
        {value:24, text:"23:00--0:00"},
      ],
      areaData:[],

      
    };
  },
  watch:{
    timeslot(){
      //console.log(this.timeslot);
      this.getArea();
    }
  },
  methods:{

    map_handler({ BMap, map }) {
        console.log("map_handler")
        let _this = this
        this.BMap = BMap
        this.map = map
        this.map_zoom = 15
    },
    getArea(){
      getData({slot:this.timeslot}).then(data =>{
        this.areaData=data.data;
      });
      console.log(this.areaData);     
      if(!this.isSupportCanvas()){
    	  this.onAlertError('Your browser can not support canvas');
      }

    }, 
    isSupportCanvas(){
        var elem = document.createElement('canvas');
        return !!(elem.getContext && elem.getContext('2d'));
    },
    
  },
  mounted () {
    this.getArea();//获得热力图
    // const bodyHeight = document.getElementsByClassName('page-main-body')[0].offsetHeight;
    const cardMargin = 15;
    const pageMainMargin = 20;
    const cardBodyPadding = 20;
    document.getElementsByClassName('el-main')[0].style.padding = `${pageMainMargin}px`;
    _.each(document.getElementsByClassName('el-card'), item => {
      item.style.margin = `${cardMargin}px`;
    });
    _.each(document.getElementsByClassName('el-card__body'), item => {
      item.style.padding = `${cardBodyPadding}px`;
    });

  }

};
</script>

<style lang="less" scoped>
.body{
  width: 100%;
  height: 100%;
  /deep/ .anchorBL {
      display: none;
  }
}
</style>
