<script>
import {getNewLogNum, getNewUserNum, getAllUser, getAllLog, getNewProductNum, getAllProductBuy, getNewProductCost, getAllProductCost} from "../apis/statisticManage/home";

export default {
  data() {
    return {
      dateList: [],
      totalList: [0, 0, 0, 0, 0, 0, 0],
      totalList2: [0, 0, 0, 0, 0, 0, 0],
      totalList3: [0, 0, 0, 0, 0, 0, 0],
      totalList4: [0, 0, 0, 0, 0, 0, 0],
      userNum: 0,
      logNum: 0,
      buyNum: 0,
      cost: 0.00
    };
  },
  // 页面初始化挂载dom

  mounted() {
    this.getNewNum();
    // this.getDataList();
    // this.getNewUserNum();
    // this.getNewLogNum();
    // this.getLoadEcharts();
    this.getAllUserAndLog();

  },
  methods: {


    getNewNum() {
      this.getDataList();
      this.getNewUserNum();
      this.getNewLogNum();
      this.getNewProductNum();
      this.getNewProductCost();
    },

    getDataList() {
      var date = new Date();
      this.dateList.push(date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate(), this.subDate(date, 1), this.subDate(date, 2), this.subDate(date, 3), this.subDate(date, 4), this.subDate(date, 5), this.subDate(date, 6))

    },
    async getNewUserNum() {
      await getNewUserNum().then((response) => {
        let tmpDate = response.data
        for (let i = 0; i < tmpDate.length; i++) {
          for (let j = 0; j < 7; j++) {
            if (this.dateList[j] === tmpDate[i].time) {
              this.totalList[j] = tmpDate[i].total
            }
          }
        }
      })
    },


    async getNewLogNum() {
      await getNewLogNum().then((response) => {
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



      })
    },
    async getNewProductNum() {
      await getNewProductNum().then((response) => {
        let tmpDate = response.data
        for (let i = 0; i < tmpDate.length; i++) {
          for (let j = 0; j < 7; j++) {
            if (this.dateList[j] === tmpDate[i].time) {
              this.totalList3[j] = tmpDate[i].total
            }
          }
        }
        console.log(this.totalList3)

        this.getLoadEcharts();
        window.onresize = () => {
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('echart'));
          myChart.resize();
        };
      })
    },

    async getNewProductCost() {
      await getNewProductCost().then((response) => {
        let tmpDate = response.data
        for (let i = 0; i < tmpDate.length; i++) {
          for (let j = 0; j < 7; j++) {
            if (this.dateList[j] === tmpDate[i].time) {
              this.totalList4[j] = tmpDate[i].cost
            }
          }
        }
        console.log(this.totalList4)
        this.getLoadEcharts();
        window.onresize = () => {
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('echart'));
          myChart.resize();
        };
      })
    },

    async getAllUserAndLog() {
      await getAllUser().then((response) => {
        this.userNum = response.data

      })
      await getAllLog().then((response) => {
        this.logNum = response.data

      })
      await getAllProductBuy().then((response) => {
        this.buyNum = response.data

      })
      await getAllProductCost().then((response) => {
        this.cost = response.data.cost
        console.log(response)


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
      var option = {
        title: {
          text: '7日数据统计'
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
          data: ['新用户注册', '用户访问']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: [this.dateList[6], this.dateList[5], this.dateList[4], this.dateList[3], this.dateList[2], this.dateList[1], this.dateList[0]]
        },
        yAxis: [
          {
            type: 'value',
            name: '新用户注册',
          },
          {
            type: 'value',
            name: '用户访问',
          },

        ],

        series: [

          {
            name: "7日新用户注册",
            data: [this.totalList[6], this.totalList[5], this.totalList[4], this.totalList[3], this.totalList[2], this.totalList[1], this.totalList[0]],
            type: 'line',
            areaStyle: {},
            // stack: 'Total',
            emphasis: {
              focus: 'series'
            },
          },
          {
            name: "7日用户访问",
            yAxisIndex: 1,
            data: [this.totalList2[6], this.totalList2[5], this.totalList2[4], this.totalList2[3], this.totalList2[2], this.totalList2[1], this.totalList2[0]],
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
    <p>欢迎您：<span>管理员</span></p>
  </div>
  <ul class="scoreboard">
    <li>
      <div class="scoreboard-number">{{ totalList[0] }}</div>
      <div class="scoreboard-title">今日新增用户</div>

      <div class="scoreboard-line scoreboard-line1"><span></span></div>
      <div class="scoreboard-yesterday">较昨日{{ ((totalList[0]/this.userNum-totalList[1]/this.userNum) * 100).toFixed(2) }}%</div>
    </li>

    <li>
      <div class="scoreboard-number">{{ totalList2[0] }}</div>
      <div class="scoreboard-title">今日访问</div>

      <div class="scoreboard-line scoreboard-line2"><span></span></div>
      <div class="scoreboard-yesterday">较昨日{{ ((totalList2[0]/this.logNum-totalList2[1]/this.logNum) * 100).toFixed(2) }}%</div>
    </li>
    <li>
      <div class="scoreboard-number">{{ totalList3[0] }}</div>
      <div class="scoreboard-title">日订单</div>

      <div class="scoreboard-line scoreboard-line3"><span></span></div>
      <div class="scoreboard-yesterday">较昨日{{ ((totalList3[0]/this.buyNum-totalList3[1]/this.buyNum) * 100).toFixed(2) }}%</div>
    </li>
    <li>
      <div class="scoreboard-number">{{ totalList4[0] }}</div>
      <div class="scoreboard-title">日销售额</div>

      <div class="scoreboard-line scoreboard-line3"><span></span></div>
      <div class="scoreboard-yesterday">较昨日{{ ((totalList4[0]/this.cost-totalList4[1]/this.cost) * 100).toFixed(2) }}%</div>
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
  width: 300px;
  height: 100%;
  background-color: white;
  border-radius: 4px;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
  transition: 0.2s cubic-bezier(.46,1.9,.4,1.9);
}

.scoreboard li:hover {
  transform: translateY(-15px);
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;


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
