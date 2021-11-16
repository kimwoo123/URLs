<template>
  <q-layout view="hHh lpR fFf">

    <q-header bordered class="bg-white text-black">
    <q-toolbar>
      <q-toolbar-title>
        율스
      </q-toolbar-title>
      <google-login-button/>
    </q-toolbar>
    </q-header>
    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import GoogleLoginButton from '../components/buttons/GoogleLoginButton.vue'

export default {
  components: { GoogleLoginButton },
}

</script>

<style>
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Apple SD Gothic Neo', Pretendard, Roboto, 'Noto Sans KR', 'Segoe UI', 'Malgun Gothic', sans-serif;
}

</style>