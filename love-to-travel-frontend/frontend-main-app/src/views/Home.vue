<script setup lang="ts">
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { citysInfoType, planInfoType } from "@apis/interface/iPlan";
import { getHotCitysInfo } from "@apis/travelService/city";
import { onMounted } from "@vue/runtime-core";
import { utilStore } from "@/store/util";

const store = utilStore();
const router = useRouter();
let citysInfos = [
  {
    cityId: "110000",
    cityName: "北京市",
    lng: "110",
    lat: "33",
    url: null,
    introduction: null,
  },
  {
    cityId: "140700",
    cityName: "晋中市",
    lng: "112.759595",
    lat: "37.692839",
    url: null,
    introduction: null,
  },
  {
    cityId: "150700",
    cityName: "呼伦贝尔市",
    lng: "119.784869",
    lat: "49.172733",
    url: null,
    introduction: null,
  },
  {
    cityId: "210200",
    cityName: "大连市",
    lng: "121.621632",
    lat: "38.918954",
    url: null,
    introduction: null,
  },
  {
    cityId: "310000",
    cityName: "上海市",
    lng: "121.480539",
    lat: "31.235929",
    url: null,
    introduction: null,
  },
  {
    cityId: "330100",
    cityName: "杭州市",
    lng: "120.215512",
    lat: "30.253083",
    url: null,
    introduction: null,
  },
  {
    cityId: "330200",
    cityName: "宁波市",
    lng: "121.630843",
    lat: "29.866068",
    url: null,
    introduction: null,
  },
  {
    cityId: "330500",
    cityName: "湖州市",
    lng: "120.094517",
    lat: "30.898964",
    url: null,
    introduction: null,
  },
  {
    cityId: "361100",
    cityName: "上饶市",
    lng: "117.94946",
    lat: "28.460626",
    url: null,
    introduction: null,
  },
  {
    cityId: "460100",
    cityName: "海口市",
    lng: "110.20672",
    lat: "20.052113",
    url: null,
    introduction: null,
  },
  {
    cityId: "500000",
    cityName: "重庆市",
    lng: "106.558434",
    lat: "29.568996",
    url: null,
    introduction: null,
  },
];
let hotCitysInfo = ref([] as citysInfoType[]);
let hotCityOne = ref({} as citysInfoType);
let hotCityTwo = ref({} as citysInfoType);
let hotCityThree = ref({} as citysInfoType);
let hotCityFour = ref({} as citysInfoType);
let hotCityFive = ref({} as citysInfoType);
const requestCitysInfo = async () => {
  await getHotCitysInfo()
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        hotCitysInfo.value = res.data.slice(0, 5);
        // citysInfo.value = res.data;
        console.log(hotCitysInfo);
        hotCityOne.value = hotCitysInfo.value[0];
        hotCityTwo.value = hotCitysInfo.value[1];
        hotCityThree.value = hotCitysInfo.value[2];
        hotCityFour.value = hotCitysInfo.value[3];
        hotCityFive.value = hotCitysInfo.value[4];
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
requestCitysInfo();

const planInfo: planInfoType = reactive({
  fromCity: "",
  toCity: {} as citysInfoType,
  goDate: "",
});

const createPlan = () => {
  console.log(planInfo);
  router.push({
    name: "TrvalPlan",
    params: {
      fromCity: planInfo.fromCity,
      toCity: JSON.stringify(planInfo.toCity),
      goDate: planInfo.goDate,
    },
  });
};

// 保证每次进入HOME页面都会刷新一次的工具方法：
const refresh = () => {
  //refreshFlag为true代表刷新过
  if (!store.refreshFlag) {
    // alert("刷新");
    //还没刷新过
    store.refreshFlag = true; //表示已经刷新了
    // console.log(store.refreshFlag);
    location.reload(); //那就刷新一下
    // setTimeout(function () {
    //   location.reload();
    // }, 1000);
    return;
  } else {
    //已经刷新过了
    store.refreshFlag = false; //表示还没有刷新
    // console.log(store.refreshFlag);

    return; //那就不刷新了
  }
};
refresh();

onMounted(() => {});
</script>
<script lang="ts">
/* 控制日历弹出 */
//@ts-ignore
(function ($) {
  $(document).ready(function () {
    /* 绑定日历弹出事件 */
    if ($("#datepicker").length) {
      $("#datepicker").datepicker();
    }
    /* 获取日历变化的值赋值给隐藏表单，从而传输给vue */
    $("#datepicker").bind("input propertychange change", function () {
      $("#hiddenInput").val($("#datepicker").val());
      $("#hiddenInput")[0].dispatchEvent(new Event("input")); //为了触发v-model的绑定
      // console.log($("#hiddenInput").val());
    });
  });
  //@ts-ignore
})(jQuery);
</script>
<template>
  <!--Main Slider Start-->
  <section class="main-slider">
    <div
      class="swiper-container thm-swiper__slider"
      data-swiper-options='{"slidesPerView": 1, "loop": true,
    "effect": "fade",
    "pagination": {
        "el": "#main-slider-pagination",
        "type": "bullets",
        "clickable": true
      },
    "navigation": {
        "nextEl": ".main-slider-button-next",
        "prevEl": ".main-slider-button-prev",
        "clickable": true
    },
    "autoplay": {
        "delay": 5000
    }}'
    >
      <div class="swiper-wrapper">
        <div class="swiper-slide">
          <div
            class="image-layer"
            style="
              background-image: url(/images/backgrounds/main-slider-1-1.jpg);
            "
          ></div>
          <div class="image-layer-overlay"></div>
          <div class="container">
            <div class="swiper-slide-inner">
              <div class="row">
                <div class="col-xl-12">
                  <h2>Fall in love with travel</h2>
                  <p>每个人心中,都会有一个古镇情怀，流水江南，烟笼人家。</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="swiper-slide">
          <div
            class="image-layer"
            style="
              background-image: url(/images/backgrounds/main-slider-1-2.jpg);
            "
          ></div>
          <div class="image-layer-overlay"></div>
          <div class="container">
            <div class="swiper-slide-inner">
              <div class="row">
                <div class="col-xl-12">
                  <h2>Fall in love with travel</h2>
                  <p>南风未起，念你成疾。</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="swiper-slide">
          <div
            class="image-layer"
            style="
              background-image: url(/images/backgrounds/main-slider-1-3.jpg);
            "
          ></div>
          <div class="image-layer-overlay"></div>
          <div class="container">
            <div class="swiper-slide-inner">
              <div class="row">
                <div class="col-xl-12">
                  <h2>Fall in love with travel</h2>
                  <p>因为有梦，所以勇敢出发，选择出发，便只顾风雨兼程。</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="main-slider-nav">
        <div class="main-slider-button-prev">
          <span class="icon-right-arrow"></span>
        </div>
        <div class="main-slider-button-next">
          <span class="icon-right-arrow"></span>
        </div>
      </div>
    </div>
  </section>

  <!--Tour Search Start-->
  <section class="tour-search">
    <div class="container">
      <div class="row">
        <div class="col-xl-12">
          <div class="tour-search-box">
            <form class="tour-search-one" action="tour-sidebar.html">
              <div class="tour-search-one__inner">
                <div class="tour-search-one__inputs">
                  <div
                    class="tour-search-one__input-box tour-search-one__input-box-last"
                  >
                    <label for="type">出发城市</label>
                    <select
                      class="selectpicker"
                      id="type"
                      v-model="planInfo.fromCity"
                    >
                      <option
                        v-for="item in citysInfos"
                        :value="item.cityName"
                        :key="item.cityId"
                      >
                        {{ item.cityName }}
                      </option>
                      <!-- <option v-for="item in 20" :value="1">
                        {{ item }}
                      </option> -->
                    </select>
                  </div>
                  <div
                    class="tour-search-one__input-box tour-search-one__input-box-last"
                  >
                    <label for="type2">目的城市</label>
                    <select
                      class="selectpicker"
                      id="type2"
                      v-model="planInfo.toCity"
                    >
                      <option
                        v-for="item in citysInfos"
                        :value="item"
                        :key="item.cityId"
                      >
                        {{ item.cityName }}
                      </option>
                    </select>
                  </div>
                  <div class="tour-search-one__input-box">
                    <label>出发日期</label>
                    <input
                      type="text"
                      placeholder="Nothing selected"
                      name="Nothing selected"
                      id="datepicker"
                    />
                  </div>
                </div>
                <div class="tour-search-one__btn-wrap">
                  <button
                    @click="createPlan"
                    class="thm-btn tour-search-one__btn"
                  >
                    制定行程
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!--Tour Search End-->
  <!-- {{ planInfo.fromCity }} 哈哈哈 {{ planInfo.backCity }} 呵呵呵{{
    planInfo.goDate
  }} -->
  <input type="text" id="hiddenInput" v-model="planInfo.goDate" />
  <!--Destinations One Start-->
  <section class="destinations-one">
    <div class="container">
      <div class="section-title text-center">
        <span class="section-title__tagline">Destination lists</span>
        <h2 class="section-title__title">向往的城市</h2>
      </div>
      <div class="row masonary-layout">
        <div class="col-xl-3 col-lg-3">
          <div class="destinations-one__single">
            <div class="destinations-one__img">
              <img src="/images/destination/destination-1-1.png" alt="" />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">{{ hotCityOne.cityEname }}</p>
                <h2 class="destinations-one__title">
                  <a href="destinations-details.html">
                    {{ hotCityOne.cityName }}</a
                  >
                </h2>
              </div>
              <div class="destinations-one__button">
                <a href="#">查看详情</a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-6 col-lg-6">
          <div class="destinations-one__single">
            <div class="destinations-one__img">
              <img src="/images/destination/destination-1-2.png" alt="" />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">{{ hotCityTwo.cityEname }}</p>
                <h2 class="destinations-one__title">
                  <a href="destinations-details.html">{{
                    hotCityTwo.cityName
                  }}</a>
                </h2>
              </div>
              <div class="destinations-one__button">
                <a href="#">查看详情</a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-3 col-lg-3">
          <div class="destinations-one__single">
            <div class="destinations-one__img">
              <img src="/images/destination/destination-1-3.png" alt="" />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">{{ hotCityThree.cityEname }}</p>
                <h2 class="destinations-one__title">
                  <a href="destinations-details.html">{{
                    hotCityThree.cityName
                  }}</a>
                </h2>
              </div>
              <div class="destinations-one__button">
                <a href="#">查看详情</a>
              </div>
            </div>
          </div>
        </div>

        <div class="col-xl-6 col-lg-6">
          <div class="destinations-one__single">
            <div class="destinations-one__img">
              <img src="/images/destination/destination-1-4.png" alt="" />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">{{ hotCityFour.cityEname }}</p>
                <h2 class="destinations-one__title">
                  <a href="destinations-details.html">{{
                    hotCityFour.cityName
                  }}</a>
                </h2>
              </div>
              <div class="destinations-one__button">
                <a href="#">查看详情</a>
              </div>
            </div>
          </div>
        </div>
        <div class="col-xl-6 col-lg-6">
          <div class="destinations-one__single">
            <div class="destinations-one__img">
              <img src="/images/destination/destination-1-5.png" alt="" />
              <div class="destinations-one__content">
                <p class="destinations-one__sub-title">{{ hotCityFive.cityEname }}</p>
                <h2 class="destinations-one__title">
                  <a href="destinations-details.html">{{
                    hotCityFive.cityName
                  }}</a>
                </h2>
              </div>
              <div class="destinations-one__button">
                <a href="#">查看详情</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!--Destinations One End-->

  <!--News One Start-->
  <section class="news-one">
    <div class="container">
      <div class="news-one__top">
        <div class="row">
          <div class="col-xl-9 col-lg-9">
            <div class="news-one__top-left">
              <div class="section-title text-left">
                <span class="section-title__tagline">From the blog post</span>
                <h2 class="section-title__title">游记 & 攻略</h2>
              </div>
            </div>
          </div>
          <div class="col-xl-3 col-lg-3">
            <div class="news-one__top-right">
              <a href="news-details.html" class="news-one__btn thm-btn"
                >查看全部</a
              >
            </div>
          </div>
        </div>
      </div>
      <div class="news-one__bottom">
        <div class="row">
          <div class="col-xl-4 col-lg-4 wow fadeInUp" data-wow-delay="100ms">
            <!--News One Single-->
            <div class="news-one__single">
              <div class="news-one__img">
                <img src="/images/blog/news-one-img-1.jpg" alt="" />
                <a href="news-details.html">
                  <span class="news-one__plus"></span>
                </a>
                <div class="news-one__date">
                  <p>
                    28 <br />
                    <span>Aug</span>
                  </p>
                </div>
              </div>
              <div class="news-one__content">
                <ul class="list-unstyled news-one__meta">
                  <li>
                    <a href="news-details.html"
                      ><i class="far fa-user-circle"></i>Admin</a
                    >
                  </li>
                  <li>
                    <a href="news-details.html"
                      ><i class="far fa-comments"></i>2 Comments</a
                    >
                  </li>
                </ul>
                <h3 class="news-one__title">
                  <a href="news-details.html"
                    >Things to See and Do When Visiting Japan</a
                  >
                </h3>
              </div>
            </div>
          </div>
          <div class="col-xl-4 col-lg-4 wow fadeInUp" data-wow-delay="200ms">
            <!--News One Single-->
            <div class="news-one__single">
              <div class="news-one__img">
                <img src="/images/blog/news-one-img-2.jpg" alt="" />
                <a href="news-details.html">
                  <span class="news-one__plus"></span>
                </a>
                <div class="news-one__date">
                  <p>
                    28 <br />
                    <span>Aug</span>
                  </p>
                </div>
              </div>
              <div class="news-one__content">
                <ul class="list-unstyled news-one__meta">
                  <li>
                    <a href="news-details.html"
                      ><i class="far fa-user-circle"></i>Admin</a
                    >
                  </li>
                  <li>
                    <a href="news-details.html"
                      ><i class="far fa-comments"></i>2 Comments</a
                    >
                  </li>
                </ul>
                <h3 class="news-one__title">
                  <a href="news-details.html"
                    >Journeys are Best Measured in New Friends</a
                  >
                </h3>
              </div>
            </div>
          </div>
          <div class="col-xl-4 col-lg-4 wow fadeInUp" data-wow-delay="300ms">
            <!--News One Single-->
            <div class="news-one__single">
              <div class="news-one__img">
                <img src="/images/blog/news-one-img-3.jpg" alt="" />
                <a href="news-details.html">
                  <span class="news-one__plus"></span>
                </a>
                <div class="news-one__date">
                  <p>
                    28 <br />
                    <span>Aug</span>
                  </p>
                </div>
              </div>
              <div class="news-one__content">
                <ul class="list-unstyled news-one__meta">
                  <li>
                    <a href="news-details.html"
                      ><i class="far fa-user-circle"></i>Admin</a
                    >
                  </li>
                  <li>
                    <a href="news-details.html"
                      ><i class="far fa-comments"></i>2 Comments</a
                    >
                  </li>
                </ul>
                <h3 class="news-one__title">
                  <a href="news-details.html"
                    >Travel the Most Beautiful Places in the World</a
                  >
                </h3>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!--Why Choose Start-->
  <section class="why-choose">
    <div class="why-choose__container">
      <div class="why-choose__left">
        <div
          class="why-choose__left-bg"
          style="
            background-image: url(/images/backgrounds/why-choose-left-bg.jpg);
          "
        ></div>
        <div class="why-choose__toggle">
          <p>
            Trips <br />
            & tours
          </p>
        </div>
      </div>
      <div class="why-choose__right">
        <div
          class="why-choose__right-map"
          style="background-image: url(/images/shapes/why-choose-right-map.png)"
        ></div>
        <div class="why-choose__right-content">
          <div class="section-title text-left">
            <span class="section-title__tagline">Our benefit lists</span>
            <h2 class="section-title__title">为什么选择爱旅游</h2>
          </div>
          <p class="why-choose__right-text">
            There are many variations of passages of Lorem Ipsum is simply free
            text available in the market for you, but the majority have suffered
            alteration in some form.
          </p>
          <ul class="list-unstyled why-choose__list">
            <li>
              <div class="icon">
                <span class="icon-travel"></span>
              </div>
              <div class="text">
                <h4>Professional and Certified</h4>
                <p>
                  Lorem ipsum is simply free text dolor sit but the majority
                  have suffered amet, consectetur notted.
                </p>
              </div>
            </li>
            <li>
              <div class="icon">
                <span class="icon-travel-map"></span>
              </div>
              <div class="text">
                <h4>Get Instant Tour Bookings</h4>
                <p>
                  Lorem ipsum is simply free text dolor sit but the majority
                  have suffered amet, consectetur notted.
                </p>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </section>
  <!--Why Choose End-->
  <!--Brand One Start-->
  <section class="brand-one">
    <div
      class="brand-one-shape"
      style="background-image: url(/images/shapes/brand-one-shape.png)"
    ></div>
    <div class="container">
      <div class="row">
        <div class="col-xl-3">
          <div class="brand-one__title">
            <h2>Our partners</h2>
          </div>
        </div>
        <div class="col-xl-9">
          <div class="brand-one__main-content">
            <div
              class="thm-swiper__slider swiper-container"
              data-swiper-options='{"spaceBetween": 100, "slidesPerView": 5, "autoplay": { "delay": 5000 }, "breakpoints": {
                        "0": {
                            "spaceBetween": 30,
                            "slidesPerView": 2
                        },
                        "375": {
                            "spaceBetween": 30,
                            "slidesPerView": 2
                        },
                        "575": {
                            "spaceBetween": 30,
                            "slidesPerView": 3
                        },
                        "767": {
                            "spaceBetween": 50,
                            "slidesPerView": 4
                        },
                        "991": {
                            "spaceBetween": 50,
                            "slidesPerView": 5
                        },
                        "1199": {
                            "spaceBetween": 50,
                            "slidesPerView": 5
                        }
                    }}'
            >
              <div class="swiper-wrapper">
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-1.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-2.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-3.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-4.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-5.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-1.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-2.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-3.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-4.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
                <div class="swiper-slide">
                  <img src="/images/brand/brand-one-5.png" alt="" />
                </div>
                <!-- /.swiper-slide -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!--Brand One End-->
</template>

<style scoped>
.tour-search-one__btn-wrap {
  height: 105px;
}
.destinations-one {
  padding: 60px 0 0;
}
.brand-one {
  margin-bottom: 150px;
}
#hiddenInput {
  display: none;
}
</style>
