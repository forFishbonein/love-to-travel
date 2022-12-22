<script lang="ts" setup>
import * as d3 from "d3";
import { ref, onMounted } from "vue";
import { getCanvasAllCitysInfo } from "@/apis/travelService/canvas";
import { useRouter } from "vue-router";
import DetailPanel from "./DetailPanel.vue";
const router = useRouter();
const width = 1200;
const height = 700;
const colorList = ["blue", "#e8604c", "#f89898"];
const getCanvasDataAndInitNode = async () => {
  await getCanvasAllCitysInfo()
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        let nodesInfo = [];
        // let linksInfo = [];
        res.data.forEach((e) => {
          // @ts-ignore
          nodesInfo.push({
            id: e.obj.name,
            group: e.group,
          });
        });
        // const strings = nodesInfo.map((item) => JSON.stringify(item));
        // const removeDupList = [...new Set(strings)]; //也可以使用Array.from(new Set(strings))
        // const result = removeDupList.map((item) => JSON.parse(item));
        // @ts-ignore
        canvasData.nodes3 = nodesInfo;
        // canvasData.links3 = linksInfo;
        // canvasData.nodes3.push(nodesInfo[0]);
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

const canvasData = {
  nodes3: [],
  links3: [],
};
let nodes = [];
let links = [];
let simulation = null;
let nodesName = [];
let linksName = [];

const goToSeeTheCitysInfo = ($event, d) => {
  router.replace({
    name: "CanvasDetail",
    //@ts-ignore
    params: {
      cityName: d.id,
    },
  });
};

const initGraph = (data) => {
  console.log("+++++++++++");
  console.log(data);
  console.log("+++++++++++");
  const links2 = data.links3.map((d) => Object.create(d));
  const nodes2 = data.nodes3.map((d) => Object.create(d));
  // @ts-ignore
  simulation = d3
    .forceSimulation(nodes2)
    .force(
      "link",
      // @ts-ignore
      d3
        .forceLink(links2)
        // @ts-ignore
        .id((d) => d.id)
        .distance(10) //距离
    )
    .force(
      "collide",
      d3.forceCollide().radius(() => 50) //碰撞半径，即点与点之间的距离
    )
    .force("charge", d3.forceManyBody().strength(-10)) //斥力的强度
    .force("center", d3.forceCenter(width / 2, height / 2.4));

  /* 实现缩放 */
  const zoom = d3
    .zoom()
    .on("zoom", (event) => {
      g.attr("transform", event.transform);
    })
    .scaleExtent([1, 10]);
  /* 放到指定的容器里面 */
  const svg = d3
    .select("#canvas-container")
    .append("svg")
    .attr("viewBox", [0, 0, width, height])
    // @ts-ignore
    .call(zoom); //绑定缩放

  /* g标签 */
  const g = svg.append("g");

  // @ts-ignore
  nodes = g
    .append("g")
    .attr("fill", "#e8604c") //填充的颜色
    .attr("stroke", " #f3d19e") //光晕效果的颜色
    .attr("stroke-width", 2)
    .selectAll("circle")
    .data(nodes2) //这个放置的顺序不能变
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
    .on("click", goToSeeTheCitysInfo)
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
  nodes.append("title").text((d) => d.id);

  // @ts-ignore
  nodesName = g
    .append("g")
    .selectAll("text")
    .data(nodes2)
    .join("text")
    .text(function (d) {
      // @ts-ignore
      return d.id;
    })
    // .attr("dx", -35)
    .attr("dy", 25)
    .attr("class", "node-name")
    .style("text-anchor", "middle") //样式
    .style("font-size", "12px");

  //控制仿真效果
  // @ts-ignore
  simulation.on("tick", () => {
    // @ts-ignore
    nodes.attr("cx", (d) => d.x).attr("cy", (d) => d.y);
    // @ts-ignore
    nodesName.attr("x", (d) => d.x).attr("y", (d) => d.y);
  });
};

//统一颜色
const color = (d) => {
  return colorList[d.group]; //取第一个颜色
};

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
    if (!event.active) simulation.alphaTarget(0); //0就是不上浮不下沉
    event.subject.fx = null; //置空就会固定形状
    event.subject.fy = null;
  }

  return d3
    .drag()
    .on("start", dragstarted)
    .on("drag", dragged)
    .on("end", dragended);
};

onMounted(() => {});
</script>
<template>
  <!-- <div class="node-info-container"></div> -->
  <div id="canvas-container"></div>
  <!-- <div class="operation-panel-container"> -->
  <!-- <detail-panel
  ></detail-panel> -->
  <!-- </div> -->
</template>

<style lang="scss" scoped>
// .node-info-container {
//   width: 150px;
//   height: 400px;
//   position: fixed;
//   top: 150px;
//   left: 0;
//   // border: 1px #e8604c solid;
//   border-top-right-radius: 10px;
//   border-bottom-right-radius: 10px;
//   box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
// }
// .operation-panel-container {
//   width: 130px;
//   height: 400px;
//   position: fixed;
//   top: 150px;
//   right: 3px;
//   // border: 1px #e8604c solid;
//   border-top-left-radius: 10px;
//   border-bottom-left-radius: 10px;
//   box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
// }
#canvas-container {
  // margin-top: 100px;
  width: 1200px;
  height: 700px;
  background-color: #f7f2ea;
  // display: flex;
  // justify-content: center;
  margin: 20px auto;
  // margin-left: 35px;
  border-radius: 10px;
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
}
// svg text {
//   pointer-event: none;
// }
</style>
