<template>
  <div class="no-data-container">
    <div class="icon">
      <img src="../assets/svg/404.svg" alt="" />
    </div>
    <div class="text">糟糕~ 页面失踪啦</div>
    <el-button round type="primary" @click="$router.push('home')">返回首页</el-button>
  </div>
</template>

<script>
export default {
  name: '404',
  data() {
    return {}
  }
}
</script>

<style lang="scss" scoped>
.no-data-container {
  height: 90vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  color: #ccc;
  font-size: 16px;
  img {
    width: 80px;
    height: 80px;
    object-fit: cover;
  }
  .text {
    margin-top: 10px;
  }
  .el-button {
    margin-top: 20px;
  }
}
</style>
