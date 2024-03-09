<template>
  <div :class="`${themes[themeStatus]}`">
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入搜索内容" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" plain @click="load">搜索</el-button>
      <el-button type="info" plain @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" plain @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" plain slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
<!--      <el-upload :action="serverHost+'/article/import'" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">-->
<!--        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--      </el-upload>-->
<!--      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>-->
    </div>

    <el-table :data="tableData" stripe @selection-change="handleSelectionChange" :header-cell-style="{'padding-left':'4px','padding-right':'4px'}">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
      <el-table-column prop="name" label="资讯标题"></el-table-column>
      <el-table-column label="资讯封面"><template v-slot="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image></template></el-table-column>
      <el-table-column label="资讯详情"><template v-slot="scope"><el-button type="primary" @click="views(scope.row.content)">查看</el-button></template></el-table-column>
      <el-table-column prop="createDate" label="发布时间"></el-table-column>

      <el-table-column label="操作"  width="180" align="center">
        <template v-slot:="scope">
          <div class="table-row-options-warp">
            <span @click="handleEdit(scope.row)">编辑 </span>
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

    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%" :close-on-click-modal="false">
      <el-form label-width="140px" size="small" style="width: 85%;" :model="form" :rules="rules" ref="ruleForm">
        <el-form-item prop="name" label="资讯标题">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="img" label="资讯封面">
          <el-upload :action="serverHost+'/file/upload'" ref="img" :on-success="handleImgUploadSuccess">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="content" label="资讯详情">
          <mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>
        </el-form-item>
        <el-form-item prop="createDate" label="发布时间">
          <el-date-picker v-model="form.createDate" type="date" value-format="yyyy-MM-dd" placeholder="选择日期"></el-date-picker>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="富文本" :visible.sync="dialogContentVisible" width="70%" :close-on-click-modal="false">
      <mavon-editor
        class="md"
        :subfield="false"
        :value="this.content"
        :defaultOpen="'preview'"
        :toolbarsFlag="false"
        :editable="false"
        :scrollStyle="true"
        :ishljs="true"
      />
    </el-dialog>


  </div>
</template>

<script>

import {serverHost} from '../../config/config.default'

import axios from "axios"


export default {
  name: "Article",
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
      content: '',

    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/article/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.request.post("/article", this.form).then(res => {
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
    del(id) {
      this.request.delete("/article/" + id).then(res => {
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
    delBatch() {
      if (!this.multipleSelection.length) {
        this.$message.error("请选择需要删除的数据")
        return
      }
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/article/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
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
    exp() {
      window.open(this.serverHost+"/article/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
    imgAdd(pos, $file) {
      let $vm = this.$refs.md
      const formData = new FormData()
      formData.append('file', $file)
      axios({
        url: this.serverHost+'/file/upload',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then((res) => {
        $vm.$img2Url(pos, res.data)
      })
    },
    views(content){
      this.content = content
      this.dialogContentVisible = true
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
