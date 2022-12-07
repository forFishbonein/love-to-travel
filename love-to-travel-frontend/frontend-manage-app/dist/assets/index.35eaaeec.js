import{d as ct,R as Wt,_ as dt,o as L,c as q,a as V,F as He,v as We,q as Gt,t as Se,e as Tt,k as Ct,y as jt,r as rt,x as It,M as zt,b as Ut,w as qt}from"./index.2257ad0b.js";const Vt=ct({props:{data:{type:Object,required:!0,default:()=>({title:"",tags:[],options:[]})}},setup(){return{colorInit:Wt(()=>e=>{const o=[{color:"#57c05d",tag:"\u65B0\u589E"},{color:"#67a4dc",tag:"\u4F18\u5316"}].find(i=>i.tag===e);return o&&o.tag?o.color:"#67a4dc"})}}}),Kt={class:"item"},Zt={class:"title"},Qt={class:"tags"},Jt={class:"options"};function en(n,e,t,o,i,r){return L(),q("div",Kt,[V("div",Zt,[V("div",Qt,[(L(!0),q(He,null,We(n.data.tags,(a,l)=>(L(),q("div",{class:"tag",key:l,style:Gt({background:n.colorInit(a)})},Se(a),5))),128))]),Tt(" "+Se(n.data.name),1)]),V("div",Jt,[(L(!0),q(He,null,We(n.data.options,(a,l)=>(L(),q("p",{key:l},Se(a),1))),128))])])}var tn=dt(Vt,[["render",en],["__scopeId","data-v-2af06efc"]]);/**!
 * Sortable 1.15.0
 * @author	RubaXa   <trash@rubaxa.org>
 * @author	owenm    <owen23355@gmail.com>
 * @license MIT
 */function mt(n,e){var t=Object.keys(n);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(n);e&&(o=o.filter(function(i){return Object.getOwnPropertyDescriptor(n,i).enumerable})),t.push.apply(t,o)}return t}function j(n){for(var e=1;e<arguments.length;e++){var t=arguments[e]!=null?arguments[e]:{};e%2?mt(Object(t),!0).forEach(function(o){nn(n,o,t[o])}):Object.getOwnPropertyDescriptors?Object.defineProperties(n,Object.getOwnPropertyDescriptors(t)):mt(Object(t)).forEach(function(o){Object.defineProperty(n,o,Object.getOwnPropertyDescriptor(t,o))})}return n}function Xe(n){return typeof Symbol=="function"&&typeof Symbol.iterator=="symbol"?Xe=function(e){return typeof e}:Xe=function(e){return e&&typeof Symbol=="function"&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},Xe(n)}function nn(n,e,t){return e in n?Object.defineProperty(n,e,{value:t,enumerable:!0,configurable:!0,writable:!0}):n[e]=t,n}function Z(){return Z=Object.assign||function(n){for(var e=1;e<arguments.length;e++){var t=arguments[e];for(var o in t)Object.prototype.hasOwnProperty.call(t,o)&&(n[o]=t[o])}return n},Z.apply(this,arguments)}function on(n,e){if(n==null)return{};var t={},o=Object.keys(n),i,r;for(r=0;r<o.length;r++)i=o[r],!(e.indexOf(i)>=0)&&(t[i]=n[i]);return t}function rn(n,e){if(n==null)return{};var t=on(n,e),o,i;if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(n);for(i=0;i<r.length;i++)o=r[i],!(e.indexOf(o)>=0)&&(!Object.prototype.propertyIsEnumerable.call(n,o)||(t[o]=n[o]))}return t}var an="1.15.0";function K(n){if(typeof window!="undefined"&&window.navigator)return!!navigator.userAgent.match(n)}var Q=K(/(?:Trident.*rv[ :]?11\.|msie|iemobile|Windows Phone)/i),Pe=K(/Edge/i),vt=K(/firefox/i),Te=K(/safari/i)&&!K(/chrome/i)&&!K(/android/i),Ot=K(/iP(ad|od|hone)/i),At=K(/chrome/i)&&K(/android/i),Pt={capture:!1,passive:!1};function _(n,e,t){n.addEventListener(e,t,!Q&&Pt)}function b(n,e,t){n.removeEventListener(e,t,!Q&&Pt)}function Ge(n,e){if(!!e){if(e[0]===">"&&(e=e.substring(1)),n)try{if(n.matches)return n.matches(e);if(n.msMatchesSelector)return n.msMatchesSelector(e);if(n.webkitMatchesSelector)return n.webkitMatchesSelector(e)}catch{return!1}return!1}}function ln(n){return n.host&&n!==document&&n.host.nodeType?n.host:n.parentNode}function W(n,e,t,o){if(n){t=t||document;do{if(e!=null&&(e[0]===">"?n.parentNode===t&&Ge(n,e):Ge(n,e))||o&&n===t)return n;if(n===t)break}while(n=ln(n))}return null}var bt=/\s+/g;function M(n,e,t){if(n&&e)if(n.classList)n.classList[t?"add":"remove"](e);else{var o=(" "+n.className+" ").replace(bt," ").replace(" "+e+" "," ");n.className=(o+(t?" "+e:"")).replace(bt," ")}}function h(n,e,t){var o=n&&n.style;if(o){if(t===void 0)return document.defaultView&&document.defaultView.getComputedStyle?t=document.defaultView.getComputedStyle(n,""):n.currentStyle&&(t=n.currentStyle),e===void 0?t:t[e];!(e in o)&&e.indexOf("webkit")===-1&&(e="-webkit-"+e),o[e]=t+(typeof t=="string"?"":"px")}}function pe(n,e){var t="";if(typeof n=="string")t=n;else do{var o=h(n,"transform");o&&o!=="none"&&(t=o+" "+t)}while(!e&&(n=n.parentNode));var i=window.DOMMatrix||window.WebKitCSSMatrix||window.CSSMatrix||window.MSCSSMatrix;return i&&new i(t)}function xt(n,e,t){if(n){var o=n.getElementsByTagName(e),i=0,r=o.length;if(t)for(;i<r;i++)t(o[i],i);return o}return[]}function G(){var n=document.scrollingElement;return n||document.documentElement}function C(n,e,t,o,i){if(!(!n.getBoundingClientRect&&n!==window)){var r,a,l,s,u,f,d;if(n!==window&&n.parentNode&&n!==G()?(r=n.getBoundingClientRect(),a=r.top,l=r.left,s=r.bottom,u=r.right,f=r.height,d=r.width):(a=0,l=0,s=window.innerHeight,u=window.innerWidth,f=window.innerHeight,d=window.innerWidth),(e||t)&&n!==window&&(i=i||n.parentNode,!Q))do if(i&&i.getBoundingClientRect&&(h(i,"transform")!=="none"||t&&h(i,"position")!=="static")){var m=i.getBoundingClientRect();a-=m.top+parseInt(h(i,"border-top-width")),l-=m.left+parseInt(h(i,"border-left-width")),s=a+r.height,u=l+r.width;break}while(i=i.parentNode);if(o&&n!==window){var y=pe(i||n),v=y&&y.a,E=y&&y.d;y&&(a/=E,l/=v,d/=v,f/=E,s=a+f,u=l+d)}return{top:a,left:l,bottom:s,right:u,width:d,height:f}}}function _t(n,e,t){for(var o=oe(n,!0),i=C(n)[e];o;){var r=C(o)[t],a=void 0;if(t==="top"||t==="left"?a=i>=r:a=i<=r,!a)return o;if(o===G())break;o=oe(o,!1)}return!1}function ge(n,e,t,o){for(var i=0,r=0,a=n.children;r<a.length;){if(a[r].style.display!=="none"&&a[r]!==p.ghost&&(o||a[r]!==p.dragged)&&W(a[r],t.draggable,n,!1)){if(i===e)return a[r];i++}r++}return null}function ft(n,e){for(var t=n.lastElementChild;t&&(t===p.ghost||h(t,"display")==="none"||e&&!Ge(t,e));)t=t.previousElementSibling;return t||null}function X(n,e){var t=0;if(!n||!n.parentNode)return-1;for(;n=n.previousElementSibling;)n.nodeName.toUpperCase()!=="TEMPLATE"&&n!==p.clone&&(!e||Ge(n,e))&&t++;return t}function Et(n){var e=0,t=0,o=G();if(n)do{var i=pe(n),r=i.a,a=i.d;e+=n.scrollLeft*r,t+=n.scrollTop*a}while(n!==o&&(n=n.parentNode));return[e,t]}function sn(n,e){for(var t in n)if(!!n.hasOwnProperty(t)){for(var o in e)if(e.hasOwnProperty(o)&&e[o]===n[t][o])return Number(t)}return-1}function oe(n,e){if(!n||!n.getBoundingClientRect)return G();var t=n,o=!1;do if(t.clientWidth<t.scrollWidth||t.clientHeight<t.scrollHeight){var i=h(t);if(t.clientWidth<t.scrollWidth&&(i.overflowX=="auto"||i.overflowX=="scroll")||t.clientHeight<t.scrollHeight&&(i.overflowY=="auto"||i.overflowY=="scroll")){if(!t.getBoundingClientRect||t===document.body)return G();if(o||e)return t;o=!0}}while(t=t.parentNode);return G()}function un(n,e){if(n&&e)for(var t in e)e.hasOwnProperty(t)&&(n[t]=e[t]);return n}function Ke(n,e){return Math.round(n.top)===Math.round(e.top)&&Math.round(n.left)===Math.round(e.left)&&Math.round(n.height)===Math.round(e.height)&&Math.round(n.width)===Math.round(e.width)}var Ce;function Nt(n,e){return function(){if(!Ce){var t=arguments,o=this;t.length===1?n.call(o,t[0]):n.apply(o,t),Ce=setTimeout(function(){Ce=void 0},e)}}}function cn(){clearTimeout(Ce),Ce=void 0}function Ft(n,e,t){n.scrollLeft+=e,n.scrollTop+=t}function Mt(n){var e=window.Polymer,t=window.jQuery||window.Zepto;return e&&e.dom?e.dom(n).cloneNode(!0):t?t(n).clone(!0)[0]:n.cloneNode(!0)}var B="Sortable"+new Date().getTime();function dn(){var n=[],e;return{captureAnimationState:function(){if(n=[],!!this.options.animation){var o=[].slice.call(this.el.children);o.forEach(function(i){if(!(h(i,"display")==="none"||i===p.ghost)){n.push({target:i,rect:C(i)});var r=j({},n[n.length-1].rect);if(i.thisAnimationDuration){var a=pe(i,!0);a&&(r.top-=a.f,r.left-=a.e)}i.fromRect=r}})}},addAnimationState:function(o){n.push(o)},removeAnimationState:function(o){n.splice(sn(n,{target:o}),1)},animateAll:function(o){var i=this;if(!this.options.animation){clearTimeout(e),typeof o=="function"&&o();return}var r=!1,a=0;n.forEach(function(l){var s=0,u=l.target,f=u.fromRect,d=C(u),m=u.prevFromRect,y=u.prevToRect,v=l.rect,E=pe(u,!0);E&&(d.top-=E.f,d.left-=E.e),u.toRect=d,u.thisAnimationDuration&&Ke(m,d)&&!Ke(f,d)&&(v.top-d.top)/(v.left-d.left)===(f.top-d.top)/(f.left-d.left)&&(s=hn(v,m,y,i.options)),Ke(d,f)||(u.prevFromRect=f,u.prevToRect=d,s||(s=i.options.animation),i.animate(u,v,d,s)),s&&(r=!0,a=Math.max(a,s),clearTimeout(u.animationResetTimer),u.animationResetTimer=setTimeout(function(){u.animationTime=0,u.prevFromRect=null,u.fromRect=null,u.prevToRect=null,u.thisAnimationDuration=null},s),u.thisAnimationDuration=s)}),clearTimeout(e),r?e=setTimeout(function(){typeof o=="function"&&o()},a):typeof o=="function"&&o(),n=[]},animate:function(o,i,r,a){if(a){h(o,"transition",""),h(o,"transform","");var l=pe(this.el),s=l&&l.a,u=l&&l.d,f=(i.left-r.left)/(s||1),d=(i.top-r.top)/(u||1);o.animatingX=!!f,o.animatingY=!!d,h(o,"transform","translate3d("+f+"px,"+d+"px,0)"),this.forRepaintDummy=fn(o),h(o,"transition","transform "+a+"ms"+(this.options.easing?" "+this.options.easing:"")),h(o,"transform","translate3d(0,0,0)"),typeof o.animated=="number"&&clearTimeout(o.animated),o.animated=setTimeout(function(){h(o,"transition",""),h(o,"transform",""),o.animated=!1,o.animatingX=!1,o.animatingY=!1},a)}}}}function fn(n){return n.offsetWidth}function hn(n,e,t,o){return Math.sqrt(Math.pow(e.top-n.top,2)+Math.pow(e.left-n.left,2))/Math.sqrt(Math.pow(e.top-t.top,2)+Math.pow(e.left-t.left,2))*o.animation}var ce=[],Ze={initializeByDefault:!0},xe={mount:function(e){for(var t in Ze)Ze.hasOwnProperty(t)&&!(t in e)&&(e[t]=Ze[t]);ce.forEach(function(o){if(o.pluginName===e.pluginName)throw"Sortable: Cannot mount plugin ".concat(e.pluginName," more than once")}),ce.push(e)},pluginEvent:function(e,t,o){var i=this;this.eventCanceled=!1,o.cancel=function(){i.eventCanceled=!0};var r=e+"Global";ce.forEach(function(a){!t[a.pluginName]||(t[a.pluginName][r]&&t[a.pluginName][r](j({sortable:t},o)),t.options[a.pluginName]&&t[a.pluginName][e]&&t[a.pluginName][e](j({sortable:t},o)))})},initializePlugins:function(e,t,o,i){ce.forEach(function(l){var s=l.pluginName;if(!(!e.options[s]&&!l.initializeByDefault)){var u=new l(e,t,e.options);u.sortable=e,u.options=e.options,e[s]=u,Z(o,u.defaults)}});for(var r in e.options)if(!!e.options.hasOwnProperty(r)){var a=this.modifyOption(e,r,e.options[r]);typeof a!="undefined"&&(e.options[r]=a)}},getEventProperties:function(e,t){var o={};return ce.forEach(function(i){typeof i.eventProperties=="function"&&Z(o,i.eventProperties.call(t[i.pluginName],e))}),o},modifyOption:function(e,t,o){var i;return ce.forEach(function(r){!e[r.pluginName]||r.optionListeners&&typeof r.optionListeners[t]=="function"&&(i=r.optionListeners[t].call(e[r.pluginName],o))}),i}};function pn(n){var e=n.sortable,t=n.rootEl,o=n.name,i=n.targetEl,r=n.cloneEl,a=n.toEl,l=n.fromEl,s=n.oldIndex,u=n.newIndex,f=n.oldDraggableIndex,d=n.newDraggableIndex,m=n.originalEvent,y=n.putSortable,v=n.extraEventProperties;if(e=e||t&&t[B],!!e){var E,Y=e.options,z="on"+o.charAt(0).toUpperCase()+o.substr(1);window.CustomEvent&&!Q&&!Pe?E=new CustomEvent(o,{bubbles:!0,cancelable:!0}):(E=document.createEvent("Event"),E.initEvent(o,!0,!0)),E.to=a||t,E.from=l||t,E.item=i||t,E.clone=r,E.oldIndex=s,E.newIndex=u,E.oldDraggableIndex=f,E.newDraggableIndex=d,E.originalEvent=m,E.pullMode=y?y.lastPutMode:void 0;var A=j(j({},v),xe.getEventProperties(o,e));for(var R in A)E[R]=A[R];t&&t.dispatchEvent(E),Y[z]&&Y[z].call(e,E)}}var gn=["evt"],x=function(e,t){var o=arguments.length>2&&arguments[2]!==void 0?arguments[2]:{},i=o.evt,r=rn(o,gn);xe.pluginEvent.bind(p)(e,t,j({dragEl:c,parentEl:S,ghostEl:g,rootEl:w,nextEl:ue,lastDownEl:Ye,cloneEl:D,cloneHidden:ne,dragStarted:ye,putSortable:I,activeSortable:p.active,originalEvent:i,oldIndex:he,oldDraggableIndex:Ie,newIndex:k,newDraggableIndex:te,hideGhostForTarget:Yt,unhideGhostForTarget:Rt,cloneNowHidden:function(){ne=!0},cloneNowShown:function(){ne=!1},dispatchSortableEvent:function(l){P({sortable:t,name:l,originalEvent:i})}},r))};function P(n){pn(j({putSortable:I,cloneEl:D,targetEl:c,rootEl:w,oldIndex:he,oldDraggableIndex:Ie,newIndex:k,newDraggableIndex:te},n))}var c,S,g,w,ue,Ye,D,ne,he,k,Ie,te,Fe,I,fe=!1,je=!1,ze=[],le,$,Qe,Je,yt,wt,ye,de,Oe,Ae=!1,Me=!1,Re,O,et=[],at=!1,Ue=[],Ve=typeof document!="undefined",ke=Ot,Dt=Pe||Q?"cssFloat":"float",mn=Ve&&!At&&!Ot&&"draggable"in document.createElement("div"),kt=function(){if(!!Ve){if(Q)return!1;var n=document.createElement("x");return n.style.cssText="pointer-events:auto",n.style.pointerEvents==="auto"}}(),Bt=function(e,t){var o=h(e),i=parseInt(o.width)-parseInt(o.paddingLeft)-parseInt(o.paddingRight)-parseInt(o.borderLeftWidth)-parseInt(o.borderRightWidth),r=ge(e,0,t),a=ge(e,1,t),l=r&&h(r),s=a&&h(a),u=l&&parseInt(l.marginLeft)+parseInt(l.marginRight)+C(r).width,f=s&&parseInt(s.marginLeft)+parseInt(s.marginRight)+C(a).width;if(o.display==="flex")return o.flexDirection==="column"||o.flexDirection==="column-reverse"?"vertical":"horizontal";if(o.display==="grid")return o.gridTemplateColumns.split(" ").length<=1?"vertical":"horizontal";if(r&&l.float&&l.float!=="none"){var d=l.float==="left"?"left":"right";return a&&(s.clear==="both"||s.clear===d)?"vertical":"horizontal"}return r&&(l.display==="block"||l.display==="flex"||l.display==="table"||l.display==="grid"||u>=i&&o[Dt]==="none"||a&&o[Dt]==="none"&&u+f>i)?"vertical":"horizontal"},vn=function(e,t,o){var i=o?e.left:e.top,r=o?e.right:e.bottom,a=o?e.width:e.height,l=o?t.left:t.top,s=o?t.right:t.bottom,u=o?t.width:t.height;return i===l||r===s||i+a/2===l+u/2},bn=function(e,t){var o;return ze.some(function(i){var r=i[B].options.emptyInsertThreshold;if(!(!r||ft(i))){var a=C(i),l=e>=a.left-r&&e<=a.right+r,s=t>=a.top-r&&t<=a.bottom+r;if(l&&s)return o=i}}),o},Xt=function(e){function t(r,a){return function(l,s,u,f){var d=l.options.group.name&&s.options.group.name&&l.options.group.name===s.options.group.name;if(r==null&&(a||d))return!0;if(r==null||r===!1)return!1;if(a&&r==="clone")return r;if(typeof r=="function")return t(r(l,s,u,f),a)(l,s,u,f);var m=(a?l:s).options.group.name;return r===!0||typeof r=="string"&&r===m||r.join&&r.indexOf(m)>-1}}var o={},i=e.group;(!i||Xe(i)!="object")&&(i={name:i}),o.name=i.name,o.checkPull=t(i.pull,!0),o.checkPut=t(i.put),o.revertClone=i.revertClone,e.group=o},Yt=function(){!kt&&g&&h(g,"display","none")},Rt=function(){!kt&&g&&h(g,"display","")};Ve&&!At&&document.addEventListener("click",function(n){if(je)return n.preventDefault(),n.stopPropagation&&n.stopPropagation(),n.stopImmediatePropagation&&n.stopImmediatePropagation(),je=!1,!1},!0);var se=function(e){if(c){e=e.touches?e.touches[0]:e;var t=bn(e.clientX,e.clientY);if(t){var o={};for(var i in e)e.hasOwnProperty(i)&&(o[i]=e[i]);o.target=o.rootEl=t,o.preventDefault=void 0,o.stopPropagation=void 0,t[B]._onDragOver(o)}}},_n=function(e){c&&c.parentNode[B]._isOutsideThisEl(e.target)};function p(n,e){if(!(n&&n.nodeType&&n.nodeType===1))throw"Sortable: `el` must be an HTMLElement, not ".concat({}.toString.call(n));this.el=n,this.options=e=Z({},e),n[B]=this;var t={group:null,sort:!0,disabled:!1,store:null,handle:null,draggable:/^[uo]l$/i.test(n.nodeName)?">li":">*",swapThreshold:1,invertSwap:!1,invertedSwapThreshold:null,removeCloneOnHide:!0,direction:function(){return Bt(n,this.options)},ghostClass:"sortable-ghost",chosenClass:"sortable-chosen",dragClass:"sortable-drag",ignore:"a, img",filter:null,preventOnFilter:!0,animation:0,easing:null,setData:function(a,l){a.setData("Text",l.textContent)},dropBubble:!1,dragoverBubble:!1,dataIdAttr:"data-id",delay:0,delayOnTouchOnly:!1,touchStartThreshold:(Number.parseInt?Number:window).parseInt(window.devicePixelRatio,10)||1,forceFallback:!1,fallbackClass:"sortable-fallback",fallbackOnBody:!1,fallbackTolerance:0,fallbackOffset:{x:0,y:0},supportPointer:p.supportPointer!==!1&&"PointerEvent"in window&&!Te,emptyInsertThreshold:5};xe.initializePlugins(this,n,t);for(var o in t)!(o in e)&&(e[o]=t[o]);Xt(e);for(var i in this)i.charAt(0)==="_"&&typeof this[i]=="function"&&(this[i]=this[i].bind(this));this.nativeDraggable=e.forceFallback?!1:mn,this.nativeDraggable&&(this.options.touchStartThreshold=1),e.supportPointer?_(n,"pointerdown",this._onTapStart):(_(n,"mousedown",this._onTapStart),_(n,"touchstart",this._onTapStart)),this.nativeDraggable&&(_(n,"dragover",this),_(n,"dragenter",this)),ze.push(this.el),e.store&&e.store.get&&this.sort(e.store.get(this)||[]),Z(this,dn())}p.prototype={constructor:p,_isOutsideThisEl:function(e){!this.el.contains(e)&&e!==this.el&&(de=null)},_getDirection:function(e,t){return typeof this.options.direction=="function"?this.options.direction.call(this,e,t,c):this.options.direction},_onTapStart:function(e){if(!!e.cancelable){var t=this,o=this.el,i=this.options,r=i.preventOnFilter,a=e.type,l=e.touches&&e.touches[0]||e.pointerType&&e.pointerType==="touch"&&e,s=(l||e).target,u=e.target.shadowRoot&&(e.path&&e.path[0]||e.composedPath&&e.composedPath()[0])||s,f=i.filter;if(In(o),!c&&!(/mousedown|pointerdown/.test(a)&&e.button!==0||i.disabled)&&!u.isContentEditable&&!(!this.nativeDraggable&&Te&&s&&s.tagName.toUpperCase()==="SELECT")&&(s=W(s,i.draggable,o,!1),!(s&&s.animated)&&Ye!==s)){if(he=X(s),Ie=X(s,i.draggable),typeof f=="function"){if(f.call(this,e,s,this)){P({sortable:t,rootEl:u,name:"filter",targetEl:s,toEl:o,fromEl:o}),x("filter",t,{evt:e}),r&&e.cancelable&&e.preventDefault();return}}else if(f&&(f=f.split(",").some(function(d){if(d=W(u,d.trim(),o,!1),d)return P({sortable:t,rootEl:d,name:"filter",targetEl:s,fromEl:o,toEl:o}),x("filter",t,{evt:e}),!0}),f)){r&&e.cancelable&&e.preventDefault();return}i.handle&&!W(u,i.handle,o,!1)||this._prepareDragStart(e,l,s)}}},_prepareDragStart:function(e,t,o){var i=this,r=i.el,a=i.options,l=r.ownerDocument,s;if(o&&!c&&o.parentNode===r){var u=C(o);if(w=r,c=o,S=c.parentNode,ue=c.nextSibling,Ye=o,Fe=a.group,p.dragged=c,le={target:c,clientX:(t||e).clientX,clientY:(t||e).clientY},yt=le.clientX-u.left,wt=le.clientY-u.top,this._lastX=(t||e).clientX,this._lastY=(t||e).clientY,c.style["will-change"]="all",s=function(){if(x("delayEnded",i,{evt:e}),p.eventCanceled){i._onDrop();return}i._disableDelayedDragEvents(),!vt&&i.nativeDraggable&&(c.draggable=!0),i._triggerDragStart(e,t),P({sortable:i,name:"choose",originalEvent:e}),M(c,a.chosenClass,!0)},a.ignore.split(",").forEach(function(f){xt(c,f.trim(),tt)}),_(l,"dragover",se),_(l,"mousemove",se),_(l,"touchmove",se),_(l,"mouseup",i._onDrop),_(l,"touchend",i._onDrop),_(l,"touchcancel",i._onDrop),vt&&this.nativeDraggable&&(this.options.touchStartThreshold=4,c.draggable=!0),x("delayStart",this,{evt:e}),a.delay&&(!a.delayOnTouchOnly||t)&&(!this.nativeDraggable||!(Pe||Q))){if(p.eventCanceled){this._onDrop();return}_(l,"mouseup",i._disableDelayedDrag),_(l,"touchend",i._disableDelayedDrag),_(l,"touchcancel",i._disableDelayedDrag),_(l,"mousemove",i._delayedDragTouchMoveHandler),_(l,"touchmove",i._delayedDragTouchMoveHandler),a.supportPointer&&_(l,"pointermove",i._delayedDragTouchMoveHandler),i._dragStartTimer=setTimeout(s,a.delay)}else s()}},_delayedDragTouchMoveHandler:function(e){var t=e.touches?e.touches[0]:e;Math.max(Math.abs(t.clientX-this._lastX),Math.abs(t.clientY-this._lastY))>=Math.floor(this.options.touchStartThreshold/(this.nativeDraggable&&window.devicePixelRatio||1))&&this._disableDelayedDrag()},_disableDelayedDrag:function(){c&&tt(c),clearTimeout(this._dragStartTimer),this._disableDelayedDragEvents()},_disableDelayedDragEvents:function(){var e=this.el.ownerDocument;b(e,"mouseup",this._disableDelayedDrag),b(e,"touchend",this._disableDelayedDrag),b(e,"touchcancel",this._disableDelayedDrag),b(e,"mousemove",this._delayedDragTouchMoveHandler),b(e,"touchmove",this._delayedDragTouchMoveHandler),b(e,"pointermove",this._delayedDragTouchMoveHandler)},_triggerDragStart:function(e,t){t=t||e.pointerType=="touch"&&e,!this.nativeDraggable||t?this.options.supportPointer?_(document,"pointermove",this._onTouchMove):t?_(document,"touchmove",this._onTouchMove):_(document,"mousemove",this._onTouchMove):(_(c,"dragend",this),_(w,"dragstart",this._onDragStart));try{document.selection?$e(function(){document.selection.empty()}):window.getSelection().removeAllRanges()}catch{}},_dragStarted:function(e,t){if(fe=!1,w&&c){x("dragStarted",this,{evt:t}),this.nativeDraggable&&_(document,"dragover",_n);var o=this.options;!e&&M(c,o.dragClass,!1),M(c,o.ghostClass,!0),p.active=this,e&&this._appendGhost(),P({sortable:this,name:"start",originalEvent:t})}else this._nulling()},_emulateDragOver:function(){if($){this._lastX=$.clientX,this._lastY=$.clientY,Yt();for(var e=document.elementFromPoint($.clientX,$.clientY),t=e;e&&e.shadowRoot&&(e=e.shadowRoot.elementFromPoint($.clientX,$.clientY),e!==t);)t=e;if(c.parentNode[B]._isOutsideThisEl(e),t)do{if(t[B]){var o=void 0;if(o=t[B]._onDragOver({clientX:$.clientX,clientY:$.clientY,target:e,rootEl:t}),o&&!this.options.dragoverBubble)break}e=t}while(t=t.parentNode);Rt()}},_onTouchMove:function(e){if(le){var t=this.options,o=t.fallbackTolerance,i=t.fallbackOffset,r=e.touches?e.touches[0]:e,a=g&&pe(g,!0),l=g&&a&&a.a,s=g&&a&&a.d,u=ke&&O&&Et(O),f=(r.clientX-le.clientX+i.x)/(l||1)+(u?u[0]-et[0]:0)/(l||1),d=(r.clientY-le.clientY+i.y)/(s||1)+(u?u[1]-et[1]:0)/(s||1);if(!p.active&&!fe){if(o&&Math.max(Math.abs(r.clientX-this._lastX),Math.abs(r.clientY-this._lastY))<o)return;this._onDragStart(e,!0)}if(g){a?(a.e+=f-(Qe||0),a.f+=d-(Je||0)):a={a:1,b:0,c:0,d:1,e:f,f:d};var m="matrix(".concat(a.a,",").concat(a.b,",").concat(a.c,",").concat(a.d,",").concat(a.e,",").concat(a.f,")");h(g,"webkitTransform",m),h(g,"mozTransform",m),h(g,"msTransform",m),h(g,"transform",m),Qe=f,Je=d,$=r}e.cancelable&&e.preventDefault()}},_appendGhost:function(){if(!g){var e=this.options.fallbackOnBody?document.body:w,t=C(c,!0,ke,!0,e),o=this.options;if(ke){for(O=e;h(O,"position")==="static"&&h(O,"transform")==="none"&&O!==document;)O=O.parentNode;O!==document.body&&O!==document.documentElement?(O===document&&(O=G()),t.top+=O.scrollTop,t.left+=O.scrollLeft):O=G(),et=Et(O)}g=c.cloneNode(!0),M(g,o.ghostClass,!1),M(g,o.fallbackClass,!0),M(g,o.dragClass,!0),h(g,"transition",""),h(g,"transform",""),h(g,"box-sizing","border-box"),h(g,"margin",0),h(g,"top",t.top),h(g,"left",t.left),h(g,"width",t.width),h(g,"height",t.height),h(g,"opacity","0.8"),h(g,"position",ke?"absolute":"fixed"),h(g,"zIndex","100000"),h(g,"pointerEvents","none"),p.ghost=g,e.appendChild(g),h(g,"transform-origin",yt/parseInt(g.style.width)*100+"% "+wt/parseInt(g.style.height)*100+"%")}},_onDragStart:function(e,t){var o=this,i=e.dataTransfer,r=o.options;if(x("dragStart",this,{evt:e}),p.eventCanceled){this._onDrop();return}x("setupClone",this),p.eventCanceled||(D=Mt(c),D.removeAttribute("id"),D.draggable=!1,D.style["will-change"]="",this._hideClone(),M(D,this.options.chosenClass,!1),p.clone=D),o.cloneId=$e(function(){x("clone",o),!p.eventCanceled&&(o.options.removeCloneOnHide||w.insertBefore(D,c),o._hideClone(),P({sortable:o,name:"clone"}))}),!t&&M(c,r.dragClass,!0),t?(je=!0,o._loopId=setInterval(o._emulateDragOver,50)):(b(document,"mouseup",o._onDrop),b(document,"touchend",o._onDrop),b(document,"touchcancel",o._onDrop),i&&(i.effectAllowed="move",r.setData&&r.setData.call(o,i,c)),_(document,"drop",o),h(c,"transform","translateZ(0)")),fe=!0,o._dragStartId=$e(o._dragStarted.bind(o,t,e)),_(document,"selectstart",o),ye=!0,Te&&h(document.body,"user-select","none")},_onDragOver:function(e){var t=this.el,o=e.target,i,r,a,l=this.options,s=l.group,u=p.active,f=Fe===s,d=l.sort,m=I||u,y,v=this,E=!1;if(at)return;function Y(Ee,Lt){x(Ee,v,j({evt:e,isOwner:f,axis:y?"vertical":"horizontal",revert:a,dragRect:i,targetRect:r,canSort:d,fromSortable:m,target:o,completed:A,onMove:function(gt,Ht){return Be(w,t,c,i,gt,C(gt),e,Ht)},changed:R},Lt))}function z(){Y("dragOverAnimationCapture"),v.captureAnimationState(),v!==m&&m.captureAnimationState()}function A(Ee){return Y("dragOverCompleted",{insertion:Ee}),Ee&&(f?u._hideClone():u._showClone(v),v!==m&&(M(c,I?I.options.ghostClass:u.options.ghostClass,!1),M(c,l.ghostClass,!0)),I!==v&&v!==p.active?I=v:v===p.active&&I&&(I=null),m===v&&(v._ignoreWhileAnimating=o),v.animateAll(function(){Y("dragOverAnimationComplete"),v._ignoreWhileAnimating=null}),v!==m&&(m.animateAll(),m._ignoreWhileAnimating=null)),(o===c&&!c.animated||o===t&&!o.animated)&&(de=null),!l.dragoverBubble&&!e.rootEl&&o!==document&&(c.parentNode[B]._isOutsideThisEl(e.target),!Ee&&se(e)),!l.dragoverBubble&&e.stopPropagation&&e.stopPropagation(),E=!0}function R(){k=X(c),te=X(c,l.draggable),P({sortable:v,name:"change",toEl:t,newIndex:k,newDraggableIndex:te,originalEvent:e})}if(e.preventDefault!==void 0&&e.cancelable&&e.preventDefault(),o=W(o,l.draggable,t,!0),Y("dragOver"),p.eventCanceled)return E;if(c.contains(e.target)||o.animated&&o.animatingX&&o.animatingY||v._ignoreWhileAnimating===o)return A(!1);if(je=!1,u&&!l.disabled&&(f?d||(a=S!==w):I===this||(this.lastPutMode=Fe.checkPull(this,u,c,e))&&s.checkPut(this,u,c,e))){if(y=this._getDirection(e,o)==="vertical",i=C(c),Y("dragOverValid"),p.eventCanceled)return E;if(a)return S=w,z(),this._hideClone(),Y("revert"),p.eventCanceled||(ue?w.insertBefore(c,ue):w.appendChild(c)),A(!0);var N=ft(t,l.draggable);if(!N||Dn(e,y,this)&&!N.animated){if(N===c)return A(!1);if(N&&t===e.target&&(o=N),o&&(r=C(o)),Be(w,t,c,i,o,r,e,!!o)!==!1)return z(),N&&N.nextSibling?t.insertBefore(c,N.nextSibling):t.appendChild(c),S=t,R(),A(!0)}else if(N&&wn(e,y,this)){var ie=ge(t,0,l,!0);if(ie===c)return A(!1);if(o=ie,r=C(o),Be(w,t,c,i,o,r,e,!1)!==!1)return z(),t.insertBefore(c,ie),S=t,R(),A(!0)}else if(o.parentNode===t){r=C(o);var H=0,re,me=c.parentNode!==t,F=!vn(c.animated&&c.toRect||i,o.animated&&o.toRect||r,y),ve=y?"top":"left",J=_t(o,"top","top")||_t(c,"top","top"),be=J?J.scrollTop:void 0;de!==o&&(re=r[ve],Ae=!1,Me=!F&&l.invertSwap||me),H=Sn(e,o,r,y,F?1:l.swapThreshold,l.invertedSwapThreshold==null?l.swapThreshold:l.invertedSwapThreshold,Me,de===o);var U;if(H!==0){var ae=X(c);do ae-=H,U=S.children[ae];while(U&&(h(U,"display")==="none"||U===g))}if(H===0||U===o)return A(!1);de=o,Oe=H;var _e=o.nextElementSibling,ee=!1;ee=H===1;var Ne=Be(w,t,c,i,o,r,e,ee);if(Ne!==!1)return(Ne===1||Ne===-1)&&(ee=Ne===1),at=!0,setTimeout(yn,30),z(),ee&&!_e?t.appendChild(c):o.parentNode.insertBefore(c,ee?_e:o),J&&Ft(J,0,be-J.scrollTop),S=c.parentNode,re!==void 0&&!Me&&(Re=Math.abs(re-C(o)[ve])),R(),A(!0)}if(t.contains(c))return A(!1)}return!1},_ignoreWhileAnimating:null,_offMoveEvents:function(){b(document,"mousemove",this._onTouchMove),b(document,"touchmove",this._onTouchMove),b(document,"pointermove",this._onTouchMove),b(document,"dragover",se),b(document,"mousemove",se),b(document,"touchmove",se)},_offUpEvents:function(){var e=this.el.ownerDocument;b(e,"mouseup",this._onDrop),b(e,"touchend",this._onDrop),b(e,"pointerup",this._onDrop),b(e,"touchcancel",this._onDrop),b(document,"selectstart",this)},_onDrop:function(e){var t=this.el,o=this.options;if(k=X(c),te=X(c,o.draggable),x("drop",this,{evt:e}),S=c&&c.parentNode,k=X(c),te=X(c,o.draggable),p.eventCanceled){this._nulling();return}fe=!1,Me=!1,Ae=!1,clearInterval(this._loopId),clearTimeout(this._dragStartTimer),lt(this.cloneId),lt(this._dragStartId),this.nativeDraggable&&(b(document,"drop",this),b(t,"dragstart",this._onDragStart)),this._offMoveEvents(),this._offUpEvents(),Te&&h(document.body,"user-select",""),h(c,"transform",""),e&&(ye&&(e.cancelable&&e.preventDefault(),!o.dropBubble&&e.stopPropagation()),g&&g.parentNode&&g.parentNode.removeChild(g),(w===S||I&&I.lastPutMode!=="clone")&&D&&D.parentNode&&D.parentNode.removeChild(D),c&&(this.nativeDraggable&&b(c,"dragend",this),tt(c),c.style["will-change"]="",ye&&!fe&&M(c,I?I.options.ghostClass:this.options.ghostClass,!1),M(c,this.options.chosenClass,!1),P({sortable:this,name:"unchoose",toEl:S,newIndex:null,newDraggableIndex:null,originalEvent:e}),w!==S?(k>=0&&(P({rootEl:S,name:"add",toEl:S,fromEl:w,originalEvent:e}),P({sortable:this,name:"remove",toEl:S,originalEvent:e}),P({rootEl:S,name:"sort",toEl:S,fromEl:w,originalEvent:e}),P({sortable:this,name:"sort",toEl:S,originalEvent:e})),I&&I.save()):k!==he&&k>=0&&(P({sortable:this,name:"update",toEl:S,originalEvent:e}),P({sortable:this,name:"sort",toEl:S,originalEvent:e})),p.active&&((k==null||k===-1)&&(k=he,te=Ie),P({sortable:this,name:"end",toEl:S,originalEvent:e}),this.save()))),this._nulling()},_nulling:function(){x("nulling",this),w=c=S=g=ue=D=Ye=ne=le=$=ye=k=te=he=Ie=de=Oe=I=Fe=p.dragged=p.ghost=p.clone=p.active=null,Ue.forEach(function(e){e.checked=!0}),Ue.length=Qe=Je=0},handleEvent:function(e){switch(e.type){case"drop":case"dragend":this._onDrop(e);break;case"dragenter":case"dragover":c&&(this._onDragOver(e),En(e));break;case"selectstart":e.preventDefault();break}},toArray:function(){for(var e=[],t,o=this.el.children,i=0,r=o.length,a=this.options;i<r;i++)t=o[i],W(t,a.draggable,this.el,!1)&&e.push(t.getAttribute(a.dataIdAttr)||Cn(t));return e},sort:function(e,t){var o={},i=this.el;this.toArray().forEach(function(r,a){var l=i.children[a];W(l,this.options.draggable,i,!1)&&(o[r]=l)},this),t&&this.captureAnimationState(),e.forEach(function(r){o[r]&&(i.removeChild(o[r]),i.appendChild(o[r]))}),t&&this.animateAll()},save:function(){var e=this.options.store;e&&e.set&&e.set(this)},closest:function(e,t){return W(e,t||this.options.draggable,this.el,!1)},option:function(e,t){var o=this.options;if(t===void 0)return o[e];var i=xe.modifyOption(this,e,t);typeof i!="undefined"?o[e]=i:o[e]=t,e==="group"&&Xt(o)},destroy:function(){x("destroy",this);var e=this.el;e[B]=null,b(e,"mousedown",this._onTapStart),b(e,"touchstart",this._onTapStart),b(e,"pointerdown",this._onTapStart),this.nativeDraggable&&(b(e,"dragover",this),b(e,"dragenter",this)),Array.prototype.forEach.call(e.querySelectorAll("[draggable]"),function(t){t.removeAttribute("draggable")}),this._onDrop(),this._disableDelayedDragEvents(),ze.splice(ze.indexOf(this.el),1),this.el=e=null},_hideClone:function(){if(!ne){if(x("hideClone",this),p.eventCanceled)return;h(D,"display","none"),this.options.removeCloneOnHide&&D.parentNode&&D.parentNode.removeChild(D),ne=!0}},_showClone:function(e){if(e.lastPutMode!=="clone"){this._hideClone();return}if(ne){if(x("showClone",this),p.eventCanceled)return;c.parentNode==w&&!this.options.group.revertClone?w.insertBefore(D,c):ue?w.insertBefore(D,ue):w.appendChild(D),this.options.group.revertClone&&this.animate(c,D),h(D,"display",""),ne=!1}}};function En(n){n.dataTransfer&&(n.dataTransfer.dropEffect="move"),n.cancelable&&n.preventDefault()}function Be(n,e,t,o,i,r,a,l){var s,u=n[B],f=u.options.onMove,d;return window.CustomEvent&&!Q&&!Pe?s=new CustomEvent("move",{bubbles:!0,cancelable:!0}):(s=document.createEvent("Event"),s.initEvent("move",!0,!0)),s.to=e,s.from=n,s.dragged=t,s.draggedRect=o,s.related=i||e,s.relatedRect=r||C(e),s.willInsertAfter=l,s.originalEvent=a,n.dispatchEvent(s),f&&(d=f.call(u,s,a)),d}function tt(n){n.draggable=!1}function yn(){at=!1}function wn(n,e,t){var o=C(ge(t.el,0,t.options,!0)),i=10;return e?n.clientX<o.left-i||n.clientY<o.top&&n.clientX<o.right:n.clientY<o.top-i||n.clientY<o.bottom&&n.clientX<o.left}function Dn(n,e,t){var o=C(ft(t.el,t.options.draggable)),i=10;return e?n.clientX>o.right+i||n.clientX<=o.right&&n.clientY>o.bottom&&n.clientX>=o.left:n.clientX>o.right&&n.clientY>o.top||n.clientX<=o.right&&n.clientY>o.bottom+i}function Sn(n,e,t,o,i,r,a,l){var s=o?n.clientY:n.clientX,u=o?t.height:t.width,f=o?t.top:t.left,d=o?t.bottom:t.right,m=!1;if(!a){if(l&&Re<u*i){if(!Ae&&(Oe===1?s>f+u*r/2:s<d-u*r/2)&&(Ae=!0),Ae)m=!0;else if(Oe===1?s<f+Re:s>d-Re)return-Oe}else if(s>f+u*(1-i)/2&&s<d-u*(1-i)/2)return Tn(e)}return m=m||a,m&&(s<f+u*r/2||s>d-u*r/2)?s>f+u/2?1:-1:0}function Tn(n){return X(c)<X(n)?1:-1}function Cn(n){for(var e=n.tagName+n.className+n.src+n.href+n.textContent,t=e.length,o=0;t--;)o+=e.charCodeAt(t);return o.toString(36)}function In(n){Ue.length=0;for(var e=n.getElementsByTagName("input"),t=e.length;t--;){var o=e[t];o.checked&&Ue.push(o)}}function $e(n){return setTimeout(n,0)}function lt(n){return clearTimeout(n)}Ve&&_(document,"touchmove",function(n){(p.active||fe)&&n.cancelable&&n.preventDefault()});p.utils={on:_,off:b,css:h,find:xt,is:function(e,t){return!!W(e,t,e,!1)},extend:un,throttle:Nt,closest:W,toggleClass:M,clone:Mt,index:X,nextTick:$e,cancelNextTick:lt,detectDirection:Bt,getChild:ge};p.get=function(n){return n[B]};p.mount=function(){for(var n=arguments.length,e=new Array(n),t=0;t<n;t++)e[t]=arguments[t];e[0].constructor===Array&&(e=e[0]),e.forEach(function(o){if(!o.prototype||!o.prototype.constructor)throw"Sortable: Mounted plugin must be a constructor function, not ".concat({}.toString.call(o));o.utils&&(p.utils=j(j({},p.utils),o.utils)),xe.mount(o)})};p.create=function(n,e){return new p(n,e)};p.version=an;var T=[],we,st,ut=!1,nt,ot,qe,De;function On(){function n(){this.defaults={scroll:!0,forceAutoScrollFallback:!1,scrollSensitivity:30,scrollSpeed:10,bubbleScroll:!0};for(var e in this)e.charAt(0)==="_"&&typeof this[e]=="function"&&(this[e]=this[e].bind(this))}return n.prototype={dragStarted:function(t){var o=t.originalEvent;this.sortable.nativeDraggable?_(document,"dragover",this._handleAutoScroll):this.options.supportPointer?_(document,"pointermove",this._handleFallbackAutoScroll):o.touches?_(document,"touchmove",this._handleFallbackAutoScroll):_(document,"mousemove",this._handleFallbackAutoScroll)},dragOverCompleted:function(t){var o=t.originalEvent;!this.options.dragOverBubble&&!o.rootEl&&this._handleAutoScroll(o)},drop:function(){this.sortable.nativeDraggable?b(document,"dragover",this._handleAutoScroll):(b(document,"pointermove",this._handleFallbackAutoScroll),b(document,"touchmove",this._handleFallbackAutoScroll),b(document,"mousemove",this._handleFallbackAutoScroll)),St(),Le(),cn()},nulling:function(){qe=st=we=ut=De=nt=ot=null,T.length=0},_handleFallbackAutoScroll:function(t){this._handleAutoScroll(t,!0)},_handleAutoScroll:function(t,o){var i=this,r=(t.touches?t.touches[0]:t).clientX,a=(t.touches?t.touches[0]:t).clientY,l=document.elementFromPoint(r,a);if(qe=t,o||this.options.forceAutoScrollFallback||Pe||Q||Te){it(t,this.options,l,o);var s=oe(l,!0);ut&&(!De||r!==nt||a!==ot)&&(De&&St(),De=setInterval(function(){var u=oe(document.elementFromPoint(r,a),!0);u!==s&&(s=u,Le()),it(t,i.options,u,o)},10),nt=r,ot=a)}else{if(!this.options.bubbleScroll||oe(l,!0)===G()){Le();return}it(t,this.options,oe(l,!1),!1)}}},Z(n,{pluginName:"scroll",initializeByDefault:!0})}function Le(){T.forEach(function(n){clearInterval(n.pid)}),T=[]}function St(){clearInterval(De)}var it=Nt(function(n,e,t,o){if(!!e.scroll){var i=(n.touches?n.touches[0]:n).clientX,r=(n.touches?n.touches[0]:n).clientY,a=e.scrollSensitivity,l=e.scrollSpeed,s=G(),u=!1,f;st!==t&&(st=t,Le(),we=e.scroll,f=e.scrollFn,we===!0&&(we=oe(t,!0)));var d=0,m=we;do{var y=m,v=C(y),E=v.top,Y=v.bottom,z=v.left,A=v.right,R=v.width,N=v.height,ie=void 0,H=void 0,re=y.scrollWidth,me=y.scrollHeight,F=h(y),ve=y.scrollLeft,J=y.scrollTop;y===s?(ie=R<re&&(F.overflowX==="auto"||F.overflowX==="scroll"||F.overflowX==="visible"),H=N<me&&(F.overflowY==="auto"||F.overflowY==="scroll"||F.overflowY==="visible")):(ie=R<re&&(F.overflowX==="auto"||F.overflowX==="scroll"),H=N<me&&(F.overflowY==="auto"||F.overflowY==="scroll"));var be=ie&&(Math.abs(A-i)<=a&&ve+R<re)-(Math.abs(z-i)<=a&&!!ve),U=H&&(Math.abs(Y-r)<=a&&J+N<me)-(Math.abs(E-r)<=a&&!!J);if(!T[d])for(var ae=0;ae<=d;ae++)T[ae]||(T[ae]={});(T[d].vx!=be||T[d].vy!=U||T[d].el!==y)&&(T[d].el=y,T[d].vx=be,T[d].vy=U,clearInterval(T[d].pid),(be!=0||U!=0)&&(u=!0,T[d].pid=setInterval(function(){o&&this.layer===0&&p.active._onTouchMove(qe);var _e=T[this.layer].vy?T[this.layer].vy*l:0,ee=T[this.layer].vx?T[this.layer].vx*l:0;typeof f=="function"&&f.call(p.dragged.parentNode[B],ee,_e,n,qe,T[this.layer].el)!=="continue"||Ft(T[this.layer].el,ee,_e)}.bind({layer:d}),24))),d++}while(e.bubbleScroll&&m!==s&&(m=oe(m,!1)));ut=u}},30),$t=function(e){var t=e.originalEvent,o=e.putSortable,i=e.dragEl,r=e.activeSortable,a=e.dispatchSortableEvent,l=e.hideGhostForTarget,s=e.unhideGhostForTarget;if(!!t){var u=o||r;l();var f=t.changedTouches&&t.changedTouches.length?t.changedTouches[0]:t,d=document.elementFromPoint(f.clientX,f.clientY);s(),u&&!u.el.contains(d)&&(a("spill"),this.onSpill({dragEl:i,putSortable:o}))}};function ht(){}ht.prototype={startIndex:null,dragStart:function(e){var t=e.oldDraggableIndex;this.startIndex=t},onSpill:function(e){var t=e.dragEl,o=e.putSortable;this.sortable.captureAnimationState(),o&&o.captureAnimationState();var i=ge(this.sortable.el,this.startIndex,this.options);i?this.sortable.el.insertBefore(t,i):this.sortable.el.appendChild(t),this.sortable.animateAll(),o&&o.animateAll()},drop:$t};Z(ht,{pluginName:"revertOnSpill"});function pt(){}pt.prototype={onSpill:function(e){var t=e.dragEl,o=e.putSortable,i=o||this.sortable;i.captureAnimationState(),t.parentNode&&t.parentNode.removeChild(t),i.animateAll()},drop:$t};Z(pt,{pluginName:"removeOnSpill"});p.mount(new On);p.mount(pt,ht);const An=ct({components:{Item:tn},props:{data:{type:Object,default:()=>({name:"",children:[]})}},setup(n){const e=Ct(null);return jt(()=>{e.value.list=n.data,new p(e.value,{group:"shared",animation:150,ghostClass:"blue-background-class",onEnd:function(t){const o=t.pullMode,i=t.oldIndex,r=t.newIndex;let a=t.target.list.children,l=t.to.list.children;if(o)l.splice(r,0,a[i]),a.splice(i,1);else{const s=a[i];a[i]=a[r],a[r]=s,console.log(a[0])}}})}),{dom:e}}}),Pn={class:"block"},xn={class:"header"},Nn={class:"num"},Fn={class:"list",ref:"dom"};function Mn(n,e,t,o,i,r){const a=rt("Item");return L(),q("div",Pn,[V("div",xn,[V("span",Nn,Se(n.data.children.length),1),V("div",null,Se(n.data.name),1)]),V("div",Fn,[(L(!0),q(He,null,We(n.data.children,l=>(L(),It(a,{key:l.id,data:l},null,8,["data"]))),128))],512)])}var kn=dt(An,[["render",Mn],["__scopeId","data-v-cf88fed8"]]);function Bn(n){return zt({url:"/work/list",method:"post",baseURL:"/mock",data:n})}const Xn=ct({components:{Block:kn},setup(){let n=Ct([]);return(()=>{Bn({}).then(o=>{n.value=o.data.list})})(),{list:n}}}),Yn={class:"layout-container"},Rn=Tt("\u8BF7\u5C1D\u8BD5\u5DE6\u53F3\u62D6\u52A8\u6A21\u5757\uFF0C\u6570\u636E\u4ECE\u63A5\u53E3\u800C\u6765\uFF0C\u4EFB\u610F\u65F6\u523B\u5747\u53EF\u4EE5\u63D0\u4EA4\u81F3\u63A5\u53E3\u4E2D\u53BB"),$n={class:"layout-container-table"},Ln={class:"list"};function Hn(n,e,t,o,i,r){const a=rt("el-alert"),l=rt("Block");return L(),q("div",Yn,[Ut(a,{type:"success"},{default:qt(()=>[Rn]),_:1}),V("div",$n,[V("div",Ln,[(L(!0),q(He,null,We(n.list,(s,u)=>(L(),It(l,{key:u,data:s},null,8,["data"]))),128))])])])}var jn=dt(Xn,[["render",Hn],["__scopeId","data-v-c164b1ae"]]);export{jn as default};
