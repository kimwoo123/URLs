<template>
  <q-layout view="hHh lpR fFf">
    <q-header bordered class="bg-white text-black">
      <q-toolbar>
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
            <img src="https://cdn.quasar.dev/img/avatar2.jpg">
          </q-avatar>
          <div>{{ username }}</div>
        </q-btn>
      </q-toolbar>
    </q-header>

    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" bordered :width="200" :breakpoint="500">
      <left-drawer/>
    </q-drawer>

    <q-drawer show-if-above v-model="rightDrawerOpen" side="right" bordered :width="200" :breakpoint="500">
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
} from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { urls } from 'src/api/index'
import AutoComplete from '../components/AutoComplete.vue'
import LeftDrawer from 'src/components/drawer/LeftDrawer.vue'
import RightDrawer from 'src/components/drawer/RightDrawer.vue'

export default defineComponent({
  components: { AutoComplete, LeftDrawer, RightDrawer },
  name: 'MainLayout',
  setup () {
    const $store = useStore()
    const $router = useRouter()

    // $store.dispatch('urls/GET_FOLDER')
    // const folders = computed({
    //   get: () => $store.state.urls.folders
    //  })

    const username = $store.state.user.username
    const userid = $store.state.user.userid
    const goToSettings = () => {
      $router.push({ name: 'Settings', params: { id: userid }})
    }

    // const drawer = ref(false)
    const leftDrawerOpen = ref(false)
    const rightDrawerOpen = ref(false)

    return {
      // drawer,
      text: ref(''),
      username,
      userid,
      goToSettings,
      // folders
      
      leftDrawerOpen,
      toggleLeftDrawer () {
        leftDrawerOpen.value = !leftDrawerOpen.value
      },

      rightDrawerOpen,
      toggleRightDrawer () {
        rightDrawerOpen.value = !rightDrawerOpen.value
      }
    }
  }
})
</script>

<style lang="scss">
a:link, a:hover, a:active, a:visited {
  text-decoration: None;
  color: gray
}
</style>