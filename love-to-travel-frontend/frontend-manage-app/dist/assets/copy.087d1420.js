import{E as d,_ as l,d as p,k as r,r as o,D as _,o as n,c as m,a,b as v,G as y,x as f,w as C,e as h}from"./index.2257ad0b.js";const b={mounted(e,t){e.copyData=t.value,e.addEventListener("click",D)},updated(e,t){e.copyData=t.value},beforeUnmount(e){e.removeEventListener("click",e.__handleClick__)}};function D(e){let t=document.createElement("input");t.value=this.copyData.toLocaleString(),document.body.appendChild(t),t.select(),document.execCommand("Copy"),document.body.removeChild(t),d({type:"success",message:"\u590D\u5236\u6210\u529F"})}const k=p({directives:{Copy:b},setup(){return{input:r("")}}}),x={class:"layout-container"},E={class:"layout-container-table"},V={class:"box"},g=h("\u590D\u5236\u5230\u526A\u5207\u677F");function B(e,t,$,w,F,L){const s=o("el-input"),c=o("el-button"),u=_("copy");return n(),m("div",x,[a("div",E,[a("div",V,[v(s,{modelValue:e.input,"onUpdate:modelValue":t[0]||(t[0]=i=>e.input=i),placeholder:"\u8F93\u5165\u9700\u8981\u7C98\u8D34\u7684\u503C"},null,8,["modelValue"]),y((n(),f(c,{type:"primary"},{default:C(()=>[g]),_:1})),[[u,e.input]])])])])}var U=l(k,[["render",B],["__scopeId","data-v-5f7b0532"]]);export{U as default};
