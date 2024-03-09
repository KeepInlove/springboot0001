<template>
  <div style=" margin: 10px 0;  width: 80%;margin: 0 auto;margin-top: 20px">

    <div style="font-size: 28px; margin-bottom: 20px;margin-top: 20px;display: inline-block"><span style="margin-left: 1px;">收货地址</span> </div><br>

    <el-button type="primary" @click="addAddress()">新增</el-button>
    <el-table
        ref="filterTable"
        :data="tableData"
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
      <el-table-column label="操作"  width="180" align="center">
        <template v-slot:="scope">
          <div class="table-row-options-warp">
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='取消'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除这条数据吗？"
                @confirm="del(scope.row.id)"
            >
              <span slot="reference">删除</span>
            </el-popconfirm>
          </div>
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

    <el-dialog title="新增地址" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
      <el-form label-width="140px" size="small" style="width: 85%;" :model="form" :rules="rules" ref="ruleForm">
        <el-form-item prop="addressName" label="收货人姓名">
          <el-input v-model="form.addressName" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item prop="phone" label="收货人手机号">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item prop="address" label="收货人地址详情">
          <el-input v-model="form.address" autocomplete="off"></el-input>
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
export default {
  name: "Password",
  data() {
    return {
      form: {},
      dialogFormVisible: false,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/address/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.user.id,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

    },
    addAddress() {
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
    save() {
      this.form.userId=this.user.id
      this.request.post("/address", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    del(id) {
      this.request.delete("/address/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
  }
}
</script>

<style>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>
