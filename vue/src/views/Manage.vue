<template>
  <el-container style="min-height: 100vh">
    <el-aside :width="sideWidth + 'px'">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" @openDrawer="openDrawer" :themeStatus="themeStatus" style="padding-bottom: 20px" />
    </el-aside>

    <el-container>
      <el-header style="background: #fff" v-if="header">
        <Header :collapseBtnClass="collapseBtnClass" :user="user" :themeStatus="themeStatus" />
      </el-header>

      <el-main>
        <!--        表示当前页面的子路由会在 <router-view /> 里面展示-->
        <router-view @refreshUser="getUser" :themeStatus="themeStatus" />
      </el-main>
    </el-container>

    <el-drawer :visible.sync="drawer" :with-header="false" size="18%">
      <div>
        <h5 style="margin-top: 30px; margin-left: 30px">主题色</h5>
        <div style="text-align: center; margin-top: 5px">
          <div v-for="index in themes.length" style="display: inline-block; margin-right: 20px" :class="` ${themes[index - 1]}`">
            <div class="theme" style="height: 20px; width: 20px" @click="changeStatus(index - 1)"></div>
            <div style="text-align: center" v-if="themeStatus === index - 1" class="fontTheme">✓</div>
          </div>
        </div>
      </div>

      <el-divider />

      <div>
        <h5 style="margin-top: 30px; margin-left: 30px">内容展示</h5>
        <div style="margin-left: 30px; margin-top: 20px">
          <span style="font-size: 15px">菜单</span>
          <el-switch v-model="aside" active-color="#13ce66" inactive-color="#ccc" @change="changeAside()" style="left: 60%"></el-switch>
        </div>
        <div style="margin-left: 30px; margin-top: 20px">
          <span style="font-size: 15px">顶栏</span>
          <el-switch v-model="header" active-color="#13ce66" inactive-color="#ccc" style="left: 60%"></el-switch>
        </div>
      </div>

      <el-divider />

      <div>
        <h5 style="margin-top: 30px; margin-left: 30px">界面</h5>
        <div style="margin-left: 30px; margin-top: 20px">
          <span style="font-size: 15px">全屏</span>
          <el-switch v-model="fullScreen" active-color="#13ce66" inactive-color="#ccc" @change="changeScreen()" style="left: 60%"></el-switch>
        </div>
      </div>

      <el-divider />


    </el-drawer>
  </el-container>
</template>

<script>
import Aside from '@/components/Aside'
import Header from '@/components/Header'
import screenFull from 'screenfull'

export default {
  name: 'Home',
  data() {
    return {
      drawer: false,
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: {},
      themeStatus: +localStorage.getItem('theme') || 0,
      themes: ['theme1', 'theme2', 'theme3', 'theme4', 'theme5', 'theme6', 'theme7', 'theme8'],
      aside: true,
      header: true,
      fullScreen: false
    }
  },
  components: {
    Aside,
    Header
  },
  created() {
    // 从后台获取最新的User数据
    this.getUser()
  },
  methods: {
    changeAside() {
      // 点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {
        // 收缩
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {
        // 展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    changeScreen() {
      screenFull.toggle()
    },
    openDrawer() {
      this.drawer = true
    },
    changeStatus(index) {
      window.localStorage.setItem('theme', JSON.stringify(index))
      this.themeStatus = index
    },
    getUser() {
      let username = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')).username : ''
      if (username) {
        // 从后台获取User数据
        this.request.get('/user/username/' + username).then((res) => {
          // 重新赋值后台的最新User数据
          this.user = res.data
        })
      }
    }
  }
}
</script>

<style scoped>
.theme {
  background-color: var(--font-color-primary) !important;
}

.fontTheme {
  color: var(--font-color-primary) !important;
}
.el-container {
  background-color: #f9f9f9;
}
.el-main {
  background-color: #fff;
  margin: 10px;
  padding: 10px;
  border-radius: 4px;
}
</style>
