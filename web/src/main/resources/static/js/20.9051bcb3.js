"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[20],{2020:function(e,t,i){i.r(t),i.d(t,{default:function(){return r}});var l=function(){var e=this,t=e._self._c;return t("div",{staticStyle:{display:"flex","flex-direction":"column",height:"80vh"}},[t("el-breadcrumb",{attrs:{separator:"/"}},[t("el-breadcrumb-item",[t("b",[e._v("资源管理")])]),t("el-breadcrumb-item",[e._v("测试用-上传公共数据包")])],1),t("el-row",[t("el-col",{attrs:{span:8}},[t("div",{staticStyle:{"padding-top":"0px","padding-left":"20px",float:"left"}},[t("el-upload",{ref:"upload",staticStyle:{display:"inline-block",float:"left"},attrs:{action:"#","show-file-list":!0,"before-upload":e.checkImageFile,disabled:!1,"auto-upload":!1,"on-preview":e.handlePreview,"on-success":e.handle_success,"http-request":e.handleFileUpload,"on-change":e.handleChange,"on-remove":e.handleRemove,limit:"1"}},[t("el-button",{staticStyle:{"margin-top":"20px"},attrs:{slot:"trigger",size:"small",type:"warning"},slot:"trigger"},[e._v("选取测试数据包")]),t("el-button",{staticStyle:{"margin-left":"20px"},attrs:{size:"small",type:"success"},on:{click:e.submitUpload}},[e._v("确认")])],1)],1),e.ifUploadPlugInFiles?t("div",{staticStyle:{float:"left","padding-left":"20px","padding-top":"14px"}},[t("i",{staticClass:"el-icon-success",staticStyle:{color:"green","font-size":"40px"}})]):e._e()])],1),t("el-row",{staticStyle:{"padding-top":"20px","padding-left":"20px"}},[t("el-col",{attrs:{span:4}},[t("el-input",{attrs:{placeholder:"请输入数据名"},model:{value:e.fileName,callback:function(t){e.fileName=t},expression:"fileName"}})],1),t("el-col",{attrs:{span:4}},[t("el-input",{attrs:{placeholder:"请输入数据描述"},model:{value:e.fileNote,callback:function(t){e.fileNote=t},expression:"fileNote"}})],1),t("el-col",{attrs:{span:4}},[t("el-input",{attrs:{placeholder:"请输入数据类型"},model:{value:e.fileType,callback:function(t){e.fileType=t},expression:"fileType"}})],1),t("el-col",{attrs:{span:4}},[t("el-input",{attrs:{placeholder:"请输入数据版本"},model:{value:e.fileVersion,callback:function(t){e.fileVersion=t},expression:"fileVersion"}})],1),t("el-col",{attrs:{span:4}},[t("el-button",{attrs:{type:"primary"},on:{click:e.uploadFunction}},[e._v("确定")])],1)],1)],1)},a=[],s={name:"UploadPublicDataTemp",data(){return{fileList:[],fileUid:"",ifUploadPlugInFiles:!1,fileName:"",fileNote:"",fileType:"",fileVersion:""}},methods:{checkImageFile(e){},handlePreview(e){console.log(e)},handleRemove(e,t){this.packageDetailInfo="",this.$message.info("文件已移除，请重新选取")},handle_success(e){this.$message.success("文件已上传")},handleFileUpload(){},handleChange(e,t){this.fileList=t},handleRemoveWorkLoadYaml(e,t){this.$message.info("文件已移除，请重新选取")},async submitUpload(){if(0==this.fileList.length)this.$message.warning("没有检测到选取文件，请检查。");else{const e=new FormData;this.fileList.forEach((t=>{e.append("files",t.raw)}));const t={headers:{"Content-Type":"multipart/form-data"}};this.maskDialogVisible=!0,await this.request.post("/publicData/uploadPublicData",e,t).then((e=>{"200"===e.code?(this.fileUid=e.data,this.ifUploadPlugInFiles=!0,this.$message.success("上传成功")):this.$message.error("上传失败,原因:"+e.msg)}))}},uploadFunction(){""===this.fileName?this.$message.warning("没有填公共数据包名"):""===this.fileNote?this.$message.warning("没有填公共数据描述"):""===this.fileUid?this.$message.warning("没有上传数据包"):""===this.fileType?this.$message.warning("没有数据类型"):""===this.fileVersion?this.$message.warning("没有数据版本"):this.request.post("/publicData/postPublicData",{params:{publicDataUid:this.fileUid,publicDataName:this.fileName,publicDataNote:this.fileNote,publicDataType:this.fileType,publicDataVersion:this.fileVersion}}).then((e=>{"200"===e.code?this.$message.success("测试数据包得到确认"):this.$message.error("系统异常")}))}}},n=s,o=i(3736),c=(0,o.Z)(n,l,a,!1,null,"38f4a514",null),r=c.exports}}]);
//# sourceMappingURL=20.9051bcb3.js.map