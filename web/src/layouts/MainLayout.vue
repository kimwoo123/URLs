<template>
  <q-layout view="hHh Lpr lff">
    <q-header bordered class="bg-white text-black">
      <q-toolbar>
        <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
        <q-toolbar-title>
          율스
        </q-toolbar-title>
        
        <q-btn flat round dense icon="fas fa-bell" class="q-mr-md">
          <q-badge floating color="red">2</q-badge>
        </q-btn>

        <q-btn flat no-caps class="q-mx-xs">
          <q-avatar size="36px">
            <img src="https://cdn.quasar.dev/img/avatar2.jpg">
          </q-avatar>
          <div>{{ username }}</div>
        </q-btn>

        <logout-button/>

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

              <router-link to="/user">
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

              <router-link to="/user/allurls">
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
import { defineComponent, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import LogoutButton from 'components/buttons/LogoutButton.vue'

const menuList = [
  {
    icon: 'inbox',
    label: '추천 URLs',
    link: '/',
    separator: true
  },
  {
    icon: 'home',
    label: '모든 URLs',
    link: '/allurls',
    separator: false
  },
]

export default defineComponent({
  name: 'MainLayout',

  components: {LogoutButton},

  setup () {
    const $store = useStore()
    // const $router = useRouter()
    const username = $store.state.user.username

    return {
      drawer: ref(false),
      menuList,
      username
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