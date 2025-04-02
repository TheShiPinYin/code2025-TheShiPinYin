<template>
  <div class="container">
    <div class="left-section" style="margin-top: 10px; width: 65%;">
      <div style="margin-bottom: 20px">
        <el-carousel trigger="click" height="350px" style="">
          <el-carousel-item v-for="item in data.carouselData" :key="item">
            <img :src="item" alt="" style="height: 350px; width: 100%">
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="card" style="line-height: 30px; border: 1px solid #e4e7ed; border-radius: 4px; padding: 15px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.04);">
        <div>欢迎使用超市管理系统</div>
      </div>
    </div>
    <div class="right-section card" style="margin-top: 10px; width: 35%; border: 1px solid #e4e7ed; border-radius: 4px; padding: 15px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.04);">
      <div style="font-size: 18px; margin-bottom: 20px">系统公告</div>
      <el-timeline style="max-width: 100%;">
        <el-timeline-item :timestamp="item.time" color="#0bbd87" placement="top" v-for="item in data.noticeData">
          <h4>{{ item.title }}</h4>
          <p>{{ item.content }}</p>
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import lun1 from '@/assets/imgs/lun-1.jpg';
import lun2 from '@/assets/imgs/lun-2.jpg';
import lun3 from '@/assets/imgs/lun-3.jpg';

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || '{}'),
  noticeData: [],
  carouselData: [lun1, lun2, lun3]
})

const loadNotice = () => {
  request.get('/notice/selectAll').then(res => {
    if (res.code === '200') {
      data.noticeData = res.data
      if (data.noticeData.length > 5) {
        data.noticeData = data.noticeData.slice(0, 5)
      }
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadNotice()
</script>

<style scoped>
.container {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}
</style>

