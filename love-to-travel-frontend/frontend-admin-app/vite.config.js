import { defineConfig } from "vite";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import vue from "@vitejs/plugin-vue";
import { resolve } from "path";

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
    hmr: true,
    port: 5175
  },
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
      "@config": resolve(__dirname, "src/config"),
    },
  },
});
