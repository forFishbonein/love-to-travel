<script>
import {getOrder, getSales, getUse, getNewSalesNum} from '@/apis/statisticManage/rank.js'
import theme from '@/apis/statisticManage/theme.json'

export default {
  data() {
    return {
      order: {},
      sales: {},
      use: {},
      orderNum: 0,
      salesNum: 0,
      useNum: 0,
      dateList: [],
      totalList: [0, 0, 0, 0, 0, 0, 0],

    }
  },
  mounted() {
    this.getDataList();
    this.getOrder();
    this.getUse();
    this.getSales();
    this.getNewSalesNum();
  },

  methods: {
    async getSales() {
      await getSales().then((response) => {

        this.sales = response.data

        for (let i = 0; i < this.sales.length; i++) {
          this.salesNum=this.sales[i].value + this.salesNum
        }

      })


      this.getLoadEcharts();
      window.onresize = () => {
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('echart'), theme);
        myChart.resize();
      };

    },

    async getOrder() {
      await getOrder().then((response) => {
        this.order = response.data
        for (let i = 0; i < this.order.length; i++) {
          this.orderNum = this.order[i].value + this.orderNum
        }

      })
      this.getLoadEcharts();
      window.onresize = () => {
        // 基于准备好的dom，初始化echarts实例
        let myChart3 = this.$echarts.init(document.getElementById('echart3'), theme);
        myChart3.resize();
      };

    },


    async getUse() {
      await getUse().then((response) => {
        this.use = response.data
        for (let i = 0; i < this.use.length; i++) {
          this.useNum=this.use[i].value + this.useNum
        }

      })
    },

    getDataList() {
      var date = new Date();
      this.dateList.push(date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate(), this.subDate(date, 1), this.subDate(date, 2), this.subDate(date, 3), this.subDate(date, 4), this.subDate(date, 5), this.subDate(date, 6))
    },
    async getNewSalesNum() {
      await getNewSalesNum().then((response) => {
        console.log(response)
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

      var myChart = this.$echarts.init(document.getElementById("echart"), theme);
      var option = {
        title: {
          text: '产品销售统计前5',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: 20,
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            center: ['50%', '60%'],
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 20,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [this.sales[0], this.sales[1], this.sales[2], this.sales[3], this.sales[4]]
          }
        ]
      };
      myChart.setOption(option);


      var myChart2 = this.$echarts.init(
          document.getElementById("echart2"), theme
      );
      var option2 = {
        title: {
          text: '产品销售统计前5趋势',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        xAxis: [
          {
            type: 'category',
            data: [
              this.sales[4].name, this.sales[3].name, this.sales[2].name, this.sales[1].name, this.sales[0].name,
            ],
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
          },
          {
            type: 'value',
          }
        ],
        series: [
          {
            name: '销量',
            type: 'bar',
            tooltip: {
              valueFormatter: function (value) {
                return value;
              }
            },
            data: [
              this.sales[4].value, this.sales[3].value, this.sales[2].value, this.sales[1].value, this.sales[0].value,
            ]
          },
          {
            type: 'line',
            yAxisIndex: 1,
            tooltip: {
              valueFormatter: function (value) {
                return value;
              }
            },
            data: [
              this.sales[4].value, this.sales[3].value, this.sales[2].value, this.sales[1].value, this.sales[0].value,
            ],
            smooth: {}
          }
        ]
      };
      myChart2.setOption(option2);


      var myChart3 = this.$echarts.init(
          document.getElementById("echart3"), theme
      );
      var option3 = {
        title: {
          text: '订单转化图'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c}%'
        },
        series: [
          {
            type: 'funnel',
            left: '10%',
            top: 60,
            bottom: 60,
            width: '80%',
            sort: 'descending',
            gap: 2,
            label: {
              show: true,
              position: 'inside'
            },
            labelLine: {
              length: 10,
              lineStyle: {
                width: 1,
                type: 'solid'
              }
            },
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 1
            },
            emphasis: {
              label: {
                fontSize: 20
              }
            },
            data: [
              {value: this.orderNum, name: '已下单'},
              {value: this.salesNum, name: '已付款'},
              {value: this.useNum, name: '已使用'}
            ]
          }
        ]
      };
      myChart3.setOption(option3);


      var myChart4 = this.$echarts.init(
          document.getElementById("echart4"), theme
      );
      var option4 = {
        title: {
          text: '7日下单统计'
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
        xAxis: {
          type: 'category',
          data: [this.dateList[6], this.dateList[5], this.dateList[4], this.dateList[3], this.dateList[2], this.dateList[1], this.dateList[0]]
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: "7日下单统计",
            data: [this.totalList[6], this.totalList[5], this.totalList[4], this.totalList[3], this.totalList[2], this.totalList[1], this.totalList[0]],
            type: 'line',
            areaStyle: {},
            // stack: 'Total',
            emphasis: {
              focus: 'series'
            },
          },
        ]
      };
      myChart4.setOption(option4);
    }
  }
}

</script>


<template>

  <div id="echart4" class="echarts2"></div>
  <div id="echart" class="echarts"></div>
  <div id="echart2" class="echarts"></div>
  <div id="echart3" class="echarts"></div>


</template>


<style scoped>

.echarts {
  display: inline-block;
  margin-left: 20px;
  margin-top: 10px;
  padding: 16px;
  box-sizing: border-box;
  width: 400px;
  height: 600px;
  background-color: #fff;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
}

.echarts2 {
  display: inline-block;
  margin-left: 20px;
  margin-top: 10px;
  padding: 16px;
  box-sizing: border-box;
  width: 1200px;
  height: 600px;
  background-color: #fff;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
}

</style>