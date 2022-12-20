<script>
import {getSales} from '@/apis/statisticManage/rank.js'

export default {
  data() {
    return {
      sales: {}


    }
  },
  mounted() {
    this.getSales();

  },

  methods: {
    async getSales() {
      await getSales().then((response) => {
        console.log(response)

        this.sales = response.data


      })

      this.getLoadEcharts();
      window.onresize = () => {
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.$echarts.init(document.getElementById('echart'));
        myChart.resize();
      };

      console.log(this.sales)


    },


    getLoadEcharts() {
      var myChart = this.$echarts.init(
          document.getElementById("echart")
      );
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
            data: this.sales
          }
        ]
      };
      myChart.setOption(option);


      var myChart2 = this.$echarts.init(
          document.getElementById("echart2")
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
              this.sales[4].name,this.sales[3].name,this.sales[2].name,this.sales[1].name,this.sales[0].name,
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
              this.sales[4].value,this.sales[3].value,this.sales[2].value,this.sales[1].value,this.sales[0].value,
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
              this.sales[4].value,this.sales[3].value,this.sales[2].value,this.sales[1].value,this.sales[0].value,
            ]
          }
        ]
      };
      myChart2.setOption(option2);
    }
  }
}

</script>


<template>

  <div id="echart" class="echarts"></div>
  <div id="echart2" class="echarts"></div>

</template>


<style scoped>

.echarts {
  display: inline-block;
  margin-left: 20px;
  margin-top: 10px;
  padding: 16px;
  box-sizing: border-box;
  width: 600px;
  height: 600px;
  background-color: #fff;
  box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
}

</style>