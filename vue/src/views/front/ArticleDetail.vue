<template>
  <div style="color: #666;width: 70%;margin: 0 auto">
    <div style="margin: 20px 0; ">
      <el-button type="info" plain @click="$router.go(-1)" size="small" style="margin-left: 0px">返回</el-button>

      <div style="text-align: center"> <h1>{{item.name}}</h1></div>

      <div style="font-size: 16px;margin-top: 5px; display: flex;justify-content: space-around;">
          <span>发布时间:{{item.createDate}}</span>
      </div>


    </div>

    <div style="margin: 20px 0">
      <mavon-editor
          class="md"
          :value="item.content"
          :subfield="false"
          :defaultOpen="'preview'"
          :toolbarsFlag="false"
          :editable="false"
          :scrollStyle="true"
          :ishljs="true"
      />
    </div>

  </div>
</template>

<script>

export default {
  name: "",
  data() {
    return {
      item: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      id: this.$route.query.id,
      commentItemId: this.$route.query.id,

    }
  },
  created() {
    this.load()

  },
  methods: {
    load() {
      this.request.get("/article/" + this.id).then(res => {
        this.item = res.data
      })
    },

  }
}
</script>

<style scoped>
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
