<script setup>
import { onMounted } from "vue";
onMounted(() => {
  /*背景雪花飘落特效*/
  (function ($) {
    $.fn.snow = function (options) {
      var $flake = $('<div id="flake" />')
          .css({ position: "absolute", top: "-50px" })
          .html("&#10052;"),
        documentHeight = $(document).height(),
        documentWidth = $(document).width(),
        defaults = {
          minSize: 20,
          maxSize: 40,
          newOn: 300,
          flakeColor: "#2894FF",
        },
        options = $.extend({}, defaults, options);
      var interval = setInterval(function () {
        var startPositionLeft = Math.random() * documentWidth - 100,
          startOpacity = 0.5 + Math.random(),
          sizeFlake = options.minSize + Math.random() * options.maxSize,
          endPositionTop = documentHeight - 40,
          endPositionLeft = startPositionLeft - 100 + Math.random() * 200,
          durationFall = documentHeight * 10 + Math.random() * 5000;
        $flake
          .clone()
          .appendTo("body")
          .css({
            left: startPositionLeft,
            opacity: startOpacity,
            "font-size": sizeFlake,
            color: options.flakeColor,
          })
          .animate(
            { top: endPositionTop, left: endPositionLeft, opacity: 0.2 },
            durationFall,
            "linear",
            function () {
              $(this).remove();
            }
          );
      }, options.newOn);
    };
  })(jQuery);
  $.fn.snow({ minSize: 5, maxSize: 50, newOn: 800, flakeColor: "#2894FF" });
});
</script>
<template>
  <div class="question-container">
    <iframe
      id="iframeContain"
      name="iframeContain"
      seamless
      scrolling="no"
      src="https://chatbot.aliyuncs.com/intl/index.htm?from=FsviSWX3wu"
    >
    </iframe>
  </div>
</template>

<style lang="scss" scoped>
.question-container {
  width: 100%;
  height: 800px;
  padding: 0;
  #iframeContain {
    width: 100%;
    height: 800px;
  }
}
</style>
