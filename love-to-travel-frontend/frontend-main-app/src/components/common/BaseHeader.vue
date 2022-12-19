<script lang="ts">
//@ts-ignore
(function ($) {
  $(document).ready(function () {
    // 弹出和收回搜索框
    $("#search-up-down").click(function () {
      $(".search-popup").css("transform", "translateY(0%)");
    });
    $(".search-popup").click(function (e) {
      //e.target = e.target[0] = document.getElementsByClassName("search-popup__content")[0] //普通dom对象
      //$(e.target) = $(e.target)[0] //jquery对象
      //获得class的方式为.className
      if (e.target.className === "search-popup__overlay search-toggler") {
        //@ts-ignore
        $(this).css("transform", "translateY(-110%)");
      }
    });
    //向下滚动的导航栏效果
    if ($(".sticky-header__content").length) {
      //@ts-ignore
      let navContent = document.querySelector(".main-menu").innerHTML;
      let mobileNavContainer = document.querySelector(
        ".sticky-header__content"
      );
      //@ts-ignore
      mobileNavContainer.innerHTML = navContent;
    }
    $(window).on("scroll", function () {
      if ($(".stricked-menu").length) {
        var headerScrollPos = 130;
        var stricky = $(".stricked-menu");
        if ($(window).scrollTop() > headerScrollPos) {
          stricky.addClass("stricky-fixed");
          //@ts-ignore
        } else if ($(this).scrollTop() <= headerScrollPos) {
          stricky.removeClass("stricky-fixed");
        }
      }
      if ($(".scroll-to-top").length) {
        var strickyScrollPos = 100;
        if ($(window).scrollTop() > strickyScrollPos) {
          $(".scroll-to-top").fadeIn(500);
          //@ts-ignore
        } else if ($(this).scrollTop() <= strickyScrollPos) {
          $(".scroll-to-top").fadeOut(500);
        }
      }
    });
  });
  //@ts-ignore
})(jQuery);
</script>
<script setup lang="ts">
import { useRouter } from "vue-router";
import { mainStore } from "@/store/user";
import { ref } from "vue";
import { InfoFilled } from "@element-plus/icons-vue";
const router = useRouter();
const store = mainStore();
let loginFlag = ref(false);
const toLoginOrOwnCenter = () => {
  if (store.token === "") {
    router.push("/login");
  } else {
    router.push("/personal");
  }
};
const confirmLogout = () => {
  store
    .logout()
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        //@ts-ignore
        ElMessage({
          type: "success",
          message: "已退出登录",
        });
        router.replace({ path: "/" });
        location.reload(); //必须要刷新一下，否则不能显示最新的内容
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
const displayText = () => {
  if (store.token === "") {
    loginFlag.value = false;
    return "登录 / 注册";
  }
  loginFlag.value = true;
  return "个人中心";
};

/* 搜索功能实现 */
const keyword = ref("");
const searchId = ref("0");
const searchSomething = () => {
  if (searchId.value === "0") {
    router.push({
      name: "Search",
      params: {
        keyword: keyword.value,
      },
    });
  } else if (searchId.value === "1") {
    // alert("跳转");
    // alert(keyword.value);
    router.push({
      name: "CityList",
      params: {
        keyword: keyword.value,
      },
    });
    // router.push({
    //   // path: "/goTravel/city/list",
    //   name: "CityList",
    //   query: {
    //     keyword: keyword.value,
    //   },
    // });
  } else if (searchId.value === "2") {
    router.push({
      name: "SceneryList",
      params: {
        keyword: keyword.value,
      },
    });
  } else if (searchId.value === "3") {
    router.push({
      name: "NoteList",
      params: {
        keyword: keyword.value,
      },
    });
  }
};
</script>
<template>
  <header class="main-header clearfix">
    <div class="main-header__top">
      <div class="container">
        <div class="main-header__top-inner clearfix">
          <div class="main-header__top-left">
            <ul class="list-unstyled main-header__top-address">
              <li>
                <div class="icon">
                  <span class="icon-phone-call"></span>
                </div>
                <div class="text">
                  <a href="tel:+92-666-999-0000">+ 92 666 888 0000</a>
                </div>
              </li>
              <li>
                <div class="icon">
                  <span class="icon-at"></span>
                </div>
                <div class="text">
                  <a href="javascript:;">lovetotravel@scmzu.com</a>
                </div>
              </li>
            </ul>
          </div>
          <div class="main-header__top-right">
            <div class="main-header__top-right-inner">
              <div class="main-header__top-right-social">
                <a href="javascript:;"><i class="fab fa-facebook"></i></a>
                <a href="javascript:;"><i class="fab fa-twitter"></i></a>
                <a href="javascript:;"><i class="fab fa-instagram"></i></a>
                <a href="javascript:;"><i class="fab fa-dribbble"></i></a>
              </div>
              <div class="main-header__top-right-btn-box">
                <a
                  class="thm-btn main-header__top-right-btn"
                  @click="toLoginOrOwnCenter"
                  v-text="displayText()"
                ></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <nav class="main-menu clearfix">
      <div class="main-menu-wrapper clearfix">
        <div class="container clearfix">
          <div class="main-menu-wrapper-inner clearfix">
            <div class="main-menu-wrapper__left clearfix">
              <div class="main-menu-wrapper__logo">
                <router-link to="/"
                  ><img src="/images/resources/love-to-travel-logo1.png" alt=""
                /></router-link>
              </div>
              <div class="main-menu-wrapper__main-menu">
                <a href="javascript:;" class="mobile-nav__toggler"
                  ><i class="fa fa-bars"></i
                ></a>
                <ul class="main-menu__list">
                  <li class="dropdown current">
                    <router-link to="/" class="aly-index">爱旅游</router-link>
                  </li>
                  <li class="dropdown">
                    <router-link to="/goTravel/city">去旅游</router-link>
                    <ul>
                      <li>
                        <router-link
                          to="/goTravel/city"
                          active-class="active-router"
                          >城市</router-link
                        >
                      </li>
                      <li>
                        <router-link
                          to="/goTravel/scenery"
                          active-class="active-router"
                          >景点</router-link
                        >
                      </li>
                    </ul>
                  </li>
                  <li class="dropdown">
                    <router-link to="/readTravel/note">阅旅游</router-link>
                    <ul>
                      <li>
                        <router-link
                          to="/readTravel/note"
                          active-class="active-router"
                          >旅行游记</router-link
                        >
                      </li>
                      <li>
                        <router-link
                          to="/readTravel/write"
                          active-class="active-router"
                          >发游记</router-link
                        >
                      </li>
                    </ul>
                  </li>
                  <li class="dropdown">
                    <router-link to="/groupTravel/team">组队游</router-link>
                    <ul>
                      <li>
                        <router-link
                          to="/groupTravel/team"
                          active-class="active-router"
                          >旅行组队</router-link
                        >
                      </li>
                      <li>
                        <router-link
                          to="/groupTravel/create"
                          active-class="active-router"
                          >创建队伍</router-link
                        >
                      </li>
                    </ul>
                  </li>
                  <li class="dropdown">
                    <router-link to="/buyTravel/product">购旅游</router-link>
                    <ul>
                      <li>
                        <router-link
                          to="/buyTravel/product"
                          active-class="active-router"
                          >旅游产品</router-link
                        >
                      </li>
                    </ul>
                  </li>
                  <li class="dropdown">
                    <router-link to="/canvasTravel">旅行脉络</router-link>
                    <ul>
                      <li>
                        <router-link
                          to="/canvasTravel"
                          active-class="active-router"
                          >知识图谱</router-link
                        >
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
            <div class="main-menu-wrapper__right">
              <a
                href="javascript:;"
                class="main-menu__search search-toggler icon-magnifying-glass"
                id="search-up-down"
              ></a>
              <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="取消"
                :icon="InfoFilled"
                icon-color="#e8604c"
                title="您确定要退出登录吗？"
                @confirm="confirmLogout"
              >
                <template #reference>
                  <a
                    href="javascript:;"
                    class="main-menu__user"
                    v-if="loginFlag"
                  >
                    <el-icon :size="25"><SwitchButton /></el-icon>
                  </a>
                </template>
              </el-popconfirm>
            </div>
          </div>
        </div>
      </div>
    </nav>
  </header>

  <div class="stricky-header stricked-menu main-menu">
    <div class="sticky-header__content"></div>
    <!-- /.sticky-header__content -->
  </div>
  <!-- /.stricky-header -->

  <div class="mobile-nav__wrapper">
    <div class="mobile-nav__overlay mobile-nav__toggler"></div>
    <!-- /.mobile-nav__overlay -->
    <div class="mobile-nav__content">
      <span class="mobile-nav__close mobile-nav__toggler"
        ><i class="fa fa-times"></i
      ></span>

      <div class="logo-box">
        <a href="javascript:;" aria-label="logo image"
          ><img src="/images/resources/logo-2.png" width="155" alt=""
        /></a>
      </div>
      <!-- /.logo-box -->
      <div class="mobile-nav__container"></div>
      <!-- /.mobile-nav__container -->

      <ul class="mobile-nav__contact list-unstyled">
        <li>
          <i class="fa fa-envelope"></i>
          <a href="javascript:;">lovetotravel@scmzu.com</a>
        </li>
        <li>
          <i class="fa fa-phone-alt"></i>
          <a href="tel:666-888-0000">666 888 0000</a>
        </li>
      </ul>
      <!-- /.mobile-nav__contact -->
      <div class="mobile-nav__top">
        <div class="mobile-nav__social">
          <a href="javascript:;" class="fab fa-twitter"></a>
          <a href="javascript:;" class="fab fa-facebook-square"></a>
          <a href="javascript:;" class="fab fa-pinterest-p"></a>
          <a href="javascript:;" class="fab fa-instagram"></a>
        </div>
        <!-- /.mobile-nav__social -->
      </div>
      <!-- /.mobile-nav__top -->
    </div>
    <!-- /.mobile-nav__content -->
  </div>
  <!-- /.mobile-nav__wrapper -->

  <div class="search-popup">
    <div class="search-popup__overlay search-toggler"></div>
    <div id="search-border">
      <div id="radio-el">
        <el-radio-group v-model="searchId">
          <el-radio label="0" border>全部</el-radio>
          <el-radio label="1" border>城市</el-radio>
          <el-radio label="2" border>景区</el-radio>
          <el-radio label="3" border>游记</el-radio>
        </el-radio-group>
      </div>
      <!-- /.search-popup__overlay -->
      <div class="search-popup__content">
        <form autocomplete="off" @submit.prevent="searchSomething">
          <label for="search" class="sr-only">search here</label
          ><!-- /.sr-only -->
          <input
            type="text"
            id="search"
            placeholder="搜全部/城市/景区/游记"
            v-model="keyword"
          />
          <button type="submit" aria-label="search submit" class="thm-btn">
            <i class="icon-magnifying-glass"></i>
          </button>
        </form>
      </div>
    </div>
    <!-- /.search-popup__content -->
  </div>
  <!-- /.search-popup -->
</template>

<style lang="scss" scoped>
// @import url(@/assets/css/tevily.css);
.active-router {
  background-color: #e8604c;
}
.main-header__top-right-btn {
  font-size: 15px !important;
  font-family: ali-blod !important;
}
.main-header__top-right-btn-box {
  cursor: pointer;
}
#radio-el {
  position: relative;
  bottom: 20px;
}
#search-border {
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 50%;
  top: 45%;
  /* 这样可以水平垂直居中 */
  transform: translate(-50%, -50%);
}
.el-radio,
.is-checked {
  color: #e8604c !important;
  border-color: #e8604c !important;
}
.el-radio {
  --el-radio-input-border-color-hover: #e8604c !important;
}

.aly-index {
  font-weight: 1000 !important;
}
.dropdown {
  > a {
    font-weight: 700 !important;
    font-size: 18px !important;
    font-family: ali-blod;
  }
  > ul {
    > li {
      > a {
        font-size: 16px !important;
        font-family: ali-regular;
        // font-weight: 600 !important;
      }
    }
  }
}
</style>
