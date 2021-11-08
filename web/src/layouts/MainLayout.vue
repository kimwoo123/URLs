<template>
  <q-layout view="hHh Lpr lff">
    <q-header bordered class="bg-white text-black">
      <q-toolbar>
        <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
        <q-toolbar-title>
          율스
        </q-toolbar-title>

        <q-input rounded outlined v-model="text">
          <template v-slot:append>
            <q-avatar>
              <img src="https://cdn.quasar.dev/logo-v2/svg/logo.svg">
            </q-avatar>
          </template>
        </q-input>
        
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

    <q-drawer
        v-model="drawer"
        show-if-above
        :width="200"
        :breakpoint="500"
        bordered
        class="bg-grey-3"
      >
        <q-scroll-area class="fit">
          <q-list>

              <router-link :to="{ name: 'Recommendation', params: {id: `${userid}`}}">
                <q-item clickable v-ripple>
                  <q-item-section avatar>
                    <q-icon name="inbox" />
                  </q-item-section>
                  <q-item-section>
                    추천 URLs
                  </q-item-section>
                </q-item>
              </router-link>

              <q-separator/>

              <router-link :to="{ name: 'AllUrls', params: {id: `${userid}`}}">
                <q-item clickable v-ripple>
                  <q-item-section avatar>
                    <q-icon name="home" />
                  </q-item-section>
                  <q-item-section>
                    모든 URLs
                  </q-item-section>
                </q-item>
              </router-link>

          </q-list>
        </q-scroll-area>
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

export default defineComponent({
  name: 'MainLayout',
  setup () {
    const $store = useStore()
    const $router = useRouter()
    const username = $store.state.user.username
    const userid = $store.state.user.userid
    
    const goToSettings = () => {
      $router.push({ name: 'Settings', params: { id: userid }})
    }

    urls.folderMe()
      .then(async(result) => {
        console.log(result)
      })

    return {
      drawer: ref(false),
      text: ref(''),
      username,
      userid,
      goToSettings,
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