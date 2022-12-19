<script lang="ts" setup>
import * as d3 from "d3";
import { ref, onMounted } from "vue";
import {
  getCanvasAllInfo,
  getCanvasAllInfoByName,
  getCanvasAllInfoByNameAndRelation,
} from "@/apis/travelService/canvas";
const width = 1000;
const height = 800;
const colorList = [
  "#e8604c",
  "red",
  "blue",
  "red",
  "blue",
  "red",
  "blue",
  "red",
  "blue",
  "red",
  "blue",
  "red",
  "blue",
  "red",
  "blue",
  "red",
];

// const canvasData = {
//   nodes: [],
//   links: [],
// };
const nodes = ref([]);
const links = ref([]);

const getCanvasDataAndInitNode = async () => {
  await getCanvasAllInfo()
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        let notesInfo = [];
        res.data.forEach((e) => {
          // @ts-ignore
          notesInfo.push({
            id: e.obj.id,
            level: e.obj.level,
            name: e.obj.name,
          });
        });
        nodes.value = notesInfo;
        //   axios
        //     .get("person/all")
        //     .then(function (response) {
        //       console.log(response);
        //       testGraph["nodes"] = [response.data];
        //       initGraph(testGraph);
        //     })
        //     .catch(function (error) {
        //       console.log(error);
        //     });
        // }
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  initGraph(canvasData);
};
getCanvasDataAndInitNode();

const requestNodeRelByName = ($event, d) => {
  // console.log($event);
  // console.log(d.name); //拿到name
  getCanvasAllInfoByName(d.name)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        console.log(res.data);
        let linksInfo = [];
        let e = res.data;
        if (e.obj.sceneryHasDet.length !== 0) {
          e.obj.sceneryHasDet.forEach((e2) => {
            // @ts-ignore
            linksInfo.push({
              source: e.obj.name,
              target: e2.name,
              value: "景区特色",
            });
          });
        }
        if (e.obj.sceneryLocated.length !== 0) {
          e.obj.sceneryLocated.forEach((e3) => {
            // @ts-ignore
            linksInfo.push({
              source: e.obj.name,
              target: e3.name,
              value: "所在地区",
            });
          });
        }
        if (e.obj.sceneryReFood.length !== 0) {
          e.obj.sceneryReFood.forEach((e4) => {
            // @ts-ignore
            linksInfo.push({
              source: e.obj.name,
              target: e4.name,
              value: "推荐美食",
            });
          });
        }
        if (e.obj.sceneryRelHistory.length !== 0) {
          e.obj.sceneryRelHistory.forEach((e5) => {
            // @ts-ignore
            linksInfo.push({
              source: e.obj.name,
              target: e5.name,
              value: "相关历史",
            });
          });
        }
        links.value = linksInfo;
        // alert("完成");
        console.log(links.value);
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
};

const initGraph = (data) => {
  const links = data.links.map((d) => Object.create(d));
  const nodes = data.nodes.map((d) => Object.create(d));

  const simulation = d3
    .forceSimulation(nodes)
    .force(
      "link",
      // @ts-ignore
      d3.forceLink(links).id((d) => d.id)
    )
    .force("charge", d3.forceManyBody())
    .force("center", d3.forceCenter(width / 2, height / 2.4));

  /* 实现缩放 */
  const zoom = d3
    .zoom()
    .on("zoom", (event) => {
      g.attr("transform", event.transform);
    })
    .scaleExtent([1, 40]);
  /* 放到指定的容器里面 */
  const svg = d3
    .select("#canvas-container")
    .append("svg")
    .attr("viewBox", [0, 0, width, height])
    // @ts-ignore
    .call(zoom); //绑定缩放

  // svg.call(
  //   d3.zoom().on("zoom", function () {
  //     svg.attr("transform", d3.event.transform);
  //   })
  // );

  /* g标签 */
  const g = svg.append("g");

  nodes.value = g
    .append("g")
    .attr("fill", color) //填充的颜色
    .attr("stroke", " #f3d19e") //光晕效果的颜色
    .attr("stroke-width", 2)
    .selectAll("circle")
    .data(nodes) //这个放置的顺序不能变
    // .style("pointer-events", "none")
    .join("circle")
    .attr("r", 15)
    .style("cursor", "pointer")
    // @ts-ignore
    // .on("click", function ($event, d) {
    //   // console.log($event);
    //   console.log(d.name);
    //   // console.log(d3.select(this));
    //   // @ts-ignore
    //   // console.log(d3.select(this)._groups[0][0].__data__.name); //拿到节点的数据
    //   requestNodeRelByName;
    // }) //不能加括号，写了就相当于直接执行掉，就不是绑定了
    .on("click", requestNodeRelByName)
    .on("mouseover", function () {
      // alert(1111);
      // @ts-ignore
      d3.select(this).attr("stroke-width", 3);
      // d3.select(this).transition(300).attr("fill", "orange");
    })
    .on("mouseout", function () {
      // @ts-ignore
      d3.select(this).attr("stroke-width", 2);
    })
    // .attr("class", "node-style")
    // @ts-ignore
    .call(drag(simulation)); //拖动的函数
  // @ts-ignore

  //可以悬浮出现内容
  // node.append("title").text((d) => d.id);

  const nodeNameText = g
    .append("g")
    .selectAll("text")
    .data(nodes)
    .join("text")
    .text(function (d) {
      // @ts-ignore
      return d.name;
    })
    // .attr("dx", -35)
    .attr("dy", 25)
    .attr("class", "node-name")
    .style("text-anchor", "middle") //样式
    .style("font-size", "12px");

  /* 连线的颜色等 */
  links.value = g
    .append("g")
    .attr("stroke", "#999")
    .attr("stroke-width", 2) //粗细固定值
    .attr("stroke-width", 1)
    .attr("stroke-opacity", 0.6) //透明度
    .selectAll("line")
    .data(links)
    .join("line");
  // .attr("stroke-width", (d) => Math.sqrt(d.value)); //粗细由link的value决定

  //控制仿真效果
  simulation.on("tick", () => {
    link
      // @ts-ignore
      .attr("x1", (d) => d.source.x)
      // @ts-ignore
      .attr("y1", (d) => d.source.y)
      // @ts-ignore
      .attr("x2", (d) => d.target.x)
      // @ts-ignore
      .attr("y2", (d) => d.target.y);
    // @ts-ignore
    node.attr("cx", (d) => d.x).attr("cy", (d) => d.y);
    // @ts-ignore
    nodeNameText.attr("x", (d) => d.x);
    // @ts-ignore
    nodeNameText.attr("y", (d) => d.y);
  });
};
//统一颜色
const color = (d) => {
  return colorList[0]; //取第一个颜色
};

//官网：按照分类赋予不同的颜色，即node的group属性
// color(d) {
//   // alert(111);
//   const scale = d3.scaleOrdinal(d3.schemeCategory10);
//   return (d) => scale(d.group); //取第一个颜色
// },

//拖动的三个事件
const drag = (simulation) => {
  function dragstarted(event) {
    if (!event.active) simulation.alphaTarget(0.3).restart();
    event.subject.fx = event.subject.x;
    event.subject.fy = event.subject.y;
  }

  function dragged(event) {
    event.subject.fx = event.x;
    event.subject.fy = event.y;
  }

  function dragended(event) {
    if (!event.active) simulation.alphaTarget(0);
    event.subject.fx = null;
    event.subject.fy = null;
  }

  return d3
    .drag()
    .on("start", dragstarted)
    .on("drag", dragged)
    .on("end", dragended);
};
// V5版本
// drag(simulation) {
//   function dragstarted(d) {
//     if (!d.event.active) simulation.alphaTarget(0.3).restart();
//     d.fx = d.x;
//     d.fy = d.y;
//   }

//   function dragged(d) {
//     d.fx = d3.event.x;
//     d.fy = d3.event.y;
//   }

//   function dragended(d) {
//     if (!d3.event.active) simulation.alphaTarget(0);
//     d.fx = null;
//     d.fy = null;
//   }

//   return d3
//     .drag()
//     .on("start", dragstarted)
//     .on("drag", dragged)
//     .on("end", dragended);
// },

onMounted(() => {});
</script>
<template>
  <div id="canvas-container"></div>
</template>

<style lang="scss" scoped>
#canvas-container {
  // margin-top: 100px;
  width: 100%;
  height: 100%;
  background-color: #f7f2ea;
}
// svg text {
//   pointer-event: none;
// }
</style>
