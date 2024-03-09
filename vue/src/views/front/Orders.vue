<template>
  <div style=" margin: 10px 0;  width: 80%;margin: 0 auto;margin-top: 20px">

    <div style="font-size: 28px; margin-bottom: 20px;margin-top: 20px;display: inline-block"><span style="margin-left: 1px;">预约服务</span> </div>


    <div style="border-bottom:1px solid #CCC"></div>

    <el-table :data="tableData" stripe style="border-radius: 10px;margin-top: 30px;line-height: 30px">

      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="petsName" label="宠物名称"></el-table-column>
      <el-table-column label="宠物照片"><template v-slot="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image></template></el-table-column>
      <el-table-column prop="startTime" label="预约时间"></el-table-column>
      <el-table-column prop="employ" label="服务"></el-table-column>
      <el-table-column prop="checkStatus" label="订单状态">
        <template slot-scope="scope">
          {{ getStatusName(scope.row.checkStatus) }}
        </template>
      </el-table-column>
      <el-table-column  label="评分"  width="350" align="center">
        <template slot-scope="scope">
        <el-button @click="handleRate(scope.row)" :disabled="scope.row.checkStatus!=2">评价</el-button>
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


    <el-dialog title="打分" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
      <el-form label-width="140px" size="small" style="width: 85%;" :model="form" ref="ruleForm">
        <el-form-item prop="rate" label="服务评价">
          <template slot-scope="scope">
            <el-rate v-model="form.rate">本次评分：</el-rate>
          </template>
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




import {serverHost} from "../../../config/config.default";

export default {
  name: "",
  data() {
    return {
      tableData: [],
      serverHost : serverHost,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      total: 0,
      rate: 0,
      comment: '',
      pageNum: 1,
      pageSize: 10,
      name: "",
      dialogQrVisible: true,
      dialogFormVisible:false,
      dialogFormVisible1: false,
      form: {},
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur'}
        ],
      },
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request.get("/orders/my/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res=>{
        this.tableData=res.data.records
        this.total=res.data.total
      })
    },
    getStatusName(status) {
      switch (status) {
        case 1:
          return '已预约';
        case 2:
          return '已接单';
        case 3:
          return '订单完成';
      }
    },
    save() {
      const rate = this.form.rate;
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
            this.form.rate = rate;
            if (rate !== null) {
              this.form.checkStatus= 3;
            }
          this.request.post("/orders", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error(res.msg)
            }
          })
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
      this.request.delete("/orders/" + id).then(res => {
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
