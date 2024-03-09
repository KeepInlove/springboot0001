<template>

    <div class="payment-page">
      <el-card class="payment-card">
        <h2 STYLE="color: #d63031">支付金额：{{ payAmount }}</h2>
        <div class="options">
          <el-radio-group v-model="paymentMethod">
            <el-radio label="alipay">
              <img src="/alipay_icon.png" alt="Alipay" class="payment-icon">
              支付宝
            </el-radio>
            <el-radio label="wechat">
              <img src="/wechat_icon.png" alt="WeChat" class="payment-icon">
              微信
            </el-radio>
          </el-radio-group>
        </div>
        <div class="button-container">
          <el-button type="primary" @click="pay"  medium>支付</el-button>
        </div>
      </el-card>
    </div>
</template>
<script>
export default {
  name:"Pay",
  data() {
    return {
      paymentMethod: 'alipay', // 默认支付方式为支付宝
      payAmount:this.$route.params.totalAmount?this.$route.params.totalAmount:0,
      orderId:this.$route.params.orderId?this.$route.params.orderId:0
    };
  },
  methods: {
    pay() {
      this.request.get("/orderInfo/pay/"+this.orderId).then(res => {
        if (res.code === '200') {
          this.$message.success("支付成功,3秒自动跳转")
          setTimeout(() => {
            this.$router.push({ name: 'orderInfo' }); // 跳转到订单管理页面
          }, 3000); // 3000 毫秒后跳转
        }
      })
    }
  }
};
</script>

<style scoped>
.payment-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.payment-card {
  width: 1000px;
  height: 500px;
  text-align: center;
  padding: 20px;
}

.options {
  margin-bottom: 20px;
}

.payment-icon {
  width: 300px;
  height: 300px;
}

.button-container {
  margin-top: auto; /* 将按钮放置在卡片底部 */
}
</style>
