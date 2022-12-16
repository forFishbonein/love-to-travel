<script>
import {getNewLogNum, getNewUserNum} from "../apis/statisticManage/home";

export default {
  data() {
    return {
      dateList: [],
      totalList: [0, 0, 0, 0, 0, 0, 0],
      totalList2: [0, 0, 0, 0, 0, 0, 0],
    };
  },
  // 页面初始化挂载dom

  mounted() {
    this.getNewNum();
    // this.getDataList();
    // this.getNewUserNum();
    // this.getNewLogNum();
    // this.getLoadEcharts();

  },
  methods: {


    getNewNum() {
      this.getDataList();
      this.getNewUserNum();
      this.getNewLogNum();

    },

    getDataList() {
      var date = new Date();
      this.dateList.push(date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate(), this.subDate(date, 1), this.subDate(date, 2), this.subDate(date, 3), this.subDate(date, 4), this.subDate(date, 5), this.subDate(date, 6))

    },
    getNewUserNum() {
      getNewUserNum().then((response) => {
        let tmpDate = response.data
        // console.log(tmpDate)
        // console.log(this.dateList)
        for (let i = 0; i < tmpDate.length; i++) {
          for (let j = 0; j < 7; j++) {
            // console.log(this.dateList[j])
            // console.log(tmpDate[i][0])
            if (this.dateList[j] === tmpDate[i].time) {
              this.totalList[j] = tmpDate[i].total
            }
          }
        }
        // console.log(this.dateList)
        // console.log(this.totalList)

      })
    },
    getNewLogNum() {
      getNewLogNum().then((response) => {
        let tmpDate = response.data
        // console.log(tmpDate)
        // console.log(this.dateList)
        for (let i = 0; i < tmpDate.length; i++) {
          for (let j = 0; j < 7; j++) {
            // console.log(this.dateList[j])
            // console.log(tmpDate[i][0])
            if (this.dateList[j] === tmpDate[i].time) {
              this.totalList2[j] = tmpDate[i].total
            }
          }
        }
        // console.log(this.dateList)
        // console.log(this.totalList2)

        this.getLoadEcharts();
        window.onresize = () => {
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('newEcharts'));
          myChart.resize();
        };

      })
    },


    addDate(date, days) {
      if (days == undefined || days == '') {
        days = 1;
      }
      var date = new Date(date);
      date.setDate(date.getDate() + days);
      var month = date.getMonth() + 1; //月份从0开始所以需要+1
      var day = date.getDate();
      return date.getFullYear() + '-' + month + '-' + day;
    },
    subDate(date, days) {
      if (days == undefined || days == '') {
        days = 1;
      }
      var date = new Date(date);
      date.setDate(date.getDate() - days);
      var month = date.getMonth() + 1; //月份从0开始所以需要+1
      var day = date.getDate();
      return date.getFullYear() + '-' + month + '-' + day;
    },

    getLoadEcharts() {
      var myChart = this.$echarts.init(
          document.getElementById("echart")
      );
      console.log("=============")
      console.log(this.dateList)
      console.log(this.totalList)
      console.log(this.totalList2)
      console.log("=============")
      var option = {
        title: {
          text: '7日新增用户'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['7日新增用户', '7日访问']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: this.dateList.reverse(),
        },
        yAxis: {
          type: 'value'
        },
        series: [

          {
            name: "7日新增用户",
            data: this.totalList.reverse(),
            type: 'line',
            areaStyle: {},
            // stack: 'Total',
            emphasis: {
              focus: 'series'
            },

          },
          {
            name: "7日访问",
            data: this.totalList2.reverse(),
            type: 'line',
            areaStyle: {},
            // stack: 'Total',
            emphasis: {
              focus: 'series'
            },

          },
        ]
      };
      myChart.setOption(option);
    }
  }
};
</script>
<template>
  <div class="top">
    <p>欢迎您：<span>XXX</span></p>
  </div>
  <ul class="scoreboard">
    <li>
      <div class="scoreboard-number">{{ totalList[0] }}</div>
      <div class="scoreboard-title">今日新增用户</div>

      <div class="scoreboard-line scoreboard-line1"><span></span></div>
      <div class="scoreboard-yesterday" v-show="totalList[1] !== 0">较昨日{{ totalList[0] / totalList[1] * 100 }}%</div>
      <div class="scoreboard-yesterday" v-show="totalList[1] === 0">较昨日0%</div>
    </li>

    <li>
      <div class="scoreboard-number">{{ totalList2[0] }}</div>
      <div class="scoreboard-title">今日访问</div>

      <div class="scoreboard-line scoreboard-line2"><span></span></div>
      <div class="scoreboard-yesterday" v-show="totalList2[1] !== 0">较昨日{{ totalList2[0] / totalList2[1] * 100 }}%</div>
      <div class="scoreboard-yesterday" v-show="totalList2[1] === 0">较昨日0%</div>
    </li>
    <li>
      <div class="scoreboard-number">1600</div>
      <div class="scoreboard-title">今日发布游记</div>

      <div class="scoreboard-line scoreboard-line3"><span></span></div>
      <div class="scoreboard-yesterday">较昨日15%</div>
    </li>
  </ul>

  <div id="echart" class="echarts"></div>

</template>
<style>
.top {
  width: 100%;
  height: 130px;
  background-color: #01d8da;
}

.top p {
  color: white;
  padding: 30px;
}

.scoreboard {
  position: relative;
  top: -60px;
  width: 100%;
  /*height: 160px;*/
  display: flex;
  justify-content: space-evenly;
}

.scoreboard li {

  /*position: absolute;*/
  padding: 20px;
  width: 400px;
  height: 100%;
  background-color: white;
  border-radius: 4px;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
}

.scoreboard-title {
  margin-bottom: 20px;
}

.scoreboard .scoreboard-number {

  font-weight: bold;
  font-size: 28px;
  margin-bottom: 5px;

}

.scoreboard .scoreboard-line {
  width: 95%;
  height: 10px;
  background-color: #eee;
  margin-bottom: 5px;
}

.scoreboard .scoreboard-line span {
  display: block;
  width: 65%;
  height: 10px;
}

.scoreboard .scoreboard-line1 span {
  background: rgb(237, 72, 72);
  background: linear-gradient(90deg, rgba(237, 72, 72, 1) 0%, rgba(255, 197, 150, 1) 100%);
}

.scoreboard .scoreboard-line2 span {
  background: rgb(126, 237, 72);
  background: linear-gradient(90deg, rgba(126, 237, 72, 1) 0%, rgba(150, 255, 229, 1) 100%);
}

.scoreboard .scoreboard-line3 span {
  background: rgb(72, 106, 237);
  background: linear-gradient(90deg, rgba(72, 106, 237, 1) 0%, rgba(234, 150, 255, 1) 100%);
}

.echarts {
  display: inline-block;
  margin-left: 20px;
  padding: 16px;
  box-sizing: border-box;
  width: 1250px;
  height: 350px;
  background-color: #fff;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
}

</style>
