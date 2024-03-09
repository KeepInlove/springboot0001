<template>
  <div style="color: #666; font-size: 14px">

    <el-row :gutter="24">
      <el-col :span="14">
        <el-carousel height="550px" :interval="5000">
          <el-carousel-item v-for="item in imgs" :key="item">
            <img :src="item" style="width: 100%; height: 100%" />
          </el-carousel-item>
        </el-carousel>
      </el-col>

      <el-col :span="10">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>系统技术选型</span>
          </div>
          <div class="text item">
            操作系统: Windows10/11
          </div>
          <div class="text item">
            开发工具: Idea
          </div>
          <div class="text item">
            系统架构: 前后端分离B/S架构
          </div>
          <div class="text item">
            后端: SpringBoot+MybatisPlus
          </div>
          <div class="text item">
            前端: Vue+ElementUI+Axios
          </div>
          <div class="text item">
            数据库: Mysql5.7/8.0
          </div>
        </el-card>

        <el-card class="box-card" style="margin-top: 10px">
          <div slot="header" class="clearfix">
            <span>系统运行方式</span>
          </div>
          <div class="text item">
            创建mysql数据库，导入目录中的sql文件
          </div>
          <div class="text item">
            后端:安装jdk1.8，配置maven，使用idea运行SpringBoot服务
          </div>
          <div class="text item">
            前端:安装nodejs16，进入vue项目所在目录，打开命令行输入npm i安装依赖，依赖安装完成后命令行继续输入npm run serve
          </div>
          <div class="text item">
            前端服务运行完成后在浏览器输入
            <el-link type="success">http://localhost:8080</el-link>
          </div>
        </el-card>
      </el-col>
    </el-row>


    <el-row :gutter="24" style="margin-top: 10px">
      <el-col :span="14">
        <el-card style="width: 100%">
          <p>
            你好，管理员
          </p>
          <p style="margin-top: 5px">
            欢迎使用本系统
          </p>
          <el-divider />
        </el-card>
      </el-col>

      <el-col :span="10">
        <el-card class="box-card" style="height: 150px">
          <div slot="header" class="clearfix">
            <span>系统额外功能</span>
          </div>
          <div class="text item">
            点击当前页面左上角融合管理平台左边的
            <el-tag>LOGO</el-tag>
          </div>
          <div class="text item">
            在右边的弹窗当中可以选择系统后台的
            <el-tag type="success">主题色</el-tag>
            和
            <el-tag type="warning">界面布局</el-tag>
          </div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      imgs: [
        "https://ts1.cn.mm.bing.net/th/id/R-C.06f740c6fe1f071e0b1c3e2223e639c9?rik=8dQ3mjvXPbM6MA&riu=http%3a%2f%2fp8.qhimg.com%2ft01f6eb0501493f49c2.jpg%3fsize%3d2560x1600&ehk=1%2b4k7I8rsILza8cVNQeufROx57BXipPxG0r69A7WFnw%3d&risl=&pid=ImgRaw&r=0",
        "https://ts1.cn.mm.bing.net/th/id/R-C.b2b9e2e19aa34905771a4956e36cb898?rik=2GPfRiq0aOQdag&riu=http%3a%2f%2fimage.tianjimedia.com%2fuploadImages%2f2010%2f221%2f0122327KRMZE_028.jpg&ehk=q1EzXNjY083lQrtuQTHOaMjw14DVJlyAJtwWMKxDWrw%3d&risl=&pid=ImgRaw&r=0",
        "https://ts1.cn.mm.bing.net/th/id/R-C.c50424990f2df738101819e9faf0bc8e?rik=B%2fSuxByNc2J6nw&riu=http%3a%2f%2fimg.pconline.com.cn%2fimages%2fupload%2fupc%2ftx%2fwallpaper%2f1208%2f08%2fc0%2f12768539_1344397602300.jpg&ehk=fgBFosBrRHtZYNM4VEi3jXmtGOGQuBVdjd2WDWQGbNc%3d&risl=&pid=ImgRaw&r=0",
      ],
    };
  },
};
</script>

<style>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}
</style>

