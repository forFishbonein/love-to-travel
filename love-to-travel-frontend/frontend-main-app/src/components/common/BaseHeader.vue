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
const radio = ref("1");
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
                  <a href="tel:+92-666-999-0000">+ 92 666 999 0000</a>
                </div>
              </li>
              <li>
                <div class="icon">
                  <span class="icon-at"></span>
                </div>
                <div class="text">
                  <a href="1558637209@qq.com">lovetotravel@scmzu.com</a>
                </div>
              </li>
            </ul>
          </div>
          <div class="main-header__top-right">
            <div class="main-header__top-right-inner">
              <div class="main-header__top-right-social">
                <a href="#"><i class="fab fa-facebook"></i></a>
                <a href="#"><i class="fab fa-twitter"></i></a>
                <a href="#"><i class="fab fa-instagram"></i></a>
                <a href="#"><i class="fab fa-dribbble"></i></a>
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
                <a href="#" class="mobile-nav__toggler"
                  ><i class="fa fa-bars"></i
                ></a>
                <ul class="main-menu__list">
                  <li class="dropdown current aly-index">
                    <a href="#">爱旅游</a>
                  </li>
                  <li class="dropdown">
                    <a href="#">去旅游</a>
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
                    <a href="#">阅旅游</a>
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
                    <a href="#">组旅游</a>
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
                          >发布组队</router-link
                        >
                      </li>
                    </ul>
                  </li>
                  <li class="dropdown">
                    <a href="#">问旅游</a>
                    <ul>
                      <li><a href="news.html">旅行解惑</a></li>
                    </ul>
                  </li>
                  <li><a href="#">旅游脉络</a></li>
                </ul>
              </div>
            </div>
            <div class="main-menu-wrapper__right">
              <a
                href="#"
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
                  <a href="#" class="main-menu__user" v-if="loginFlag">
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
        <a href="index.html" aria-label="logo image"
          ><img src="/images/resources/logo-2.png" width="155" alt=""
        /></a>
      </div>
      <!-- /.logo-box -->
      <div class="mobile-nav__container"></div>
      <!-- /.mobile-nav__container -->

      <ul class="mobile-nav__contact list-unstyled">
        <li>
          <i class="fa fa-envelope"></i>
          <a href="mailto:needhelp@packageName__.com">needhelp@tevily.com</a>
        </li>
        <li>
          <i class="fa fa-phone-alt"></i>
          <a href="tel:666-888-0000">666 888 0000</a>
        </li>
      </ul>
      <!-- /.mobile-nav__contact -->
      <div class="mobile-nav__top">
        <div class="mobile-nav__social">
          <a href="#" class="fab fa-twitter"></a>
          <a href="#" class="fab fa-facebook-square"></a>
          <a href="#" class="fab fa-pinterest-p"></a>
          <a href="#" class="fab fa-instagram"></a>
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
        <el-radio-group v-model="radio">
          <el-radio label="1" border>全部</el-radio>
          <el-radio label="2" border>目的地</el-radio>
          <el-radio label="3" border>游记</el-radio>
          <el-radio label="4" border>酒店</el-radio>
          <el-radio label="5" border>机票</el-radio>
        </el-radio-group>
      </div>
      <!-- /.search-popup__overlay -->
      <div class="search-popup__content">
        <form action="#">
          <label for="search" class="sr-only">search here</label
          ><!-- /.sr-only -->
          <input
            type="text"
            id="search"
            placeholder="搜目的地/游记/酒店/机票"
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
  background: #e8604c;
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
}
</style>
