<script>
import {getStatistic} from '@/apis/statisticManage/rank.js'

export default {
  data() {
    return {
      likeList: [0,0,0,0,0],
      likeNote: ["","","","",""],

      starList: [0,0,0,0,0],
      starNote: ["","","","",""],

      viewList: [0,0,0,0,0],
      viewNote: ["","","","",""],


    }
  },
  mounted() {
    this.getStatistic();

  },

  methods: {
    async getStatistic() {
      await getStatistic().then((response) => {
        console.log(response)
        for (let i = 0; i < 5; i++) {
          this.likeList[i] = response.data.likeList[i].like

          this.likeNote[i] = response.data.likeList[i].title
          this.starList[i] = response.data.starList[i].star
          this.starNote[i] = response.data.starList[i].title
          this.viewList[i] = response.data.viewList[i].view
          this.viewNote[i] = response.data.viewList[i].title
        }

      })

      this.getLoadEcharts();
      window.onresize = () => {
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('echart'));
        myChart.resize();
      };

      console.log(this.likeList)


    },


    getLoadEcharts() {
      var myChart = this.$echarts.init(
          document.getElementById("echart")
      );
      var option = {
        title: {
          text: '游记点赞榜'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: [this.likeNote[4], this.likeNote[3], this.likeNote[2], this.likeNote[1], this.likeNote[0]]
        },
        series: [
          {
            name: '2011',
            type: 'bar',
            barWidth: '25',
            itemStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 1,
                y2: 0,
                colorStops: [
                  {
                    offset: 0,
                    color: '#ff87e2' // 0% 处的颜色
                  },
                  {
                    offset: 1,
                    color: '#f223ba' // 100% 处的颜色
                  }
                ],
                global: false // 缺省为 false
              }
            },
            data: [this.likeList[4], this.likeList[3], this.likeList[2], this.likeList[1], this.likeList[0]]
          }
        ]
      };
      myChart.setOption(option);


      var myChart2 = this.$echarts.init(
          document.getElementById("echart2")
      );
      var option2 = {
        title: {
          text: '游记收藏榜'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: [this.starNote[4], this.starNote[3], this.starNote[2], this.starNote[1], this.starNote[0]]
        },
        series: [
          {
            name: '2011',
            type: 'bar',
            barWidth: '25',
            itemStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 1,
                y2: 0,
                colorStops: [
                  {
                    offset: 0,
                    color: '#87e0ff' // 0% 处的颜色
                  },
                  {
                    offset: 1,
                    color: '#237bf2' // 100% 处的颜色
                  }
                ],
                global: false // 缺省为 false
              }
            },
            data: [this.starList[4], this.starList[3], this.starList[2], this.starList[1], this.starList[0]]
          }
        ]
      };
      myChart2.setOption(option2);


      var myChart3 = this.$echarts.init(
          document.getElementById("echart3")
      );
      var option3 = {
        title: {
          text: '游记浏览榜'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'value'
        },
        yAxis: {
          type: 'category',
          data: [this.viewNote[4], this.viewNote[3], this.viewNote[2], this.viewNote[1], this.viewNote[0]]
        },
        series: [
          {
            name: '2011',
            type: 'bar',
            barWidth: '25',
            itemStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 1,
                y2: 0,
                colorStops: [
                  {
                    offset: 0,
                    color: '#87ff93' // 0% 处的颜色
                  },
                  {
                    offset: 1,
                    color: '#58f223' // 100% 处的颜色
                  }
                ],
                global: false // 缺省为 false
              }
            },
            data: [this.viewList[4], this.viewList[3], this.viewList[2], this.viewList[1], this.viewList[0]]
          }
        ]
      };
      myChart3.setOption(option3);
    }
  }
}

</script>


<template>

  <div id="echart" class="echarts"></div>
  <div id="echart3" class="echarts"></div>
  <div id="echart2" class="echarts"></div>

</template>


<style scoped>

.echarts {
  display: inline-block;
  margin-left: 20px;
  margin-top: 10px;
  margin-bottom: 20px;
  padding: 16px;
  box-sizing: border-box;
  width: 1250px;
  height: 350px;
  background-color: #fff;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
}

</style>