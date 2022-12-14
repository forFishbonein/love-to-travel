<template>
  <el-row>
    <el-col :span="10" :offset="1">
      <el-card class="box-card">
        <span>点赞排行榜</span>
        <div id="myChart" :style="{ height: '500px' }"></div>
      </el-card>
    </el-col>
    <el-col :span="10" :offset="2">
      <el-card class="box-card">
        <span>浏览排行榜</span>
        <div id="myChart2" :style="{ height: '500px' }"></div>
      </el-card>

    </el-col>

  </el-row>

</template>
<script lang="ts">
import {getStatistic} from '@/apis/statisticManage/rank.js'

export default {
  data() {
    return {
      option: {},
      likeList: [],

      likek: [],
      likev: [],

      starList: [],
      viewList: [],
    }
  },
  mounted() {
    // this.getStatistic();

    this.setEcharts();

  },

  methods: {
    getStatistic() {
      getStatistic().then((response) => {
        this.likeList = response.data.likeList.map((item, index) => {
          this.likek.push(item.title)
          this.likev.push(item.like)
          return [index] = [item.title, item.like]
        });
        console.log(this.likek)


        this.starList = response.data.starList.map((item, index) => {
          return [index] = [item.title, item.star]
        });
        this.viewList = response.data.viewList.map((item, index) => {
          return [index] = [item.title, item.view]
        });
        console.log(this.viewList)
      })
    },

    setEcharts() {
      this.getStatistic();
      let myChart = this.$echarts.init(document.getElementById("myChart"));
      var _this = this
      // 绘制图表
      myChart.setOption({
        tooltip: {},

        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },

        dataset: [{
          dimensions: ['title', 'like'],
          source: [
            ['周末不远游，在这里与家人共度温馨假期', 108],
            ['KK游齐鲁上集', 123],
            ['【骆驼去哪儿系列】渔风东来，唱响大海', 142],
            ['KK游齐鲁下集', 157],
            ['遇见，山城重庆之美', 199],
          ]
        },],
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          // data: ["周末不远游，在这里与家人共度温馨假期", "KK游齐鲁上集", "【骆驼去哪儿系列】渔风东来，唱响大海","KK游齐鲁下集", "遇见，山城重庆之美" ]
        },
        series: [
          {
            name: '点赞数',
            type: 'bar',
            // data: [199, 170, 135, 98, 58]
            // data2: [1,2,3,4,5]
          }
        ]
      });


      let myChart2 = this.$echarts.init(document.getElementById("myChart2"));
      var _this = this
      // 绘制图表
      myChart2.setOption({
        tooltip: {},

        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },

        dataset: [{
          dimensions: ['title', 'like'],
          source: [
            ['我的旅行攻略', 999],
            ['品晚晴楼美食攻略', 9993],
            ['南京一日游', 9993],
            ['东北古镇·吉林乌拉街满族镇', 9993],
            ['元上都皇家草原文化旅游度假区旅行攻略', 9999],
          ]
        },],
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          // data: ["周末不远游，在这里与家人共度温馨假期", "KK游齐鲁上集", "【骆驼去哪儿系列】渔风东来，唱响大海","KK游齐鲁下集", "遇见，山城重庆之美" ]
        },
        series: [
          {
            name: '浏览数',
            type: 'bar',
            // data: [199, 170, 135, 98, 58]
            // data2: [1,2,3,4,5]
          }
        ]
      });
    }

  }

}
</script>


<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
}
</style>