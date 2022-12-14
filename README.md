# 工程简介

# 延伸阅读


# debug
- Bean的自动注入失败时，除了POM的依赖关系之外，应该检查一下SpringBoot启动类上有没有加包扫描注解

# 功能开发
2022年9月15日10:19:00
- 用户功能
    - 支持用户角色区分，暂定为管理员和普通开发者
    - 支持用户登录、注册、注销（删号）
    - 开发者账户只能操作自己上传的包，对市场里的其他包，能够下载，但是不能够修改或者删除
    - 管理员拥有审核、删除、发布、查看等所有权限
    
- 软件包管理功能
    - 支持浏览器软件包上传
    - 支持校验软件包是否符合统一打包的规范；
        - 目前暂时只校验一下里面的信息能否正常读取，如果读取信息正常，认为包是正常的
        - 这个功能应该是可选项，可以选择是否开启检查
        - **这个功能具备很多可以扩展延伸的东西，比如如果有其他的杀毒或者漏洞扫描的服务，接入这个功能，就可以做更丰富的软件包扫描
    - 支持符合权限的软件包下载
    - 软件包上传校验后，状态应该是【待审核】，需要管理员账号审批后，才进入正常的状态；
    
- 多点同步(较复杂，待，边缘项目暂时不涉及这个)
    - 假设有固定软件商店和边缘软件商店，可以考虑边缘商店会始终在后台拉取固定软件商店的包
        + 【边】读取【固定】的包列表的MD5值列表
        + 将列表的内容与本地仓库中的包的MD5值进行对比
        + 遇到Md5值相同的，不做处理，但是要检测名称之类的是否变更，如果有，更新PackageInfo
        + 遇到Md5值不同的，则在后台静默拉取同步
        + 主要参数：同步周期（若干小时）；限制带宽（后台同步不把带宽全部占用？）
        + 反向操作： 如果中心云删除了一个包，而边缘云之前同步过这个包的副本，现在中央仓库没有了，要不要删掉？
    - 扩展场景
        + 一个中心云，多个边缘云，边缘云的配置文件指向中心云，这些边缘云将不断地与中心云保持同步
        + 这个功能应该由边缘云的运维界面来决定是否开启，就是一个勾选项，“开启云边仓库同步”
    
- 是否做【开设部署】和【软件商店】的互通
    - 若互通，可以用同一套用户系统
    - 两个系统是否可以互相跳转，比如通过导航栏，或者下方的友情链接
    - 对于云来讲，【开设部署管理员】和【软件商店管理员】很可能不是一个人，可能互通性没有太多要求；
    - 对于边来讲，可能比较有意义，因为边上的信息服务席位就一个，从开设部署环境整体来讲，他应该能在两边进行跳转或者管理
        - 看看自己收到了哪些部署任务，哪些需要自己确定核实的；
        - 看看自己的边缘仓库里现在有哪些服务
        
