<template>
  <el-footer class="me-area">
    <div class="me-footer">
      <strong>
        <router-link to="/" class="me-login-design-color">富强  民主  文明  和谐  自由  平等  公正  法治  爱国  敬业  诚信  友善</router-link>
      </strong>
    </div>
  </el-footer>

</template>

<script>

  export default {
    name: 'BaseFooter',
    data() {
      return {}
    },
    methods: {},
    mounted() {
    }
  }
</script>

<style>

  .el-footer {
    min-width: 100%;
    box-shadow: 0 -2px 2px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 1024;
  }

  .me-footer {
    text-align: center;
    line-height: 60px;
    font-family: 'Open Sans', sans-serif;
    font-size: 16px;

  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

</style>
