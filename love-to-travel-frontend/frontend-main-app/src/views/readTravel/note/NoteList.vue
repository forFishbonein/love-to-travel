<script setup lang="ts">
import { ref } from "vue";
import { theNotesInfoType } from "@apis/interface/iPlan";
import { getAllNoteList } from "@apis/travelService/note";
let notesInfo = ref([] as theNotesInfoType[]);
const requestNotesInfo = async () => {
  await getAllNoteList()
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
        notesInfo.value = res.data;
        console.log(notesInfo);
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
requestNotesInfo();
</script>

<template>
  <section class="news-one">
    <div class="container">
      <div class="row">
        <div
          class="col-xl-4 col-lg-6 col-md-6 wow fadeInUp"
          data-wow-delay="100ms"
          v-for="(item, index) in notesInfo"
          :key="index"
        >
          <!--News One Single-->
          <div class="news-one__single">
            <div class="news-one__img">
              <img :src="item.url" alt="" class="notes-img" />
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
                <a href="news-details.html">{{ item.title }}</a>
              </h3>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style lang="scss" scoped>
/* 修改图片那个地方的样式！ */
.notes-img {
  width: 100%;
  height: 100%;
}
.news-one__img {
  width: 350px;
  height: 300px;
}
</style>
