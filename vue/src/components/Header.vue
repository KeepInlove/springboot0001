<template>
  <div style="line-height: 60px; display: flex" :class="`${themes[themeStatus]}`">
    <div style="flex: 1;">
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div style="width: 220px; color: gray ; font-size: 14px; display: flex;align-items: center; margin-right: 20px">北京时间： {{ time }}</div>

    <el-dropdown style="width: 120px; cursor: pointer; text-align: right;margin-right: 20px">
      <div style="display: inline-block">
        <img :src="user.avatarUrl" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/password">修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/person">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <div style="text-decoration: none"  @click="logout">退出</div>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    user: Object,
    themeStatus : Number,
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  data() {
    return {
      themes:['theme1','theme2','theme3','theme4','theme5','theme6','theme7','theme8'],
      time: this.getTime(),
    }
  },
  created() {
    setInterval(() => {
      this.time = this.getTime()
    }, 1000)
  },
  methods: {
    logout() {
      this.$store.commit("logout")
      this.$message.success("退出成功")
    },
    getTime(){
      // 获取时间戳
      const newDate = new Date();
      const year = newDate.getFullYear();
      let month = newDate.getMonth() + 1;
      const date = newDate.getDate();
      let hour = newDate.getHours();
      let minute = newDate.getMinutes();
      let second = newDate.getSeconds();
      if (hour < 10) {
        hour = "0" + hour
      }
      if (minute < 10) {
        minute = "0" + minute
      }
      if (second < 10) {
        second = "0" + second
      }
      return `${year}/${month}/${date} ${hour}:${minute}:${second}`;
    },
  },
}
</script>

<style scoped>

.el-breadcrumb >>> .el-breadcrumb__inner{
  color: var(--font-color-primary) !important;
}
</style>

<style>
.el-header {
  padding-left: 10px!important;
  padding-right: 0!important;
}
</style>
