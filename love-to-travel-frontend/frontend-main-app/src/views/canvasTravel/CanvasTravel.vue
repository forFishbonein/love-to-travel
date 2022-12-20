<script lang="ts" setup>
import * as d3 from "d3";
import { ref, onMounted } from "vue";
import {
  getCanvasAllInfo,
  getCanvasAllInfoByName,
  getCanvasAllInfoByNameAndRelation,
} from "@/apis/travelService/canvas";
import DetailPanel from "./DetailPanel.vue";
import { getCurrentInstance } from "@vue/runtime-core";
const currentInstance = getCurrentInstance();
const width = 1050;
const height = 700;
const colorList = [
  "blue",
  "#e8604c",
  "#f89898",
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
        let nodesInfo = [];
        let linksInfo = [];
        res.data.forEach((e) => {
          // @ts-ignore
          nodesInfo.push({
            id: e.obj.name,
            group: e.group,
            level: e.obj.level,
            opentime: e.obj.opentime,
            score: e.obj.score,
            ticket: e.obj.ticket,
          });
          // if (e.obj.sceneryHasDet.length !== 0) {
          //   e.obj.sceneryHasDet.forEach((e2) => {
          //     // @ts-ignore
          //     linksInfo.push({
          //       source: e.obj.name,
          //       target: e2.name,
          //       relationship: "景区特色",
          //     });
          //     // @ts-ignore
          //     // 注意：这里是需要把关联节点加到节点里面的！
          //     nodesInfo.push({
          //       id: e2.name,
          //       group: 1,
          //     });
          //   });
          // }
          // if (e.obj.sceneryLocated.length !== 0) {
          //   e.obj.sceneryLocated.forEach((e3) => {
          //     // @ts-ignore
          //     linksInfo.push({
          //       source: e.obj.name,
          //       target: e3.name,
          //       relationship: "所在地区",
          //     });
          //     // @ts-ignore
          //     nodesInfo.push({
          //       id: e3.name,
          //       group: 1,
          //     });
          //   });
          // }
          // if (e.obj.sceneryReFood.length !== 0) {
          //   e.obj.sceneryReFood.forEach((e4) => {
          //     // @ts-ignore
          //     linksInfo.push({
          //       source: e.obj.name,
          //       target: e4.name,
          //       relationship: "推荐美食",
          //     });
          //     // @ts-ignore
          //     nodesInfo.push({
          //       id: e4.name,
          //       group: 1,
          //     });
          //   });
          // }
          // if (e.obj.sceneryRelHistory.length !== 0) {
          //   e.obj.sceneryRelHistory.forEach((e5) => {
          //     // @ts-ignore
          //     linksInfo.push({
          //       source: e.obj.name,
          //       target: e5.name,
          //       relationship: "相关历史",
          //     });
          //     // @ts-ignore
          //     nodesInfo.push({
          //       id: e5.name,
          //       group: 1,
          //     });
          //   });
          // }
        });
        const strings = nodesInfo.map((item) => JSON.stringify(item));
        const removeDupList = [...new Set(strings)]; //也可以使用Array.from(new Set(strings))
        const result = removeDupList.map((item) => JSON.parse(item));
        // @ts-ignore
        canvasData.nodes3 = result;
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

const requestNodeRelByName = async ($event, d) => {
  // console.log($event);
  // console.log(d.id); //拿到name
  await getCanvasAllInfoByName(d.id)
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
        let nodesInfo = [];
        let e = res.data;
        // @ts-ignore
        // nodesInfo.push({
        //   id: e.obj.name,
        //   group: 1,
        // });
        if (e.obj.sceneryHasDet.length !== 0) {
          e.obj.sceneryHasDet.forEach((e2) => {
            // @ts-ignore
            linksInfo.push({
              source: e.obj.name,
              target: e2.name,
              relationship: "景区特色",
            });
            // @ts-ignore
            nodesInfo.push({
              id: e2.name,
              group: 2,
            });
          });
        }
        if (e.obj.sceneryLocated.length !== 0) {
          e.obj.sceneryLocated.forEach((e3) => {
            // @ts-ignore
            linksInfo.push({
              source: e.obj.name,
              target: e3.name,
              relationship: "所在地区",
            });
            // @ts-ignore
            nodesInfo.push({
              id: e3.name,
              group: 2,
            });
          });
        }
        if (e.obj.sceneryReFood.length !== 0) {
          e.obj.sceneryReFood.forEach((e4) => {
            // @ts-ignore
            linksInfo.push({
              source: e.obj.name,
              target: e4.name,
              relationship: "推荐美食",
            });
            // @ts-ignore
            nodesInfo.push({
              id: e4.name,
              group: 2,
            });
          });
        }
        if (e.obj.sceneryRelHistory.length !== 0) {
          e.obj.sceneryRelHistory.forEach((e5) => {
            // @ts-ignore
            linksInfo.push({
              source: e.obj.name,
              target: e5.name,
              relationship: "相关历史",
            });
            // @ts-ignore
            nodesInfo.push({
              id: e5.name,
              group: 2,
            });
          });
        }
        const strings = nodesInfo.map((item) => JSON.stringify(item));
        const removeDupList = [...new Set(strings)]; //也可以使用Array.from(new Set(strings))
        const result = removeDupList.map((item) => JSON.parse(item));

        // @ts-ignore
        canvasData.nodes3.push.apply(canvasData.nodes3, result); //合并数组
        // canvasData.nodes3 = result; //直接覆盖原来的，但是原来的不会消失，只是作为新的加入原来的
        // canvasData.links3 = linksInfo;
        canvasData.links3.push.apply(canvasData.links3, linksInfo);
        // alert("完成");
        console.log("canvasData");
        console.log(canvasData);
        console.log("canvasData");
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });
  console.log("-----------");
  console.log(canvasData);
  console.log("-----------");
  // debugger
  // @ts-ignore
  currentInstance.ctx.$refs.detailPanel.currentNode = [
    d.id,
    d.level,
    d.score,
    d.ticket,
    d.opentime,
  ];
  // @ts-ignore
  currentInstance.ctx.$refs.detailPanel.ifShow = true;
  updateGraph(canvasData);
};
// const reClickMethod = ($event, d) => {
//   // debugger
//   // @ts-ignore
//   currentInstance.ctx.$refs.detailPanel.currentNode = [
//     d.id,
//     d.level,
//     d.score,
//     d.ticket,
//     d.opentime,
//   ];
//   // @ts-ignore
//   currentInstance.ctx.$refs.detailPanel.ifShow = true;
// };

const useQueryResult = (result, currentNode, currentType) => {
  let currentText = "";
  // alert(currentType);
  if (currentType === "is_detail") {
    currentText = "景区特色";
  } else if (currentType === "in_area") {
    currentText = "所在地区";
  } else if (currentType === "recommand_food") {
    currentText = "推荐美食";
  } else if (currentType === "is_related") {
    currentText = "相关历史";
  }
  // alert(currentText);
  let new_links = [];
  let will_del_links = [];
  let new_nodes = [];
  for (let i = 0; i < canvasData.links3.length; i++) {
    /*  &&
      // @ts-ignore
      canvasData.links3[i].relationship === currentType */
    if (
      // @ts-ignore
      canvasData.links3[i].source !== currentNode
    ) {
      // alert(i);
      //删除所有的相关的links
      // canvasData.links3.splice(i, 1); //会返回新的数组
      new_links.push(canvasData.links3[i]);
    } else {
      // @ts-ignore
      will_del_links.push(canvasData.links3[i].target);
    }
  }
  // 只能少补
  for (let j = 0; j < canvasData.nodes3.length; j++) {
    will_del_links.forEach((e) => {
      // @ts-ignore
      if (canvasData.nodes3[j].id !== e) {
        new_nodes.push(canvasData.nodes3[j]);
      }
    });
  }
  const strings = new_nodes.map((item) => JSON.stringify(item));
  const removeDupList = [...new Set(strings)]; //也可以使用Array.from(new Set(strings))
  const results = removeDupList.map((item) => JSON.parse(item));
  // @ts-ignore
  canvasData.nodes3 = results;
  canvasData.links3 = new_links;
  console.log("清空");
  console.log(canvasData.nodes3);
  console.log(canvasData.links3);
  console.log("清空");
  let linksInfo = [];
  let nodesInfo = [];
  result.forEach((e) => {
    // @ts-ignore
    nodesInfo.push({
      id: e.id,
      group: e.group,
    });
    // @ts-ignore
    linksInfo.push({
      source: currentNode,
      target: e.id,
      relationship: currentText,
    });
  });
  console.log(linksInfo);
  // console.log(nodesInfo);
  // const strings = linksInfo.map((item) => JSON.stringify(item));
  // const removeDupList = [...new Set(strings)]; //也可以使用Array.from(new Set(strings))
  // const results = removeDupList.map((item) => JSON.parse(item));
  // @ts-ignore
  canvasData.nodes3.push.apply(canvasData.nodes3, nodesInfo); //合并数组
  canvasData.links3.push.apply(canvasData.links3, linksInfo);
  const strings2 = canvasData.nodes3.map((item) => JSON.stringify(item));
  const removeDupList2 = [...new Set(strings2)]; //也可以使用Array.from(new Set(strings))
  const results2 = removeDupList2.map((item) => JSON.parse(item));
  const strings3 = canvasData.links3.map((item) => JSON.stringify(item));
  const removeDupList3 = [...new Set(strings3)]; //也可以使用Array.from(new Set(strings))
  const results3 = removeDupList3.map((item) => JSON.parse(item));
  // @ts-ignore
  canvasData.nodes3 = results2;
  // @ts-ignore
  canvasData.links3 = results3;
  // alert(1111111);
  updateGraph(canvasData);
};
const canvasData = {
  nodes3: [],
  links3: [],
};
let nodes = [];
let links = [];
let simulation = null;
let nodesName = [];
let linksName = [];

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
  // svg.call(
  //   d3.zoom().on("zoom", function () {
  //     svg.attr("transform", d3.event.transform);
  //   })
  // );
  const positiveMarker = svg
    .append("marker")
    .attr("id", "positiveMarker")
    .attr("orient", "auto")
    .attr("stroke-width", 2)
    .attr("markerUnits", "strokeWidth")
    .attr("markerUnits", "strokeSpaceOnUse")
    .attr("viewBox", "0 -5 10 10")
    .attr("refX", 23) //让箭头显现
    .attr("refY", 0)
    .attr("markerWidth", 12)
    .attr("markerHeight", 12)
    .append("path")
    .attr("d", "M 0 -5 L 10 0 L 0 5") //绘制一个三角形
    .attr("fill", "#999")
    .attr("stroke-opacity", 0.6);

  const negativeMarker = svg
    .append("marker")
    .attr("id", "negativeMarker")
    .attr("orient", "auto")
    .attr("stroke-width", 2)
    .attr("markerUnits", "strokeWidth")
    .attr("markerUnits", "strokeSpaceOnUse")
    .attr("viewBox", "0 -5 10 10")
    .attr("refX", -15) //让箭头显现
    .attr("refY", 0)
    .attr("markerWidth", 12)
    .attr("markerHeight", 12)
    .append("path")
    .attr("d", "M 10 -5 L 0 0 L 10 5")
    .attr("fill", "#999")
    .attr("stroke-opacity", 0.6);
  /* g标签 */
  const g = svg.append("g");

  /* 先线后点 */
  /* 连线的颜色等 */
  /* , function (d) {
      // @ts-ignore
      if (typeof d.source === "object") {
        // @ts-ignore
        return d.source.id + "_" + d.relationship + "_" + d.target.id;
      } else {
        // @ts-ignore
        return d.source + "_" + d.relationship + "_" + d.target;
      }
    } */
  // @ts-ignore
  links = g
    .append("g")
    .attr("stroke", "#999")
    .attr("stroke-width", 2) //粗细固定值
    .attr("stroke-width", 1)
    .attr("stroke-opacity", 0.6) //透明度
    // .attr("marker-end", "url(#positiveMarker)")
    .selectAll("path")
    .data(links2)
    .join("path")
    // @ts-ignore
    .attr("id", function (d) {
      // @ts-ignore
      // if (typeof d.source === "object") {
      //   // @ts-ignore
      //   return d.source.id + "_" + d.relationship + "_" + d.target.id;
      // } else {
      //   // @ts-ignore
      return d.source + "_" + d.relationship + "_" + d.target;
      // }
    }); //一对关系的id是唯一的
  // .attr("stroke-width", (d) => Math.sqrt(d.value)); //粗细由link的value决定

  // @ts-ignore
  linksName = g
    .append("g")
    .selectAll("text")
    .data(links)
    .join("text")
    // .attr("x", 30)
    // .attr("y", 70)
    .style("text-anchor", "middle")
    .style("fill", "#303133")
    .style("font-size", "10px")
    .style("font-weight", "blod")
    .append("textPath")
    .attr(
      "xlink:href",
      // @ts-ignore
      (d) => "#" + d.source + "_" + d.relationship + "_" + d.target //文字的路径跟随link
    )
    .attr("startOffset", "50%")
    .text(function (d) {
      // @ts-ignore
      return d.relationship; //内容为relationship
    });

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
    // @ts-ignore  //path格式
    // links.attr(
    //   "d",
    //   (d) =>
    //     " M " +
    //     d.source.x +
    //     " " +
    //     d.source.y +
    //     " L " +
    //     d.target.x +
    //     " " +
    //     d.target.y
    // );
    // 确定原点终点的path格式
    links
      // @ts-ignore
      .attr("d", function (d) {
        if (d.source.x < d.target.x) {
          return (
            " M " +
            d.source.x +
            " " +
            d.source.y +
            " L " +
            d.target.x +
            " " +
            d.target.y
          );
        } else {
          return (
            " M " +
            d.target.x +
            " " +
            d.target.y +
            " L " +
            d.source.x +
            " " +
            d.source.y
          );
        }
      })
      .attr("marker-end", function (d) {
        if (d.source.x < d.target.x) {
          return "url(#positiveMarker)";
        } else {
          return null;
        }
      })
      .attr("marker-start", function (d) {
        if (d.source.x < d.target.x) {
          return null;
        } else {
          return "url(#negativeMarker)";
        }
      });

    // line格式
    // // @ts-ignore
    // .attr("x1", (d) => d.source.x)
    // // @ts-ignore
    // .attr("y1", (d) => d.source.y)
    // // @ts-ignore
    // .attr("x2", (d) => d.target.x)
    // // @ts-ignore
    // .attr("y2", (d) => d.target.y);
    // @ts-ignore
    nodes.attr("cx", (d) => d.x).attr("cy", (d) => d.y);
    // @ts-ignore
    nodesName.attr("x", (d) => d.x).attr("y", (d) => d.y);
  });
};

const updateGraph = (data) => {
  console.log("==========");
  console.log(data);
  console.log("==========");
  const links2 = data.links3.map((d) => Object.create(d));
  const nodes2 = data.nodes3.map((d) => Object.create(d));
  // const nodes2 = Array.from(data.nodes3).map((d) => Object.create(d));
  // nodes
  //   // @ts-ignore
  //   .data(nodes2)
  //   .exit() //这是多退的功能，多出来了就直接移除掉
  //   .remove();
  nodes = nodes
    // @ts-ignore
    .data(nodes2) //这个放置的顺序不能变
    // .enter() //多退少补中少补的功能，补充页面元素
    // .append("circle")
    .join("circle")
    .attr("fill", color) //填充的颜色
    .attr("r", 15)
    .attr("class", "node")
    .merge(nodes)
    // .on("click", requestNodeRelByName) //不用再绑定了
    .on("mouseover", function () {
      // @ts-ignore
      d3.select(this).attr("stroke-width", 3);
    })
    .on("mouseout", function () {
      // @ts-ignore
      d3.select(this).attr("stroke-width", 2);
    })
    // @ts-ignore
    .call(drag(simulation)); //拖动的函数

  // links
  //   // @ts-ignore
  //   .data(links2)
  //   .exit() //这是多退的功能，多出来了就直接移除掉
  //   .remove();
  // @ts-ignore
  links = links
    // @ts-ignore
    .data(links2)
    // .enter() //对于新数据，有不足的地方再补上
    // .append("path")
    .join("path")
    .attr("stroke", "#999")
    .attr("stroke-width", 2) //粗细固定值
    .attr("stroke-width", 1) //粗细固定值
    .attr("stroke-opacity", 0.6) //透明度
    // .attr("marker-end", "url(#positiveMarker)")
    .merge(links)
    .attr("id", (d) => d.source + "_" + d.relationship + "_" + d.target)
    .attr("class", "link");

  // linksName
  //   // @ts-ignore
  //   .data(links2)
  //   .exit() //这是多退的功能，多出来了就直接移除掉
  //   .remove();
  // @ts-ignore
  linksName = linksName
    // @ts-ignore
    .data(links2)
    // .enter()
    // .append("text")
    .join("text")
    // .attr("x", 25)
    // .attr("y", 70)
    .style("text-anchor", "middle")
    .style("fill", "#303133")
    .style("font-size", "10px")
    .style("font-weight", "blod")
    .append("textPath")
    .attr(
      "xlink:href",
      // @ts-ignore
      (d) => "#" + d.source + "_" + d.relationship + "_" + d.target //文字的路径跟随link
    )
    .attr("startOffset", "50%")
    .merge(linksName)
    .text(function (d) {
      // @ts-ignore
      return d.relationship; //内容为relationship
    });

  //可以悬浮出现内容
  // @ts-ignore
  nodes.append("title").text((d) => d.id);

  // @ts-ignore
  nodesName = nodesName
    // @ts-ignore
    .data(nodes2)
    // .enter()
    // .append("text")
    .join("text")
    .merge(nodesName)
    .text(function (d) {
      // @ts-ignore
      return d.id;
    })
    // .attr("dx", -35)
    .attr("dy", 25)
    .attr("class", "node-name")
    .style("text-anchor", "middle") //样式
    .style("font-size", "12px");

  // @ts-ignore
  simulation.nodes(nodes2);
  // @ts-ignore
  simulation.force("link").links(links2);
  // @ts-ignore
  simulation.alpha(0.2).restart(); //alpha会衰减到一定程度，力停止
};

//统一颜色
const color = (d) => {
  return colorList[d.group]; //取第一个颜色
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
  <!-- <div class="node-info-container"></div> -->
  <div id="canvas-container"></div>
  <!-- <div class="operation-panel-container"> -->
  <detail-panel
    ref="detailPanel"
    @queryRelation="useQueryResult"
  ></detail-panel>
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
