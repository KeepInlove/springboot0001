<template>
  <div style="margin: 10px 0;  width: 60%;margin: 0 auto;margin-top: 20px">
    <el-card style="">
      <el-button style="margin-bottom: 10px;float: right" type="info" @click="$router.go(-1)">返回</el-button>

      <div style="display: flex">
        <div style="display: flex;flex-direction:column">
          <img :src=item.img alt="" style="width: 100%; height: 350px">
        </div>
        <div style="flex: 1; margin-left: 10px">
          <el-form label-width="80px" style="font-size: 20px">
            <el-form-item label="商品名称">{{ item.goodsName }}</el-form-item>
            <el-form-item label="价格">{{ item.price }}元</el-form-item>
            <el-form-item label="剩余库存">{{ item.stockNum}}</el-form-item>
            <el-form-item label="已售出">{{ item.stockOutNum }}</el-form-item>
          </el-form>
          <div style="display: flex;justify-content: space-around">
            <el-input-number v-model="item.num"   :min="0" :max="10"  label="描述文字"></el-input-number>
            <el-button type="danger" @click="addToCart(item)"> 加入购物车 </el-button>
          </div>
        </div>

      </div>
    </el-card>

    <el-tabs type="border-card">
      <el-tab-pane label="详细信息" >
        <mavon-editor
            class="md"
            :subfield="false"
            :value="item.des"
            :defaultOpen="'preview'"
            :toolbarsFlag="false"
            :editable="false"
            :scrollStyle="true"
            :ishljs="true"
        />
      </el-tab-pane>

      <el-tab-pane label="留言" >


        <div style="margin: 30px 0">
          <div style="margin: 10px 0">
            <div style="border-bottom: 2px solid #ccc; padding: 10px 0; font-size: 20px; margin-bottom:10px;" >留言({{comments.length}})</div>
            <div class="comment-send-warp">
              <el-input class="comment-send-input" size="small" type="textarea" rows="4" v-model="commentForm.content" placeholder="善语结善缘，恶语伤人心"></el-input>
              <el-button :disabled="!commentForm.content" class="btn-sent-comment" type="primary" size="medium" @click="save1" >留言</el-button>
            </div>
          </div>
          <div class="comment-warp">
            <div v-for="item in comments" :key="item.id">
              <div class="comment-item">
                <div class="comment-avatar">
                  <el-image :src="item.avatarUrl"></el-image>
                </div> <!--  头像-->
                <div class="comment-info">
                  <div class="comment-name">{{ item.nickname }}</div>
                  <div class="comment-content">{{ item.content }}</div>

                  <div class="comment-other">
                    <div class="comment-time">
                      <i class="el-icon-time"></i><span>{{ item.time }}</span>
                    </div>
                    <div>
                      <el-button type="text" @click="handleReply(item.id)">回复</el-button>
                      <el-button type="text" @click="del(item.id)" v-if="user.id === item.userId || user.role==='ROLE_ADMIN'">删除</el-button>
                    </div>
                  </div>
                </div>
              </div>
              <!--回复-->
              <template v-if="item.children.length">
                <div v-for="subItem in item.children" class="comment-item comment-item-reply">
                  <div class="comment-avatar">
                    <el-image :src="subItem.avatarUrl"/>
                  </div> <!--  头像-->
                  <div class="comment-info">
                    <div class="comment-name">{{ subItem.nickname }} 回复 @ {{subItem.pnickname}}</div>
                    <div class="comment-content">{{ subItem.content }}</div>

                    <div class="comment-other">
                      <div class="comment-time">
                        <i class="el-icon-time"></i><span>{{ subItem.time }}</span>
                      </div>
                      <div>
                        <el-button type="text" @click="handleReply(subItem.id)">回复</el-button>
                        <el-button type="text" @click="del(subItem.id)" v-if="user.id === subItem.userId || user.role==='ROLE_ADMIN'">删除</el-button>
                      </div>
                    </div>
                  </div>
                </div>
              </template>
            </div>
          </div>
        </div>

        <el-dialog title="回复" :visible.sync="dialogFormVisible2" width="50%" >
          <el-form label-width="80px" size="small">
            <el-form-item label="回复内容">
              <el-input type="textarea" v-model="commentForm.contentReply" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible2 = false" size="small">取 消</el-button>
            <el-button type="primary" @click="save1"  size="small">确 定</el-button>
          </div>
        </el-dialog>

      </el-tab-pane>

    </el-tabs>

    <el-dialog title="请填写预约信息" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false">
      <el-form label-width="auto" size="small" style="width: 100%" :model="form" :rules="rules" ref="ruleForm">
        <el-form-item prop="userName" label="真实姓名">
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="联系电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="startTime" label="预约时间">
          <el-date-picker v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        <el-form-item prop="employ" label="服务">
          <el-select v-model="form.employ" placeholder="请选择服务">
            <el-option label="喂养" value="喂养"></el-option>
            <el-option label="洗澡" value="洗澡"></el-option>
            <el-option label="遛" value="遛"></el-option>
          </el-select>
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
  name: 'Detail',
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      id: this.$route.query.id,
      commentItemId: this.$route.query.id,
      item: {},

      dialogFormVisible2: false,
      comments: [],
      commentForm: {},
      dialogFormVisible: false,
      form: {},
      cartFrom:{},
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'}
        ],
      },
    }
  },
  created() {
    this.load()
    this.loadComment()
  },
  methods: {
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
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.form.username = this.username;
          this.form.phone = this.form.phone;
          this.form.petsId = this.id;
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

    load() {
      this.request.get('/goods/' + this.id).then(res => {
        this.item = res.data
      })
    },
    addToCart(item){
      // 将对应商品加入购物车

      this.cartFrom.goodsId=item.id
      this.cartFrom.userId=this.user.id
      this.cartFrom.num=item.num
      this.request.post("/cart", this.cartFrom).then(res => {
        if (res.code === '200') {
          this.$message.success("加入购物车成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    handleReply(pid) {
      this.commentForm = { pid: pid }
      this.dialogFormVisible2 = true
    },
    loadComment() {
      this.request.get("/comment/tree/" + this.commentItemId).then(res => {
        this.comments = res.data
      })
    },
    save1() {
      if (!this.user.id) {
        this.$message.warning("请登录后操作")
        return
      }
      this.commentForm.itemId = this.commentItemId
      if (this.commentForm.contentReply) {
        this.commentForm.content = this.commentForm.contentReply
      }
      this.request.post("/comment", this.commentForm).then(res => {
        if (res.code === '200') {
          this.$message.success("评论成功")
          this.commentForm = {}  // 初始化评论对象内容
          this.loadComment()
          this.dialogFormVisible2 = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {
      this.request.delete("/comment/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.loadComment()
        } else {
          this.$message.error("删除失败")
        }
      })
    },

  }
}
</script>

<style>
.el-form-item__label {
  color: #000 !important;
  font-weight: bold !important;
}
.comment-send-warp {
  position: relative;
}
.comment-send-warp .btn-sent-comment {
  position: absolute;
  bottom: 35px;
  right: 30px
}
.comment-send-input{
  padding: 20px 10px;
  font-size: 16px;

}
.el-textarea__inner {
  padding: 15px ;
  font-family: '微软雅黑';
}
.comment-warp {
  padding: 0 10px;
}
.comment-item {
  display: flex;
  width: 100%;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
  margin-bottom: 20px;
}
.comment-item-reply {
  margin-left: 22px;
  margin-right: -30px;
  transform: scale(0.9);
  padding-left: 10px;
  border-radius: 4px;
}
.comment-item  .comment-avatar  {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 20px;
}

.comment-item  .comment-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.comment-info {
  flex: 1;
}
.comment-info .comment-name {
  color: #8c939d;
  font-size: 14px;
}
.comment-info .comment-content {
  color: #8c939d;
  font-size: 14px;
  margin-top: 4px;
  font-size: 16px;
  color: #666;
  text-align: justify;
}
.comment-info .comment-other {
  display: flex;
  /*justify-content: space-between;*/
  align-items: center;
  font-size: 14px;
  margin-top: 2px;
  color: #9499A0;
}
.comment-info .comment-other  .comment-time {
  margin-right: 20px;
  min-width: 170px;
}
.comment-info .comment-other  .comment-time i {
  margin-right: 4px;
}
.comment-info .comment-other .el-button {
  color: #9499A0;
}
</style>
