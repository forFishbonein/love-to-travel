<script setup lang="ts">
import { ref } from "vue";
const theAboutUsVisible = ref(false);
</script>

<template>
  <!--Site Footer One Start-->
  <footer class="site-footer">
    <div class="site-footer__top">
      <div class="container">
        <div class="site-footer__top-inner">
          <div class="row">
            <div
              class="col-xl-4 col-lg-6 col-md-6 wow fadeInUp"
              data-wow-delay="100ms"
            >
              <div class="footer-widget__column footer-widget__about">
                <div class="footer-widget__about-logo">
                  <router-link to="/"
                    ><img
                      src="/images/resources/love-to-travel-logo2.png"
                      alt=""
                  /></router-link>
                </div>
                <p class="footer-widget__about-text">欢迎光临爱旅游！</p>
                <ul class="footer-widget__about-contact list-unstyled">
                  <li>
                    <div class="icon">
                      <i class="fas fa-phone-square-alt"></i>
                    </div>
                    <div class="text">
                      <a href="tel:+92-666-888-0000">+92 666 888 0000</a>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <i class="fas fa-envelope"></i>
                    </div>
                    <div class="text">
                      <a href="javascript:;">lovetotravel@scmzu.com</a>
                    </div>
                  </li>
                  <li>
                    <div class="icon">
                      <i class="fas fa-map-marker-alt"></i>
                    </div>
                    <div class="text">
                      <p>中南民族大学</p>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
            <div
              class="col-xl-2 col-lg-6 col-md-6 wow fadeInUp"
              data-wow-delay="200ms"
            >
              <div
                class="footer-widget__column footer-widget__company clearfix"
              >
                <h3 class="footer-widget__title">公司</h3>
                <ul class="footer-widget__company-list list-unstyled">
                  <li>
                    <a href="javascript:;" @click="theAboutUsVisible = true"
                      >关于我们</a
                    >
                  </li>
                  <li>
                    <router-link to="/goTravel/city">城市/景区</router-link>
                  </li>
                  <li><router-link to="/readTravel/note">游记</router-link></li>
                  <li>
                    <router-link to="/groupTravel/team">组队</router-link>
                  </li>
                  <li>
                    <router-link to="/buyTravel/product">产品</router-link>
                  </li>
                </ul>
              </div>
            </div>
            <div
              class="col-xl-2 col-lg-6 col-md-6 wow fadeInUp"
              data-wow-delay="300ms"
            >
              <div class="footer-widget__column footer-widget__explore">
                <h3 class="footer-widget__title">探索</h3>
                <ul class="list-unstyled footer-widget__explore-list">
                  <li><a href="javascript:;">账户</a></li>
                  <li><a href="javascript:;">法律</a></li>
                  <li><a href="javascript:;">联系</a></li>
                  <li><a href="javascript:;">程序</a></li>
                  <li><a href="javascript:;">隐私政策</a></li>
                </ul>
              </div>
            </div>
            <div
              class="col-xl-4 col-lg-6 col-md-6 wow fadeInUp"
              data-wow-delay="400ms"
            >
              <div class="footer-widget__column footer-widget__newsletter">
                <h3 class="footer-widget__title">持续关注我们</h3>
                <form
                  class="footer-widget__newsletter-form mc-form"
                  data-url="https://xyz.us18.list-manage.com/subscribe/post?u=20e91746ef818cd941998c598&id=cc0ee8140e"
                >
                  <div class="footer-widget__newsletter-input-box">
                    <input
                      type="email"
                      placeholder="您的电子邮箱地址"
                      name="EMAIL"
                    />
                    <button
                      style="width: 100%"
                      type="submit"
                      class="footer-widget__newsletter-btn"
                    >
                      立即订阅
                    </button>
                  </div>
                </form>
                <div class="mc-form__response text-center"></div>
                <!-- /.mc-form__response -->
                <div class="footer-widget__newsletter-bottom">
                  <div class="footer-widget__newsletter-bottom-icon">
                    <i class="fa fa-check"></i>
                  </div>
                  <div class="footer-widget__newsletter-bottom-text">
                    <p>我同意所有条款和政策</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="site-footer__bottom">
      <div class="container">
        <div class="row">
          <div class="col-xl-12">
            <div class="site-footer__bottom-inner">
              <div class="site-footer__bottom-left">
                <div class="footer-widget__social">
                  <a href="javascript:;"><i class="fab fa-twitter"></i></a>
                  <a href="javascript:;"><i class="fab fa-facebook"></i></a>
                  <a href="javascript:;"><i class="fab fa-pinterest-p"></i></a>
                  <a href="javascript:;"><i class="fab fa-instagram"></i></a>
                </div>
              </div>
              <div class="site-footer__bottom-right">
                <p>
                  @ All Copyright 2022.12,
                  <a href="javascript:;">aly</a>
                </p>
              </div>
              <div class="site-footer__bottom-left-arrow">
                <a
                  href="#"
                  data-target="html"
                  class="scroll-to-target scroll-to-top"
                  ><span class="icon-right-arrow"></span
                ></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </footer>
  <!--Site Footer One End-->
  <el-dialog v-model="theAboutUsVisible" :show-close="false" align-center>
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass">爱旅游团队成员</h4>
        <el-button type="danger" @click="close">
          <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon>
          关闭
        </el-button>
      </div>
    </template>
    <el-scrollbar>
      <div class="container about-container">
        <div class="row-about-container">
          <div
            class="col-xl-3 col-lg-6 col-md-6 wow fadeInLeft"
            data-wow-delay="0ms"
            data-wow-duration="1000ms"
          >
            <!--Team One Single-->
            <div class="team-one__single">
              <div class="team-one__img">
                <img src="/images/team/tian.jpg" alt="" />
              </div>
              <div class="team-one__content">
                <h4 class="team-one__name">田忆孝</h4>
                <p class="team-one__title">数据分析工程师</p>
                <div class="team-one__social">
                  <a href="javascript:;"><i class="fab fa-facebook"></i></a>
                  <a href="javascript:;"><i class="fab fa-twitter"></i></a>
                  <a href="javascript:;"><i class="fab fa-instagram"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div
            class="col-xl-3 col-lg-6 col-md-6 wow fadeInLeft"
            data-wow-delay="100ms"
            data-wow-duration="1000ms"
          >
            <!--Team One Single-->
            <div class="team-one__single">
              <div class="team-one__img">
                <img src="/images/team/hao.jpg" alt="" />
              </div>
              <div class="team-one__content">
                <h4 class="team-one__name">郝文海</h4>
                <p class="team-one__title">前端开发工程师</p>
                <div class="team-one__social">
                  <a href="javascript:;"><i class="fab fa-facebook"></i></a>
                  <a href="javascript:;"><i class="fab fa-twitter"></i></a>
                  <a href="javascript:;"><i class="fab fa-instagram"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div
            class="col-xl-3 col-lg-6 col-md-6 wow fadeInRight"
            data-wow-delay="0ms"
            data-wow-duration="1000ms"
          >
            <!--Team One Single-->
            <div class="team-one__single">
              <div class="team-one__img">
                <img src="/images/team/xiao.png" alt="" />
              </div>
              <div class="team-one__content">
                <h4 class="team-one__name">肖红娇</h4>
                <p class="team-one__title">全能项目经理</p>
                <div class="team-one__social">
                  <a href="javascript:;"><i class="fab fa-facebook"></i></a>
                  <a href="javascript:;"><i class="fab fa-twitter"></i></a>
                  <a href="javascript:;"><i class="fab fa-instagram"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div
            class="col-xl-3 col-lg-6 col-md-6 wow fadeInRight"
            data-wow-delay="100ms"
            data-wow-duration="1000ms"
          >
            <!--Team One Single-->
            <div class="team-one__single">
              <div class="team-one__img">
                <img src="/images/team/he.jpg" alt="" />
              </div>
              <div class="team-one__content">
                <h4 class="team-one__name">何宇航</h4>
                <p class="team-one__title">后端开发工程师</p>
                <div class="team-one__social">
                  <a href="javascript:;"><i class="fab fa-facebook"></i></a>
                  <a href="javascript:;"><i class="fab fa-twitter"></i></a>
                  <a href="javascript:;"><i class="fab fa-instagram"></i></a>
                </div>
              </div>
            </div>
          </div>
          <div
            class="col-xl-3 col-lg-6 col-md-6 wow fadeInRight"
            data-wow-delay="100ms"
            data-wow-duration="1000ms"
          >
            <!--Team One Single-->
            <div class="team-one__single">
              <div class="team-one__img">
                <img src="/images/team/cui.jpg" alt="" />
              </div>
              <div class="team-one__content">
                <h4 class="team-one__name">崔娜</h4>
                <p class="team-one__title">数据库工程师</p>
                <div class="team-one__social">
                  <a href="javascript:;"><i class="fab fa-facebook"></i></a>
                  <a href="javascript:;"><i class="fab fa-twitter"></i></a>
                  <a href="javascript:;"><i class="fab fa-instagram"></i></a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </el-dialog>
</template>

<style lang="scss" scoped>
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.about-container {
  width: 1500px;
  height: 480px;
}
.team-one__single {
  width: 245px;
  height: 459px;
  // border: 1px #e8604c solid;
  border-radius: 10px;
  padding-bottom: 10px;
  margin-top: 20px;
  box-shadow: 0 2px 27px 6px rgba(0, 0, 0, 0.12);
}
.team-one__img {
  width: 245px;
  height: 300px;
  img {
    width: 100%;
    height: 100%;
  }
}
.team-one__name {
  cursor: pointer;
}
.team-one__title {
  margin-top: 10px;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
}
.team-one__content {
  width: 225px;
}
.row-about-container {
  width: 100%;
  height: auto;
  display: flex;
}
// ::v-deep .el-dialog__body {
//   width: 1100px;
//   // height: 600px;
// }
// ::v-deep .el-dialog.is-align-center {
//   // width: 1100px;
//   // height: 600px;
//   margin: 0 !important;
// }
</style>
