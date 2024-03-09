<template>
  <div style=" margin: 10px 0;  width: 80%;margin: 0 auto;margin-top: 20px">

    <div style="font-size: 28px; margin-bottom: 20px;margin-top: 20px;display: inline-block"><span style="margin-left: 1px;">商品购物车</span>

    </div>


    <div style="border-bottom:1px solid #CCC"></div>

    <el-table :data="tableData" stripe style="border-radius: 10px;margin-top: 30px;line-height: 30px"
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="goodsName" label="商品名称"></el-table-column>
      <el-table-column label="商品图片"><template v-slot="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image></template></el-table-column>
      <el-table-column prop="price" label="价格"></el-table-column>
      <el-table-column prop="num" label="数量">
      </el-table-column>
      <el-table-column label="小计价格" align="center">
        <template slot-scope="scope">
          {{ scope.row.price * scope.row.num }}
        </template>
      </el-table-column>

      <el-table-column label="管理"  width="80" align="center">
        <template slot-scope="scope">
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删 除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
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
    <div style="margin-top: 20px; display: flex; justify-content: flex-end; align-items: center;">
      <div style="margin-right: 20px;">
        <span style="font-weight: bold; ">地址: </span>
          <el-select v-model="addressId" placeholder="请选择地址">
              <el-option
                  v-for="item in address"
                  :key="item.id"
                  :label="item.address"
                  :value="item.id">
              </el-option>
          </el-select>
      </div>
      <div style="margin-right: 20px;">
        <span style="font-weight: bold; ">总价格: </span>
        <span style="color: #d63031">{{ totalAmount }}</span>
      </div>
      <div>
        <el-button type="primary"  @click="save()">下单</el-button>
      </div>
    </div>

  </div>
</template>

<script>




import {serverHost} from "../../../config/config.default";

export default {
  name: "Cart",
  data() {
    return {
      tableData: [],
      serverHost : serverHost,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      total: 0,
      rate: 0,
      comment: '',
      pageNum: 0,
      pageSize: 10,
      totalAmount:0,
      name: "",
      dialogQrVisible: true,
      dialogFormVisible:false,
      dialogFormVisible1: false,
      form: {},
      orderInfo:[],
      cartIds:[],
      address:[],
      addressId:null
    };
  },

  created() {
    this.load();
  },
  methods: {
    load() {
      this.request.get("/cart/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.id,
        }
      }).then(res=>{
        this.tableData=res.data.records
        this.total=res.data.total
      })

      this.request.get("/address/findUserAddress/"+this.user.id).then(res=>{
        this.address=res.data
        this.total=res.data.total
      })
      console.log(this.address)

    },
    save() {
      let cartIds = this.orderInfo.map(item => item.id).join(',');
      console.log(cartIds);
      if (this.addressId==null){
        this.$message.error("请选择收货地址");
        return
      }
      this.request.get("/orderInfo/userSave",{
        params:{
          cartIds: cartIds,
          userId: this.user.id,
          addressId:this.addressId
        }
      }).then(res => {
        if (res.code === '200') {
          this.$message.success("下单成功")
          // 跳转到支付页面，并传递支付总金额作为参数
          this.$router.push({
            name: 'Pay',
            params: {
              totalAmount: this.totalAmount,
              orderId:res.data.id
            }
          })
        } else {
          this.$message.error("下单失败")
        }
      })
    },
    handleRate(row) {
      this.form = JSON.parse(JSON.stringify(row))

      this.dialogFormVisible = true
      this.$nextTick(() => {
        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
    },
    reset() {
      this.name = ""
      this.load()
    },
    handleSelectionChange(val) {
      console.log(val)
      this.orderInfo = val;
      // 初始化总价格为0
      this.totalAmount = 0;
      for (let i = 0; i < this.orderInfo.length; i++) {
        const item = this.orderInfo[i];
        this.totalAmount += item.price * item.num;
      }
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    download(url) {
      window.open(url)
    },
    del(id) {
      this.request.delete("/cart/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
  },

}
</script>

<style>

</style>
