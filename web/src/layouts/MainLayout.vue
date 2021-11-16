<template>
  <q-layout view="hHh lpR fFf">
    <q-header bordered class="bg-white text-black">
      <q-toolbar class="q-py-sm">
        <!-- <q-btn flat @click="drawer = !drawer" round dense icon="menu" /> -->
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title>
          율스
        </q-toolbar-title>

        <!-- <q-input rounded outlined v-model="text">
          <template v-slot:append>
            <q-avatar>
              <img src="https://cdn.quasar.dev/logo-v2/svg/logo.svg">
            </q-avatar>
          </template>
        </q-input> -->
        
        <q-btn flat round dense icon="fas fa-bell" class="q-mr-md">
          <q-badge floating color="red">2</q-badge>
        </q-btn>

        <q-btn 
          flat 
          no-caps 
          class="q-mx-xs"
          @click="goToSettings"
        >
          <q-avatar size="36px">
            <img :src="avatarUrl">
          </q-avatar>
          <div>{{ username }}</div>
        </q-btn>
      </q-toolbar>
    </q-header>

    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" bordered :width="250" :breakpoint="900">
      <left-drawer/>
    </q-drawer>

    <q-drawer v-model="rightDrawerOpen" side="right" bordered :width="300" :breakpoint="500">
      <right-drawer/>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { 
  defineComponent, 
  ref,
  watch,
  computed,
  onMounted
} from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import LeftDrawer from 'src/components/drawer/LeftDrawer.vue'
import RightDrawer from 'src/components/drawer/RightDrawer.vue'

export default defineComponent({
  components: { LeftDrawer, RightDrawer },
  name: 'MainLayout',
  setup () {
    const $store = useStore()
    const $router = useRouter()

    const isUrlMemoOpen = computed({
      get: () => $store.getters['urls/isUrlMemoOpen']
    })

    const username = $store.state.user.username
    const userid = $store.state.user.userid
    const avatarUrl = $store.state.user.avatar

    const goToSettings = () => {
      $router.push({ name: 'Settings', params: { id: userid }})
    }

    const leftDrawerOpen = ref(true)
    const rightDrawerOpen = ref(false)
    
    watch(isUrlMemoOpen, () => {
      if (isUrlMemoOpen.value === false) {
        rightDrawerOpen.value = false
      } else {
        rightDrawerOpen.value = true
      }
    })

    watch(rightDrawerOpen, () => {
      if (rightDrawerOpen.value === true) {
        $store.dispatch('urls/OPEN_MEMO')
      } else {
        $store.dispatch('urls/CLOSE_MEMO')
      }
    })

    return {
      // drawer,
      text: ref(''),
      username,
      userid,
      avatarUrl,

      goToSettings,
      // folders
      
      leftDrawerOpen,
      toggleLeftDrawer () {
        leftDrawerOpen.value = !leftDrawerOpen.value
      },

      rightDrawerOpen,
    }
  }
})
</script>

<style lang="scss">
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Apple SD Gothic Neo', Pretendard, Roboto, 'Noto Sans KR', 'Segoe UI', 'Malgun Gothic', sans-serif;
}

a:link, a:hover, a:active, a:visited {
  text-decoration: None;
  color: gray
}
</style>