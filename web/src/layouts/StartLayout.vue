<template>
  <q-layout view="hHh lpR fFf">
    <q-header bordered class="bg-white text-black" id="navbar">
    <q-toolbar style="padding: 0 35px 0 44px;">
      <q-toolbar-title>
        <img src="../assets/logo/logo_primary.png" alt="" class="navbar-logo">
      </q-toolbar-title>
      <google-login-button />
    </q-toolbar>
    </q-header>
    <div>
      <router-view />
    </div>
  </q-layout>
</template>

<script>
import GoogleLoginButton from '../components/buttons/GoogleLoginButton.vue'

export default {
  components: { GoogleLoginButton },
  setup() {
    function scrollF() {
      if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
        document.getElementById("navbar").style.top = "0"
      } else {
        document.getElementById("navbar").style.top = "-50px"
      }
    }
    window.addEventListener("scroll", scrollF);
  }
}
</script>

<style scoped lang="scss">
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Apple SD Gothic Neo', Pretendard, Roboto, 'Noto Sans KR', 'Segoe UI', 'Malgun Gothic', sans-serif;
}
#navbar {
  position: fixed;
  top: -50px;
  transition: top 0.5s;
}
.navbar-logo {
  height: 16px;
  widows: 100%;
}
</style>