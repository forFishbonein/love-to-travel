<div class="selectcity">
<div class="city">
  <div @click="getcity(0)">
    {{ cityArr[0] ? cityArr[0].name : '选择省' }}
  </div>
  <div @click="getcity(1)">
    {{ cityArr[1] ? cityArr[1].name : '选择市' }}
  </div>
  <div @click="getcity(2)">
    {{ cityArr[2] ? cityArr[2].name : '选择区' }}
  </div>
</div>

<div class="swiper">
  <div class="swiper-box" :style="{ ...domWidth }">
    <div class="swiper-item">
      <div
          class="item"
          v-for="(item, index) in province"
          :key="item.code"
          @click="choice(index, item)"
      >
        {{ item.name }}
      </div>
    </div>
    <div class="swiper-item">
      <div
          class="item"
          v-for="(item, index) in city"
          :key="item.code"
          @click="choice(index, item)"
      >
        {{ item.name }}
      </div>
    </div>
    <div class="swiper-item">
      <div
          class="item"
          v-for="(item, index) in area"
          :key="item.code"
          @click="choice(index, item)"
      >
        {{ item.name }}
      </div>
    </div>
  </div>
</div>
</div>
</template>
<script>
export default {
  data() {
    return {
      citylist: [
        {
          code: 1,
          name: '广东',
          childlist: [
            {
              code: 1,
              name: '广州',
              childlist: [
                {
                  code: 1,
                  name: '天河区',
                },
                {
                  code: 2,
                  name: '荔湾区',
                },
              ],
            },
            {
              code: 2,
              name: '深圳',
              childlist: [
                {
                  code: 1,
                  name: '福田区',
                },
                {
                  code: 2,
                  name: '罗湖区',
                },
              ],
            },
          ],
        },
        {
          code: 2,
          name: '福建',
          childlist: [
            {
              code: 1,
              name: '福州',
              childlist: [
                {
                  code: 1,
                  name: '晋安区',
                },
                {
                  code: 2,
                  name: '仓山区',
                },
              ],
            },
            {
              code: 2,
              name: '南平',
              childlist: [
                {
                  code: 1,
                  name: '湖山区',
                },
                {
                  code: 2,
                  name: '井水区',
                },
              ],
            },
          ],
        },
        {
          code: 3,
          name: '福建',
          childlist: [
            {
              code: 1,
              name: '福州',
              childlist: [
                {
                  code: 1,
                  name: '晋安区',
                },
                {
                  code: 2,
                  name: '仓山区',
                },
              ],
            },
            {
              code: 2,
              name: '南平',
              childlist: [
                {
                  code: 1,
                  name: '湖山区',
                },
                {
                  code: 2,
                  name: '井水区',
                },
              ],
            },
          ],
        },
        {
          code: 4,
          name: '福建',
          childlist: [
            {
              code: 1,
              name: '福州',
              childlist: [
                {
                  code: 1,
                  name: '晋安区',
                },
                {
                  code: 2,
                  name: '仓山区',
                },
              ],
            },
            {
              code: 2,
              name: '南平',
              childlist: [
                {
                  code: 1,
                  name: '湖山区',
                },
                {
                  code: 2,
                  name: '井水区',
                },
              ],
            },
          ],
        },
        {
          code: 5,
          name: '福建',
          childlist: [
            {
              code: 1,
              name: '福州',
              childlist: [
                {
                  code: 1,
                  name: '晋安区',
                },
                {
                  code: 2,
                  name: '仓山区',
                },
              ],
            },
            {
              code: 2,
              name: '南平',
              childlist: [
                {
                  code: 1,
                  name: '湖山区',
                },
                {
                  code: 2,
                  name: '井水区',
                },
              ],
            },
          ],
        },
        {
          code: 6,
          name: '福建',
          childlist: [
            {
              code: 1,
              name: '福州',
              childlist: [
                {
                  code: 1,
                  name: '晋安区',
                },
                {
                  code: 2,
                  name: '仓山区',
                },
              ],
            },
            {
              code: 2,
              name: '南平',
              childlist: [
                {
                  code: 1,
                  name: '湖山区',
                },
                {
                  code: 2,
                  name: '井水区',
                },
              ],
            },
          ],
        },
      ],
      style: 0,
      index: [0, 0],
      cityArr: [null, null, null],
    }
  },
  computed: {
    province() {
      return this.citylist
    },
    city() {
      return this.province[this.index[0]].childlist
    },
    area() {
      return this.city[this.index[1]].childlist
    },
    domWidth() {
      const arr = [0, '-100%', '-200%']
      return {
        transform: `translateX(${arr[this.style]})`,
      }
    },
  },
  methods: {
    getcity(e) {
      if (this.style > e || this.index[e]) {
        this.style = e
        this.cityArr.forEach((index) => {
          if (index > this.style) {
            this.cityArr[index] = null
            this.index[index - 1] = 0
          }
        })
      }
    },
    choice(e, v) {
      console.log(this.style, this.index, this.cityArr)
      this.$set(this.cityArr, this.style, {
        ...v,
      })
      if (this.style == 2) {
        setTimeout(() => {
          this.ok()
        }, 0)
        return
      }
      this.style = this.style != 2 ? ++this.style : 2
      this.$set(this.index, this.style - 1, e)
    },
    ok() {
      alert('完成')
    },
  },
}
</script>

<style>
.selectcity {
  display: flex;
  flex-direction: column;
}
.swiper {
  overflow: hidden;
}
.swiper-box {
  display: flex;
  transform: translateX(0);
  transition: 0.5s;
}
.swiper-item {
  width: 100%;
  overflow: hidden;
  flex-shrink: 0;
}
.item {
  padding: 30px;
  border-bottom: 1px solid #ccc;
  white-space: nowrap;
}
.city {
  display: flex;
  justify-content: space-between;
  padding: 30px;
}
.city div:nth-child(1) {
  width: 100%;
  text-align: left;
}
.city div:nth-child(2) {
  width: 100%;
  text-align: center;
}
.city div:nth-child(3) {
  width: 100%;
  text-align: right;
}
</style>

