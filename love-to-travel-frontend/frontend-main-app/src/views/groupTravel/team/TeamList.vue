<script setup lang="ts">
import { ref, reactive, toRefs } from "vue";
import { theTeamInfoType} from "@/apis/interface/iPlan"
import { getPageTeamsInfo } from "@/apis/travelService/team"
// 引入中文包
import zhCn from "element-plus/lib/locale/lang/zh-cn";
/* 分页获取数据 */
let teamsPageInfo = ref([] as theTeamInfoType[]);
const pageParams = reactive({
  total: 0,
  page: 1,
  limit: 5,
});
const { total, page, limit } = toRefs(pageParams);
const requestPageNotesInfo = async () => {
  await getPageTeamsInfo(page.value, limit.value)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("获取成功");
        // citysInfo.value = res.data.slice(0, 5);
        teamsPageInfo.value = res.data.records;
        total.value = res.data.total;
        console.log(teamsPageInfo);
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
// requestPageNotesInfo();
// 回调函数1：每页记录数改变时调用，size：回调参数，表示当前选中的“每页条数”
const changePageSize = (size: number) => {
  limit.value = size; //将页面大小改变
  requestPageNotesInfo(); //请求新的数据
};

// 回调函数2：改变页码时调用，page：回调参数，表示当前选中的“页码”
const changeCurrentPage = (p: number) => {
  page.value = p; //将当前页数改变
  requestPageNotesInfo(); //请求新的数据
};

</script>

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
       <el-config-provider :locale="zhCn">
        <el-pagination
          :current-page="page"
          :total="total"
          :page-size="limit"
          :page-sizes="[5,10,15,20,50,100]"
          style="padding: 30px 0; text-align: center"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="changePageSize"
          @current-change="changeCurrentPage"
          pager-count="10"
          prev-icon="Back"
          next-icon="Right"
          default-page-size="5"
        />
        <!-- hide-on-single-page -->
      </el-config-provider>
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
.el-pagination {
  display: flex;
  justify-content: center;
}
</style>
