<template>
  <el-menu
    :default-openeds="opens"
    style="min-height: 100%; overflow-x: hidden"
    :default-active="$route.path"
    :collapse-transition="false"
    :collapse="isCollapse"
    router
    :class="`${themes[themeStatus]}`"
  >
    <div class="logo-warp" style="height: 70px; line-height: 60px; text-align: center">

      <div class="logo-inner">
        <div @click="openDrawer" style="display: inline-block">
          <img src="../../public/logo.svg" alt="" style="width: 30px; position: relative; top: 8px" />
        </div>

        <span style="margin-left: 5px; font-size: 22px; font-weight: 500" v-show="logoTextShow" :class="`theme ${themes[themeStatus]}`">宠物预约管理平台</span>
      </div>
    </div>
    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id + ''" :class="`theme ${themes[themeStatus]}`">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: 'Aside',
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
    themeStatus: Number
  },
  data() {
    return {
      themes: ['theme1', 'theme2', 'theme3', 'theme4', 'theme5', 'theme6', 'theme7', 'theme8'],
      menus: localStorage.getItem('menus') ? JSON.parse(localStorage.getItem('menus')) : [],
      opens: localStorage.getItem('menus') ? JSON.parse(localStorage.getItem('menus')).map((v) => v.id + '') : []
    }
  },
  methods: {
    openDrawer() {
      this.$emit('openDrawer')
    }
  }
}
</script>

<style scoped>
.el-menu-item:hover {
  color: var(--font-color-primary) !important;
  background-color: white !important;
}

.el-menu-item.is-active {
  background-color: var(--back-color-primary) !important;
  color: var(--font-color-primary) !important;
  border-right-style: solid;
  border-right-width: 4px;
  border-right-color: var(--font-color-primary) !important;
}

.el-submenu >>> .el-submenu__title {
  background-color: white !important;
}

.el-submenu >>> .el-submenu__title :hover {
  color: var(--font-color-primary) !important;
}

::v-deep.el-submenu.is-opened > .el-submenu__title {
  color: var(--font-color-primary) !important;
}

::v-deep .el-submenu.is-opened > .el-submenu__title i {
  color: var(--font-color-primary) !important;
}

/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
  visibility: hidden;
}

.theme {
  color: var(--font-color-primary) !important;
}

.el-menu {
  border: none;
}

.logo-warp {
  background-color: #f9f9f9;
  padding-bottom: 10px;
  height: 100%;
}
.logo-inner {
  background-color: #fff;
}
</style>
