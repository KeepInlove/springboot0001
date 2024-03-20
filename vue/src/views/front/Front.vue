<template>
  <div class="front-container">
    <div class="header-nav">
      <div class="header-left-warp">
        <div class="logo-warp">
          <div class="logo">
            <img src="../../../public/logo.svg" alt="" />
          </div>
          <div class="logo-text">{{ projectName }}</div>
        </div>
        <div class="header-navs">
          <el-menu
            router
            :default-active="$route.path"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
          >
            <!--前台路由-->
            <el-menu-item index="/front/home">首页</el-menu-item>
            <el-menu-item index='/front/article'>宠物资讯</el-menu-item>
            <el-menu-item index='/front/collect'>服务收藏</el-menu-item>
            <el-menu-item index='/front/goods'>商品信息</el-menu-item>
            <el-menu-item index='/front/cart'>我的购物车</el-menu-item>
            <el-menu-item index='/front/orders'>服务记录</el-menu-item>
            <el-menu-item index='/front/myOrderInfo'>商品订单</el-menu-item>

            <!--前台路由-->
          </el-menu>
        </div>
      </div>
      <div class="user-warp">
        <el-button style="margin-bottom: 10px;float: right;margin-right: 20px" type="primary" @click="handleAdd">预约服务</el-button>
        <div v-if="!user.id" class="btn-login">
          <el-button @click="$router.push('/login')">登录</el-button>
        </div>
        <div v-if="!user.id" class="btn-login" style="margin-left: 10px">
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <el-dropdown v-else>
          <div class="user-avatar">
            <img :src="user.avatarUrl" />
          </div>
          <el-dropdown-menu size="medium" slot="dropdown">
            <el-dropdown-item>{{ user.nickname }}</el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/front/person"><div>个人信息</div></router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/front/address"><div>地址管理</div></router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/front/myOrderInfo"><div>下单记录</div></router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/front/password"
                ><div>修改密码</div></router-link
              >
            </el-dropdown-item>
            <el-dropdown-item
              ><div @click="logout">退出登录</div></el-dropdown-item
            >

          </el-dropdown-menu>
        </el-dropdown>
      </div>

    </div>
    <div class="main-content">
      <router-view @refreshUser="getUser"></router-view>
    </div>

    <el-dialog title="请填写预约信息" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false">
      <el-form label-width="auto" size="small" style="width: 100%" :model="form"  ref="ruleForm">

        <el-form-item prop="petsName" label="宠物名称">
          <el-input v-model="form.petsName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="userName" label="主人姓名">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="联系电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="address" label="地址">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="img" label="宠物图片">
          <el-upload :action="serverHost+'/file/upload'" ref="img" :on-success="handleImgUploadSuccess">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="startTime" label="预约时间">
          <el-date-picker v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item prop="employ" label="服务">
          <el-input v-model="form.employ" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {projectName, serverHost} from "../../../config/config.default";
export default {
  name: "",
  data() {
    return {
      serverHost : serverHost,
      projectName: projectName,
      user: localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user"))
        : {},
      dialogFormVisible: false,
      form:{}
    };
  },
  methods: {
    handleSelect(key, keyPath) {},
    logout() {
      this.$store.commit("logout");
      this.$message.success("退出成功");
    },
    getUser() {
      let username = localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user")).username
        : "";
      if (username) {
        // 从后台获取User数据
        this.request.get("/user/username/" + username).then((res) => {
          // 重新赋值后台的最新User数据
          this.user = res.data;
        });
      }
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
      this.$nextTick(() => {
        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
    save() {
      this.form.username = this.username;
      this.form.phone = this.form.phone;
      this.form.petsId = this.id;
      this.form.userId=this.user.id
      this.request.post("/orders", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })


    },
  },
};
</script>

<style lang="scss" scoped>
.front-container {
  background-color: var(--color-background-2);
  min-height: 100vh;
  .header-nav {
    z-index: 1800;
    position: sticky;
    top: 0;
    height: 70px;
    background-color: var(--color-background-2);
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    box-shadow: 0 2px 10px 0 rgb(0 0 0 / 10%);
    .header-left-warp {
      margin-left: 20px;
      display: flex;
      align-items: center;
      .logo-warp {
        display: flex;
        align-items: center;
        .logo {
          width: 30px;
          height: 30px;
          margin-right: 5px;
          img {
            width: 100%;
            height: 100%;
            object-fit: cover;
          }
        }
        .logo-text {
          font-size: 22px;
          font-weight: 500;
          color: var(--color-success);
        }
      }
      .header-navs {
        margin-left: 100px;
        .el-menu {
          background: transparent;
        }
        .el-menu--horizontal {
          border: none;
        }
        .el-submenu__title {
          padding: 0;
          font-size: 17px !important;
          height: 35px;
          margin: 0 10px;
          display: flex;
          align-items: center;
        }
        .is-active {
          border-bottom: 2px solid var(--color-success);
          color: var(--color-success);
        }
      }
    }
    .user-warp {
      margin-right: 10px;
      margin-top: 5px;
      display: flex;
      .user-avatar {
        width: 40px;
        height: 40px;

        border-radius: 50%;
        overflow: hidden;
        border: 1px solid var(--color-primary);
        padding: 2px;
        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
          border-radius: 50%;
        }
      }
    }
  }
  .main-content {

  }
}
</style>
