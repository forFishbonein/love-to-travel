import { defineConfig, ConfigEnv, loadEnv } from 'vite'
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import vue from "@vitejs/plugin-vue";
import { resolve } from "path";
import pkg from './package.json'
import dayjs from 'dayjs'
const { dependencies, devDependencies, name, version } = pkg
const __APP_INFO__ = {
	pkg: { dependencies, devDependencies, name, version },
	lastBuildTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
}
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  server: {
    //启动的ip
    host: "0.0.0.0",
  },
  // alias: {
  //   "@": "/src/",
  //   "@components": "/src/components/",
  //   "@assets": "/src/assets/",
  // },
  resolve: {
    alias: {
      "@": resolve(__dirname, "src"),
      "@assets": resolve(__dirname, "src/assets"),
      "@request": resolve(__dirname, "src/request"),
      "@views": resolve(__dirname, "src/views"),
      "@components": resolve(__dirname, "src/components"),
      "@store": resolve(__dirname, "src/store"),
      "@apis": resolve(__dirname, "src/apis"),
      "@plugins": resolve(__dirname, "src/plugins"),
      "@styles": resolve(__dirname, "src/styles"),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        charset: false, // 避免出现: build时的 @charset 必须在第一行的警告
        additionalData: `
          @import "@/styles/mixin.scss";
          @import "@/styles/variables.scss";
          `,
      },
    },
  },
  define: {
    __APP_INFO__: JSON.stringify(__APP_INFO__)
  }
});


