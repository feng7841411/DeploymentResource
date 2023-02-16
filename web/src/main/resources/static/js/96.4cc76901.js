"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[96],{9096:function(t,e,a){a.r(e),a.d(e,{default:function(){return r}});var i=function(){var t=this,e=t._self._c;return e("div",{staticClass:"myHome"},[e("div",{staticStyle:{padding:"20px"}},[e("el-breadcrumb",{attrs:{separator:"/"}},[e("el-breadcrumb-item",{attrs:{to:{path:""}}},[e("span",{staticStyle:{color:"white"}},[t._v("用户中心")])]),e("el-breadcrumb-item",[e("span",{staticStyle:{color:"white"}},[t._v("我的资源")])])],1),e("div",[e("div",{staticStyle:{padding:"10px",float:"left"}},[e("el-input",{staticClass:"ml-5",staticStyle:{width:"200px"},attrs:{placeholder:"请输入资源名称","suffix-icon":"el-icon-search"},model:{value:t.packageName,callback:function(e){t.packageName=e},expression:"packageName"}})],1),e("div",{staticStyle:{padding:"10px",float:"left"}},[e("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"primary"},on:{click:t.load}},[t._v("搜索")])],1),e("div",{staticStyle:{padding:"10px",float:"left"}},[e("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"success"},on:{click:t.clearFilter}},[t._v("重置筛选条件")])],1)]),e("el-table",{staticStyle:{"font-size":"15px"},attrs:{data:t.tableData,border:"",stripe:"","header-cell-style":{background:"#42b983",color:"white"},stripe:!0,"highlight-current-row":!0,"max-height":"600"},on:{"selection-change":t.handleSelectionChange}},[e("el-table-column",{attrs:{type:"selection","min-width":"20%"}}),e("el-table-column",{attrs:{prop:"packageName",label:"资源名称","min-width":"20%"}}),e("el-table-column",{attrs:{prop:"packageTime",label:"资源上传时间","min-width":"20%"}}),e("el-table-column",{attrs:{prop:"packageType",label:"资源类型","min-width":"20%"}}),e("el-table-column",{attrs:{prop:"packageStatus",label:"资源状态","min-width":"20%"}}),e("el-table-column",{attrs:{prop:"",label:"操作","min-width":"20%",align:"center"},scopedSlots:t._u([{key:"default",fn:function(a){return[e("el-button",{attrs:{type:"success",size:"mini"},on:{click:function(e){return t.getPackageDetailInfoButton(a.row.packageName,a.row.connectedPackageUid,a.row.connectedDetailInfoId)}}},[e("i",{staticClass:"el-icon-view"},[t._v("查看详情")])])]}}])})],1),e("el-dialog",{attrs:{title:"资源包基础信息",visible:t.packageDetailDialogFormVisible},on:{"update:visible":function(e){t.packageDetailDialogFormVisible=e}}},[e("el-card",[e("div",{staticStyle:{"text-align":"left"},attrs:{slot:"header"},slot:"header"},[e("span",[t._v(t._s(this.packageNameInDialog))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("资源包名:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.connectedPackageOriginalFileName))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("资源包大小:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.connectedPackageSize))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("服务中文名:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.softwareCnName))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("运行环境:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.softwareEnvironment))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("服务版本号:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.softwareVersion))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("服务唯一标识:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.softwareUniqueName))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("服务开发者:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.developerName))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("服务开发单位:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.developerGroup))])]),e("div",{staticStyle:{"text-align":"left",color:"black","padding-left":"10px","font-size":"larger"}},[e("span",{staticStyle:{"padding-right":"20px"}},[t._v("联系方式:")]),e("span",{staticStyle:{"font-weight":"bold","padding-right":"10px",color:"#1482f0","font-size":"larger"}},[t._v(t._s(this.packageDetailInfo.developerPhone))])])]),e("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[e("el-button",{attrs:{type:"primary"},on:{click:function(e){t.packageDetailDialogFormVisible=!1}}},[t._v("确 定")])],1)],1),e("div",{staticStyle:{padding:"10px 0","background-color":"white"}},[e("el-pagination",{attrs:{"current-page":t.pageNum,"page-sizes":[2,5,10,20],"page-size":t.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:t.total},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}})],1)],1)])},l=[],o={name:"MyResources",data(){return{tableData:[],pageNum:1,pageSize:10,total:0,packageName:"",packageIdInDialog:"",packageNameInDialog:"",packageStatusInDialog:"",packageUidInDialog:"",packageConnectedDetailId:"",packageDetailDialogFormVisible:!1,packageDetailInfo:"",user:window.sessionStorage.getItem("userAccount")}},created(){this.load()},methods:{load(){console.log(this.operator),console.log(this.logKeyWord),console.log(this.timeValue),this.request.post("/myResources/getMyResources",{params:{pageNum:this.pageNum,pageSize:this.pageSize,packageName:this.packageName,author:this.user}}).then((t=>{this.tableData=t.data.records,this.total=t.data.total,console.log(this.tableData),console.log(this.total)}))},handleSelectionChange(t){this.multipleSelection=t,console.log(t)},handleSizeChange(t){console.log(t),this.pageSize=t,this.load()},handleCurrentChange(t){console.log(t),this.pageNum=t,this.load()},clearFilter(){this.packageName="",this.load()},getPackageDetailInfoButton(t,e,a){this.packageNameInDialog=t,this.packageConnectedDetailId=a,this.request.post("/servicePackageDetailInfo/getServicePackageDetailInfoById",{params:{packageConnectedDetailId:this.packageConnectedDetailId}}).then((t=>{"200"===t.code&&(this.packageDetailDialogFormVisible=!0,this.packageDetailInfo=t.data)}))}}},n=o,s=a(3736),c=(0,s.Z)(n,i,l,!1,null,"680dacef",null),r=c.exports}}]);
//# sourceMappingURL=96.4cc76901.js.map