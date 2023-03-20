"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[294],{5294:function(e,t,a){a.r(t),a.d(t,{default:function(){return g}});var i=function(){var e=this,t=e._self._c;return t("div",{staticClass:"myHome"},[t("div",{staticStyle:{padding:"20px"}},[t("el-breadcrumb",{attrs:{separator:"/"}},[t("el-breadcrumb-item",{attrs:{to:{path:""}}},[t("span",{staticStyle:{color:"white"}},[e._v("资源管理")])]),t("el-breadcrumb-item",[t("span",{staticStyle:{color:"white"}},[e._v("已发布资源")])])],1),t("div",[t("div",{staticStyle:{padding:"10px",float:"left"}},[t("el-input",{staticClass:"ml-5",staticStyle:{width:"200px"},attrs:{placeholder:"请输入资源包名","suffix-icon":"el-icon-search"},model:{value:e.packageName,callback:function(t){e.packageName=t},expression:"packageName"}})],1),t("div",{staticStyle:{padding:"10px",float:"left"}},[t("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入上传者名称","suffix-icon":"el-icon-search"},model:{value:e.packageAuthor,callback:function(t){e.packageAuthor=t},expression:"packageAuthor"}})],1),t("div",{staticStyle:{padding:"10px",float:"left"}},[t("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"primary"},on:{click:e.load}},[e._v("搜索")])],1),t("div",{staticStyle:{padding:"10px",float:"left"}},[t("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"success"},on:{click:e.clearFilter}},[e._v("重置筛选条件")])],1)]),t("el-table",{staticStyle:{"font-size":"15px"},attrs:{data:e.tableData,border:"",stripe:"","header-cell-style":{background:"#42b983",color:"white"},stripe:!0,"highlight-current-row":!0,"max-height":"600"},on:{"selection-change":e.handleSelectionChange}},[t("el-table-column",{attrs:{type:"selection","min-width":"20%"}}),t("el-table-column",{attrs:{prop:"bePublishedPackageName",label:"资源包名","min-width":"20%"}}),t("el-table-column",{attrs:{prop:"packageCnName",label:"资源中文名","min-width":"15%",resizable:!1}}),t("el-table-column",{attrs:{prop:"bePublishedPackageType",label:"资源包类型","min-width":"20%"}}),t("el-table-column",{attrs:{prop:"bePublishedPackageAuthor",label:"资源包上传者","min-width":"20%"}}),t("el-table-column",{attrs:{prop:"bePublishedPackageTime",label:"上架时间","min-width":"20%"}}),t("el-table-column",{attrs:{prop:"",label:"操作","min-width":"20%",align:"center"},scopedSlots:e._u([{key:"default",fn:function(a){return[t("el-button",{attrs:{type:"success",size:"mini"},on:{click:function(t){return e.getPackageDetailInfoButton(a.row.bePublishedPackageId,a.row.bePublishedPackageName,a.row.connectedDetailInfoId)}}},[t("i",{staticClass:"el-icon-view"},[e._v("查看详情")])]),t("el-button",{attrs:{type:"danger"},on:{click:function(t){return e.packageRemoveButton(a.row.bePublishedPackageId,a.row.bePublishedPackageName,a.row.connectedDetailInfoId)}}},[t("i",{staticClass:"el-icon-"},[e._v("下 架")])])]}}])})],1),t("el-dialog",{attrs:{title:"资源包基础信息",visible:e.packageDetailDialogFormVisible},on:{"update:visible":function(t){e.packageDetailDialogFormVisible=t}}},[t("el-card",[t("div",{staticStyle:{"text-align":"left"},attrs:{slot:"header"},slot:"header"},[t("span",[e._v(e._s(this.packageNameInDialog))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("资源包名:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.connectedPackageOriginalFileName))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("资源包大小:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.connectedPackageSize))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("服务中文名:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.softwareCnName))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("运行环境:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.softwareEnvironment))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("服务版本号:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.softwareVersion))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("服务唯一标识:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.softwareUniqueName))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("容器服务CPU需求(cpuRequests):")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.cpuRequests))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("容器服务内存需求(memoryRequests):")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.memoryRequests))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("服务开发者:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.developerName))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("服务开发单位:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.developerGroup))])]),t("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[t("span",{staticStyle:{"padding-right":"20px"}},[e._v("联系方式:")]),t("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[e._v(e._s(this.packageDetailInfo.developerPhone))])])]),t("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{attrs:{type:"primary"},on:{click:function(t){e.packageDetailDialogFormVisible=!1}}},[e._v("确 定")])],1)],1),t("el-dialog",{attrs:{title:"下架",visible:e.packageRemoveDialogFormVisible},on:{"update:visible":function(t){e.packageRemoveDialogFormVisible=t}}},[t("el-card",[t("div",{staticStyle:{"text-align":"left"},attrs:{slot:"header"},slot:"header"},[t("span",[e._v(e._s(this.packageNameInDialog))]),t("br"),t("span",[e._v("此操作将下架对应的资源包，资源包下架后，外部应用将无法查询和下载到资源包，但是可以被管理员重新上架")])])]),t("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{attrs:{type:"info"},on:{click:function(t){e.packageRemoveDialogFormVisible=!1}}},[e._v("确 定")]),t("el-button",{attrs:{type:"danger"},on:{click:e.packageRemove}},[e._v("确 定")])],1)],1),t("div",{staticStyle:{padding:"10px 0","background-color":"white"}},[t("el-pagination",{attrs:{"current-page":e.pageNum,"page-sizes":[2,5,10,20],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1)])},l=[],s={name:"PublishedPackage",data(){return{tableData:[],pageNum:1,pageSize:10,total:0,packageName:"",packageAuthor:"",multipleSelection:[],user:window.sessionStorage.getItem("userAccount"),packageIdInDialog:"",packageNameInDialog:"",packageStatusInDialog:"",packageConnectedDetailId:"",packageDetailDialogFormVisible:!1,packageDetailInfo:"",packageRemoveDialogFormVisible:!1}},created(){this.load()},methods:{async load(){await this.request.get("/publishedPackage/page",{params:{pageNum:this.pageNum,pageSize:this.pageSize,packageName:this.packageName,packageAuthor:this.packageAuthor}}).then((e=>{console.log(e),this.tableData=e.records,this.total=e.total})),this.getPendingReviewPackageAsideNumber(),this.getPublishedPackageAsideNumber(),this.getRemovedPackageAsideNumber()},getPendingReviewPackageAsideNumber(){this.request.get("/pendingReviewPackage/getPendingPackageAsideNumber").then((e=>{this.$store.commit("setPendingPackageNumber",e.data)}))},getPublishedPackageAsideNumber(){this.request.get("/publishedPackage/getPublishedPackageAsideNumber").then((e=>{this.$store.commit("setPublishedPackageNumber",e.data)}))},getRemovedPackageAsideNumber(){this.request.get("/removedPackage/getRemovedPackageAsideNumber").then((e=>{this.$store.commit("setRemovedPackageNumber",e.data)}))},handleSizeChange(e){console.log(e),this.pageSize=e,this.load()},handleCurrentChange(e){console.log(e),this.pageNum=e,this.load()},clearFilter(){this.packageName="",this.packageAuthor="",this.load()},handleSelectionChange(e){this.multipleSelection=e,console.log(e)},getPackageDetailInfoButton(e,t,a){this.packageIdInDialog=e,this.packageNameInDialog=t,this.packageConnectedDetailId=a,this.request.post("/servicePackageDetailInfo/getServicePackageDetailInfoById",{params:{packageConnectedDetailId:this.packageConnectedDetailId}}).then((e=>{"200"===e.code&&(this.packageDetailDialogFormVisible=!0,this.packageDetailInfo=e.data)}))},packageRemoveButton(e,t,a){this.packageIdInDialog=e,this.packageNameInDialog=t,this.packageConnectedDetailId=a,this.packageRemoveDialogFormVisible=!0},async packageRemove(){await this.request.post("/publishedPackage/removePackageById",{params:{packageId:this.packageIdInDialog,operator:this.user,servicePackageDetailInfoId:this.packageConnectedDetailId}}).then((e=>{"200"===e.code?this.$message.success(e.msg):this.$message.warning("系统异常，操作失败")})),await this.load(),this.packageRemoveDialogFormVisible=!1}}},o=s,n=a(3736),c=(0,n.Z)(o,i,l,!1,null,"1d6d72f9",null),g=c.exports}}]);
//# sourceMappingURL=294.40ffaab9.js.map