<template>
  <div :class="`${themes[themeStatus]}`">
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入搜索内容" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" plain @click="load">搜索</el-button>
      <el-button type="info" plain @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-upload :action="serverHost+'/user/import'" :show-file-list="false"
                 :on-success="handleFileUploadSuccess" style="display: inline-block">
        <el-button type="primary" plain class="ml-5">上传文件 <i class="el-icon-top"></i></el-button>
      </el-upload>
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

    </div>
    <el-table :data="tableData" stripe @selection-change="handleSelectionChange" :header-cell-style="{'padding-left':'4px','padding-right':'4px'}">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="文件名称"></el-table-column>
      <el-table-column prop="type" label="文件类型"></el-table-column>
      <el-table-column prop="size" label="文件大小(kb)"></el-table-column>
      <el-table-column label="下载">
        <template v-slot:="scope">
          <el-button plain type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
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

  </div>
</template>

<script>

import {serverHost} from '../../config/config.default'

export default {
  name: "File",
  props: {
    themeStatus : Number,
  },
  data() {
    return {
      serverHost : serverHost,
      themes:['theme1','theme2','theme3','theme4','theme5','theme6','theme7','theme8'],
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      total: 0
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/file/page", {
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
    del(id) {
      this.request.delete("/file/" + id).then(res => {
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
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/file/del/batch", ids).then(res => {
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
    handleFileUploadSuccess() {
      this.$message.success("上传成功")
      this.load()
    },
    download(url) {
      window.open(url)
    },
  }
}
</script>

<style scoped>

.el-button:focus,
.el-button:hover {
  background: var(--font-color-primary);
  border-color: var(--font-color-primary);
  border-color: var(--back-color-primary);
  color: #fff;
}

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
  color: #d63031;
}

</style>
