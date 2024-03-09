<template>
  <div class="login-container">
    <div class="login-bg-warp">
      <div class="login-form-warp">
        <div class="login-form">
          <div class="welcome-title">登  录</div>
          <el-form :model="user" :rules="rules" ref="userFormInst" @keydown.enter="login">
            <el-form-item prop="username">
              <el-input  placeholder="请输入用户名"  size="medium" prefix-icon="el-icon-user" v-model="user.username"></el-input>
            </el-form-item>
            <el-form-item prop="password" >
              <el-input size="medium" prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
            </el-form-item>

            <!-- <el-form-item>
              <div class="verify-code-form-item">
                <el-input placeholder="请输入验证码" size="medium" prefix-icon="el-icon-news" v-model="code"></el-input>
                <span @click="refreshCode">
                  <Identify :identifyCode="identifyCode"></Identify>
                </span>
              </div>
            </el-form-item> -->


            <el-form-item>
              <el-checkbox v-model="isAllow" class="allow-warp">我已阅读同意{{ projectName }}《隐私政策》和《服务条款》</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-button class="btn-login" type="primary" size="medium" autocomplete="off" :disabled="!isAllow" @click="login">立即登录</el-button>
            </el-form-item>
            <el-form-item>
              <div class="other-warp">
                <div @click="$router.push('/register')">还没有账号，去注册</div>
                <div @click="handlePass">找回密码</div>
              </div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <el-dialog title="找回密码" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="100px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="pass.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="pass.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="passwordBack">重置密码</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { resetRouter, setRoutes } from '@/router'
import Identify from '@/components/Identify'
import { projectName } from '../../config/config.default'

export default {
  name: 'Login',
  data() {
    return {
      projectName: projectName,
      user: {},
      pass: {},
      code: '',
      isAllow: true,
      dialogFormVisible: false,
      // 图片验证码
      identifyCode: '',
      // 验证码规则
      identifyCodes: '3456789ABCDEFGHGKMNPQRSTUVWXY',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  components: { Identify },
  mounted() {
    // 重置路由
    resetRouter()
    // this.refreshCode()
  },
  methods: {
    login() {
      if (this.code !== this.identifyCode.toLowerCase()) {
        this.$message({
          type: 'error',
          message: '验证码错误'
        })
        return
      }
      this.$refs['userFormInst'].validate((valid) => {
        if (valid) {
          // 表单校验合法
          this.request.post('/user/login', this.user).then((res) => {
            if (res.code === '200') {
              localStorage.setItem('user', JSON.stringify(res.data)) // 存储用户信息到浏览器
              localStorage.setItem('menus', JSON.stringify(res.data.menus)) // 存储用户信息到浏览器
              // 动态设置当前用户的路由
              setRoutes()
              if (res.data.menus.length==0) {
                this.$router.push('/front/home')
                this.$message.success('登录成功')
              } else {
                this.$router.push('/')
                this.$message.success('登录成功')
              }
            } else {
              this.$message.error(res.msg || '出错啦')
            }
          })
        }
      })
    },
    // // 切换验证码
    // refreshCode() {
    //   this.identifyCode = ''
    //   this.makeCode(this.identifyCodes, 4)
    // },
    // // 生成随机验证码
    // makeCode(o, l) {
    //   for (let i = 0; i < l; i++) {
    //     this.identifyCode += this.identifyCodes[Math.floor(Math.random() * this.identifyCodes.length)]
    //   }
    // },
    handlePass() {
      this.dialogFormVisible = true
      this.pass = {}
    },
    passwordBack() {
      this.request.put('/user/reset', this.pass).then((res) => {
        if (res.code === '200') {
          this.$message.success('重置密码成功，新密码为：123，请尽快修改密码')
          this.dialogFormVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.login-container {
  width: 100%;
  height: 100vh;
  background-color: #f5f5f5;
  display: flex;
  .login-bg-warp {
    width: 100%;
    background: url('../assets/login.png') no-repeat;
    background-size: cover;
  }
  .login-form-warp {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 200px ;

    .login-form {
      width: 450px;
      // border: #4084d9 1px solid;
      border-radius: 20px;
      background-color: rgba(255, 255, 255, 0.219);
      backdrop-filter: blur(10px);
      // background: url('../assets/login.png');
      padding: 25px;
      box-shadow:6px 6px 20px 3px #858383   ;

      .input{
        background-color: #366ef4;
      }
      .welcome-title {
        font-size: 24px;
        margin-bottom: 30px;
        font-family: Arial, Helvetica, sans-serif;
        color: #f5f5f5;
        text-align: center;
      }
      .verify-code-form-item {
        display: flex;
        align-items: center;
      }
      .allow-warp {
        color: #366ef4;
        display: flex;
        align-items: center;
        font-size: 12px;
      }
      .btn-login {
        width: 100%;
      }
      .other-warp {
        width: 100%;
        display: flex;
        justify-content: space-between;
        color: #366ef4;
        cursor: pointer;
      }
    }
  }
}
</style>
