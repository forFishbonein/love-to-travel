import{_ as u,d as i,aT as l,E as n,r as d,D as v,o as s,c as p,a as c,G as f,x as m,w as b,e as h}from"./index.2257ad0b.js";const k={mounted(e,t){if(typeof t.value!="function"){console.error("callback must be a function");return}let o=null;e.__handleClick__=function(a){o&&clearInterval(o),o=setTimeout(()=>{t.value()},200)},e.addEventListener("click",e.__handleClick__)},beforeUnmount(e){e.removeEventListener("click",e.__handleClick__)}};const y=i({directives:{Debounce:k},setup(){const e=t=>function(){console.log(t),n({type:"success",message:"\u6B63\u5728\u62C9\u53D6\u6570\u636E"})};return l(()=>{n.closeAll()}),{getData:e}}}),x={class:"layout-container"},C={class:"layout-container-table"},D={class:"box"},E=h("\u9632\u6296\u6309\u94AE");function g(e,t,o,a,B,$){const r=d("el-button"),_=v("debounce");return s(),p("div",x,[c("div",C,[c("div",D,[f((s(),m(r,{type:"primary"},{default:b(()=>[E]),_:1})),[[_,e.getData(123)]])])])])}var T=u(y,[["render",g],["__scopeId","data-v-361793dd"]]);export{T as default};
