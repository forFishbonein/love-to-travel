<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getOneProductInfoById,buyOneProduct } from "@apis/travelService/product";
import { productInfoType ,routeInfoType} from "@/apis/interface/myInterface";
import { subPlansFormat } from "@/utils/filters/subPlan";
import { timeFormat } from "@/utils/filters/time";
import AMapLoader from "@amap/amap-jsapi-loader"; // 使用加载器加载JSAPI，可以避免异步加载、重复加载等常见错误加载错误
import { shallowRef } from "@vue/reactivity";
import { mainStore } from "@/store/user";
import { useRouter } from "vue-router";
import { getNowTime } from "@/utils/getNowTime"
const router = useRouter();
const store = mainStore();
const props = defineProps<{
  productId: string;
}>();
// alert(props.cityId);
const productId = props.productId;
const productInfo = ref({} as productInfoType);
const requestOneProductInfo = async () => {
  await getOneProductInfoById(productId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("得到数据了2！");
        productInfo.value = res.data;
        console.log(productInfo.value);
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
/* 高德地图实现 */
const map = shallowRef(null);
// 初始化地图方法
function initMap(routeInfo?: routeInfoType[]) {
  AMapLoader.load({
    key: "c3e09b00e68a28103b9377d18f5c2101", // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: [
      "AMap.Scale", //工具条，控制地图的缩放、平移等
      "AMap.ToolBar", //比例尺，显示当前地图中心的比例尺
    ], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
  })
    .then((AMap) => {
      let map = new AMap.Map("map", {
        //设置地图容器id
        zoom: 8.5, //初始化地图层级
        viewMode: "3D", //是否为3D地图模式
        // center: [116.397436, 39.909165], //初始化地图中心点位置，北京
        // @ts-ignore
        center: routeInfo[0].origin, //初始化地图中心点位置，其中一个景点
        dragEnable: true, //禁止鼠标拖拽
        scrollWheel: true, //鼠标滚轮放大缩小
        doubleClickZoom: true, //双击放大缩小
        keyboardEnable: true, //键盘控制放大缩小移动旋转
      });
      map.setDefaultCursor("pointer"); //使用CSS默认样式定义地图上的鼠标样式（default/pointer/move/crosshair）
      map.addControl(new AMap.Scale()); //异步同时加载多个插件
      map.addControl(new AMap.ToolBar());

      let Obj = {
        markerList: [],
        windowList: [],
      };
      if (routeInfo) {
        // 封装一下
        routeInfo.forEach((e: routeInfoType) => {
          // var weather = new AMap.Weather();
          // let info = {};
          // //执行实时天气信息查询
          // weather.getLive(e.toCity, function (err, data) {
          //   console.log(err, data);
          //   info = data;
          // });
          Obj.markerList.push(
            // @ts-ignore
            new AMap.Marker({
              position: new AMap.LngLat(e.origin[0],e.origin[1])
            })
          );
          Obj.windowList.push(
            // @ts-ignore
            new AMap.InfoWindow({
              //创建信息窗体
              isCustom: false, //使用自定义窗体
              anchor: "top-right", //信息窗体的三角所在位置
              content: `<p style="color:#e8604c;margin:0">目的景区：${e.originName}</p>
            <p style="color:#e8604c;margin:0">出发时间：${e.departTime}</p>
            <p style="color:#e8604c;margin:0">交通工具：${e.vehicle}</p>`, //信息窗体的内容可以是任意html片段
              offset: new AMap.Pixel(0, 0), //不需要偏移量
            })
          );
        });

        map.add(Obj.markerList); // 地图添加标记
        for (let i = 0; i < Obj.markerList.length; i++) {
          // @ts-ignore
          Obj.markerList[i].on("click", () => {
            // @ts-ignore
            Obj.windowList[i].open(map, Obj.markerList[i].getPosition());
            map.on("click", () => {
              // @ts-ignore
              Obj.windowList[i] && Obj.windowList[i].close();
            });
          });
        }
      }

      // @ts-ignore
      AMapLoader.load({
        //可多次调用load
        plugins: ["AMap.MapType"],
      })
        .then((AMap) => {
          map.addControl(new AMap.MapType());
        })
        .catch((e) => {
          console.error(e);
        });
    })
    .catch((e) => {
      console.log(e);
    });
}
const theMapVisible = ref(false)
// let pointArray = [] as routeInfoType[]
const openTheMapForOneDay = (routeInfo:routeInfoType[])=>{
  // pointArray = [] as routeInfoType[]
  // route.forEach((e) => {
  //   pointArray.push(e)
  // });
  initMap(routeInfo);
  theMapVisible.value=true
}
const buyDialogVisiable = ref(false)
const buyTheProduct = ()=>{
  buyDialogVisiable.value = false
  if(store.userInfo.id){
    buyOneProduct(productId, store.userInfo.id, productInfo.value.price, getNowTime()).then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        successDialogVisible.value = true
      }
    })
    .catch((error) => {
      //@ts-ignore
      ElMessage({
        type: "error",
        message: error.message,
      });
    });

  }else{
      //@ts-ignore
      ElMessage({
    type: "error",
    message: "请先登录！",
  });
  }

}
const successDialogVisible = ref(false);
const goSeeMyBuy = () => {
  successDialogVisible.value = false;
  router.push("/personal/buy");
};
onMounted(() => {
  requestOneProductInfo();
});
</script>
<script lang="ts">
//@ts-ignore
(function ($) {
  $(document).ready(function () {
    if ($(".accrodion-grp").length) {
      var accrodionGrp = $(".accrodion-grp");
      accrodionGrp.each(function () {
        // @ts-ignore
        var accrodionName = $(this).data("grp-name");
        // @ts-ignore
        var Self = $(this);
        var accordion = Self.find(".accrodion");
        Self.addClass(accrodionName);
        Self.find(".accrodion .accrodion-content").hide();
        Self.find(".accrodion.active").find(".accrodion-content").show();
        accordion.each(function () {
          // @ts-ignore
          $(this)
            .find(".accrodion-title")
            .on("click", function () {
              // @ts-ignore
              if ($(this).parent().hasClass("active") === false) {
                $(".accrodion-grp." + accrodionName)
                  .find(".accrodion")
                  .removeClass("active");
                $(".accrodion-grp." + accrodionName)
                  .find(".accrodion")
                  .find(".accrodion-content")
                  .slideUp();
                // @ts-ignore
                $(this).parent().addClass("active");
                // @ts-ignore
                $(this).parent().find(".accrodion-content").slideDown();
              }
            });
        });
      });
    }
    if ($(".count-bar").length) {
      $(".count-bar").appear(
        function () {
          // @ts-ignore
          var el = $(this);
          var percent = el.data("percent");
          $(el).css("width", percent).addClass("counted");
        },
        {
          accY: -50,
        }
      );
    }
  });
  //@ts-ignore
})(jQuery);
</script>
<template>
  <section class="tour-details">
    <div class="tour-details__top">
      <div class="container">
        <div class="row">
          <div class="col-xl-12">
            <div class="tour-details__top-inner">
              <div class="tour-details__top-left">
                <h2 class="tour-details__top-title">
                  {{ productInfo.name }}
                </h2>
                <p class="tour-details__top-rate">
                  <span>¥{{ productInfo.price }}</span>
                </p>
              </div>
              <div class="tour-details__top-right">
                <ul class="list-unstyled tour-details__top-list">
                  <li>
                    <div class="icon">
                      <span class="icon-clock"></span>
                    </div>
                    <div class="text">
                      <p>旅行时长</p>
                      <h6>
                        {{ subPlansFormat(productInfo?.plan?.subPlans) }}天
                      </h6>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <span class="icon-place"></span>
                    </div>
                    <div class="text">
                      <p>途经城市</p>
                      <h6>{{ productInfo?.plan?.subPlans?.length }}个</h6>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <span class="icon-user"></span>
                    </div>
                    <div class="text">
                      <p>联系电话</p>
                      <h6>{{ productInfo?.tele }}</h6>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="tour-details__bottom">
      <div class="container">
        <div class="row">
          <div class="col-xl-12">
            <div class="tour-details__bottom-inner">
              <div class="tour-details__bottom-left">
                <ul class="list-unstyled tour-details__bottom-list">
                  <li>
                    <div class="icon">
                      <span class="icon-clock"></span>
                    </div>
                    <div class="text">
                      <p>发布于{{ timeFormat(productInfo.createTime) }}</p>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="tour-details__bottom-right">
                <a href="#">goTop</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <section class="about-page">
    <div class="container">
      <div class="row">
        <div class="col-xl-6">
          <div class="about-page__left">
            <div class="about-page__img">
              <img :src="productInfo.url" alt="" />
            </div>
          </div>
        </div>
        <div class="col-xl-6">
          <div class="about-page__right">
            <div class="section-title text-left">
              <span class="section-title__tagline">Product Information</span>
              <h2 class="section-title__title">产品简介</h2>
            </div>
            <p class="about-page__text-1">
              爱旅游旨在为您提供优质的服务与旅游保障
            </p>
            <p class="about-page__text-2">
              {{ productInfo.introduction }}
            </p>
            <div class="about-page__progress">
              <div class="about-page__progress-single">
                <h4 class="about-page__progress-title">好评度</h4>
                <div class="bar">
                  <div class="bar-inner count-bar" data-percent="77%">
                    <div class="count-text">77%</div>
                  </div>
                </div>
              </div>
              <div class="about-page__progress-single">
                <h4 class="about-page__progress-title">库存剩余</h4>
                <div class="bar marb-0">
                  <div class="bar-inner count-bar" data-percent="38%">
                    <div class="count-text">38%</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <section class="tour-details">
    <div class="tour-details__top">
      <div class="container">
        <div class="row">
          <div class="tour-details-two__overview-bottom">
            <h3 class="tour-details-two-overview__title">包含/不包含</h3>
            <div class="tour-details-two__overview-bottom-inner">
              <div class="tour-details-two__overview-bottom-left">
                <ul
                  class="list-unstyled tour-details-two__overview-bottom-list"
                >
                  <li>
                    <div class="icon">
                      <i class="fa fa-check"></i>
                    </div>
                    <div class="text">
                      <p>专业的团队服务</p>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <i class="fa fa-check"></i>
                    </div>
                    <div class="text">
                      <p>优质的行程规划</p>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <i class="fa fa-check"></i>
                    </div>
                    <div class="text">
                      <p>贴心的全程导游</p>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <i class="fa fa-check"></i>
                    </div>
                    <div class="text">
                      <p>机票/火车票</p>
                    </div>
                  </li>
                </ul>
              </div>
              <div class="tour-details-two__overview-bottom-right">
                <ul
                  class="list-unstyled tour-details-two__overview-bottom-right-list"
                >
                  <li>
                    <div class="icon">
                      <i class="fa fa-times"></i>
                    </div>
                    <div class="text">
                      <p>住宿费</p>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <i class="fa fa-times"></i>
                    </div>
                    <div class="text">
                      <p>景点门票</p>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <i class="fa fa-times"></i>
                    </div>
                    <div class="text">
                      <p>食物 & 饮料</p>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <i class="fa fa-times"></i>
                    </div>
                    <div class="text">
                      <p>个人旅行物品</p>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="tour-details-two__tour-plan">
            <h3 class="tour-details-two__title">产品行程计划</h3>
            <div class="accrodion-grp" data-grp-name="faq-one-accrodion">
              <div
                class="accrodion active"
                v-for="item in productInfo.plan?.subPlans"
              >
                <div class="accrodion-title">
                  <h4>
                    <span>{{ item.city }}</span> &nbsp;&nbsp;&nbsp;预算:&nbsp;{{
                      item.budget || "-"
                    }}元 &nbsp;&nbsp;&nbsp;时长:&nbsp;{{
                      item.days?.length || "-"
                    }}天
                  </h4>
                </div>
                <div class="accrodion-content">
                  <div class="inner">
                    <p v-for="(i,index) in item.days">
                      <span class="cityname-span">第{{index+1}}天
                      <div class="open-map-oneday" @click="openTheMapForOneDay(i.route)">
                        查看该日行程地图
                      </div>
                      </span>
                      <ul v-for="j in i.route" class="list-unstyled">
                        <li>
                          <span class="span-style-front">景点</span>{{j.originName}}
                          <span class="span-style-front">出行时间</span>{{j.departTime}}
                          <span class="span-style-front">交通工具</span>{{j.vehicle}}
                        </li>
                      </ul>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <section class="book-now">
    <div
      class="book-now-shape"
      style="background-image: url(/images/shapes/book-now-shape.png)"
    ></div>
    <div class="container">
      <div class="row">
        <div class="col-xl-12">
          <div class="book-now__inner">
            <div class="book-now__left">
              <p>Buy our products</p>
              <h2>这是你的心动产品吗？</h2>
            </div>
            <div class="book-now__right">
              <a href="javascript:;" class="thm-btn book-now__btn" @click="buyDialogVisiable = true">点击购买</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <el-dialog v-model="theMapVisible" :show-close="false">
      <template #header="{ close, titleId, titleClass }">
        <div class="my-header">
          <h4 :id="titleId" :class="titleClass">景区行程地图</h4>
          <el-button type="danger" @click="close">
            <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon>
            关闭
          </el-button>
        </div>
      </template>
      <div id="map"></div>
    </el-dialog>

    <el-dialog v-model="buyDialogVisiable" title="支付框">
      <div class="img-qrcode">
        <div class="img">
          <img src="/static/images/QRcode.png"/>
        </div>

      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="buyDialogVisiable = false">取消支付</el-button>
          <el-button type="primary" @click="buyTheProduct">
            支付完成
          </el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog
    v-model="successDialogVisible"
    title="成功提示"
    width="30%"
    draggable
    show-close="false"
  >
    <span>恭喜您，产品购买成功！</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="goSeeMyBuy">
          查看我的订单
        </el-button>
        <el-button type="success" @click="successDialogVisible = false;">
          继续购买
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.img-qrcode{
  width: 100%;
  height: 400px;
  display: flex;
  justify-content: center;
  .img{
    width: 300px;
  height: 400px;
  border: 1px #CDD0D6 solid;
    img{
    width: 100%;
    height: 100%;
  }
  }

}
#map {
  margin: 0px;
  width: 100%;
  height: 550px;
  // height: 100%;
  padding: 0px;
}
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.span-style-front {
  display: inline-block;
  min-width: 50px;
  padding: 5px;
  height: 30px;
  line-height: 20px;
  color: #e8604c;
  font-size: 14px;
  font-weight: 600;
  background-color: #FAF5EE;
  border-radius: 5px;
  margin-right: 10px;
  margin-left: 25px;
}
.open-map-oneday{
  width: 150px;
  height: 25px;
  border-radius: 10px;
  background-color: #FAF5EE;
  position: absolute;
  right:25px;
  color:#e8604c;
  font-size: 16px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  transition: all 0.1s linear;
}
.open-map-oneday:hover{
  box-shadow:0 2px 27px 6px rgba(0, 0, 0, .12);
color: #e74128;
}
.cityname-span {
  position: relative;
  width: 100%;
  height: 35px;
  background-color: #e8604c;
  display: inline-block;
  padding: 5px;
  color: #ffffff;
  font-weight: 700;
  font-size: 22px;
  display: flex;
  justify-content: center;
  align-items: center;
  // margin-bottom: 30px;
  border-radius: 15px;
  margin-top: 10px;
}
.about-page__text-2 {
  width: 570px;
  height: 150px;
  border: 2px #e8604c solid;
  padding: 10px;
  border-radius: 10px;
}
.about-page__img {
  width: 520px;
  height: 572px;
  cursor: pointer;
  img {
    width: 100%;
    height: 100%;
  }
}
.about-page {
  padding-bottom: 50px;
  padding-top: 50px;
}
.tour-details-two__overview-bottom {
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  align-items: center;
}
</style>
