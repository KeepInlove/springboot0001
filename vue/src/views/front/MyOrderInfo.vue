<template>
  <div :class="`${themes[themeStatus]}`">
    <el-table :data="tableData" stripe @selection-change="handleSelectionChange" :header-cell-style="{'padding-left':'4px','padding-right':'4px'}">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="id" label="订单号"></el-table-column>
      <el-table-column prop="totalNum" label="订单数量"></el-table-column>
      <el-table-column prop="totalAmount" label="订单金额"></el-table-column>
      <el-table-column label="订单详情"><template v-slot="scope"><el-button type="primary" @click="goodsInfoViews(scope.row.id)">查看</el-button></template></el-table-column>
      <el-table-column label="收获地址"><template v-slot="scope"><el-button type="primary" @click="addressViews(scope.row.addressId)">查看</el-button></template></el-table-column>
      <el-table-column prop="checkStatus" label="订单状态">
        <template slot-scope="scope">
          {{ getStatusName(scope.row.checkStatus) }}
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="下单时间"></el-table-column>
      <el-table-column prop="expressNo" label="物流单号"></el-table-column>
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

    <el-dialog title="填写发货信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
      <el-form label-width="140px" size="small" style="width: 85%;" :model="form" :rules="rules" ref="ruleForm">
        <el-form-item  label="物流单号">
          <el-input v-model="form.expressNo" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="商品信息" :visible.sync="dialogGoodsInfo" width="60%" :close-on-click-modal="false">
      <el-table :data="orderInfoData" :header-cell-style="{'padding-left':'4px','padding-right':'4px'}">
        <el-table-column prop="goodsName" label="商品名称"></el-table-column>
        <el-table-column label="商品图照"><template v-slot="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image></template></el-table-column>
        <el-table-column prop="num" label="购买数量"></el-table-column>
        <el-table-column prop="price" label="商品价格"></el-table-column>
      </el-table>
    </el-dialog>
    <el-dialog title="收货信息" :visible.sync="dialogAddress" width="60%" :close-on-click-modal="false">
      <el-table
          :data="addressData"
          style="width: 100%">
        <el-table-column
            prop="addressName"
            label="收货人姓名">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="收货人电话">
        </el-table-column>
        <el-table-column
            prop="address"
            label="收货地址"
            :formatter="formatter">
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>

import {serverHost} from "../../../config/config.default";
export default {
  name: "myOrderInfo",
  props: {
    themeStatus : Number,
  },
  data() {
    return {
      serverHost : serverHost,
      themes:['theme1','theme2','theme3','theme4','theme5','theme6','theme7','theme8'],
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur'}
        ],
      },
      dialogContentVisible: false,
      dialogGoodsInfo: false,
      dialogAddress: false,
      orderInfoData:[],
      addressData:[]
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/orderInfo/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId:this.user.id
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    getStatusName(status) {
      switch (status) {
        case 1:
          return '待支付';
        case 2:
          return '支付成功';
        case 3:
          return '已发货';
        case 4:
          return '订单完成';
        default:
          return '未知状态';
      }
    },
    save() {
      this.request.post("/orderInfo/send", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("发货成功")
          this.dialogFormVisible=false
          this.load()
        } else {
          this.$message.error("发货失败")
        }
      })
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
    handleEdit(row) {
      this.form.id=row.id
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
    del(id) {
      this.request.delete("/goods/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },

    handleImgUploadSuccess(res) {
      this.form.img = res
    },
    addressViews(id){
      this.dialogAddress = true
      this.$nextTick(() => {
        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
      this.addressData=[]
      this.request.get("/address/"+id).then(res => {
        this.addressData.push(res.data)
      })

    },
    goodsInfoViews(id){
      this.dialogGoodsInfo = true
      this.$nextTick(() => {
        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
      this.request.get("/orderItem/page", {
        params: {
          orderInfoId: id
        }
      }).then(res => {
        this.orderInfoData = res.data
      })
    },

  }
}
</script>


<style scoped>

.el-button--primary{
  color: var(--font-color-primary);
  background-color: var(--back-color-primary);
  border-color: var(--back-color-primary);
}

.el-button--primary:focus,
.el-button--primary:hover {
  background: var(--font-color-primary);
  border-color: var(--font-color-primary);
  border-color: var(--back-color-primary);
  color: #fff;
}

.el-button--danger{
  color: rgb(214, 48, 49);
  background-color: rgb(214, 48, 49,0.1);
  border-color: rgb(214, 48, 49,0.1);
}

.el-button--danger:focus,
.el-button--danger:hover {
  background: rgb(214, 48, 49);
  border-color: rgb(214, 48, 49);
  border-color: rgb(214, 48, 49,0.1);
  color: #fff;
}

.table-row-options-warp span {
  cursor: pointer;
  color: var(--font-color-primary)
}
.table-row-options-warp span:last-child {
  cursor: pointer;
  color: #d63031;
}

</style>
