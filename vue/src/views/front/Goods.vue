<template>
  <div class="home-container">

    <el-card style="margin-top: 10px;">
      <div style="display: flex;justify-content: flex-end;">
        <div style="display: flex">
          <div style="flex: 1">
            <el-input v-model="name" placeholder="请输入内容"></el-input>
          </div>
          <div style="flex: 1">
            <el-button style="margin-left: 10px;" @click="load">搜索</el-button>
            <el-button style="margin-left: 10px;" type="warning" @click="reset">重置</el-button>
          </div>
        </div>

      </div>
    </el-card>

    <div class="item-center">
      <el-row :gutter="24">
        <el-col :span="17">
          <el-row :gutter="10">
            <el-col :span="6" v-for="item in items" :key="item.id" style="margin-bottom: 10px">
              <div style="border: 1px solid #ccc; padding-bottom: 10px; border-radius: 10px; overflow: hidden;">
                <img :src="item.img" alt="" style="width: 100%; height: 220px;cursor: pointer" @click="$router.push('/front/goodsDetail?id=' + item.id)">
                <div style="color: #000; margin: 15px" class="info">
                  商品：{{ item.goodsName }}
                  价格：{{item.price}}元
                </div>
                <div style="color: #000; margin: 15px" class="info">
                  剩余：{{item.stockNum}}
                  已售出：{{item.stockOutNum}}
                </div>
                <div style="display: flex;justify-content: space-around">
                  <el-input-number v-model="item.num"   :min="0" :max="10"  label="描述文字"></el-input-number>
                  <el-button type="danger" @click="addToCart(item)"> 加入购物车 </el-button>
                </div>
              </div>
            </el-col>
          </el-row>
          <div style="margin: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[8 , 16]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>

        </el-col>

        <el-col :span="7">

          <el-card>
            <el-carousel height="300px" :interval="10000">
              <el-carousel-item v-for="item in imgs" :key="item">
                <img :src="item" style="width: 100%;height: 100%">
              </el-carousel-item>
            </el-carousel>
          </el-card>

        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script>

export default {
  name: "Goods",
  data() {
    return {
      items:[],
      name:'',
      pageNum:1,
      pageSize:8,
      total:0,
      imgs: [
        "https://demosc.chinaz.net/Files/DownLoad/webjs1/201907/jiaoben6919/img/lunbo1.jpg",
        "https://pic2.zhimg.com/v2-d0e775ca666f3f2b049de8a88fc4aba5_r.jpg",
        "https://ts1.cn.mm.bing.net/th/id/R-C.bd3b631bde86bfdd75720dd544e3d32f?rik=IxwYcffHn4f3tQ&riu=http%3a%2f%2fimg.zcool.cn%2fcommunity%2f01f6625768ad220000012e7e78a909.jpg&ehk=TjQyehtL0i1DZVMZkrwUyBShDQclVX2ACNRZTTDKbzk%3d&risl=&pid=ImgRaw&r=0",
      ],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      cartFrom:{}
    };
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.request.get("/goods/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
        }
      }).then(res=>{
        if(res.code==='200'){
          this.items=res.data?.records || []
          this.total=res.data?.total || 0
        }
      })
    },
    addToCart(item){
      // 将对应商品加入购物车
      if (item.num<0){
        this.$message.error("请选择数量加入购物车")
        return
      }
      this.cartFrom.goodsId=item.id
      this.cartFrom.userId=this.user.id
      this.cartFrom.num=item.num
      this.request.post("/cart", this.cartFrom).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    reset(){
      this.name=''
      this.load()
    }
  }
};
</script>

<style lang="scss" scoped>
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

.info{
  height: 15px;
  line-height: 1.2;
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1; /* 这里是超出几行省略 */
  overflow: hidden;
}

.home-container {
  width: 70%;
  margin: 0 auto;
  .item-center{
    .el-divider--horizontal {
      display: block;
      height: 1px;
      width: 100%;
      margin: 10px 0;
    }
    margin-top: 20px;
  }
}
</style>
