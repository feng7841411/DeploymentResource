"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[812],{8812:function(e,t,s){s.r(t),s.d(t,{default:function(){return o}});var r=function(){var e=this,t=e._self._c;return t("div",{staticClass:"myHome"},[t("div",{staticStyle:{padding:"20px"}},[t("h1",{staticClass:"whiteHeaderStyle"},[e._v("欢迎使用边缘开设部署软件，您的登入身份是："+e._s(e.user)+", 权限："+e._s(e.role)+" ")]),t("br"),t("h1",{staticClass:"whiteHeaderStyle"},[e._v("请选择左侧的功能分栏开始使用。")]),t("br"),e._e()])])},l=[],n=(s(5306),{name:"Home",data(){return{user:window.sessionStorage.getItem("userAccount"),role:window.sessionStorage.getItem("userRole")}},created(){let e=this.user;console.log(e),null===e&&this.$router.replace("/login"),""!==e||this.$router.replace("/login")}}),u=n,a=s(3736),i=(0,a.Z)(u,r,l,!1,null,"268450cb",null),o=i.exports}}]);
//# sourceMappingURL=812.adda7e7d.js.map