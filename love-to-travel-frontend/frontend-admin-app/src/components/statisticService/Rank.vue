<template>
  <div id="myChart" :style="{ height: '300px' }"></div>
</template>
<script lang="ts">
import {getStatistic} from '@/apis/statisticManage/rank.js'

export default {
  data() {
    return {
      option: {},
      likeList: [],
      starList: [],
      viewList: [],
    }
  },
  mounted() {
    this.getStatistic();

    this.setEcharts();

  },

  methods: {
    getStatistic() {
      getStatistic().then((response) => {
        this.likeList = response.data.likeList.map((item, index)=> {
          return [index] = [item.title, item.like]
        });
        this.starList = response.data.starList.map((item, index)=> {
          return [index] = [item.title, item.star]
        });
        this.viewList = response.data.viewList.map((item, index)=> {
          return [index] = [item.title, item.view]
        });
        console.log(this.likeList)
        console.log(this.starList)
        console.log(this.viewList)
      })
    },

    setEcharts() {
      let myChart = this.$echarts.init(document.getElementById("myChart"));
      // 绘制图表
      myChart.setOption({
        tooltip: {
        },
        legend: {},
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value',
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          type: 'category',
          // data: [this.likeList[0][0], this.likeList[1][0], this.likeList[2][0], this.likeList[3][0], this.likeList[4][0]]
        },
        series: [
          {
            name: 'like',
            type: 'bar',
            data: [this.likeList[0][1], this.likeList[1][1], this.likeList[2][1], this.likeList[3][1], this.likeList[4][1]]
            // data: [1,2,3,4,5]
          }
        ]
      });
    }

  }

}
</script>



<style scoped>

</style>