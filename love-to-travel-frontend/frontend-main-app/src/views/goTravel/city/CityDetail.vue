<script setup lang="ts">
import { ref } from "vue";
import { getOneCityInfoById } from "@/apis/travelService/city";
import { citysInfoType } from "@/apis/interface/iPlan";
const props = defineProps<{
  cityId: number;
}>();
// alert(props.cityId);
const cityId = props.cityId;
const cityInfo = ref({} as citysInfoType);
const requestOneCityInfo = async () => {
  await getOneCityInfoById(cityId)
    .then((res: any) => {
      if (res.code != 0) {
        //@ts-ignore
        ElMessage({
          type: "error",
          message: res.msg,
        });
      } else {
        // alert("得到数据了2！");
        cityInfo.value = res.data;
        console.log(cityInfo.value);
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
requestOneCityInfo();
</script>

<template>
  <section class="destinations-details">
    <div class="container">
      <div class="row">
        <div class="col-xl-8 col-lg-7">
          <div class="destinations-details__left">
            <div class="destinations-details__img">
              <img
                src="/images/resources/destinations-details-img-1.jpg"
                alt=""
              />
            </div>
            <div class="destinations-details__discover">
              <h3 class="destinations-details__title">Discover Spain</h3>
              <p class="destinations-details__discover-text-1">
                Lorem ipsum available isn but the majority have suffered
                alteratin in some or form injected. Lorem Ipsum. Proin gravida
                nibh vel velit auctor aliqueenean sollicitudin, lorem quis
                bibendum auctor, nisi elit consequat ipsum, nec sagittis sem
                nibh id elit. vulputate cursus a sit amet mauris. Morbi accumsan
                ipsum veliam nec tellus a odio tincidunt auctor.
              </p>
              <p class="destinations-details__discover-text-2">
                There are many variations of passages of Lorem Ipsum available,
                but the majority have suffered alteration in some injected or
                words which don't look even slightly believable. If you are
                going to use a passage of Lorem Ipsum, you need to be sure there
                isn't anything embarrassing hidden in the middle of text. All
                the Lorem Ipsum generators on the Internet tend to repeat
                predefined chunks as necessary,
              </p>
            </div>
          </div>
        </div>
        <div class="col-xl-4 col-lg-5">
          <div class="destinations-details__right">
            <div
              class="tour-details-two__last-minute tour-details-two__last-minute-2"
            >
              <h3 class="tour-details-two__sidebar-title">Last Minute</h3>
              <ul class="tour-details-two__last-minute-list list-unstyled">
                <li>
                  <div class="tour-details-two__last-minute-image">
                    <img src="/images/resources/td-img-1.jpg" alt="" />
                  </div>
                  <div class="tour-details-two__last-minute-content">
                    <h6>$380</h6>
                    <h5>Africa 2 Days Tour</h5>
                    <p>Los Angeles</p>
                  </div>
                </li>
                <li>
                  <div class="tour-details-two__last-minute-image">
                    <img src="/images/resources/td-img-2.jpg" alt="" />
                  </div>
                  <div class="tour-details-two__last-minute-content">
                    <h6>$380</h6>
                    <h5>Africa 2 Days Tour</h5>
                    <p>Los Angeles</p>
                  </div>
                </li>
                <li>
                  <div class="tour-details-two__last-minute-image">
                    <img src="/images/resources/td-img-3.jpg" alt="" />
                  </div>
                  <div class="tour-details-two__last-minute-content">
                    <h6>$380</h6>
                    <h5>Africa 2 Days Tour</h5>
                    <p>Los Angeles</p>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style lang="scss" scoped></style>
