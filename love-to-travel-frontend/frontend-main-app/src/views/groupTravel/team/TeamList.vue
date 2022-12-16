<script setup lang="ts"></script>

<template>
  <section class="news-one">
    <div class="container">
      <div class="row">
        <div class="tours-list__single">
          <div class="tours-list__content">
            <div class="tours-list__stars">
              <el-icon><Calendar /></el-icon> 出发时间：
            </div>
            <h3 class="tours-list__title">
              <a href="tour-details.html"><span style="color:#e8604c;margin-right:10px">队名:</span>Moscow Red City Land</a>
            </h3>
            <p class="tours-list__rate">预算：<span>￥870</span> / 每人</p>
            <p class="tours-list__text">
              <span class="span-style">简介</span> Lorem ipsum available isn but
              the majority have suffered alteratin in some or form injected
              semper fames.
            </p>
            <p class="tours-list__text">
              <span class="span-style">途径地点</span>
              Lorem ipsum available isn but the majority have suffered alteratin
              in some or form injected semper fames.
            </p>
            <p class="tours-list__text">
              <span class="span-style">已加入成员</span>
              Lorem ipsum available isn but the majority have suffered alteratin
              in some or form injected semper fames.
            </p>
            <ul class="tours-list__meta list-unstyled">
              <li>
                <a href="tour-details.html"
                  ><i class="far fa-calendar"></i>预计3天</a
                >
              </li>
              <li>
                <a href="tour-details.html"
                  ><i class="far fa-user-circle"></i>预期6人</a
                >
              </li>
              <li>
                <a href="tour-details.html">
                  <i class="far fa-map"></i>口号:我们必胜！
                </a>
              </li>
            </ul>
            <p class="tours-list__text">
              <div class="join-team-button">加入队伍</div>
              <!-- <div class="out-team-button">退出队伍</div> -->
            </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style lang="scss" scoped>
.row {
  display: flex;
  justify-content: center;
}
.tours-list__single {
  margin: 0 auto;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  .tours-list__content {
    border-left: 1px solid #ece8e0;
    border-radius: 8px;
    padding-bottom: 20px;
    .tours-list__text {
      padding: 10px 0;
      display: flex;
      align-items: center;
      overflow: hidden;
    }
  }
}
.span-style {
  display: inline-block;
  width: auto;
  max-width: 100px;
  overflow: hidden;
  height: 1.5em;
  color: #ffffff;
  background-color: #e8604c;
  border-radius: 5px;
  padding: 0 5px;
  line-height: 2em;
  margin-right: 5px;
  font-size: 16px;
  line-height: 1.5em;
  margin-right: 10px;
}
.join-team-button{
  width: 150px;
  height: 40px;
  float:right;
  border: 1px #e8604c solid;
  cursor: pointer;
  border-radius: 20px;
  background-color: #e8604c;
  font-weight: 700;
  color:#ffffff;
  display: flex;
  justify-content: center;
  align-items: center;
}
.join-team-button:hover{
  background-color: #e74128;
}
</style>
