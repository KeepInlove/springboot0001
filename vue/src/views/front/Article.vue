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

          <div v-for="item in items">
            <div class="item" >
              <div class="item-img">
                <img :src="item.img" style="height: 120px;width: 180px;">
              </div>
              <div class="item-container">
                <div class="item-title" style="cursor: pointer" @click="$router.push('/front/articleDetail?id='+item.id)">
                  <span style="font-size: 20px;font-weight: bolder;">{{item.name}}</span>
                </div>
                <div class="item-content">
                  <p>
                    {{item.content}}
                  </p>
                </div>
                <div class="item-info">
                  <span>发布时间:{{item.createDate}}</span>


                </div>
              </div>
            </div>
            <el-divider />
          </div>

          <div style="padding: 10px 0;text-align: center;margin-top: 10px;">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]"
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
  name: "",
  data() {
    return {
      items:[],
      name:'',
      pageNum:1,
      pageSize:10,
      total:0,
      imgs: [
        "https://demosc.chinaz.net/Files/DownLoad/webjs1/201907/jiaoben6919/img/lunbo1.jpg",
        "https://pic2.zhimg.com/v2-d0e775ca666f3f2b049de8a88fc4aba5_r.jpg",
        "https://ts1.cn.mm.bing.net/th/id/R-C.bd3b631bde86bfdd75720dd544e3d32f?rik=IxwYcffHn4f3tQ&riu=http%3a%2f%2fimg.zcool.cn%2fcommunity%2f01f6625768ad220000012e7e78a909.jpg&ehk=TjQyehtL0i1DZVMZkrwUyBShDQclVX2ACNRZTTDKbzk%3d&risl=&pid=ImgRaw&r=0",
      ],
    };
  },
  created(){
    this.load()
  },
  methods:{
    load(){
      this.request.get("/article/page",{
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
    .item{
      height: 150px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .item-img{
        height: 120px;
        width: 180px;
        flex: 1;
      }
      .item-container{
        flex: 5;
        margin-left: 10px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 120px;
        .item-title{
          height: 20px;
        }
        .item-content{
          height: 60px;
          line-height: 1.6;
          word-break: break-all;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2; /* 这里是超出几行省略 */
          overflow: hidden;
        }
        .item-info{
          margin-top: 5px;
          height: 20px;
        }
      }
    }
  }
}
</style>
