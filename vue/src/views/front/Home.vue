<template>
  <div class="home-container">
    <el-card style="margin-top: 10px;">
      <div class="type-container" >
        <div class="type-left">
          <span style="color: orange;">宠物分类</span>
          <div style="border-right: 1px solid grey;margin-left: 10px;height: 20px;"></div>
          <span style="cursor: pointer;margin-left: 10px" :style="{color:(cateId===0 ? 'blueviolet':'black')}" @click="changeType(0)">全部</span>
        </div>
        <div class="type-item" v-for="item in types">
          <span style="cursor: pointer;" :style="{color:(cateId===item.id ? 'blueviolet':'black')}" @click="changeType(item.id)">{{item.name}}</span>
        </div>
        <div class="type-search" >
          <el-input v-model="name" placeholder="请输入内容"></el-input>
          <el-button style="margin-left: 10px;" @click="load">搜索</el-button>
          <el-button style="margin-left: 10px;" type="warning" @click="reset">重置</el-button>
        </div>
      </div>
    </el-card>

    <div class="item-center">
      <el-row :gutter="24">
        <el-col :span="17">

          <el-row :gutter="10">
            <el-col :span="5" v-for="item in items" :key="item.id" style="margin-bottom: 10px">
              <div style="border: 1px solid #ccc; padding-bottom: 10px; border-radius: 10px; overflow: hidden; cursor: pointer" @click="$router.push('/front/detail?id=' + item.id)">
                <img :src="item.img" style="width: 100%; height: 200px; overflow: hidden; border-radius: 10px">
                <div style="color: #000; margin: 10px" class="info">宠物名称:{{ item.name }}</div>
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

          <el-card style="width: 100%;  margin: 10px 0">
            <h2 style="margin: 20px 0">公告列表</h2>
            <div >
              <el-timeline  reverse slot="reference">
                <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.time">
                  <el-popover
                      placement="right"
                      width="200"
                      trigger="hover"
                      :content="item.info">
                    <span slot="reference">{{ item.name }}</span>
                  </el-popover>
                </el-timeline-item>
              </el-timeline>
            </div>
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
      nickname:'',
      pageNum:1,
      pageSize:8,
      types:[],
      total:0,
      cateId:0,
      typeId:0,
      notices:[],
      name:"",
      imgs: [
        "https://demosc.chinaz.net/Files/DownLoad/webjs1/201907/jiaoben6919/img/lunbo1.jpg",
        "https://pic2.zhimg.com/v2-d0e775ca666f3f2b049de8a88fc4aba5_r.jpg",
        "https://ts1.cn.mm.bing.net/th/id/R-C.bd3b631bde86bfdd75720dd544e3d32f?rik=IxwYcffHn4f3tQ&riu=http%3a%2f%2fimg.zcool.cn%2fcommunity%2f01f6625768ad220000012e7e78a909.jpg&ehk=TjQyehtL0i1DZVMZkrwUyBShDQclVX2ACNRZTTDKbzk%3d&risl=&pid=ImgRaw&r=0",
      ],
    };
  },
  created(){
    this.load()
    this.loadType()
  },
  methods:{
    load(){
      this.request.get("/pets/front/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          nickname:this.nickname,
          cateId:this.cateId,
        }
      }).then(res=>{
        if(res.code==='200'){
          this.items=res.data?.records || []
          this.total=res.data?.total || 0
        }
      })
      this.request.get('/notices/' ).then(res => {
        this.notices = res.data
      })
    },
    changeType(id) {
      this.cateId = id
      this.load()
    },
    loadType(){
      this.request.get("/category").then(res=>{
        this.types=res.data
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
      this.nickname=''
      this.load()
    }
  }
};
</script>

<style lang="scss" scoped>

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
  .type-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    .type-left{
      flex: 1;
      display: flex;
      align-items: center;
    }
    .type-item{
      flex: 1;
      text-align: center;
    }
    .type-search{
      display: flex;
    }
  }
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
